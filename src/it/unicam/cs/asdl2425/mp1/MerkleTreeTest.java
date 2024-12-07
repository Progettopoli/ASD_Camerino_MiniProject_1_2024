package it.unicam.cs.asdl2425.mp1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe di test per la classe {@link MerkleTree}. Questa classe verifica
 * il comportamento dei metodi della classe {@link MerkleTree}.
 * Di seguito, l'elenco dei test inclusi con una breve descrizione:
 * 
 * <ul>
 * <li>{@link #testConstructorWithValidHashList1()}: Verifica la corretta costruzione di un MerkleTree
 * con una lista di hash valida di 4 elementi.</li>
 * 
 * <li>{@link #testConstructorWithValidHashList2()}: Verifica la corretta costruzione di un MerkleTree
 * con una lista di hash valida di 13 elementi.</li>
 * 
 * <li>{@link #testConstructorWithValidHashList3()}: Verifica la corretta costruzione di un MerkleTree
 * con una lista di hash valida di 1 elemento.</li>
 * 
 * <li>{@link #testConstructorWithEmptyHashList()}: Verifica che venga lanciata un'eccezione
 * per una lista vuota.</li>
 * 
 * <li>{@link #testConstructorWithNullHashList()}: Verifica che venga lanciata un'eccezione
 * per una lista null.</li>
 * 
 * <li>{@link #testGetHeight1()}, {@link #testGetHeight2()}, {@link #testGetHeight3()}: Verificano
 * che l'altezza dell'albero sia calcolata correttamente per diverse configurazioni.</li>
 * 
 * <li>{@link #testValidateData1()}, {@link #testValidateData2()}, {@link #testValidateData3()}: Verificano
 * che la validazione di un dato nell'albero funzioni correttamente.</li>
 * 
 * <li>{@link #testGetIndexOfData1()}, {@link #testGetIndexOfData2()}, {@link #testGetIndexOfData3()},
 * {@link #testGetIndexOfData4()}: Verificano che l'indice di un dato venga calcolato correttamente
 * o restituisca -1 per dati non presenti.</li>
 * 
 * <li>{@link #testGetIndexOfDataInBranch()}, {@link #testGetIndexOfDataInBranchNotPresent()}: Verificano
 * il calcolo dell'indice relativo di un dato in un branch dell'albero.</li>
 * 
 * <li>{@link #testValidateBranch1()}, {@link #testValidateBranch2()}, {@link #testValidateBranch3()},
 * {@link #testValidateBranch4()}: Verificano che i branch dell'albero siano validati correttamente.</li>
 * 
 * <li>{@link #testValidateTree1()}, {@link #testValidateTree2()}, {@link #testValidateTree3()},
 * {@link #testValidateTree4()}: Verificano la validità di un MerkleTree rispetto a un altro,
 * sia in caso di alberi identici che diversi.</li>
 * 
 * <li>{@link #testFindInvalidDataIndices1()}, {@link #testFindInvalidDataIndices2()},
 * {@link #testFindInvalidDataIndices3()}, {@link #testFindInvalidDataIndices4()}: Verificano che
 * gli indici dei dati invalidi vengano identificati correttamente rispetto a un altro albero.</li>
 * 
 * <li>{@link #testGetMerkleProofData()}, {@link #testGetMerkleProofData2()},
 * {@link #testGetMerkleProofData3()}, {@link #testGetMerkleProofData4()}: Verificano che le prove
 * di Merkle siano generate correttamente per diversi dati nell'albero.</li>
 * 
 * <li>{@link #testGetMerkleProofDataNotPresent()}: Verifica che venga lanciata un'eccezione
 * per la richiesta di una prova di Merkle per un dato non presente.</li>
 * 
 * <li>{@link #testVerifyProofData()}, {@link #testVerifyProofData2()}, {@link #testVerifyProofData3()},
 * {@link #testVerifyProofData4()}, {@link #testVerifyProofData5()}: Verificano la validità di una
 * prova di Merkle per dati presenti e non presenti nell'albero.</li>
 * 
 * <li>{@link #testGetMerkleProofBranch1()}, {@link #testGetMerkleProofBranch2()}: Verificano la generazione
 * di prove di Merkle per branch specifici dell'albero.</li>
 * 
 * <li>{@link #testVerifyProofBranch()}: Verifica la validità di una prova di Merkle per un branch valido.</li>
 * 
 * <li>{@link #testVerifyProofBranchInvalid()}: Verifica che una prova di Merkle per un branch non valido
 * venga respinta.</li>
 * 
 * <li>{@link #testSingleLeafTree()}: Verifica il comportamento del MerkleTree per un singolo elemento,
 * controllando larghezza, altezza e validità dei dati.</li>
 * </ul>
 */
class MerkleTreeTest {

    private HashLinkedList<String> hashList1;
    private HashLinkedList<Long> hashList2;

    private HashLinkedList<Boolean> hashList3;

    private MerkleTree<String> merkleTree1;
    private MerkleTree<Long> merkleTree2;
    private MerkleTree<Boolean> merkleTree3;

    @BeforeEach
    void setUp() {
        // Creazione di una HashLinkedList con dati di esempio
        hashList1 = new HashLinkedList<>();
        hashList1.addAtTail("Alice paga Bob");
        hashList1.addAtTail("Bob paga Charlie");
        hashList1.addAtTail("Charlie paga Diana");
        hashList1.addAtTail("Diana paga Alice");
        // Creazione del MerkleTree
        merkleTree1 = new MerkleTree<>(hashList1);

        // Creazione di una seconda HashLinkedList con dati di esempio
        hashList2 = new HashLinkedList<>();
        for (int i = 1; i <= 13; i++) hashList2.addAtTail(111L * i);
        // Creazione del MerkleTree
        merkleTree2 = new MerkleTree<>(hashList2);

        //Creazione di una terza HashLinkedList con dati di esempio
        hashList3 = new HashLinkedList<>();
        hashList3.addAtTail(true);

        //Creazione del MerkleTree
        merkleTree3 = new MerkleTree<>(hashList3);

    }

    @Test
    void testConstructorWithValidHashList1() {
        assertNotNull(merkleTree1.getRoot(),
                "La radice dell'albero non dovrebbe essere null.");
        assertEquals(4, merkleTree1.getWidth(),
                "La larghezza dell'albero dovrebbe essere 4.");
    }

    @Test
    void testConstructorWithValidHashList2() {
        assertNotNull(merkleTree2.getRoot(),
                "La radice dell'albero non dovrebbe essere null.");
        assertEquals(13, merkleTree2.getWidth(),
                "La larghezza dell'albero dovrebbe essere 13.");
    }

    @Test
    void testConstructorWithValidHashList3() {
        assertNotNull(merkleTree3.getRoot(),
                "La radice dell'albero non dovrebbe essere null.");
        assertEquals(1, merkleTree3.getWidth(),
                "La larghezza dell'albero dovrebbe essere 1.");
    }

    @Test
    void testConstructorWithEmptyHashList() {
        HashLinkedList<String> emptyList = new HashLinkedList<>();
        assertThrows(IllegalArgumentException.class,
                () -> new MerkleTree<>(emptyList),
                "Dovrebbe lanciare IllegalArgumentException per una lista vuota.");
    }

    @Test
    void testConstructorWithNullHashList() {
        assertThrows(IllegalArgumentException.class,
                () -> new MerkleTree<>(null),
                "Dovrebbe lanciare IllegalArgumentException per una lista null.");
    }

    @Test
    void testGetHeight1() {
        int expectedHeight = 2; // Con 4 foglie, l'altezza è log2(4) = 2
        assertEquals(expectedHeight, merkleTree1.getHeight(),
                "L'altezza dell'albero non è corretta.");
    }

    @Test
    void testGetHeight2() {
        int expectedHeight = 4; // Con 13 foglie, l'altezza è  4
        assertEquals(expectedHeight, merkleTree2.getHeight(),
                "L'altezza dell'albero non è corretta.");
    }

    @Test
    void testGetHeight3() {
        int expectedHeight = 0; // Con 1 foglia, l'altezza è  0
        assertEquals(expectedHeight, merkleTree3.getHeight(),
                "L'altezza dell'albero non è corretta.");
    }

    @Test
    void testValidateData1() {
        assertTrue(merkleTree1.validateData("Alice paga Bob"),
                "Il dato dovrebbe essere valido.");
        assertFalse(merkleTree1.validateData("Dati non presenti"),
                "Il dato non dovrebbe essere valido.");
    }

    @Test
    void testValidateData2() {
        assertTrue(merkleTree2.validateData(555L),
                "Il dato dovrebbe essere valido.");
        assertFalse(merkleTree2.validateData(112L),
                "Il dato non dovrebbe essere valido.");
    }

    @Test
    void testValidateData3() {
        assertTrue(merkleTree3.validateData(true),
                "Il dato dovrebbe essere valido.");
        assertFalse(merkleTree3.validateData(false),
                "Il dato non dovrebbe essere valido.");
    }

    @Test
    void testGetIndexOfData1() {
        int index = merkleTree1.getIndexOfData("Alice paga Bob");
        assertEquals(0, index,
                "L'indice del dato 'Alice paga Bob' dovrebbe essere 0.");

        index = merkleTree1.getIndexOfData("Diana paga Alice");
        assertEquals(3, index,
                "L'indice del dato 'Diana paga Alice' dovrebbe essere 3.");
    }

    @Test
    void testGetIndexOfData2() {
        int index = merkleTree1.getIndexOfData("Dato non presente");
        assertEquals(-1, index, "Un dato non presente dovrebbe restituire -1.");
    }

    @Test
    void testGetIndexOfData3() {
        int index = merkleTree2.getIndexOfData(999L);
        assertEquals(8, index,
                "L'indice del dato 999 dovrebbe essere 8.");

        index = merkleTree2.getIndexOfData(1332L);
        assertEquals(11, index,
                "L'indice del dato 1332 dovrebbe essere 11.");
    }

    @Test
    void testGetIndexOfData4() {
        int index = merkleTree2.getIndexOfData(556L);
        assertEquals(-1, index, "Un dato non presente dovrebbe restituire -1.");
    }

    @Test
    void testGetIndexOfDataInBranch() {
        HashLinkedList<String> branchList = new HashLinkedList<>();
        branchList.addAtTail("Charlie paga Diana");
        branchList.addAtTail("Diana paga Alice");
        MerkleTree<String> merkleTreeBranch = new MerkleTree<>(branchList);

        int index = merkleTree1.getIndexOfData(merkleTreeBranch.getRoot(), "Charlie paga Diana");
        assertEquals(0, index,
                "L'indice relativo del dato 'Charlie paga Diana' dovrebbe essere 0.");
        index = merkleTree1.getIndexOfData(merkleTreeBranch.getRoot(), "Diana paga Alice");
        assertEquals(1, index,
                "L'indice relativo del dato 'Diana paga Alice' dovrebbe essere 1.");
    }

    @Test
    void testGetIndexOfDataInBranchNotPresent() {
        HashLinkedList<String> branchList = new HashLinkedList<>();
        branchList.addAtTail("Charlie paga Diana");
        branchList.addAtTail("Diana paga Alice");
        MerkleTree<String> merkleTreeBranch = new MerkleTree<>(branchList);

        int index = merkleTree1.getIndexOfData(merkleTreeBranch.getRoot(), "Dato non presente");
        assertEquals(-1, index,
                "Un dato non presente nel branch dovrebbe restituire -1.");
    }

    @Test
    void testValidateBranch1() {
        MerkleNode rootNode = merkleTree1.getRoot();
        assertTrue(merkleTree1.validateBranch(rootNode),
                "La radice dovrebbe essere un branch valido.");

        MerkleNode leftNode = rootNode.getLeft();
        assertTrue(merkleTree1.validateBranch(leftNode),
                "Il nodo sinistro della radice dovrebbe essere un branch valido.");
    }

    @Test
    void testValidateBranch2() {
        MerkleNode invalidNode = new MerkleNode("HashNonValido");
        assertFalse(merkleTree1.validateBranch(invalidNode),
                "Un nodo con hash non valido non dovrebbe essere valido.");
    }

    @Test
    void testValidateBranch3() {
        HashLinkedList<Long> branchList = new HashLinkedList<>();
        branchList.addAtTail(111L);
        branchList.addAtTail(222L);
        branchList.addAtTail(333L);
        branchList.addAtTail(444L);
        MerkleTree<Long> merkleTreeBranch = new MerkleTree<>(branchList);
        MerkleNode branchRoot = merkleTreeBranch.getRoot();
        assertTrue(merkleTree2.validateBranch(branchRoot),
                "Il branch dovrebbe essere valido.");
    }

    @Test
    void testValidateBranch4() {
        HashLinkedList<Long> branchList = new HashLinkedList<>();
        branchList.addAtTail(111L);
        branchList.addAtTail(222L);
        branchList.addAtTail(333L);
        branchList.addAtTail(555L);
        MerkleTree<Long> merkleTreeBranch = new MerkleTree<>(branchList);
        MerkleNode branchRoot = merkleTreeBranch.getRoot();
        assertFalse(merkleTree2.validateBranch(branchRoot),
                "Il branch non dovrebbe essere valido.");
    }

    @Test
    void testValidateTree1() {
        HashLinkedList<String> identicalList = new HashLinkedList<>();
        identicalList.addAtTail("Alice paga Bob");
        identicalList.addAtTail("Bob paga Charlie");
        identicalList.addAtTail("Charlie paga Diana");
        identicalList.addAtTail("Diana paga Alice");

        MerkleTree<String> identicalTree = new MerkleTree<>(identicalList);
        assertTrue(merkleTree1.validateTree(identicalTree),
                "Gli alberi identici dovrebbero essere validi.");
    }

    @Test
    void testValidateTree2() {
        HashLinkedList<String> differentList1 = new HashLinkedList<>();
        differentList1.addAtTail("Dato diverso");
        MerkleTree<String> differentTree1 = new MerkleTree<>(differentList1);
        assertFalse(merkleTree1.validateTree(differentTree1),
                "Gli alberi diversi non dovrebbero essere validi.");

        HashLinkedList<String> differentList2 = new HashLinkedList<>();
        differentList2.addAtTail("Alice paga Bob");
        differentList2.addAtTail("Bob paga Charlie");
        differentList2.addAtTail("Dato modificato");
        differentList2.addAtTail("Diana paga Alice");
        MerkleTree<String> differentTree2 = new MerkleTree<>(differentList2);
        assertFalse(merkleTree1.validateTree(differentTree2),
                "Gli alberi diversi non dovrebbero essere validi.");
    }

    @Test
    void testValidateTree3() {
        HashLinkedList<Long> identicalList = new HashLinkedList<>();
        for (int i = 1; i <= 13; i++) identicalList.addAtTail(111L * i);
        MerkleTree<Long> identicalTree = new MerkleTree<>(identicalList);
        assertTrue(merkleTree2.validateTree(identicalTree),
                "Gli alberi identici dovrebbero essere validi.");
    }

    @Test
    void testValidateTree4(){
        HashLinkedList<Long> differentList = new HashLinkedList<>();
        for (int i = 1; i <= 13; i++) differentList.addAtTail(111L * i);
        differentList.addAtTail(0L);
        differentList.addAtTail(0L);
        MerkleTree<Long> differentTree = new MerkleTree<>(differentList);
        assertFalse(merkleTree2.validateTree(differentTree),
                "Gli alberi diversi non dovrebbero essere validi.");
    }

    @Test
    void testFindInvalidDataIndices1() {
        HashLinkedList<String> modifiedList = new HashLinkedList<>();
        modifiedList.addAtTail("Alice paga Bob");
        modifiedList.addAtTail("Bob paga Charlie");
        modifiedList.addAtTail("Dato modificato");
        modifiedList.addAtTail("Diana paga Alice");

        MerkleTree<String> modifiedTree = new MerkleTree<>(modifiedList);
        Set<Integer> invalidIndices = merkleTree1
                .findInvalidDataIndices(modifiedTree);

        assertEquals(1, invalidIndices.size(),
                "Ci dovrebbe essere 1 indice non valido.");
        assertTrue(invalidIndices.contains(2),
                "L'indice 2 dovrebbe essere non valido.");
    }

    @Test
    void testFindInvalidDataIndices2() {
        HashLinkedList<String> modifiedList = new HashLinkedList<>();
        modifiedList.addAtTail("Alice paga Bob");
        modifiedList.addAtTail("Dato modificato 1");
        modifiedList.addAtTail("Dato modificato 2");
        modifiedList.addAtTail("Diana paga Alice");

        MerkleTree<String> modifiedTree = new MerkleTree<>(modifiedList);
        Set<Integer> invalidIndices = merkleTree1
                .findInvalidDataIndices(modifiedTree);

        assertEquals(2, invalidIndices.size(),
                "Ci dovrebbe essere 1 indice non valido.");
        assertEquals(new HashSet<>(Arrays.asList(1,2)), invalidIndices,
                "Gli indici 1 e 2 dovrebbero essere non validi.");
    }

    @Test
    void testFindInvalidDataIndices3() {
        HashLinkedList<Long> modifiedList = new HashLinkedList<>();
        for (int i = 1; i <= 5; i++) modifiedList.addAtTail(111L * i);
        modifiedList.addAtTail(0L);
        modifiedList.addAtTail(777L);
        modifiedList.addAtTail(0L);
        modifiedList.addAtTail(999L);
        modifiedList.addAtTail(0L);
        for (int i = 11; i <= 13; i++) modifiedList.addAtTail(111L * i);
        MerkleTree<Long> modifiedTree = new MerkleTree<>(modifiedList);
        Set<Integer> invalidIndices = merkleTree2
                .findInvalidDataIndices(modifiedTree);

        assertEquals(3, invalidIndices.size(),
                "Ci dovrebbero essere 3 indici non validi.");
        assertEquals(new HashSet<>(Arrays.asList(5, 7, 9)), invalidIndices,
                "Gli indici 5, 7 e 9 dovrebbero essere non validi.");
    }

    @Test
    void testFindInvalidDataIndices4() {
        HashLinkedList<Boolean> modifiedList = new HashLinkedList<>();
        modifiedList.addAtTail(false);

        MerkleTree<Boolean> modifiedTree = new MerkleTree<>(modifiedList);
        Set<Integer> invalidIndices = merkleTree3
                .findInvalidDataIndices(modifiedTree);
        assertEquals(1, invalidIndices.size(),
                "Ci dovrebbe essere 1 indice non valido.");
    }

    @Test
    void testGetMerkleProofData() {
        MerkleProof proof = merkleTree1.getMerkleProof("Alice paga Bob");
        assertNotNull(proof, "La prova di Merkle non dovrebbe essere null.");
        assertEquals(2, proof.getLength(), "La prova di Merkle dovrebbe avere dimensione 2.");
    }

    @Test
    void testGetMerkleProofData2() {
        MerkleProof proof = merkleTree2.getMerkleProof(555L);
        assertNotNull(proof, "La prova di Merkle non dovrebbe essere null.");
        assertEquals(4, proof.getLength(), "La prova di Merkle dovrebbe avere dimensione 4.");
    }

    @Test
    void testGetMerkleProofData3() {
        MerkleProof proof = merkleTree2.getMerkleProof(1443L);
        assertNotNull(proof, "La prova di Merkle non dovrebbe essere null.");
        assertEquals(4, proof.getLength(), "La prova di Merkle dovrebbe avere dimensione 4.");
    }

    @Test
    void testGetMerkleProofData4() {
        MerkleProof proof = merkleTree3.getMerkleProof(true);
        assertNotNull(proof, "La prova di Merkle non dovrebbe essere null.");
        assertEquals(0, proof.getLength(), "La prova di Merkle dovrebbe avere dimensione 0.");
    }

    @Test
    void testGetMerkleProofDataNotPresent() {
        assertThrows(IllegalArgumentException.class, () -> merkleTree1.getMerkleProof("Dato non presente"),
                "Dovrebbe lanciare IllegalArgumentException per un dato non presente.");
    }

    @Test
    void testVerifyProofData() {
        MerkleProof proof = merkleTree1.getMerkleProof("Alice paga Bob");
        assertTrue(proof.proveValidityOfData("Alice paga Bob"),
                "La prova di Merkle dovrebbe essere valida.");

        assertFalse(proof.proveValidityOfData("Dati non presenti"),
                "Una prova di Merkle per un dato non presente non dovrebbe essere valida.");
    }

    @Test
    void testVerifyProofData2() {
        MerkleProof proof = merkleTree2.getMerkleProof(555L);
        assertTrue(proof.proveValidityOfData(555L),
                "La prova di Merkle dovrebbe essere valida.");

        assertFalse(proof.proveValidityOfData(556L),
                "Una prova di Merkle per un dato non presente non dovrebbe essere valida.");
    }

    @Test
    void testVerifyProofData3(){
        MerkleProof proof = merkleTree3.getMerkleProof(true);
        assertTrue(proof.proveValidityOfData(true),
                "La prova di Merkle dovrebbe essere valida.");

        assertFalse(proof.proveValidityOfData(false),
                "Una prova di Merkle per un dato non presente non dovrebbe essere valida.");
    }

    @Test
    void testVerifyProofData4(){
        try {
            MerkleProof proof =  merkleTree1.getMerkleProof("Alice paga Bob");
            Class<?> clazz = proof.getClass();
            Field privateField = clazz.getDeclaredField("proof");
            privateField.setAccessible(true);

            HashLinkedList<MerkleProof.MerkleProofHash> list = (HashLinkedList<MerkleProof.MerkleProofHash>) privateField.get(proof);

            Iterator<MerkleProof.MerkleProofHash> itr = list.iterator();
            assertTrue(itr.hasNext());
            MerkleProof.MerkleProofHash hash = itr.next();
            assertEquals(HashUtil.dataToHash("Bob paga Charlie"), hash.getHash(), "L'hash dovrebbe essere uguale a quello di 'Bob paga Charlie'");
            assertFalse(hash.isLeft(), "L'hash dovrebbe essere concatenato a destra");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testVerifyProofData5(){
        try {
            MerkleProof proof =  merkleTree2.getMerkleProof(1443L);
            Class<?> clazz = proof.getClass();
            Field privateField = clazz.getDeclaredField("proof");
            privateField.setAccessible(true);

            HashLinkedList<MerkleProof.MerkleProofHash> list = (HashLinkedList<MerkleProof.MerkleProofHash>) privateField.get(proof);

            Iterator<MerkleProof.MerkleProofHash> itr = list.iterator();
            assertTrue(itr.hasNext() && itr.next().getHash().equals(""));
            assertTrue(itr.hasNext() && itr.next().getHash().equals(""));
            assertTrue(itr.hasNext() && !itr.next().getHash().equals(""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetMerkleProofBranch1() {
        MerkleProof proof = merkleTree1.getMerkleProof(merkleTree1.getRoot().getLeft());
        assertNotNull(proof, "La prova di Merkle non dovrebbe essere null.");
        assertEquals(1, proof.getLength(), "La prova di Merkle dovrebbe avere dimensione 1.");
    }

    @Test
    void testGetMerkleProofBranch2() {
        MerkleProof proof = merkleTree2.getMerkleProof(merkleTree2.getRoot().getRight().getRight());
        assertNotNull(proof, "La prova di Merkle non dovrebbe essere null.");
        assertEquals(2, proof.getLength(), "La prova di Merkle dovrebbe avere dimensione 2.");
    }

    @Test
    void testVerifyProofBranch() {
        HashLinkedList<String> branchList = new HashLinkedList<>();
        branchList.addAtTail("Alice paga Bob");
        branchList.addAtTail("Bob paga Charlie");
        MerkleTree<String> merkleTreeBranch = new MerkleTree<>(branchList);
        MerkleProof proof = merkleTree1.getMerkleProof(merkleTree1.getRoot().getLeft());

        assertTrue(proof.proveValidityOfBranch(merkleTreeBranch.getRoot()),
                "La prova di Merkle per un branch dovrebbe essere valida.");
    }

    @Test
    void testVerifyProofBranchInvalid() {
        HashLinkedList<String> branchList = new HashLinkedList<>();
        branchList.addAtTail("Alice paga Bob");
        branchList.addAtTail("Dato non presente");
        MerkleTree<String> merkleTreeBranch = new MerkleTree<>(branchList);
        MerkleProof proof = merkleTree1.getMerkleProof(merkleTree1.getRoot().getLeft());

        assertFalse(proof.proveValidityOfBranch(merkleTreeBranch.getRoot()),
                "La prova di Merkle per il branch non dovrebbe essere valida.");
    }
    
    @Test
    void testSingleLeafTree() {
        HashLinkedList<String> singleList = new HashLinkedList<>();
        singleList.addAtTail("Alice paga Bob");
        MerkleTree<String> singleTree = new MerkleTree<>(singleList);

        assertEquals(1, singleTree.getWidth(), "La larghezza dovrebbe essere 1.");
        assertEquals(0, singleTree.getHeight(), "L'altezza dovrebbe essere 0.");
        assertTrue(singleTree.validateData("Alice paga Bob"),
                "Il dato dovrebbe essere valido.");
    }
}
