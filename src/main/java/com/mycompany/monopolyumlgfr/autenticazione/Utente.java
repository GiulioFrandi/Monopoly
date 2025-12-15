/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.monopolyumlgfr.autenticazione;

/**
 * Rappresenta un utente registrato nel sistema Monopoly UML GFR.
 * <p>
 * La classe incapsula le informazioni di base necessarie per
 * l'autenticazione e l'identificazione di un giocatore:
 * <ul>
 *   <li>Nome reale del giocatore.</li>
 *   <li>Username univoco per il login.</li>
 *   <li>Password associata all'account.</li>
 * </ul>
 * <!-- </p> -->
 *
 * <h2>Ruolo nell'architettura</h2>
 * <ul>
 *   <li>Utilizzata da {@link com.mycompany.monopolyumlgfr.autenticazione.Sistema}
 *       per validare credenziali e registrare nuovi utenti.</li>
 *   <li>Associata a un {@link com.mycompany.monopolyumlgfr.gioco.Giocatore}
 *       per collegare l'identità reale al partecipante della partita.</li>
 *   <li>Persistita e caricata tramite {@link DatabaseUtenti} in formato XML.</li>
 * </ul>
 *
 * <h2>Note</h2>
 * <ul>
 *   <li>La password è memorizzata in chiaro: per un sistema reale
 *       sarebbe opportuno utilizzare hashing e salting.</li>
 *   <li>La classe è immutabile solo parzialmente: i campi sono privati
 *       ma non definiti come {@code final}.</li>
 * </ul>
 *
 * @author giuli
 */
public class Utente {

    /**
     * Nome reale del giocatore.
     */
    private String nome;

    /**
     * Username univoco per il login.
     */
    private String username;

    /**
     * Password associata all'account.
     */
    private String password;

    /**
     * Costruttore.
     *
     * @param nome     nome reale del giocatore
     * @param username identificativo univoco per il login
     * @param password password associata all'utente
     */
    public Utente(String nome, String username, String password) {
        this.nome = nome;
        this.username = username;
        this.password = password;
    }

    /**
     * Restituisce il nome reale del giocatore.
     *
     * @return nome dell'utente
     */
    public String getNome() { return nome; }

    /**
     * Restituisce lo username dell'utente.
     *
     * @return username
     */
    public String getUsername() { return username; }

    /**
     * Restituisce la password dell'utente.
     *
     * @return password
     */
    public String getPassword() { return password; }
}