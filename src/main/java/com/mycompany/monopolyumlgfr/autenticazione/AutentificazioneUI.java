/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.monopolyumlgfr.autenticazione;

import javax.swing.*;

/**
 * Finestra di autenticazione (Swing) per il gioco Monopoly UML GFR.
 * <p>
 * Consente:
 * <ul>
 *   <li>Login di un utente esistente tramite {@link Sistema}.</li>
 *   <li>Registrazione di un nuovo utente.</li>
 *   <li>Notifica del login riuscito tramite {@link LoginSuccessListener}.</li>
 * </ul>
 * Al login riuscito la finestra viene chiusa e il controllo passa al listener,
 * che tipicamente avvia la partita e la UI di gioco.
 * <!-- </p> -->
 *
 * <h2>Note</h2>
 * Tutte le operazioni di UI devono essere eseguite sull'Event Dispatch Thread (EDT).
 * Mostrare la finestra con {@code SwingUtilities.invokeLater(...)}.
 *
 * @author giuli
 */
public class AutentificazioneUI extends JFrame {

    /**
     * Backend applicativo per la gestione di autenticazione e registrazione.
     */
    private final Sistema sistema;

    /**
     * Callback invocata quando il login va a buon fine (riceve l'utente autenticato).
     */
    private final LoginSuccessListener listener;

    /**
     * Crea e configura la finestra di autenticazione.
     *
     * @param sistema  backend per la gestione degli utenti (validazione e registrazione)
     * @param listener callback di successo login; può essere {@code null} se non necessaria
     */
    public AutentificazioneUI(Sistema sistema, LoginSuccessListener listener) {
        this.sistema = sistema;
        this.listener = listener;
        setTitle("Autenticazione Monopoly");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    /**
     * Inizializza i componenti Swing e registra i listener per login e registrazione.
     * <p>
     * Campi:
     * <ul>
     *   <li>Nome (per la registrazione)</li>
     *   <li>Username</li>
     *   <li>Password</li>
     * </ul>
     * Azioni:
     * <ul>
     *   <li>Login: valida credenziali via {@link Sistema#validaCredenziali(String, String)}.
     *       In caso di successo mostra un messaggio, invoca {@link LoginSuccessListener#onLoginSuccess(Utente)}
     *       e chiude la finestra.</li>
     *   <li>Registrazione: invoca {@link Sistema#registraGiocatore(String, String, String)} se i campi sono validi,
     *       altrimenti mostra un errore.</li>
     * </ul>
     * </p>
     */
    private void initComponents() {
        JTextField nomeField = new JTextField(15);
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Registrati");

        // --- LOGIN ---
        loginButton.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            Utente utente = sistema.validaCredenziali(user, pass);
            if (utente != null) {
                JOptionPane.showMessageDialog(this, "Login effettuato!");
                if (listener != null) {
                    listener.onLoginSuccess(utente);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenziali non valide.");
            }
        });

        // --- REGISTRAZIONE ---
        registerButton.addActionListener(e -> {
            String nome = nomeField.getText();
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            if (!nome.isEmpty() && !user.isEmpty() && !pass.isEmpty()) {
                sistema.registraGiocatore(nome, user, pass);
                JOptionPane.showMessageDialog(this, "Registrazione completata! Ora puoi fare login.");
            } else {
                JOptionPane.showMessageDialog(this, "Compila tutti i campi per registrarti.");
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);
    }
}
