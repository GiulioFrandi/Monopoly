/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.monopolyumlgfr.autenticazione;

import org.junit.jupiter.api.*;
import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe di test per {@link DatabaseUtenti}.
 * <p>
 * Questa suite verifica le funzionalità di persistenza degli utenti
 * su file XML: salvataggio, caricamento e gestione di più utenti.
 * </p>
 * <p>
 * Ogni test utilizza un file temporaneo "utenti_test.xml" che viene
 * creato e distrutto prima/dopo l'esecuzione, per garantire isolamento.
 * </p>
 * 
 * @author giuli
 */
public class DatabaseUtentiTest {

    /** Nome del file XML temporaneo usato nei test. */
    private static final String TEST_FILE = "utenti_test.xml";

    /** Istanza del database utenti da testare. */
    private DatabaseUtenti db;

    /**
     * Inizializza l'ambiente di test prima di ogni metodo.
     * <p>
     * Crea un file XML vuoto con root &lt;utenti&gt; e inizializza
     * l'istanza di {@link DatabaseUtenti} con tale file.
     * </p>
     *
     * @throws Exception se si verificano errori di I/O
     */
    @BeforeEach
    public void setUp() throws Exception {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        // Scriviamo un root element <utenti>
        try (java.io.FileWriter fw = new java.io.FileWriter(file)) {
            fw.write("<utenti></utenti>");
        }

        db = new DatabaseUtenti(TEST_FILE);
    }

    /**
     * Pulisce l'ambiente di test dopo ogni metodo.
     * <p>
     * Cancella il file XML temporaneo se esiste.
     * </p>
     */
    @AfterEach
    public void tearDown() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Verifica che un utente salvato venga correttamente caricato.
     * <p>
     * - Salva un utente nel database. <br>
     * - Carica la lista utenti. <br>
     * - Controlla che la lista non sia vuota e che i campi
     *   (username, nome, password) corrispondano ai valori attesi.
     * </p>
     */
    @Test
    public void testSalvaUtenteAndCaricaUtenti() {
        Utente u = new Utente("Mario Rossi", "mario", "password123");
        db.salvaUtente(u);

        List<Utente> utenti = db.caricaUtenti();
        assertFalse(utenti.isEmpty(), "La lista utenti non deve essere vuota");
        assertEquals("mario", utenti.get(0).getUsername(), "Lo username deve corrispondere");
        assertEquals("Mario Rossi", utenti.get(0).getNome(), "Il nome deve corrispondere");
        assertEquals("password123", utenti.get(0).getPassword(), "La password deve corrispondere");
    }

    /**
     * Verifica il comportamento del caricamento su file vuoto.
     * <p>
     * - Non salva alcun utente. <br>
     * - Carica la lista utenti. <br>
     * - Controlla che la lista sia vuota.
     * </p>
     */
    @Test
    public void testCaricaUtentiVuoto() {
        List<Utente> utenti = db.caricaUtenti();
        assertTrue(utenti.isEmpty(), "Se non ci sono utenti, la lista deve essere vuota");
    }

    /**
     * Verifica il salvataggio e caricamento di più utenti.
     * <p>
     * - Salva due utenti distinti. <br>
     * - Carica la lista utenti. <br>
     * - Controlla che la lista contenga due elementi e che
     *   il secondo utente sia quello atteso.
     * </p>
     */
    @Test
    public void testSalvaMultipliUtenti() {
        Utente u1 = new Utente("Mario Rossi", "mario", "pwd1");
        Utente u2 = new Utente("Luigi Bianchi", "luigi", "pwd2");

        db.salvaUtente(u1);
        db.salvaUtente(u2);

        List<Utente> utenti = db.caricaUtenti();
        assertEquals(2, utenti.size(), "Devono esserci due utenti salvati");
        assertEquals("luigi", utenti.get(1).getUsername(), "Il secondo utente deve essere Luigi");
    }
}
