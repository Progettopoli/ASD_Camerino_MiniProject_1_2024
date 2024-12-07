package it.unicam.cs.asdl2425.mp1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe di test per la classe {@link HashUtil}. Questa classe verifica
 * il comportamento dei metodi statici per il calcolo degli hash MD5.
 * Di seguito, l'elenco dei test inclusi con una breve descrizione:
 * 
 * <ul>
 * <li>{@link #testComputeMD5_validInput()}: Verifica che il metodo
 * {@code computeMD5} calcoli correttamente l'hash MD5 per un array di byte valido.</li>
 * 
 * <li>{@link #testComputeMD5_emptyInput()}: Verifica che il metodo
 * {@code computeMD5} restituisca il corretto hash MD5 per un array di byte vuoto.</li>
 * 
 * <li>{@link #testComputeMD5_nullInput()}: Verifica che il metodo
 * {@code computeMD5} lanci una {@code NullPointerException} quando l'input è {@code null}.</li>
 * </ul>
 */
class HashUtilTest {

    @Test
    void testComputeMD5_validInput() {
        byte[] input = "Hello, World!".getBytes();
        String expectedHash = "65a8e27d8879283831b664bd8b7f0ad4"; // Hash calcolato in anticipo

        String actualHash = HashUtil.computeMD5(input);

        assertNotNull(actualHash, "L'hash calcolato non dovrebbe essere null.");
        assertEquals(expectedHash, actualHash, "L'hash calcolato non corrisponde all'atteso.");
    }

    @Test
    void testComputeMD5_emptyInput() {
        byte[] input = new byte[0];
        String expectedHash = "d41d8cd98f00b204e9800998ecf8427e"; // Hash MD5 per array vuoto

        String actualHash = HashUtil.computeMD5(input);

        assertNotNull(actualHash, "L'hash calcolato non dovrebbe essere null.");
        assertEquals(expectedHash, actualHash, "L'hash calcolato per l'input vuoto non corrisponde all'atteso.");
    }

    @Test
    void testComputeMD5_nullInput() {
        assertThrows(NullPointerException.class, () -> HashUtil.computeMD5(null),
                "Dovrebbe lanciare NullPointerException se l'input è null.");
    }
}