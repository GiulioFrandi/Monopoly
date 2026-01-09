package com.mycompany.monopolyumlgfr.autenticazione;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Interfaccia testuale per l'autenticazione e registrazione degli utenti.
 * Supporta multiplayer 2–6 giocatori. Dopo un login valido, restituisce
 * l'oggetto {@link Utente} o lista di utenti al chiamante.
 *
 * @author Giulio
 */
public class AutentificazioneConsole {

    private final Sistema sistema;
    private final Scanner scanner;

    public AutentificazioneConsole(Sistema sistema) {
        this.sistema = sistema;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Login singolo (come prima, mantiene compatibilità con codice precedente)
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
                        running = false;
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
     * Login multiplayer (2–6 giocatori) Restituisce la lista di utenti
     * autenticati
     */
    public List<Utente> startMultiplayer() {
        List<Utente> utenti = new ArrayList<>();
        System.out.println("\n=== Login multiplayer (2–6 giocatori) ===");

        int numGiocatori = 0;
        while (numGiocatori < 2 || numGiocatori > 6) {
            System.out.print("Inserisci numero giocatori (2–6): ");
            try {
                numGiocatori = Integer.parseInt(scanner.nextLine());
                if (numGiocatori < 2 || numGiocatori > 6) {
                    System.out.println("Numero non valido. Deve essere tra 2 e 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido.");
            }
        }

        for (int i = 1; i <= numGiocatori; i++) {
            System.out.println("\n--- Giocatore " + i + " ---");
            Utente utente = null;

            while (utente == null) {
                System.out.print("Login o Registrazione? (l/r): ");
                String scelta = scanner.nextLine().trim().toLowerCase();

                if ("l".equals(scelta)) {
                    utente = handleLogin();
                    if (utente == null) {
                        System.out.println("Login fallito. Riprova.");
                    }
                } else if ("r".equals(scelta)) {
                    handleRegistration();
                    // dopo registrazione tenta login automaticamente
                    System.out.println("Effettua ora il login appena registrato:");
                    utente = handleLogin();
                } else {
                    System.out.println("Scelta non valida. Inserisci 'l' o 'r'.");
                }

                // Controlla che non ci siano duplicati di username
                boolean duplicato = false;
                for (Utente u : utenti) {
                    if (u.getUsername().equals(utente.getUsername())) {
                        duplicato = true;
                        break;
                    }
                }
                if (utente != null && duplicato) {
                    System.out.println("Username già inserito in questa partita. Riprova.");
                    utente = null;
                }

            }

            utenti.add(utente);
        }

        System.out.println("\nTutti i giocatori autenticati con successo!");
        return utenti;
    }

    /**
     * Login di un singolo utente
     */
    private Utente handleLogin() {
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        Utente utente = sistema.validaCredenziali(user, pass);
        if (utente != null) {
            System.out.println("Login effettuato per " + utente.getUsername() + "!");
            return utente;
        } else {
            System.out.println("Credenziali non valide.");
            return null;
        }
    }

    /**
     * Registrazione di un nuovo utente
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
