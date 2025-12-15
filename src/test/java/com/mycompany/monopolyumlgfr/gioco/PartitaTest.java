/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.monopolyumlgfr.gioco;

import com.mycompany.monopolyumlgfr.autenticazione.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe di test per {@link Partita}.
 * <p>
 * Questa suite verifica il comportamento principale della classe Partita:
 * <ul>
 *   <li>Avvio della partita e associazione dei giocatori.</li>
 *   <li>Esecuzione di un turno con movimento della pedina.</li>
 *   <li>Abbandono del giocatore corrente.</li>
 *   <li>Terminazione della partita.</li>
 *   <li>Reporting dello stato dei giocatori.</li>
 *   <li>Reporting del tabellone in formato HTML.</li>
 * </ul>
 * <!-- </p> -->
 */
public class PartitaTest {

    /** Sistema fittizio usato per la partita. */
    private Sistema sistema;

    /** Istanza della partita da testare. */
    private Partita partita;

    /** Utente di test. */
    private Utente utente;

    /** Giocatore associato all'utente di test. */
    private Giocatore giocatore;

    /**
     * Inizializza l'ambiente di test prima di ogni metodo.
     * <p>
     * - Crea un sistema con database fittizio. <br>
     * - Crea una partita e un utente. <br>
     * - Associa il giocatore alla partita.
     * </p>
     */
    @BeforeEach
    public void setUp() {
        sistema = new Sistema(new DatabaseUtenti("utenti_test.xml"));
        partita = new Partita(sistema);
        utente = new Utente("Mario Rossi", "mario", "pwd123");
        giocatore = new Giocatore(utente);
        partita.associaGiocatori(giocatore);
    }

    /**
     * Verifica l'avvio della partita.
     * <p>
     * - Avvia la partita. <br>
     * - Controlla che lo stato dei giocatori contenga lo username "mario".
     * </p>
     */
    @Test
    public void testAvvioPartita() {
        partita.avviaPartita();
        assertTrue(partita.getStatoGiocatoriAsString().contains("mario"),
                   "La partita deve contenere il giocatore 'mario'");
    }

    /**
     * Verifica l'esecuzione di un turno.
     * <p>
     * - Crea un nuovo sistema, partita e giocatore. <br>
     * - Avvia la partita. <br>
     * - Esegue un turno. <br>
     * - Controlla che la posizione del giocatore sia valida (0–39)
     *   e diversa da quella iniziale.
     * </p>
     */
    @Test
    public void testEseguiTurno() {
        Sistema sistema = new Sistema(new DatabaseUtenti("utenti_test.xml"));
        Partita partita = new Partita(sistema);
        Utente u = new Utente("Mario Rossi", "mario", "pwd123");
        Giocatore g = new Giocatore(u);

        partita.associaGiocatori(g);
        partita.avviaPartita();

        int posizioneIniziale = g.getPosizione();
        partita.eseguiTurno();

        assertTrue(g.getPosizione() >= 0 && g.getPosizione() < 40,
                   "La posizione deve essere valida sul tabellone");
        assertNotEquals(posizioneIniziale, g.getPosizione(),
                        "La posizione dovrebbe cambiare dopo un turno");
    }

    /**
     * Verifica l'abbandono del giocatore corrente.
     * <p>
     * - Avvia la partita. <br>
     * - Invoca {@code abbandonaGiocatoreCorrente}. <br>
     * - Controlla che lo stato dei giocatori non contenga più "mario".
     * </p>
     */
    @Test
    public void testAbbandonaGiocatoreCorrente() {
        partita.avviaPartita();
        partita.abbandonaGiocatoreCorrente();

        assertTrue(partita.getStatoGiocatoriAsString().isEmpty() ||
                   !partita.getStatoGiocatoriAsString().contains("mario"),
                   "Il giocatore deve essere rimosso dalla partita");
    }

    /**
     * Verifica la terminazione della partita.
     * <p>
     * - Avvia la partita. <br>
     * - Termina la partita. <br>
     * - Esegue un turno (che non deve fare nulla). <br>
     * - Controlla che lo stato dei giocatori non sia vuoto,
     *   perché rimane disponibile per reporting.
     * </p>
     */
    @Test
    public void testTerminaPartita() {
        partita.avviaPartita();
        partita.terminaPartita();

        partita.eseguiTurno(); // non deve fare nulla
        assertFalse(partita.getStatoGiocatoriAsString().isEmpty(),
                    "La partita è terminata ma i giocatori rimangono per reporting");
    }

    /**
     * Verifica il reporting dello stato dei giocatori.
     * <p>
     * - Avvia la partita. <br>
     * - Ottiene lo stato dei giocatori. <br>
     * - Controlla che lo stato includa lo username "mario".
     * </p>
     */
    @Test
    public void testReportingStatoGiocatori() {
        partita.avviaPartita();
        String stato = partita.getStatoGiocatoriAsString();
        assertTrue(stato.contains("mario"), "Lo stato deve includere il giocatore 'mario'");
    }

    /**
     * Verifica il reporting del tabellone in formato HTML.
     * <p>
     * - Avvia la partita. <br>
     * - Ottiene il tabellone come stringa HTML. <br>
     * - Controlla che la stringa inizi con {@code <html>}.
     * </p>
     */
    @Test
    public void testReportingTabelloneHTML() {
        partita.avviaPartita();
        String html = partita.getTabelloneAsHTML();
        assertTrue(html.startsWith("<html>"), "Il tabellone deve essere restituito in formato HTML");
    }
}
