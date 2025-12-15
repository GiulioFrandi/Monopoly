/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.monopolyumlgfr.autenticazione;

import java.util.Scanner;

/**
 * Interfaccia testuale per l'autenticazione e la registrazione degli utenti.
 * <p>
 * Questa classe sostituisce la UI Swing con una semplice interazione
 * via console. Permette:
 * <ul>
 *   <li>Login di un utente esistente tramite {@link Sistema}.</li>
 *   <li>Registrazione di un nuovo utente.</li>
 * </ul>
 * <!-- </p> -->
 * Dopo un login valido, restituisce l'oggetto {@link Utente} al chiamante.
 * 
 * @author Giulio
 */
public class AutentificazioneConsole {

    private final Sistema sistema;
    private final Scanner scanner;

    /**
     * Costruttore.
     *
     * @param sistema backend per la gestione degli utenti
     */
    public AutentificazioneConsole(Sistema sistema) {
        this.sistema = sistema;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Avvia il ciclo di interazione testuale.
     * <p>
     * Mostra un menu con tre opzioni: login, registrazione e uscita.
     * Il ciclo termina quando l'utente effettua un login valido
     * oppure sceglie di uscire.
     * </p>
     *
     * @return l'utente autenticato, oppure {@code null} se l'utente ha scelto di uscire
     */
    public Utente start() {
        boolean running = true;
        Utente utenteAutenticato = null;

        while (running) {
            System.out.println("\n=== Autenticazione Monopoly (Console) ===");
            System.out.println("1. Login");
            System.out.println("2. Registrati");
            System.out.println("3. Esci");
            System.out.print("Scelta: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    utenteAutenticato = handleLogin();
                    if (utenteAutenticato != null) {
                        running = false; // chiudi ciclo dopo login valido
                    }
                    break;
                case "2":
                    handleRegistration();
                    break;
                case "3":
                    running = false;
                    System.out.println("Chiusura programma.");
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        }

        return utenteAutenticato;
    }

    /**
     * Gestisce il login di un utente.
     *
     * @return l'utente autenticato se le credenziali sono valide, altrimenti {@code null}
     */
    private Utente handleLogin() {
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        Utente utente = sistema.validaCredenziali(user, pass);
        if (utente != null) {
            System.out.println("Login effettuato!");
            return utente;
        } else {
            System.out.println("Credenziali non valide.");
            return null;
        }
    }

    /**
     * Gestisce la registrazione di un nuovo utente.
     */
    private void handleRegistration() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        if (!nome.isEmpty() && !user.isEmpty() && !pass.isEmpty()) {
            sistema.registraGiocatore(nome, user, pass);
            System.out.println("Registrazione completata! Ora puoi fare login.");
        } else {
            System.out.println("Compila tutti i campi per registrarti.");
        }
    }
}
