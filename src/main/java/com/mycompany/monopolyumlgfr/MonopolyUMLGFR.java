package com.mycompany.monopolyumlgfr;

import com.mycompany.monopolyumlgfr.autenticazione.*;
import com.mycompany.monopolyumlgfr.gioco.*;

import java.util.List;
import java.util.Scanner;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Classe principale che avvia il gioco Monopoly UML GFR.
 * <p>
 * Questa classe funge da entry point dell'applicazione e coordina
 * l'inizializzazione del sistema, la scelta dell'interfaccia utente
 * e l'avvio della partita.
 * </p>
 *
 * <h2>Funzionalità principali</h2>
 * <ul>
 *   <li>Inizializza il {@link DatabaseUtenti} e il {@link Sistema} per la gestione degli utenti.</li>
 *   <li>Mostra un menu iniziale che consente di scegliere tra interfaccia grafica (Swing) e console.</li>
 *   <li>Nel caso console:
 *     <ul>
 *       <li>Avvia {@link AutentificazioneConsole} per login/registrazione.</li>
 *       <li>Riceve l'oggetto {@link Utente} autenticato.</li>
 *       <li>Crea un {@link Giocatore} e una {@link Partita} associata.</li>
 *       <li>Avvia l'interfaccia testuale {@link GiocoConsole}.</li>
 *     </ul>
 *   </li>
 *   <li>Nel caso grafico:
 *     <ul>
 *       <li>Avvia {@link AutentificazioneUI} per login/registrazione.</li>
 *       <li>Utilizza un {@link LoginSuccessListener} per ricevere l'utente autenticato.</li>
 *       <li>Crea un {@link Giocatore} e una {@link Partita} associata.</li>
 *       <li>Avvia l'interfaccia grafica {@link GiocoUI}.</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * <h2>Note architetturali</h2>
 * <ul>
 *   <li>La classe incapsula la logica di bootstrap dell'applicazione.</li>
 *   <li>Non contiene logica di gioco, delegata a {@link Partita} e alle classi del dominio.</li>
 *   <li>Permette di mantenere separata la gestione dell'autenticazione e dell'interfaccia utente.</li>
 * </ul>
 *
 * @author Giulio
 */
public class MonopolyUMLGFR {

    /**
     * Metodo principale che avvia l'applicazione.
     * <p>
     * Inizializza il sistema e mostra un menu per scegliere
     * l'interfaccia utente (grafica o console).
     * </p>
     *
     * @param args argomenti da linea di comando (non utilizzati)
     */
    public static void main(String[] args) {

        // 1. Inizializza database utenti
        DatabaseUtenti db = new DatabaseUtenti("utenti.xml");
        Sistema sistema = new Sistema(db);

        // 2. Chiedi all'utente quale interfaccia usare
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Monopoly UML GFR ===");
        System.out.println("Scegli interfaccia:");
        System.out.println("1. Grafica (Swing)");
        System.out.println("2. Console (testuale)");
        System.out.print("Scelta: ");
        String scelta = scanner.nextLine();

        if ("2".equals(scelta)) {
            // --- INTERFACCIA CONSOLE MULTIPLAYER ---
            AutentificazioneConsole loginConsole = new AutentificazioneConsole(sistema);

            System.out.println("Login multiplayer (2–6 giocatori).");
            List<Utente> utenti = loginConsole.startMultiplayer(); // nuovo metodo console per multi login

            if (utenti != null && utenti.size() >= 2) {
                Partita partita = new Partita(sistema);

                for (Utente u : utenti) {
                    Giocatore g = new Giocatore(u);
                    partita.aggiungiGiocatore(g);
                }

                partita.avviaPartita();

                GiocoConsole giocoConsole = new GiocoConsole(partita);
                giocoConsole.start();
            } else {
                System.out.println("Numero insufficiente di giocatori. Programma terminato.");
            }

        } else {
            // --- INTERFACCIA GRAFICA ---
            AutentificazioneUI loginUI = new AutentificazioneUI(sistema, utente -> {
                Giocatore g = new Giocatore(utente);
                Partita partita = new Partita(sistema);
                partita.aggiungiGiocatore(g);
                partita.avviaPartita();

                javax.swing.SwingUtilities.invokeLater(() -> {
                    GiocoUI giocoUI = new GiocoUI(partita);
                    giocoUI.setVisible(true);
                });
            });

            javax.swing.SwingUtilities.invokeLater(() -> {
                loginUI.setVisible(true);
            });
        }
    }
}
