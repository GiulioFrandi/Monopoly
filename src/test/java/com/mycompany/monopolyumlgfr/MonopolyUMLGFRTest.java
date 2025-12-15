/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.monopolyumlgfr;

import com.mycompany.monopolyumlgfr.autenticazione.*;
import com.mycompany.monopolyumlgfr.gioco.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Classe di test per la classe principale MonopolyUMLGFR.
 * <p>
 * In questa suite non viene testata la GUI Swing, ma si verifica
 * la corretta inizializzazione del {@link Sistema} e la gestione
 * di una {@link Partita} con i relativi {@link Giocatore}.
 * </p>
 */
public class MonopolyUMLGFRTest {

    /**
     * Verifica che il {@link Sistema} venga correttamente inizializzato
     * con un {@link DatabaseUtenti}.
     * <p>
     * Il test controlla che l'oggetto sistema non sia null dopo la creazione.
     * </p>
     */
    @Test
    public void testSistemaInizializzato() {
        DatabaseUtenti db = new DatabaseUtenti("utenti_test.xml");
        Sistema sistema = new Sistema(db);
        assertNotNull(sistema, "Il sistema deve essere inizializzato");
    }

    /**
     * Verifica la creazione di una {@link Partita} con un {@link Giocatore}.
     * <p>
     * Il test associa un giocatore alla partita, avvia la partita
     * e controlla che lo stato dei giocatori contenga lo username
     * del giocatore aggiunto.
     * </p>
     */
    @Test
    public void testCreazionePartitaConGiocatore() {
        DatabaseUtenti db = new DatabaseUtenti("utenti_test.xml");
        Sistema sistema = new Sistema(db);

        Utente utente = new Utente("Mario Rossi", "mario", "password");
        Giocatore g = new Giocatore(utente);

        Partita partita = new Partita(sistema);
        partita.associaGiocatori(g);
        partita.avviaPartita();

        assertTrue(partita.getStatoGiocatoriAsString().contains("mario"),
                   "La partita deve contenere il giocatore 'mario'");
    }

    /**
     * Simula il comportamento di un {@link LoginSuccessListener}.
     * <p>
     * Quando un utente viene autenticato con successo, il listener
     * crea un nuovo {@link Giocatore}, lo associa a una {@link Partita}
     * e avvia la partita. Il test verifica che lo username dell'utente
     * sia presente nello stato dei giocatori della partita.
     * </p>
     */
    @Test
    public void testLoginSuccessListenerCallback() {
        DatabaseUtenti db = new DatabaseUtenti("utenti_test.xml");
        Sistema sistema = new Sistema(db);

        // Simuliamo il listener di login
        LoginSuccessListener listener = utente -> {
            Giocatore g = new Giocatore(utente);
            Partita partita = new Partita(sistema);
            partita.associaGiocatori(g);
            partita.avviaPartita();

            assertTrue(partita.getStatoGiocatoriAsString().contains(utente.getUsername()),
                       "Il giocatore autenticato deve essere associato alla partita");
        };

        Utente utente = new Utente("Mario Rossi", "mario", "password");
        listener.onLoginSuccess(utente);
    }
}
