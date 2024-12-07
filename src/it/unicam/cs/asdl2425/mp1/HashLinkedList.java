package it.unicam.cs.asdl2425.mp1;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Una classe che rappresenta una lista concatenata con il calcolo degli hash
 * MD5 per ciascun elemento. Ogni nodo della lista contiene il dato originale di
 * tipo generico T e il relativo hash calcolato utilizzando l'algoritmo MD5.
 *
 * <p>
 * La classe supporta le seguenti operazioni principali:
 * <ul>
 * <li>Aggiungere un elemento in testa alla lista</li>
 * <li>Aggiungere un elemento in coda alla lista</li>
 * <li>Rimuovere un elemento dalla lista in base al dato</li>
 * <li>Recuperare una lista ordinata di tutti gli hash contenuti nella
 * lista</li>
 * <li>Costruire una rappresentazione testuale della lista</li>
 * </ul>
 *
 * <p>
 * Questa implementazione include ottimizzazioni come il mantenimento di un
 * riferimento all'ultimo nodo della lista (tail), che rende l'inserimento in
 * coda un'operazione O(1).
 *
 * <p>
 * La classe utilizza la classe HashUtil per calcolare l'hash MD5 dei dati.
 *
 * @param <T>
 *                il tipo generico dei dati contenuti nei nodi della lista.
 * 
 * @author Luca Tesei, Marco Caputo (template) **INSERIRE NOME, COGNOME ED EMAIL
 *         xxxx@studenti.unicam.it DELLO STUDENTE** (implementazione)
 * 
 */
public class HashLinkedList<T> implements Iterable<T> {
    private Node head; // Primo nodo della lista

    private Node tail; // Ultimo nodo della lista

    private int size; // Numero di nodi della lista

    private int numeroModifiche; // Numero di modifiche effettuate sulla lista
                                 // per l'implementazione dell'iteratore
                                 // fail-fast

    public HashLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.numeroModifiche = 0;
    }

    /**
     * Restituisce il numero attuale di nodi nella lista.
     *
     * @return il numero di nodi nella lista.
     */
    public int getSize() {
        return size;
    }

    /**
     * Rappresenta un nodo nella lista concatenata.
     */
    private class Node {
        String hash; // Hash del dato

        T data; // Dato originale

        Node next;

        Node(T data) {
            this.data = data;
            this.hash = HashUtil.dataToHash(data);
            this.next = null;
        }
    }

    /**
     * Aggiunge un nuovo elemento in testa alla lista.
     *
     * @param data
     *                 il dato da aggiungere.
     */
    public void addAtHead(T data) {
        if(head == null){
            Node n = new Node(data);
            head = n;
            tail = n;
        } else {
            Node n = new Node(data);
            n.next = head;
            head = n;
        }
        numeroModifiche++;
        size++;
    }

    /**
     * Aggiunge un nuovo elemento in coda alla lista.
     *
     * @param data
     *                 il dato da aggiungere.
     */
    public void addAtTail(T data) {
        if(tail == null){
            Node n = new Node(data);
            head = n;
            tail = n;
        } else {
            Node n = new Node(data);
            tail.next = n;
            tail = n;
        }
        numeroModifiche++;
        size++;
    }

    /**
     * Restituisce un'ArrayList contenente tutti gli hash nella lista in ordine.
     *
     * @return una lista con tutti gli hash della lista.
     */
    public ArrayList<String> getAllHashes() {
        ArrayList<String> list = new ArrayList<>();
        Node n = head;
        while(n != null){
            list.add(n.hash);
            n = n.next;
        }
        return list;
    }

    /**
     * Costruisce una stringa contenente tutti i nodi della lista, includendo
     * dati e hash. La stringa dovrebbe essere formattata come nel seguente
     * esempio:
     * 
     * <pre>
     *     Dato: StringaDato1, Hash: 5d41402abc4b2a76b9719d911017c592
     *     Dato: SteringaDato2, Hash: 7b8b965ad4bca0e41ab51de7b31363a1
     *     ...
     *     Dato: StringaDatoN, Hash: 2c6ee3d301aaf375b8f026980e7c7e1c
     * </pre>
     *
     * @return una rappresentazione testuale di tutti i nodi nella lista.
     */
    public String buildNodesString() {
        String result = "";
        Node n = head;
        while (n != null) {
            result += "Dato: " + n.data +  ", Hash: " + n.hash + "\n";
            n = n.next;
        }
        return result ;
    }

    /**
     * Rimuove il primo elemento nella lista che contiene il dato specificato.
     *
     * @param data
     *                 il dato da rimuovere.
     * @return true se l'elemento è stato trovato e rimosso, false altrimenti.
     */
    public boolean remove(T data) {
        Node n = head;
        Node prev = null;
        while(n != null){
            if(n.data.equals(data)){
                if(prev == null){
                    head = n.next;
                } else {
                    prev.next = n.next;
                }
                if(n.next == null){
                    tail = prev;
                } else {
                    if(prev != null){
                        prev.next = n.next;
                    }
                }
                numeroModifiche++;
                size--;
                return true;
            }
            prev = n;
            n = n.next;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    /**
     * Classe che realizza un iteratore fail-fast per HashLinkedList.
     */
    private class Itr implements Iterator<T> {

        Node node;
        int modifiche;

        private Itr() {
            node = head;
            modifiche = numeroModifiche;
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public T next() {
            if(numeroModifiche != modifiche){
                throw new ConcurrentModificationException();
            }
            T t = node.data;
            node = node.next;
            return t;
        }
    }
}