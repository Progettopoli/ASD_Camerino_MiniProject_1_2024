package it.unicam.cs.asdl2425.mp1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe di test per la classe {@link MerkleNode}. Questa classe verifica
 * il comportamento dei metodi della classe {@link MerkleNode}.
 * Di seguito, l'elenco dei test inclusi con una breve descrizione:
 * 
 * <ul>
 * <li>{@link #testLeafNodeCreation()}: Verifica che un nodo foglia venga creato correttamente,
 * controllando hash, figli nulli e identificazione come foglia.</li>
 * 
 * <li>{@link #testBranchNodeCreation()}: Verifica che un nodo branch venga creato correttamente
 * con hash, figli sinistro e destro, e che non sia identificato come foglia.</li>
 * 
 * <li>{@link #testToString()}: Controlla che il metodo {@code toString} restituisca correttamente
 * l'hash del nodo.</li>
 * 
 * <li>{@link #testEqualsSameHash()}: Verifica che due nodi con lo stesso hash siano considerati uguali.</li>
 * 
 * <li>{@link #testEqualsDifferentHash()}: Verifica che due nodi con hash diversi non siano considerati uguali.</li>
 * 
 * <li>{@link #testHashCode()}: Controlla che due nodi con lo stesso hash abbiano lo stesso valore di {@code hashCode}.</li>
 * 
 * <li>{@link #testNotEqualsWithNull()}: Verifica che un nodo non sia uguale a {@code null}.</li>
 * 
 * <li>{@link #testNotEqualsWithDifferentType()}: Verifica che un nodo non sia uguale a un oggetto di tipo diverso.</li>
 * </ul>
 */
class MerkleNodeTest {

    @Test
    void testLeafNodeCreation() {
        MerkleNode leaf = new MerkleNode("hash123");

        assertEquals("hash123", leaf.getHash(),
                "L'hash del nodo foglia non è corretto.");
        assertNull(leaf.getLeft(),
                "Un nodo foglia non dovrebbe avere un figlio sinistro.");
        assertNull(leaf.getRight(),
                "Un nodo foglia non dovrebbe avere un figlio destro.");
        assertTrue(leaf.isLeaf(),
                "Il nodo dovrebbe essere identificato come foglia.");
    }

    @Test
    void testBranchNodeCreation() {
        MerkleNode left = new MerkleNode("leftHash");
        MerkleNode right = new MerkleNode("rightHash");
        MerkleNode branch = new MerkleNode("branchHash", left, right);

        assertEquals("branchHash", branch.getHash(),
                "L'hash del nodo branch non è corretto.");
        assertEquals(left, branch.getLeft(),
                "Il figlio sinistro del nodo branch non è corretto.");
        assertEquals(right, branch.getRight(),
                "Il figlio destro del nodo branch non è corretto.");
        assertFalse(branch.isLeaf(),
                "Il nodo branch non dovrebbe essere identificato come foglia.");
    }

    @Test
    void testToString() {
        MerkleNode node = new MerkleNode("hashToString");

        assertEquals("hashToString", node.toString(),
                "Il metodo toString non restituisce l'hash corretto.");
    }

    @Test
    void testEqualsSameHash() {
        MerkleNode node1 = new MerkleNode("hashEquals");
        MerkleNode node2 = new MerkleNode("hashEquals");

        assertEquals(node1, node2,
                "Due nodi con lo stesso hash dovrebbero essere uguali.");
    }

    @Test
    void testEqualsDifferentHash() {
        MerkleNode node1 = new MerkleNode("hash1");
        MerkleNode node2 = new MerkleNode("hash2");

        assertNotEquals(node1, node2,
                "Due nodi con hash diversi non dovrebbero essere uguali.");
    }

    @Test
    void testHashCode() {
        MerkleNode node1 = new MerkleNode("hash123");
        MerkleNode node2 = new MerkleNode("hash123");

        assertEquals(node1.hashCode(), node2.hashCode(),
                "Due nodi con lo stesso hash dovrebbero avere lo stesso hashCode.");
    }

    @Test
    void testNotEqualsWithNull() {
        MerkleNode node = new MerkleNode("hash123");

        assertNotEquals(null, node,
                "Un nodo non dovrebbe essere uguale a null.");
    }

    @Test
    void testNotEqualsWithDifferentType() {
        MerkleNode node = new MerkleNode("hash123");

        assertNotEquals(node,
                "Un oggetto di tipo diverso non dovrebbe essere uguale a un MerkleNode.");
    }
}