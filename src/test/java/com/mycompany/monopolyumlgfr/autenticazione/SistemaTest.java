/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.monopolyumlgfr.autenticazione;

import com.mycompany.monopolyumlgfr.gioco.Giocatore;
import com.mycompany.monopolyumlgfr.gioco.Partita;
import org.junit.jupiter.api.*;
import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe di test per {@link Sistema}.
 * <p>
 * Questa suite verifica le funzionalità principali del sistema di autenticazione:
 * <ul>
 *   <li>Registrazione di nuovi utenti nel database XML.</li>
 *   <li>Validazione delle credenziali (corrette ed errate).</li>
 *   <li>Alias del metodo di autenticazione.</li>
 *   <li>Notifica di abbandono da parte di un giocatore.</li>
 *   <li>Notifica di fine partita.</li>
 * </ul>
 * <!-- </p> -->
 */
public class SistemaTest {

    /** Nome del file XML temporaneo usato nei test. */
    private static final String TEST_FILE = "utenti_sistema_test.xml";

    /** Database utenti fittizio per i test. */
    private DatabaseUtenti db;

    /** Istanza del sistema da testare. */
    private Sistema sistema;

    /**
     * Inizializza l'ambiente di test prima di ogni metodo.
     * <p>
     * Crea un file XML vuoto con root &lt;utenti&gt; e inizializza
     * il {@link Sistema} con tale database.
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

        try (java.io.FileWriter fw = new java.io.FileWriter(file)) {
            fw.write("<utenti></utenti>");
        }

        db = new DatabaseUtenti(TEST_FILE);
        sistema = new Sistema(db);
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
     * Verifica la registrazione di un nuovo giocatore.
     * <p>
     * - Registra un utente nel sistema. <br>
     * - Carica gli utenti dal database. <br>
     * - Controlla che la lista contenga un solo utente
     *   e che lo username corrisponda.
     * </p>
     */
    @Test
    public void testRegistraGiocatore() {
        sistema.registraGiocatore("Mario Rossi", "mario", "pwd123");

        List<Utente> utenti = db.caricaUtenti();
        assertEquals(1, utenti.size(), "Deve esserci un utente registrato");
        assertEquals("mario", utenti.get(0).getUsername(), "Lo username deve corrispondere");
    }

    /**
     * Verifica la validazione di credenziali corrette.
     * <p>
     * - Registra un utente. <br>
     * - Valida le credenziali corrette. <br>
     * - Controlla che venga restituito un utente non nullo
     *   e che il nome corrisponda.
     * </p>
     */
    @Test
    public void testValidaCredenzialiCorrette() {
        sistema.registraGiocatore("Mario Rossi", "mario", "pwd123");
        Utente u = sistema.validaCredenziali("mario", "pwd123");
        assertNotNull(u, "Le credenziali corrette devono restituire un utente");
        assertEquals("Mario Rossi", u.getNome(), "Il nome deve corrispondere");
    }

    /**
     * Verifica la validazione di credenziali errate.
     * <p>
     * - Registra un utente. <br>
     * - Valida credenziali con password errata. <br>
     * - Controlla che venga restituito {@code null}.
     * </p>
     */
    @Test
    public void testValidaCredenzialiErrate() {
        sistema.registraGiocatore("Mario Rossi", "mario", "pwd123");
        Utente u = sistema.validaCredenziali("mario", "wrongpwd");
        assertNull(u, "Credenziali errate devono restituire null");
    }

    /**
     * Verifica che {@code autenticaGiocatore} si comporti come {@code validaCredenziali}.
     * <p>
     * - Registra un utente. <br>
     * - Autentica con credenziali corrette. <br>
     * - Controlla che venga restituito un utente non nullo.
     * </p>
     */
    @Test
    public void testAutenticaGiocatoreAlias() {
        sistema.registraGiocatore("Mario Rossi", "mario", "pwd123");
        Utente u = sistema.autenticaGiocatore("mario", "pwd123");
        assertNotNull(u, "AutenticaGiocatore deve comportarsi come validaCredenziali");
    }

    /**
     * Verifica la notifica di abbandono da parte di un giocatore.
     * <p>
     * - Crea un utente e un giocatore. <br>
     * - Invoca {@code notificaAbbandono}. <br>
     * - Controlla che non vengano sollevate eccezioni.
     * </p>
     */
    @Test
    public void testNotificaAbbandono() {
        Utente u = new Utente("Mario Rossi", "mario", "pwd123");
        Giocatore g = new Giocatore(u);
        assertDoesNotThrow(() -> sistema.notificaAbbandono(g),
                "La notifica di abbandono non deve sollevare eccezioni");
    }

    /**
     * Verifica la notifica di fine partita.
     * <p>
     * - Crea una partita. <br>
     * - Invoca {@code notificaFinePartita}. <br>
     * - Controlla che non vengano sollevate eccezioni.
     * </p>
     */
    @Test
    public void testNotificaFinePartita() {
        Partita p = new Partita(sistema);
        assertDoesNotThrow(() -> sistema.notificaFinePartita(p),
                "La notifica di fine partita non deve sollevare eccezioni");
    }
}
