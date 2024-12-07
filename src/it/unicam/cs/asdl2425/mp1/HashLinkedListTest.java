package it.unicam.cs.asdl2425.mp1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe di test per la classe {@link HashLinkedList}. Questa classe include
 * test per verificare il comportamento della lista concatenata con hash MD5.
 * Di seguito, l'elenco dei test inclusi con una breve descrizione:
 * 
 * <ul>
 * <li>{@link #testIsEmpty()}: Verifica che una lista appena creata sia vuota.</li>
 * 
 * <li>{@link #testAddAtHead()}: Aggiunge elementi in testa e verifica che siano
 * inseriti correttamente nell'ordine inverso.</li>
 * 
 * <li>{@link #testAddAtTail()}: Aggiunge elementi in coda e verifica che siano
 * inseriti nell'ordine corretto.</li>
 * 
 * <li>{@link #testBuildNodesString1()}: Aggiunge elementi in testa e verifica
 * che la rappresentazione testuale della lista sia corretta.</li>
 * 
 * <li>{@link #testBuildNodesString2()}: Aggiunge elementi in coda e verifica
 * che la rappresentazione testuale della lista sia corretta.</li>
 * 
 * <li>{@link #testBuildNodesString3()}: Aggiunge elementi in testa e coda, poi
 * verifica la rappresentazione testuale della lista.</li>
 * 
 * <li>{@link #testGetAllHashes()}: Verifica che il metodo {@code getAllHashes}
 * restituisca gli hash corretti degli elementi della lista.</li>
 * 
 * <li>{@link #testRemoveHeadElement()}: Rimuove l'elemento in testa e verifica
 * che la lista sia aggiornata correttamente.</li>
 * 
 * <li>{@link #testRemoveTailElement()}: Rimuove l'elemento in coda e verifica
 * che la lista sia aggiornata correttamente.</li>
 * 
 * <li>{@link #testRemoveMiddleElement()}: Rimuove un elemento al centro della
 * lista e verifica che i puntatori siano aggiornati correttamente.</li>
 * 
 * <li>{@link #testRemoveMultipleElements()}: Rimuove più elementi e verifica
 * che la lista sia aggiornata correttamente.</li>
 * 
 * <li>{@link #testRemoveAndAddElements()}: Rimuove un elemento e ne aggiunge un
 * altro, poi verifica la rappresentazione testuale della lista.</li>
 * 
 * <li>{@link #testRemoveNonExistentElement()}: Tenta di rimuovere un elemento
 * inesistente e verifica che il metodo restituisca {@code false}.</li>
 * 
 * <li>{@link #testIteratorHasNext1()}: Verifica che l'iteratore abbia un
 * prossimo elemento quando la lista contiene più elementi.</li>
 * 
 * <li>{@link #testIteratorHasNext2()}: Verifica che l'iteratore non abbia un
 * prossimo elemento quando è stato iterato completamente.</li>
 * 
 * <li>{@link #testIterator()}: Verifica che l'iteratore attraversi correttamente
 * tutti gli elementi della lista.</li>
 * 
 * <li>{@link #testFailFastIterator1()}: Verifica che l'iteratore sia
 * {@code fail-fast} aggiungendo un elemento durante l'iterazione.</li>
 * 
 * <li>{@link #testFailFastIterator2()}: Verifica che l'iteratore sia
 * {@code fail-fast} rimuovendo un elemento durante l'iterazione.</li>
 * </ul>
 */
class HashLinkedListTest {

    private HashLinkedList<String> list;

    @BeforeEach
    void setUp() {
        list = new HashLinkedList<>();
    }

    @Test
    void testIsEmpty() {
        assertEquals(0, list.getSize(),
                "La lista non dovrebbe contenere elementi inizialmente.");
    }

    @Test
    void testAddAtHead() {
        list.addAtHead("Alice paga Bob");
        assertEquals(1, list.getSize(),
                "La lista dovrebbe contenere un solo elemento.");

        list.addAtHead("Bob paga Charlie");
        assertEquals(2, list.getSize(),
                "La lista dovrebbe contenere due elementi.");
    }

    @Test
    void testAddAtTail() {
        list.addAtTail("Alice paga Bob");
        assertEquals(1, list.getSize(),
                "La lista dovrebbe contenere un solo elemento.");

        list.addAtTail("Bob paga Charlie");
        assertEquals(2, list.getSize(),
                "La lista dovrebbe contenere due elementi.");
    }
    
    @Test
    void testBuildNodesString1() {
        list.addAtHead("Alice paga Bob");
        list.addAtHead("Bob paga Charlie");

        String expected = "Dato: Bob paga Charlie, Hash: "
                + HashUtil.dataToHash("Bob paga Charlie") + "\n"
                + "Dato: Alice paga Bob, Hash: "
                + HashUtil.dataToHash("Alice paga Bob") + "\n";

        assertEquals(expected, list.buildNodesString(),
                "La rappresentazione della lista non è corretta.");
    }

//    @Test
//    void testBuildNodesString1() {
//        list.addAtHead("Alice paga Bob");
//        list.addAtHead("Bob paga Charlie");
//
//        String expected = "Dato: Bob paga Charlie, Hash: "
//                + HashUtil.dataToHash("Bob paga Charlie") + "\n"
//                + "Dato: Alice paga Bob, Hash: "
//                + HashUtil.dataToHash("Alice paga Bob") + "\n";
//
//        assertEquals(expected, list.buildNodesString(),
//                "La rappresentazione della lista non è corretta.");
//    }

    @Test
    void testBuildNodesString2() {
        list.addAtTail("Alice paga Bob");
        list.addAtTail("Bob paga Charlie");

        String expected = "Dato: Alice paga Bob, Hash: "
                + HashUtil.dataToHash("Alice paga Bob") + "\n"
                + "Dato: Bob paga Charlie, Hash: "
                + HashUtil.dataToHash("Bob paga Charlie") + "\n";

        assertEquals(expected, list.buildNodesString(),
                "La rappresentazione della lista non è corretta.");
    }

    @Test
    void testBuildNodesString3() {
        list.addAtHead("Alice paga Bob");
        list.addAtTail("Bob paga Charlie");

        String expected = "Dato: Alice paga Bob, Hash: "
                + HashUtil.dataToHash("Alice paga Bob") + "\n"
                + "Dato: Bob paga Charlie, Hash: "
                + HashUtil.dataToHash("Bob paga Charlie") + "\n";

        assertEquals(expected, list.buildNodesString(),
                "La rappresentazione della lista non è corretta.");
    }

    @Test
    void testGetAllHashes() {
        list.addAtHead("Alice paga Bob");
        list.addAtTail("Bob paga Charlie");

        ArrayList<String> expectedHashes = new ArrayList<>();
        expectedHashes.add(HashUtil.dataToHash("Alice paga Bob"));
        expectedHashes.add(HashUtil.dataToHash("Bob paga Charlie"));

        assertEquals(expectedHashes, list.getAllHashes(),
                "Gli hash della lista non corrispondono all'atteso.");
    }

    @Test
    void testRemoveHeadElement() {
        list.addAtTail("Alice paga Bob");
        list.addAtTail("Bob paga Charlie");

        assertTrue(list.remove("Alice paga Bob"),
                "L'elemento 'Alice paga Bob' doveva essere rimosso.");
        assertFalse(list.remove("Alice paga Bob"),
                "L'elemento 'Alice paga Bob' non doveva più esistere.");
    }

    @Test
    void testRemoveTailElement() {
        list.addAtTail("Alice paga Bob");
        list.addAtTail("Bob paga Charlie");

        assertTrue(list.remove("Bob paga Charlie"),
                "L'elemento 'Bob paga Charlie' doveva essere rimosso.");
        assertFalse(list.remove("Bob paga Charlie"),
                "L'elemento 'Bob paga Charlie' non doveva più esistere.");
    }

    @Test
    void testRemoveMiddleElement() {
        list.addAtTail("Alice paga Bob");
        list.addAtTail("Bob paga Charlie");
        list.addAtTail("Charlie paga Diana");

        assertTrue(list.remove("Bob paga Charlie"),
                "L'elemento 'Bob paga Charlie' doveva essere rimosso.");
        String expected = "Dato: Alice paga Bob, Hash: "
                + HashUtil.dataToHash("Alice paga Bob") + "\n"
                + "Dato: Charlie paga Diana, Hash: "
                + HashUtil.dataToHash("Charlie paga Diana") + "\n";
        assertEquals(expected, list.buildNodesString(),
                "La lista non è corretta dopo la rimozione.");
    }

    @Test
    void testRemoveMultipleElements() {
        list.addAtTail("Alice paga Bob");
        list.addAtTail("Bob paga Charlie");
        list.addAtTail("Charlie paga Diana");
        list.addAtTail("Diana paga Alice");

        assertTrue(list.remove("Bob paga Charlie"),
                "L'elemento 'Bob paga Charlie' doveva essere rimosso.");
        assertTrue(list.remove("Diana paga Alice"),
                "L'elemento 'Diana paga Alice' doveva essere rimosso.");

        String expected = "Dato: Alice paga Bob, Hash: "
                + HashUtil.dataToHash("Alice paga Bob") + "\n"
                + "Dato: Charlie paga Diana, Hash: "
                + HashUtil.dataToHash("Charlie paga Diana") + "\n";

        assertEquals(expected, list.buildNodesString(), "La lista non è corretta dopo le rimozioni.");
    }

    @Test
    void testRemoveAndAddElements() {
        list.addAtTail("Alice paga Bob");
        list.addAtTail("Bob paga Charlie");
        list.addAtTail("Charlie paga Diana");
        list.addAtTail("Diana paga Alice");

        assertTrue(list.remove("Bob paga Charlie"),
                "L'elemento 'Bob paga Charlie' doveva essere rimosso.");
        list.addAtTail("Charlie paga Diana");

        String expected = "Dato: Alice paga Bob, Hash: "
                + HashUtil.dataToHash("Alice paga Bob") + "\n"
                + "Dato: Charlie paga Diana, Hash: "
                + HashUtil.dataToHash("Charlie paga Diana") + "\n"
                + "Dato: Diana paga Alice, Hash: "
                + HashUtil.dataToHash("Diana paga Alice") + "\n"
                + "Dato: Charlie paga Diana, Hash: "
                + HashUtil.dataToHash("Charlie paga Diana") + "\n";

        assertEquals(expected, list.buildNodesString(), "La lista non è corretta dopo le rimozioni e l'aggiunta.");
    }

    @Test
    void testRemoveNonExistentElement() {
        list.addAtTail("Alice paga Bob");
        list.addAtTail("Bob paga Charlie");

        assertFalse(list.remove("Charlie paga Diana"),
                "Non dovrebbe essere possibile rimuovere un elemento inesistente.");
    }

    @Test
    void testIteratorHasNext1() {
        list.addAtTail("Alice paga Bob");
        list.addAtTail("Bob paga Charlie");

        assertTrue(list.iterator().hasNext(), "L'iteratore dovrebbe avere un prossimo elemento.");
    }

    @Test
    void testIteratorHasNext2() {
        list.addAtTail("Alice paga Bob");

        Iterator<String> iterator = list.iterator();
        assertDoesNotThrow(iterator::next, "L'iteratore dovrebbe avere un prossimo elemento.");
        assertFalse(iterator.hasNext(), "L'iteratore non dovrebbe avere un prossimo elemento.");
    }

    @Test
    void testIterator() {
        list.addAtTail("Alice paga Bob");
        list.addAtTail("Bob paga Charlie");
        list.addAtTail("Charlie paga Diana");
        list.addAtTail("Diana paga Alice");

        List<String> iteratorList = new ArrayList<>();
        for (String s : list) {
            iteratorList.add(s);
        }

        List<String> expectedList = Arrays.asList(
                "Alice paga Bob",
                "Bob paga Charlie",
                "Charlie paga Diana",
                "Diana paga Alice"
        );

        assertEquals(expectedList, iteratorList, "La lista generata dall'iteratore non è corretta.");
    }

    @Test
    void testFailFastIterator1() {
        list.addAtTail("Alice paga Bob");
        list.addAtTail("Bob paga Charlie");
        list.addAtTail("Charlie paga Diana");
        list.addAtTail("Diana paga Alice");

        assertThrows(
                ConcurrentModificationException.class,
                () -> {
                    for (String s : list) {
                        list.addAtTail("Alice paga Bob");
                    }
                },
                "L'iteratore non è fail-fast."
        );
    }

    @Test
    void testFailFastIterator2() {
        list.addAtTail("Alice paga Bob");
        list.addAtTail("Bob paga Charlie");
        list.addAtTail("Charlie paga Diana");
        list.addAtTail("Diana paga Alice");

        assertThrows(
                ConcurrentModificationException.class,
                () -> {
                    for (String s : list) {
                        list.remove("Bob paga Charlie");
                    }
                },
                "L'iteratore non è fail-fast."
        );
    }
}