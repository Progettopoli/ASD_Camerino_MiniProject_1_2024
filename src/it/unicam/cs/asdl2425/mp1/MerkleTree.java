package it.unicam.cs.asdl2425.mp1;

import java.util.*;

/**
 * Un Merkle Tree, noto anche come hash tree binario, è una struttura dati per
 * verificare in modo efficiente l'integrità e l'autenticità dei dati
 * all'interno di un set di dati più ampio. Viene costruito eseguendo l'hashing
 * ricorsivo di coppie di dati (valori hash crittografici) fino a ottenere un
 * singolo hash root. In questa implementazione la verifica di dati avviene
 * utilizzando hash MD5.
 * 
 * @author Luca Tesei, Marco Caputo (template) **INSERIRE NOME, COGNOME ED EMAIL
 *         xxxx@studenti.unicam.it DELLO STUDENTE** (implementazione)
 *
 * @param <T>
 *                il tipo di dati su cui l'albero è costruito.
 */
public class MerkleTree<T> {
    /**
     * Nodo radice dell'albero.
     */
    private final MerkleNode root;

    /**
     * Larghezza dell'albero, ovvero il numero di nodi nell'ultimo livello.
     */
    private final int width;

    /**
     * Costruisce un albero di Merkle a partire da un oggetto HashLinkedList,
     * utilizzando direttamente gli hash presenti nella lista per costruire le
     * foglie. Si noti che gli hash dei nodi intermedi dovrebbero essere
     * ottenuti da quelli inferiori concatenando hash adiacenti due a due e
     * applicando direttmaente la funzione di hash MD5 al risultato della
     * concatenazione in bytes.
     *
     * @param hashList
     *                     un oggetto HashLinkedList contenente i dati e i
     *                     relativi hash.
     * @throws IllegalArgumentException
     *                                      se la lista è null o vuota.
     */
    public MerkleTree(HashLinkedList<T> hashList) {
        if(hashList == null || hashList.getSize() == 0)
            throw new IllegalArgumentException();
        this.width = hashList.getSize();
        int leafs = getUpperTwoPower(width);
        List<MerkleNode> nodes = new ArrayList<>();
        List<String> allHashes = hashList.getAllHashes();
        for(String hash : allHashes){
            nodes.add(new MerkleNode(hash));
        }
        for(int i = allHashes.size(); i < leafs; i++){
            nodes.add(new MerkleNode(""));
        }
        while(nodes.size() > 1){
            List<MerkleNode> newList = new ArrayList<>();
            MerkleNode left = null;
            for (MerkleNode n : nodes) {
                if(left == null){
                    left = n;
                } else {
                    if(n.getHash().equals("") && left.getHash().equals(""))
                        newList.add(new MerkleNode("", left, n));
                    else newList.add(new MerkleNode(HashUtil.computeMD5((left.getHash() + n.getHash()).getBytes()), left, n));
                    left = null;
                }
            }
            nodes = newList;
        }
        root = nodes.get(0);
    }

    private int getUpperTwoPower(int n){
        int result = 1;
        while(result < n)
            result *= 2;
        return result;
    }

    /**
     * Restituisce il nodo radice dell'albero.
     *
     * @return il nodo radice.
     */
    public MerkleNode getRoot() {
        return root;
    }

    /**
     * Restituisce la larghezza dell'albero.
     *
     * @return la larghezza dell'albero.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Restituisce l'altezza dell'albero.
     *
     * @return l'altezza dell'albero.
     */
    public int getHeight() {
        int len = 0;
        MerkleNode n = root;
        while (!n.isLeaf()) {
            n = n.getLeft();
            len++;
        }
        return len;
    }

    /**
     * Restituisce l'indice di un dato elemento secondo l'albero di Merkle
     * descritto da un dato branch. Gli indici forniti partono da 0 e
     * corrispondono all'ordine degli hash corrispondenti agli elementi
     * nell'ultimo livello dell'albero da sinistra a destra. Nel caso in cui il
     * branch fornito corrisponda alla radice di un sottoalbero, l'indice
     * fornito rappresenta un indice relativo a quel sottoalbero, ovvero un
     * offset rispetto all'indice del primo elemento del blocco di dati che
     * rappresenta. Se l'hash dell'elemento non è presente come dato
     * dell'albero, viene restituito -1.
     *
     * @param branch
     *                   la radice dell'albero di Merkle.
     * @param data
     *                   l'elemento da cercare.
     * @return l'indice del dato nell'albero; -1 se l'hash del dato non è
     *         presente.
     * @throws IllegalArgumentException
     *                                      se il branch o il dato sono null o
     *                                      se il branch non è parte
     *                                      dell'albero.
     */
    public int getIndexOfData(MerkleNode branch, T data) {
        if(branch == null || data == null || !checkBranchIntoThree(this.root, branch))
            throw new IllegalArgumentException();
        return searchIntoNode(branch, data);
    }

    private int searchIntoNode(MerkleNode node, String hash){
        Stack<MerkleNode> list = new Stack<>();
        list.push(node);
        int index = 0;
        while (!list.isEmpty()) {
            MerkleNode n = list.pop();
            if(n.isLeaf()){
                if(n.getHash().equals(hash))
                    return index;
                index++;
            } else {
                list.push(n.getRight());
                list.push(n.getLeft());
            }
        }
        return -1;
    }


    private int searchIntoNode(MerkleNode node, T data){
        return searchIntoNode(node, HashUtil.dataToHash(data));
    }

    private boolean checkBranchIntoThree(MerkleNode three, MerkleNode branch){
        if(three.equals(branch))
            return true;
        if(three.isLeaf())
            return false;
        return checkBranchIntoThree(three.getLeft(), branch)
            || checkBranchIntoThree(three.getRight(), branch);
    }

    /**
     * Restituisce l'indice di un elemento secondo questo albero di Merkle. Gli
     * indici forniti partono da 0 e corrispondono all'ordine degli hash
     * corrispondenti agli elementi nell'ultimo livello dell'albero da sinistra
     * a destra (e quindi l'ordine degli elementi forniti alla costruzione). Se
     * l'hash dell'elemento non è presente come dato dell'albero, viene
     * restituito -1.
     *
     * @param data
     *                 l'elemento da cercare.
     * @return l'indice del dato nell'albero; -1 se il dato non è presente.
     * @throws IllegalArgumentException
     *                                      se il dato è null.
     */
    public int getIndexOfData(T data) {
        return searchIntoNode(this.root, data);
    }

    /**
     * Sottopone a validazione un elemento fornito per verificare se appartiene
     * all'albero di Merkle, controllando se il suo hash è parte dell'albero
     * come hash di un nodo foglia.
     *
     * @param data
     *                 l'elemento da validare
     * @return true se l'hash dell'elemento è parte dell'albero; false
     *         altrimenti.
     */
    public boolean validateData(T data) {
        return checkHashIntoLeafs(this.root, HashUtil.dataToHash(data));
    }

    private boolean checkHashIntoLeafs(MerkleNode three, String hash){
        if(three.isLeaf())
            return three.getHash().equals(hash);
        return checkHashIntoLeafs(three.getLeft(), hash)
            || checkHashIntoLeafs(three.getRight(), hash);
    }

    /**
     * Sottopone a validazione un dato sottoalbero di Merkle, corrispondente
     * quindi a un blocco di dati, per verificare se è valido rispetto a questo
     * albero e ai suoi hash. Un sottoalbero è valido se l'hash della sua radice
     * è uguale all'hash di un qualsiasi nodo intermedio di questo albero. Si
     * noti che il sottoalbero fornito può corrispondere a una foglia.
     *
     * @param branch
     *                   la radice del sottoalbero di Merkle da validare.
     * @return true se il sottoalbero di Merkle è valido; false altrimenti.
     */
    public boolean validateBranch(MerkleNode branch) {
        return checkBranchIntoThree(this.root, branch);
    }

    /**
     * Sottopone a validazione un dato albero di Merkle per verificare se è
     * valido rispetto a questo albero e ai suoi hash. Grazie alle proprietà
     * degli alberi di Merkle, ciò può essere fatto in tempo costante.
     *
     * @param otherTree
     *                      il nodo radice dell'altro albero di Merkle da
     *                      validare.
     * @return true se l'altro albero di Merkle è valido; false altrimenti.
     * @throws IllegalArgumentException
     *                                      se l'albero fornito è null.
     */
    public boolean validateTree(MerkleTree<T> otherTree) {
        if(otherTree == null)
            throw new IllegalArgumentException();
        try {
            return findInvalidDataIndices(otherTree).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Trova gli indici degli elementi di dati non validi (cioè con un hash
     * diverso) in un dato Merkle Tree, secondo questo Merkle Tree. Grazie alle
     * proprietà degli alberi di Merkle, ciò può essere fatto confrontando gli
     * hash dei nodi interni corrispondenti nei due alberi. Ad esempio, nel caso
     * di un singolo dato non valido, verrebbe percorso un unico cammino di
     * lunghezza pari all'altezza dell'albero. Gli indici forniti partono da 0 e
     * corrispondono all'ordine degli elementi nell'ultimo livello dell'albero
     * da sinistra a destra (e quindi l'ordine degli elementi forniti alla
     * costruzione). Se l'albero fornito ha una struttura diversa, possibilmente
     * a causa di una quantità diversa di elementi con cui è stato costruito e,
     * quindi, non rappresenta gli stessi dati, viene lanciata un'eccezione.
     *
     * @param otherTree
     *                      l'altro Merkle Tree.
     * @throws IllegalArgumentException
     *                                      se l'altro albero è null o ha una
     *                                      struttura diversa.
     * @return l'insieme di indici degli elementi di dati non validi.
     */
    public Set<Integer> findInvalidDataIndices(MerkleTree<T> otherTree) {
        Set<Integer> set = new HashSet<>();
        findInvalidDataIndicesRecursive(root, otherTree.root, set);
        return set;
    }

    /**
     * Trova ricorsivamente gli indici degli elementi di dati non validi in un
     * dato Merkle Tree, secondo questo Merkle Tree.
     *
     * @param node
     *                           il nodo corrente da validare.
     * @param otherNode
     *                           il nodo corrispondente nell'altro albero da
     *                           validare.
     * @param invalidIndices
     *                           l'insieme di indici degli elementi di dati non
     *                           validi.
     */
    private void findInvalidDataIndicesRecursive(MerkleNode node,
            MerkleNode otherNode,
            Set<Integer> invalidIndices) {
        if(node.isLeaf() && otherNode.isLeaf() && !node.equals(otherNode)){
            invalidIndices.add(searchIntoNode(root, node.getHash()));
            return;
        }
        if((node.isLeaf() && !otherNode.isLeaf()) || (!node.isLeaf() && otherNode.isLeaf()))
            throw new IllegalArgumentException();
        if(!node.equals(otherNode)){
            findInvalidDataIndicesRecursive(node.getLeft(), otherNode.getLeft(), invalidIndices);
            findInvalidDataIndicesRecursive(node.getRight(), otherNode.getRight(), invalidIndices);
        }
    }

    /**
     * Restituisce la prova di Merkle per un dato elemento, ovvero la lista di
     * hash dei nodi fratelli di ciascun nodo nel cammino dalla radice a una
     * foglia contenente il dato. La prova di Merkle dovrebbe fornire una lista
     * di oggetti MerkleProofHash tale per cui, combinando l'hash del dato con
     * l'hash del primo oggetto MerkleProofHash in un nuovo hash, il risultato
     * con il successivo e così via fino all'ultimo oggetto, si possa ottenere
     * l'hash del nodo padre dell'albero. Nel caso in cui non ci, in determinati
     * step della prova non ci siano due hash distinti da combinare, l'hash deve
     * comunque ricalcolato sulla base dell'unico hash disponibile.
     *
     * @param data
     *                 l'elemento per cui generare la prova di Merkle.
     * @return la prova di Merkle per il dato.
     * @throws IllegalArgumentException
     *                                      se il dato è null o non è parte
     *                                      dell'albero.
     */
    public MerkleProof getMerkleProof(T data) {
        if(data == null)
            throw new IllegalArgumentException();
        String hash = HashUtil.dataToHash(data);
        List<MerkleNode> path = getPathToDescendant(root, hash);
        if(path == null)
            throw new IllegalArgumentException();
        return getMerkleProof(path);
    }

    /**
     * Restituisce la prova di Merkle per un dato branch, ovvero la lista di
     * hash dei nodi fratelli di ciascun nodo nel cammino dalla radice al dato
     * nodo branch, rappresentativo di un blocco di dati. La prova di Merkle
     * dovrebbe fornire una lista di oggetti MerkleProofHash tale per cui,
     * combinando l'hash del branch con l'hash del primo oggetto MerkleProofHash
     * in un nuovo hash, il risultato con il successivo e così via fino
     * all'ultimo oggetto, si possa ottenere l'hash del nodo padre dell'albero.
     * Nel caso in cui non ci, in determinati step della prova non ci siano due
     * hash distinti da combinare, l'hash deve comunque ricalcolato sulla base
     * dell'unico hash disponibile.
     *
     * @param branch
     *                   il branch per cui generare la prova di Merkle.
     * @return la prova di Merkle per il branch.
     * @throws IllegalArgumentException
     *                                      se il branch è null o non è parte
     *                                      dell'albero.
     */
    public MerkleProof getMerkleProof(MerkleNode branch) {
        if(branch == null)
            throw new IllegalArgumentException();
        List<MerkleNode> path = getPathToDescendant(root, branch.getHash());
        if(path == null)
            throw new IllegalArgumentException();
        return getMerkleProof(path);
    }

    private MerkleProof getMerkleProof(List<MerkleNode> path){
        MerkleProof proof = new MerkleProof(root.getHash(), path.size() - 1);
        for(int i = path.size() - 2; i >= 0; i--){
            MerkleNode parent = path.get(i);
            MerkleNode son = path.get(i + 1);
            if(parent.getLeft().getHash().equals(son.getHash())){
                proof.addHash(parent.getRight().getHash(), false);
            }else{
                proof.addHash(parent.getLeft().getHash(), true);
            }
        }
        return proof;
    }

    /**
     * Metodo ricorsivo che restituisce il cammino da un dato nodo a un suo
     * discendente contenente un dato hash. Se l'hash fornito non è presente
     * nell'albero come hash di un discendente, viene restituito null.
     *
     * @param currentNode
     *                        il nodo corrente da cui iniziare la ricerca.
     * @param dataHash
     *                        l'hash del dato da cercare.
     * @return il cammino da un nodo a un discendente contenente il dato hash;
     *         null se l'hash non è presente.
     */
    public List<MerkleNode> getPathToDescendant(MerkleNode currentNode,
            String dataHash) {
        List<MerkleNode> list = new ArrayList<>();
        list.add(currentNode);
        if(currentNode.getHash().equals(dataHash))
                return list;
        if(currentNode.isLeaf()){
            return null;
        }else{
            List<MerkleNode> left = getPathToDescendant(currentNode.getLeft(), dataHash);
            List<MerkleNode> right = getPathToDescendant(currentNode.getRight(), dataHash);
            if(left != null) list.addAll(left);
            else if(right != null) list.addAll(right);
            else list = null;
            return list;
        }
    }
}