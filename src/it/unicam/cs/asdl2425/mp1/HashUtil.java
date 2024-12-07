package it.unicam.cs.asdl2425.mp1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe fornita di utilità per calcolare gli hash MD5.
 * 
 * @author Luca Tesei, Marco Caputo
 */
public class HashUtil {

    /**
     * Calcola l'hash del dato fornito utilizzando MD5.
     *
     * @param data
     *                 il dato da hashare.
     * @return l'hash come stringa esadecimale.
     */
    public static String dataToHash(Object data) {
        return HashUtil.computeMD5(intToBytes(data.hashCode()));
    }

    /**
     * Calcola l'hash di un array di byte fornito utilizzando MD5.
     *
     * @param input
     *                  l'array di byte di cui calcolare l'hash.
     * @return l'hash come stringa esadecimale.
     * @throws RuntimeException
     *                              se l'algoritmo di hashing non è disponibile.
     */
    public static String computeMD5(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input);
            StringBuilder hashString = new StringBuilder();
            for (byte b : hashBytes) {
                hashString.append(String.format("%02x", b));
            }
            return hashString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    /**
     * Converte un valore intero (int) in un array di byte.
     *
     * Questo metodo suddivide l'intero in quattro byte, rappresentandolo nel
     * formato big-endian, ovvero dal byte più significativo (MSB) al byte meno
     * significativo (LSB). Questo è utile per trasformare un valore numerico in
     * un formato compatibile con algoritmi di hashing o protocolli di
     * comunicazione che richiedono una rappresentazione in byte.
     *
     * @param value
     *                  il valore intero da convertire.
     * @return un array di byte che rappresenta il valore intero.
     */
    public static byte[] intToBytes(int value) {
        return new byte[] { (byte) (value >> 24), (byte) (value >> 16),
                (byte) (value >> 8), (byte) value };
    }
}