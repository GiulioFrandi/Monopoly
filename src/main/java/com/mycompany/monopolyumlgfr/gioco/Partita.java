/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.monopolyumlgfr.gioco;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.monopolyumlgfr.autenticazione.Sistema;

/**
 * Rappresenta una partita di Monopoly UML GFR.
 * <p>
 * La classe incapsula la logica di gestione di una partita:
 * <ul>
 *   <li>Gestione dei giocatori partecipanti.</li>
 *   <li>Gestione dei turni e della sequenza di gioco.</li>
 *   <li>Gestione del tabellone e delle proprietà.</li>
 *   <li>Notifica al {@link Sistema} di eventi come abbandono o fine partita.</li>
 *   <li>Metodi di reporting per interfacce grafiche o testuali.</li>
 * </ul>
 * <!-- </p> -->
 *
 * <h2>Ruolo nell'architettura</h2>
 * <ul>
 *   <li>È il cuore della logica di gioco.</li>
 *   <li>Coordina le azioni dei {@link Giocatore} e le interazioni con il {@link Tabellone}.</li>
 *   <li>Espone metodi di utilità per le UI ({@link GiocoUI}, {@link GiocoConsole}).</li>
 * </ul>
 *
 * <h2>Note</h2>
 * <ul>
 *   <li>La partita è attiva solo dopo l'invocazione di {@link #avviaPartita()}.</li>
 *   <li>Il turno corrente è gestito tramite indice incrementale ciclico.</li>
 *   <li>Se rimane un solo giocatore, la partita viene terminata automaticamente.</li>
 * </ul>
 *
 * @author Giulio
 */
public class Partita {

    /** Lista dei giocatori partecipanti. */
    private List<Giocatore> giocatori;

    /** Stato della partita (attiva o terminata). */
    private boolean attiva;

    /** Tabellone di gioco. */
    private Tabellone tabellone;

    /** Riferimento al sistema per notifiche di eventi. */
    private Sistema sistema;

    /** Indice del turno corrente. */
    private int turnoCorrente;

    /**
     * Costruttore.
     * <p>
     * Inizializza una nuova partita con tabellone vuoto e nessun giocatore.
     * </p>
     *
     * @param s sistema per notifiche e gestione utenti
     */
    public Partita(Sistema s) {
        this.giocatori = new ArrayList<>();
        this.attiva = false;
        this.tabellone = new Tabellone();
        this.sistema = s;
        this.turnoCorrente = 0;
    }

    /**
     * Associa un giocatore alla partita.
     *
     * @param g giocatore da aggiungere
     */
    public void associaGiocatori(Giocatore g) {
        giocatori.add(g);
    }

    /**
     * Avvia la partita.
     * <p>
     * Imposta lo stato su attivo e resetta il turno corrente.
     * </p>
     */
    public void avviaPartita() {
        attiva = true;
        turnoCorrente = 0;
        System.out.println("Partita avviata con " + giocatori.size() + " giocatori.");
    }

    /**
     * Esegue il turno del giocatore corrente.
     * <p>
     * Lancia i dadi, calcola la nuova posizione sul tabellone e gestisce
     * l'acquisto o il pagamento dell'affitto della proprietà.
     * </p>
     */
    public void eseguiTurno() {
        if (!attiva || giocatori.isEmpty()) { System.out.println("Esco qui"); return; }

        Giocatore g = giocatori.get(turnoCorrente);
        System.out.println("Turno di " + g.getUtente().getUsername());

        int risultato = g.lanciaDadi();
        Proprietà nuovaPosizione = tabellone.calcolaNuovaPosizione(g, risultato);
        System.out.println("Giocatore " + g.getUtente().getUsername() +
                           " ha tirato " + risultato +
                           " ed è finito su " + nuovaPosizione.getNome());

        if (nuovaPosizione.isLibera()) {
            g.acquistaProprietà(nuovaPosizione);
        } else {
            g.pagaAffitto(nuovaPosizione);
        }

        turnoCorrente = (turnoCorrente + 1) % giocatori.size();
    }

    /**
     * Gestisce l'abbandono del giocatore corrente.
     * <p>
     * Rimuove il giocatore dalla lista, libera la pedina dal tabellone
     * e notifica il sistema. Se rimane un solo giocatore, la partita termina.
     * </p>
     */
    public void abbandonaGiocatoreCorrente() {
        if (giocatori.isEmpty()) return;

        Giocatore g = giocatori.get(turnoCorrente);
        g.abbandonaPartita();
        giocatori.remove(g);
        tabellone.rimuoviPedina(g);
        sistema.notificaAbbandono(g);

        System.out.println("Giocatore " + g.getUtente().getUsername() + " ha abbandonato la partita.");

        if (giocatori.size() <= 1) {
            terminaPartita();
        } else {
            turnoCorrente = turnoCorrente % giocatori.size();
        }
    }

    /**
     * Termina la partita e notifica il sistema.
     */
    public void terminaPartita() {
        attiva = false;
        sistema.notificaFinePartita(this);
        System.out.println("Partita terminata.");
    }

    /**
     * Restituisce il giocatore corrente.
     *
     * @return il {@link Giocatore} corrente, oppure {@code null} se non ci sono giocatori
     */
    public Giocatore getGiocatoreCorrente() {
        if (giocatori == null || giocatori.isEmpty()) {
            return null;
        }
        return giocatori.get(turnoCorrente);
    }

    /**
     * Restituisce lo stato dei giocatori in formato testuale.
     *
     * @return stringa con username, posizione e denaro di ciascun giocatore
     */
    public String getStatoGiocatoriAsString() {
        StringBuilder sb = new StringBuilder("Giocatori:\n");
        for (Giocatore g : giocatori) {
            sb.append(g.getUtente().getUsername())
              .append(" - Posizione: ").append(g.getPosizione())
              .append(" - Denaro: ").append(g.getDenaro())
              .append("\n");
        }
        return sb.toString();
    }

    /**
     * Restituisce il tabellone in formato testuale.
     * <p>
     * Evidenzia la casella occupata dal giocatore corrente.
     * </p>
     *
     * @param giocatoreCorrente giocatore di turno
     * @return rappresentazione testuale del tabellone
     */
    public String getTabelloneAsText(Giocatore giocatoreCorrente) {
        StringBuilder sb = new StringBuilder();
        int posizioneCorrente = giocatoreCorrente.getPosizione();

        for (int i = 0; i < tabellone.getCaselle().size(); i++) {
            Proprietà p = tabellone.getCaselle().get(i);

            if (i == posizioneCorrente) {
                sb.append("**").append(p.getNome()).append("**");
            } else {
                sb.append(p.getNome());
            }

            sb.append(" [valore: ").append(p.getValore()).append("]\n");
        }
        return sb.toString();
    }

    /**
     * Restituisce il tabellone in formato HTML.
     * <p>
     * Evidenzia le caselle occupate dai giocatori in grassetto.
     * </p>
     *
     * @return rappresentazione HTML del tabellone
     */
    public String getTabelloneAsHTML() {
        StringBuilder sb = new StringBuilder("<html><body><h3>Tabellone:</h3><ul>");
        int i = 0;
        for (Proprietà p : tabellone.getCaselle()) {
            boolean occupata = false;
            for (Giocatore g : giocatori) {
                if (g.getPosizione() == i) {
                    occupata = true;
                    break;
                }
            }
            if (occupata) {
                sb.append("<li><b>").append(i).append(") ").append(p.getNome())
                  .append(" - Valore: ").append(p.getValore())
                  .append(" - Affitto base: ").append(p.getAffitto()[0])
                  .append("</b></li>");
            } else {
                sb.append("<li>").append(i).append(") ").append(p.getNome())
                  .append(" - Valore: ").append(p.getValore())
                  .append(" - Affitto base: ").append(p.getAffitto()[0])
                  .append("</li>");
            }
            i++;
        }
        sb.append("</ul></body></html>");
        return sb.toString();
    }
}
