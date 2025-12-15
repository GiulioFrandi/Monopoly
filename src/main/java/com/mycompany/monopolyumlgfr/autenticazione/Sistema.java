/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.monopolyumlgfr.autenticazione;

import com.mycompany.monopolyumlgfr.gioco.Giocatore;
import com.mycompany.monopolyumlgfr.gioco.Partita;

/**
 * Componente centrale per la gestione degli utenti e delle notifiche di gioco.
 * <p>
 * La classe {@code Sistema} funge da ponte tra il livello di persistenza
 * ({@link DatabaseUtenti}) e la logica di gioco ({@link Partita}, {@link Giocatore}).
 * Si occupa di:
 * <ul>
 *   <li>Registrare nuovi giocatori nel database.</li>
 *   <li>Validare le credenziali di login.</li>
 *   <li>Autenticare un utente esistente.</li>
 *   <li>Notificare eventi di gioco come abbandono o fine partita.</li>
 * </ul>
 * <!-- </p> -->
 *
 * <h2>Note architetturali</h2>
 * <ul>
 *   <li>La classe incapsula l'accesso al {@link DatabaseUtenti} e ne semplifica l'uso.</li>
 *   <li>Le notifiche di abbandono e fine partita sono attualmente loggate su console,
 *       ma possono essere estese per persistenza o statistiche.</li>
 * </ul>
 *
 * @author giuli
 */
public class Sistema {

    /**
     * Riferimento al database utenti per operazioni di persistenza.
     */
    private DatabaseUtenti db;

    /**
     * Costruttore.
     *
     * @param db istanza di {@link DatabaseUtenti} da usare per registrazione e autenticazione
     */
    public Sistema(DatabaseUtenti db) {
        this.db = db;
    }

    /**
     * Registra un nuovo giocatore nel sistema.
     * <p>
     * Crea un nuovo {@link Utente} e lo salva nel database.
     * </p>
     *
     * @param nome     nome reale del giocatore
     * @param username identificativo univoco per il login
     * @param password password associata all'utente
     */
    public void registraGiocatore(String nome, String username, String password) {
        Utente nuovo = new Utente(nome, username, password);
        db.salvaUtente(nuovo);
    }

    /**
     * Valida le credenziali di login confrontandole con gli utenti registrati.
     *
     * @param username username inserito
     * @param password password inserita
     * @return l'oggetto {@link Utente} se le credenziali sono corrette, altrimenti {@code null}
     */
    public Utente validaCredenziali(String username, String password) {
        for (Utente u : db.caricaUtenti()) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Autentica un giocatore delegando alla validazione delle credenziali.
     *
     * @param username username inserito
     * @param password password inserita
     * @return l'oggetto {@link Utente} se autenticato, altrimenti {@code null}
     */
    public Utente autenticaGiocatore(String username, String password) {
        return validaCredenziali(username, password);
    }

    /**
     * Notifica al sistema che un giocatore ha abbandonato la partita.
     * <p>
     * Attualmente stampa un messaggio su console, ma può essere esteso
     * per logging, persistenza o statistiche.
     * </p>
     *
     * @param g giocatore che ha abbandonato
     */
    public void notificaAbbandono(Giocatore g) {
        System.out.println("Sistema: Giocatore " + g.getUtente().getUsername() + " ha abbandonato la partita.");
        // logica di logging, persistenza, statistiche
    }

    /**
     * Notifica al sistema che la partita è terminata.
     * <p>
     * Attualmente stampa un messaggio su console, ma può essere esteso
     * per chiusura risorse o salvataggio risultati.
     * </p>
     *
     * @param p partita terminata
     */
    public void notificaFinePartita(Partita p) {
        System.out.println("Sistema: La partita è terminata.");
        // logica di chiusura partita
    }
}
