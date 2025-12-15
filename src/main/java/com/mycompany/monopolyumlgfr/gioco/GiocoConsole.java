/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.monopolyumlgfr.gioco;

import java.util.Scanner;

/**
 * Interfaccia testuale del gioco Monopoly UML GFR.
 * <p>
 * Questa classe fornisce un ciclo di interazione su console che permette
 * al giocatore di:
 * <ul>
 *   <li>Eseguire i turni della partita.</li>
 *   <li>Abbandonare la partita.</li>
 *   <li>Visualizzare lo stato dei giocatori.</li>
 *   <li>Visualizzare il tabellone in formato testuale.</li>
 *   <li>Uscire dal gioco.</li>
 * </ul>
 * <!-- </p> -->
 *
 * <h2>Ruolo nell'architettura</h2>
 * <ul>
 *   <li>La logica di gioco è gestita da {@link Partita}, che invia i comandi ai {@link Giocatore}.</li>
 *   <li>{@code GiocoConsole} si occupa esclusivamente della presentazione testuale e dell'interazione utente.</li>
 *   <li>Utilizza {@link Scanner} per leggere input da tastiera.</li>
 * </ul>
 *
 * <h2>Note</h2>
 * <ul>
 *   <li>Il ciclo di gioco rimane attivo finché l'utente non seleziona l'opzione "Esci".</li>
 *   <li>Ogni azione aggiorna lo stato della partita e lo mostra sulla console.</li>
 *   <li>I messaggi informativi sono prefissati con {@code [INFO]} per distinguerli dall'output di gioco.</li>
 * </ul>
 *
 * @author Giulio
 */
public class GiocoConsole {

    /**
     * Partita associata alla UI testuale.
     */
    private final Partita partita;

    /**
     * Scanner per leggere input da tastiera.
     */
    private final Scanner scanner;

    /**
     * Costruttore.
     *
     * @param partita la partita da gestire
     */
    public GiocoConsole(Partita partita) {
        this.partita = partita;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Avvia il ciclo di interazione testuale.
     * <p>
     * Mostra un menu con opzioni per:
     * <ul>
     *   <li>Eseguire un turno.</li>
     *   <li>Abbandonare la partita.</li>
     *   <li>Mostrare lo stato dei giocatori.</li>
     *   <li>Mostrare il tabellone.</li>
     *   <li>Uscire dal gioco.</li>
     * </ul>
     * <!-- </p> -->
     */
    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Monopoly Console ===");
            System.out.println("1. Esegui Turno");
            System.out.println("2. Abbandona Partita");
            System.out.println("3. Mostra Stato Giocatori");
            System.out.println("4. Mostra Tabellone");
            System.out.println("5. Esci");
            System.out.print("Scelta: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    partita.eseguiTurno();
                    mostraMessaggio("Turno eseguito.");
                    aggiornaUI();
                    break;
                case "2":
                    partita.abbandonaGiocatoreCorrente();
                    mostraMessaggio("Hai abbandonato la partita.");
                    aggiornaUI();
                    break;
                case "3":
                    System.out.println(partita.getStatoGiocatoriAsString());
                    break;
                case "4":
                    Giocatore corrente = partita.getGiocatoreCorrente();
                    System.out.println(partita.getTabelloneAsText(corrente));
                    break;
                case "5":
                    running = false;
                    System.out.println("Chiusura gioco.");
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }

    /**
     * Aggiorna la UI testuale mostrando lo stato corrente dei giocatori.
     */
    private void aggiornaUI() {
        System.out.println("\n--- Stato Giocatori ---");
        System.out.println(partita.getStatoGiocatoriAsString());
    }

    /**
     * Mostra un messaggio informativo sulla console.
     *
     * @param msg il messaggio da mostrare
     */
    public void mostraMessaggio(String msg) {
        System.out.println("[INFO] " + msg);
    }
}
