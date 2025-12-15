/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.monopolyumlgfr.gioco;

import javax.swing.*;
import java.awt.*;

/**
 * Interfaccia grafica del gioco Monopoly UML GFR.
 * <p>
 * Questa classe estende {@link JFrame} e fornisce una finestra Swing
 * che permette al giocatore di:
 * <ul>
 *   <li>Eseguire il turno corrente.</li>
 *   <li>Abbandonare la partita.</li>
 *   <li>Visualizzare lo stato dei giocatori e il tabellone.</li>
 * </ul>
 * <!-- </p> -->
 *
 * <h2>Ruolo nell'architettura</h2>
 * <ul>
 *   <li>La logica di gioco è delegata a {@link Partita}, che gestisce turni e comandi.</li>
 *   <li>{@code GiocoUI} si occupa esclusivamente della presentazione grafica e dell'interazione utente.</li>
 *   <li>Utilizza componenti Swing come {@link JEditorPane}, {@link JTextArea} e {@link JButton}.</li>
 * </ul>
 *
 * <h2>Componenti principali</h2>
 * <ul>
 *   <li>{@code tabellonePane}: mostra il tabellone in formato HTML.</li>
 *   <li>{@code giocatoriArea}: mostra lo stato dei giocatori in formato testuale.</li>
 *   <li>Pulsanti:
 *     <ul>
 *       <li>"Esegui Turno": invoca {@link Partita#eseguiTurno()} e aggiorna la UI.</li>
 *       <li>"Abbandona Partita": invoca {@link Partita#abbandonaGiocatoreCorrente()} e aggiorna la UI.</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * <h2>Note</h2>
 * <ul>
 *   <li>La finestra deve essere avviata sull'Event Dispatch Thread (EDT) tramite {@code SwingUtilities.invokeLater}.</li>
 *   <li>La UI è aggiornata dinamicamente dopo ogni azione tramite {@link #aggiornaUI()}.</li>
 *   <li>I messaggi informativi vengono mostrati con {@link JOptionPane}.</li>
 * </ul>
 *
 * @author Giulio
 */
public class GiocoUI extends JFrame {

    /**
     * Partita associata alla UI.
     */
    private Partita partita;

    /**
     * Pannello editor per mostrare il tabellone in HTML.
     */
    private JEditorPane tabellonePane;

    /**
     * Area di testo per mostrare lo stato dei giocatori.
     */
    private JTextArea giocatoriArea;

    /**
     * Costruttore.
     * <p>
     * Inizializza la finestra e i componenti grafici.
     * </p>
     *
     * @param p partita da visualizzare e gestire
     */
    public GiocoUI(Partita p) {
        this.partita = p;

        setTitle("Monopoly - Gioco");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
    }

    /**
     * Inizializza i componenti grafici della finestra.
     * <p>
     * Crea pannelli, aree di testo e pulsanti, e registra i listener
     * per le azioni di gioco.
     * </p>
     */
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        tabellonePane = new JEditorPane();
        tabellonePane.setContentType("text/html");
        tabellonePane.setEditable(false);
        tabellonePane.setText(partita.getTabelloneAsHTML());

        giocatoriArea = new JTextArea();
        giocatoriArea.setEditable(false);
        giocatoriArea.setText(partita.getStatoGiocatoriAsString());

        JButton eseguiTurnoButton = new JButton("Esegui Turno");
        eseguiTurnoButton.addActionListener(e -> {
            partita.eseguiTurno();
            mostraMessaggio("Turno eseguito.");
            aggiornaUI();
        });

        JButton abbandonaButton = new JButton("Abbandona Partita");
        abbandonaButton.addActionListener(e -> {
            partita.abbandonaGiocatoreCorrente(); // delega a Partita
            mostraMessaggio("Hai abbandonato la partita.");
            aggiornaUI();
        });

        JPanel southPanel = new JPanel();
        southPanel.add(eseguiTurnoButton);
        southPanel.add(abbandonaButton);

        mainPanel.add(new JScrollPane(tabellonePane), BorderLayout.CENTER);
        mainPanel.add(new JScrollPane(giocatoriArea), BorderLayout.EAST);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    /**
     * Aggiorna la UI con lo stato corrente della partita.
     * <p>
     * Ricarica lo stato dei giocatori e il tabellone.
     * </p>
     */
    private void aggiornaUI() {
        giocatoriArea.setText(partita.getStatoGiocatoriAsString());
        tabellonePane.setText(partita.getTabelloneAsHTML());
    }

    /**
     * Mostra un messaggio informativo in un dialogo modale.
     *
     * @param msg testo del messaggio da mostrare
     */
    public void mostraMessaggio(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}