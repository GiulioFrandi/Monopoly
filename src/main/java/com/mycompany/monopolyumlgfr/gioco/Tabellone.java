/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.monopolyumlgfr.gioco;

import java.util.ArrayList;
import java.util.List;

/**
 * Tabellone del Monopoly classico (versione italiana).
 * <p>
 * Questa classe rappresenta il tabellone di gioco, contenente tutte le
 * proprietà, stazioni e società disponibili. Ogni casella è modellata
 * come un oggetto {@link Proprietà}, con nome, valore e tabella degli affitti.
 * </p>
 *
 * <h2>Ruolo nell'architettura</h2>
 * <ul>
 *   <li>Gestisce la disposizione delle caselle del Monopoly.</li>
 *   <li>Permette di calcolare la nuova posizione di un {@link Giocatore}
 *       in base al lancio dei dadi.</li>
 *   <li>Consente di rimuovere una pedina dal tabellone quando un giocatore
 *       abbandona la partita.</li>
 *   <li>Espone la lista delle caselle per reporting e visualizzazione
 *       nelle interfacce utente.</li>
 * </ul>
 *
 * <h2>Note</h2>
 * <ul>
 *   <li>La disposizione delle proprietà segue la versione italiana del Monopoly classico.</li>
 *   <li>Le stazioni e le società sono modellate anch'esse come {@link Proprietà}.</li>
 *   <li>Il tabellone è inizializzato automaticamente nel costruttore tramite {@link #inizializza()}.</li>
 * </ul>
 *
 * @author Giulio
 */
public class Tabellone {

    /** Lista delle caselle del tabellone (proprietà, stazioni, società). */
    private List<Proprietà> caselle;

    /**
     * Costruttore.
     * <p>
     * Inizializza il tabellone creando tutte le caselle del Monopoly classico.
     * </p>
     */
    public Tabellone() {
        this.caselle = new ArrayList<>();
        inizializza();
    }

    /**
     * Inizializza le caselle del tabellone.
     * <p>
     * Aggiunge tutte le proprietà, stazioni e società con i rispettivi valori
     * e tabelle degli affitti.
     * </p>
     */
    public void inizializza() {
        // Proprietà marroni
        caselle.add(new Proprietà("Vicolo Corto", 60, new int[]{2,10,30,90,160,250}));
        caselle.add(new Proprietà("Vicolo Stretto", 60, new int[]{4,20,60,180,320,450}));

        // Proprietà azzurro chiaro
        caselle.add(new Proprietà("Bastioni Gran Sasso", 100, new int[]{6,30,90,270,400,550}));
        caselle.add(new Proprietà("Viale Monterosa", 100, new int[]{6,30,90,270,400,550}));
        caselle.add(new Proprietà("Viale Vesuvio", 120, new int[]{8,40,100,300,450,600}));

        // Proprietà rosa
        caselle.add(new Proprietà("Via Accademia", 140, new int[]{10,50,150,450,625,750}));
        caselle.add(new Proprietà("Corso Ateneo", 140, new int[]{10,50,150,450,625,750}));
        caselle.add(new Proprietà("Piazza Università", 160, new int[]{12,60,180,500,700,900}));

        // Proprietà arancioni
        caselle.add(new Proprietà("Via Verdi", 180, new int[]{14,70,200,550,750,950}));
        caselle.add(new Proprietà("Corso Raffaello", 180, new int[]{14,70,200,550,750,950}));
        caselle.add(new Proprietà("Piazza Dante", 200, new int[]{16,80,220,600,800,1000}));

        // Proprietà rosse
        caselle.add(new Proprietà("Via Marco Polo", 220, new int[]{18,90,250,700,875,1050}));
        caselle.add(new Proprietà("Corso Magellano", 220, new int[]{18,90,250,700,875,1050}));
        caselle.add(new Proprietà("Largo Colombo", 240, new int[]{20,100,300,750,925,1100}));

        // Proprietà gialle
        caselle.add(new Proprietà("Viale Costantino", 260, new int[]{22,110,330,800,975,1150}));
        caselle.add(new Proprietà("Viale Traiano", 260, new int[]{22,110,330,800,975,1150}));
        caselle.add(new Proprietà("Piazza Giulio Cesare", 280, new int[]{24,120,360,850,1025,1200}));

        // Proprietà verdi
        caselle.add(new Proprietà("Via Roma", 300, new int[]{26,130,390,900,1100,1275}));
        caselle.add(new Proprietà("Corso Impero", 300, new int[]{26,130,390,900,1100,1275}));
        caselle.add(new Proprietà("Largo Augusto", 320, new int[]{28,150,450,1000,1200,1400}));

        // Proprietà blu scuro
        caselle.add(new Proprietà("Viale dei Giardini", 350, new int[]{35,175,500,1100,1300,1500}));
        caselle.add(new Proprietà("Parco della Vittoria", 400, new int[]{50,200,600,1400,1700,2000}));

        // Stazioni
        caselle.add(new Proprietà("Stazione Nord", 200, new int[]{25,50,100,200}));
        caselle.add(new Proprietà("Stazione Est", 200, new int[]{25,50,100,200}));
        caselle.add(new Proprietà("Stazione Sud", 200, new int[]{25,50,100,200}));
        caselle.add(new Proprietà("Stazione Ovest", 200, new int[]{25,50,100,200}));

        // Società
        caselle.add(new Proprietà("Società Elettrica", 150, new int[]{10,40}));
        caselle.add(new Proprietà("Società Acqua Potabile", 150, new int[]{10,40}));
    }

    /**
     * Calcola la nuova posizione di un giocatore in base ai passi effettuati.
     * <p>
     * Aggiorna la posizione del giocatore e restituisce la casella corrispondente.
     * </p>
     *
     * @param g     giocatore da muovere
     * @param passi numero di passi da compiere
     * @return la nuova {@link Proprietà} su cui è finito il giocatore
     */
    public Proprietà calcolaNuovaPosizione(Giocatore g, int passi) {
        int posAttuale = g.getPosizione();
        int nuovaPos = (posAttuale + passi) % caselle.size();
        g.setPosizione(nuovaPos);
        return caselle.get(nuovaPos);
    }

    /**
     * Rimuove la pedina di un giocatore dal tabellone.
     * <p>
     * Imposta la posizione del giocatore a -1 per indicare che non è più attivo.
     * </p>
     *
     * @param g giocatore da rimuovere
     */
    public void rimuoviPedina(Giocatore g) {
        g.setPosizione(-1);
    }

    /**
     * Restituisce la lista delle caselle del tabellone.
     *
     * @return lista di {@link Proprietà}
     */
    public List<Proprietà> getCaselle() { return caselle; }
}