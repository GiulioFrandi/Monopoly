package com.mycompany.monopolyumlgfr.gioco;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.monopolyumlgfr.autenticazione.Sistema;

/**
 * Rappresenta una partita di Monopoly UML GFR. Supporta modalità multiplayer da
 * 2 a 6 giocatori.
 *
 * @author Giulio
 */
public class Partita {

    /**
     * Numero minimo di giocatori
     */
    public static final int MIN_GIOCATORI = 2;

    /**
     * Numero massimo di giocatori
     */
    public static final int MAX_GIOCATORI = 6;

    /**
     * Lista dei giocatori partecipanti
     */
    private List<Giocatore> giocatori;

    /**
     * Stato della partita
     */
    private boolean attiva;

    /**
     * Tabellone di gioco
     */
    private Tabellone tabellone;

    /**
     * Riferimento al sistema per notifiche
     */
    private Sistema sistema;

    /**
     * Indice del turno corrente
     */
    private int turnoCorrente;

    /**
     * Costruttore.
     *
     * @param s sistema per notifiche e gestione eventi
     */
    public Partita(Sistema s) {
        this.giocatori = new ArrayList<>();
        this.attiva = false;
        this.tabellone = new Tabellone();
        this.sistema = s;
        this.turnoCorrente = 0;
    }

    /**
     * Aggiunge un giocatore alla partita se il limite non è superato.
     *
     * @param g giocatore da aggiungere
     * @return true se aggiunto correttamente
     */
    public boolean aggiungiGiocatore(Giocatore g) {
        if (attiva) {
            return false;
        }
        if (giocatori.size() >= MAX_GIOCATORI) {
            return false;
        }
        if (giocatori.contains(g)) {
            return false;
        }

        giocatori.add(g);
        return true;
    }

    /**
     * Avvia la partita controllando il numero di giocatori.
     *
     * @throws IllegalStateException se i giocatori non sono sufficienti
     */
    public void avviaPartita() {
        if (giocatori.size() < MIN_GIOCATORI) {
            throw new IllegalStateException(
                    "Numero di giocatori insufficiente (minimo " + MIN_GIOCATORI + ")"
            );
        }

        attiva = true;
        turnoCorrente = 0;

        System.out.println("Partita avviata con " + giocatori.size() + " giocatori.");
    }

    /**
     * Esegue il turno del giocatore corrente.
     */
    public void eseguiTurno() {
        if (!attiva || giocatori.isEmpty()) {
            return;
        }

        Giocatore g = giocatori.get(turnoCorrente);
        System.out.println("Turno di " + g.getUtente().getUsername());

        int risultato = g.lanciaDadi();
        Proprietà nuovaPosizione
                = tabellone.calcolaNuovaPosizione(g, risultato);

        System.out.println(
                "Giocatore " + g.getUtente().getUsername()
                + " ha tirato " + risultato
                + " ed è finito su " + nuovaPosizione.getNome()
        );

        if (nuovaPosizione.isLibera()) {
            g.acquistaProprietà(nuovaPosizione);
        } else {
            g.pagaAffitto(nuovaPosizione);
        }

        prossimoTurno();
    }

    /**
     * Passa al turno successivo.
     */
    private void prossimoTurno() {
        turnoCorrente = (turnoCorrente + 1) % giocatori.size();
    }

    /**
     * Gestisce l'abbandono del giocatore corrente.
     */
    public void abbandonaGiocatoreCorrente() {
        if (giocatori.isEmpty()) {
            return;
        }

        Giocatore g = giocatori.get(turnoCorrente);
        g.abbandonaPartita();

        giocatori.remove(g);
        tabellone.rimuoviPedina(g);
        sistema.notificaAbbandono(g);

        if (giocatori.size() <= 1) {
            terminaPartita();
        } else {
            turnoCorrente %= giocatori.size();
        }
    }

    /**
     * Termina la partita.
     */
    public void terminaPartita() {
        attiva = false;
        sistema.notificaFinePartita(this);
        System.out.println("Partita terminata.");
    }

    /**
     * @return giocatore corrente
     */
    public Giocatore getGiocatoreCorrente() {
        if (giocatori.isEmpty()) {
            return null;
        }
        return giocatori.get(turnoCorrente);
    }

    /**
     * @return numero di giocatori
     */
    public int getNumeroGiocatori() {
        return giocatori.size();
    }

    /**
     * @return true se la partita è attiva
     */
    public boolean isAttiva() {
        return attiva;
    }

    /* ==== METODI DI REPORTING (immutati, corretti) ==== */
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

    public String getTabelloneAsText(Giocatore giocatoreCorrente) {
        StringBuilder sb = new StringBuilder();
        int posizioneCorrente = giocatoreCorrente.getPosizione();

        for (int i = 0; i < tabellone.getCaselle().size(); i++) {
            Proprietà p = tabellone.getCaselle().get(i);
            sb.append(i == posizioneCorrente ? "**" : "")
                    .append(p.getNome())
                    .append(i == posizioneCorrente ? "**" : "")
                    .append(" [valore: ").append(p.getValore()).append("]\n");
        }
        return sb.toString();
    }

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
            sb.append("<li>")
                    .append(occupata ? "<b>" : "")
                    .append(i).append(") ").append(p.getNome())
                    .append(" - Valore: ").append(p.getValore())
                    .append(" - Affitto base: ").append(p.getAffitto()[0])
                    .append(occupata ? "</b>" : "")
                    .append("</li>");
            i++;
        }
        sb.append("</ul></body></html>");
        return sb.toString();
    }

    public String getTabelloneConGiocatori() {
        return tabellone.getTabelloneConGiocatori(giocatori);
    }

}
