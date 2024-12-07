package it.unicam.cs.asdl2425.mp1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe di test per la classe {@link MerkleProof}. Questa classe verifica
 * il comportamento dei metodi della classe {@link MerkleProof}.
 * Di seguito, l'elenco dei test inclusi con una breve descrizione:
 * 
 * <ul>
 * <li>{@link #testBuildProof1()}: Verifica che una prova di Merkle venga costruita correttamente
 * aggiungendo hash validi, e che non sia possibile aggiungere più hash del limite specificato.</li>
 * 
 * <li>{@link #testBuildProof2()}: Verifica che una prova di Merkle con lunghezza massima 0
 * non consenta l'aggiunta di alcun hash.</li>
 * 
 * <li>{@link #testBuildProof3()}: Verifica internamente che gli hash inseriti nella prova di Merkle
 * siano memorizzati correttamente e nella posizione corretta (sinistra o destra).</li>
 * 
 * <li>{@link #testVerifyProofOnData1()}: Verifica che la validità di un dato venga confermata
 * correttamente rispetto alla prova di Merkle costruita.</li>
 * 
 * <li>{@link #testVerifyProofOnData2()}: Verifica che la validità di un dato singolo (con lunghezza 0 della prova)
 * venga confermata correttamente rispetto alla prova di Merkle.</li>
 * 
 * <li>{@link #testVerifyProofOnData3()}: Verifica che un dato non valido venga correttamente respinto
 * dalla prova di Merkle.</li>
 * </ul>
 */
public class MerkleProofTest {

    private String rootHash1, rootHash2, rootHash3;


    @BeforeEach
    void setUp() {
        rootHash1 =
                HashUtil.computeMD5(
                        (HashUtil.computeMD5(((HashUtil.dataToHash("Alice paga Bob")+HashUtil.dataToHash("Bob paga Charlie")).getBytes()))+
                                (HashUtil.computeMD5((HashUtil.dataToHash("Charlie paga Diana")+HashUtil.dataToHash("Diana paga Alice")).getBytes()))).getBytes()
                );

        rootHash2 = HashUtil.dataToHash(true);
    }

    @Test
    void testBuildProof1() {
        MerkleProof proof = new MerkleProof(rootHash1, 2);
        assertTrue(proof.addHash(HashUtil.dataToHash("Alice paga Bob"), true), "L'hash dovrebbe essere inserito correttamente");
        assertTrue(proof.addHash(HashUtil.computeMD5((HashUtil.dataToHash("Charlie paga Diana")+HashUtil.dataToHash("Diana paga Alice")).getBytes()), false), "L'hash dovrebbe essere inserito correttamente");
        assertFalse(proof.addHash("Hash non inserito", false), "L'hash non dovrebbe essere inserito");
    }

    @Test
    void testBuildProof2() {
        MerkleProof proof = new MerkleProof(rootHash2, 0);
        assertFalse(proof.addHash("Hash non inserito", false), "L'hash non dovrebbe essere inserito");
    }

    @Test
    void testBuildProof3() {
        try {
            MerkleProof proof =  new MerkleProof(rootHash1, 2);
            proof.addHash(HashUtil.dataToHash("Alice paga Bob"), true);
            proof.addHash(HashUtil.computeMD5((HashUtil.dataToHash("Charlie paga Diana")+HashUtil.dataToHash("Diana paga Alice")).getBytes()), false);
            Class<?> clazz = proof.getClass();
            Field privateField = clazz.getDeclaredField("proof");
            privateField.setAccessible(true);

            HashLinkedList<MerkleProof.MerkleProofHash> list = (HashLinkedList<MerkleProof.MerkleProofHash>) privateField.get(proof);

            Iterator<MerkleProof.MerkleProofHash> itr = list.iterator();
            assertTrue(itr.hasNext());
            MerkleProof.MerkleProofHash next = itr.next();
            assertEquals(next.getHash(), HashUtil.dataToHash("Alice paga Bob"), "L'hash dovrebbe essere inserito correttamente");
            assertTrue(next.isLeft(), "L'hash dovrebbe essere inserito a sinistra");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testVerifyProofOnData1() {
        MerkleProof proof = new MerkleProof(rootHash1, 2);
        proof.addHash(HashUtil.dataToHash("Alice paga Bob"), true);
        proof.addHash(HashUtil.computeMD5((HashUtil.dataToHash("Charlie paga Diana")+HashUtil.dataToHash("Diana paga Alice")).getBytes()), false);
        assertTrue(proof.proveValidityOfData("Bob paga Charlie"), "La prova di validità del dato dovrebbe andare a buon fine");
    }

    @Test
    void testVerifyProofOnData2() {
        MerkleProof proof = new MerkleProof(rootHash2, 0);

        assertTrue(proof.proveValidityOfData(true), "La prova di validità del dato dovrebbe andare a buon fine");
    }

    @Test
    void testVerifyProofOnData3() {
        MerkleProof proof = new MerkleProof(rootHash1, 2);
        proof.addHash(HashUtil.dataToHash("Alice paga Bob"), true);
        proof.addHash(HashUtil.computeMD5((HashUtil.dataToHash("Charlie paga Diana") + HashUtil.dataToHash("Diana paga Alice")).getBytes()), false);

        assertFalse(proof.proveValidityOfData("Dato non valido"), "La prova di validità del dato non dovrebbe andare a buon fine");
    }

}
