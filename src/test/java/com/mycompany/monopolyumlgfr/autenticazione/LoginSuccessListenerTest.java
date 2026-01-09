/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.monopolyumlgfr.autenticazione;

import com.mycompany.monopolyumlgfr.gioco.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Classe di test per l'interfaccia {@link LoginSuccessListener}.
 * <p>
 * Questo test verifica che il callback {@code onLoginSuccess}
 * riceva correttamente un {@link Utente} autenticato e consenta
 * di avviare una {@link Partita} con il relativo {@link Giocatore}.
 * </p>
 * <p>
 * In particolare, si controlla che dopo l'invocazione del listener:
 * <ul>
 *   <li>La partita sia avviata.</li>
 *   <li>Lo stato dei giocatori contenga lo username dell'utente autenticato.</li>
 * </ul>
 * <!-- </p> -->
 */
public class LoginSuccessListenerTest {

    /**
     * Verifica il comportamento del metodo {@code onLoginSuccess}.
     * <p>
     * - Simula un utente autenticato. <br>
     * - Crea un {@link Sistema} fittizio con un {@link DatabaseUtenti}. <br>
     * - Implementa il listener che associa l'utente a un {@link Giocatore},
     *   avvia una {@link Partita} e verifica che il giocatore sia presente
     *   nello stato della partita. <br>
     * - Invoca il callback con l'utente simulato.
     * </p>
     */
    @Test
    public void testOnLoginSuccessAvviaPartita() {
        // Simuliamo un utente autenticato
        Utente utente = new Utente("Mario Rossi", "mario", "pwd123");

        // Simuliamo un sistema fittizio
        Sistema sistema = new Sistema(new DatabaseUtenti("utenti_test.xml"));

        // Implementazione del listener
        LoginSuccessListener listener = u -> {
            Giocatore g = new Giocatore(u);
            Partita partita = new Partita(sistema);
            partita.aggiungiGiocatore(g);
            partita.avviaPartita();

            // Verifica che la partita sia attiva e contenga il giocatore
            assertTrue(partita.getStatoGiocatoriAsString().contains("mario"),
                       "Il giocatore 'mario' deve essere presente nella partita");
        };

        // Invocazione del callback
        listener.onLoginSuccess(utente);
    }
}

