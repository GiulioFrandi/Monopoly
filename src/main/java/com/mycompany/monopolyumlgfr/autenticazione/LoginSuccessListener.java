/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.monopolyumlgfr.autenticazione;

/**
 * Listener funzionale per la gestione del login riuscito.
 * <p>
 * Questa interfaccia definisce un singolo metodo di callback che viene
 * invocato quando un utente ha effettuato correttamente l'autenticazione.
 * È annotata con {@link FunctionalInterface}, quindi può essere implementata
 * tramite espressioni lambda o reference a metodi.
 * </p>
 *
 * <h2>Ruolo nell'architettura</h2>
 * <ul>
 *   <li>Permette di disaccoppiare la logica di autenticazione dalla logica
 *       di avvio della partita.</li>
 *   <li>Utilizzata da {@link AutentificazioneUI} per notificare al chiamante
 *       che il login è stato completato con successo.</li>
 *   <li>Tipicamente, l'implementazione crea un {@link com.mycompany.monopolyumlgfr.gioco.Giocatore},
 *       avvia una {@link com.mycompany.monopolyumlgfr.gioco.Partita} e mostra la UI di gioco.</li>
 * </ul>
 *
 * <h2>Esempio d'uso</h2>
 * <pre>{@code
 * AutentificazioneUI ui = new AutentificazioneUI(sistema, utente -> {
 *     Giocatore g = new Giocatore(utente);
 *     Partita p = new Partita(sistema);
 *     p.associaGiocatori(g);
 *     p.avviaPartita();
 *     new GiocoUI(p).setVisible(true);
 * });
 * }</pre>
 *
 * @author giuli
 */
@FunctionalInterface
public interface LoginSuccessListener {

    /**
     * Callback invocata quando il login va a buon fine.
     *
     * @param utente l'oggetto {@link Utente} autenticato con successo
     */
    void onLoginSuccess(Utente utente);
}


