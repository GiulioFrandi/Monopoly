/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.monopolyumlgfr.gioco;

/**
 * Rappresenta una proprietà del Monopoly UML GFR.
 * <p>
 * Ogni proprietà ha un nome, un valore di acquisto, uno stato (libera o posseduta,
 * ipotecata o meno), un numero di edifici costruiti e una tabella di affitti.
 * Può essere acquistata da un {@link Giocatore} e genera affitto quando altri
 * giocatori vi capitano.
 * </p>
 *
 * <h2>Ruolo nell'architettura</h2>
 * <ul>
 *   <li>Elemento del {@link Tabellone} su cui i giocatori possono capitare.</li>
 *   <li>Gestisce la logica di affitto in base al numero di edifici.</li>
 *   <li>Può essere acquistata, ipotecata o liberata.</li>
 * </ul>
 *
 * <h2>Note</h2>
 * <ul>
 *   <li>Il campo {@code affitto} è un array che rappresenta il valore dell'affitto
 *       in base al numero di edifici (0 = terreno nudo, 1 = una casa, ecc.).</li>
 *   <li>Se il numero di edifici supera la lunghezza dell'array, viene restituito
 *       l'ultimo valore disponibile.</li>
 *   <li>Le proprietà sono inizialmente libere e non ipotecate.</li>
 * </ul>
 *
 * @author Giulio
 */
public class Proprietà {

    /** Nome della proprietà. */
    private String nome;

    /** Valore di acquisto della proprietà. */
    private int valore;

    /** Stato: true se la proprietà è libera, false se posseduta. */
    private boolean libera;

    /** Stato: true se la proprietà è ipotecata. */
    private boolean ipotecata;

    /** Numero di edifici costruiti sulla proprietà. */
    private int edifici;

    /** Tabella degli affitti in base al numero di edifici. */
    private int[] affitto;

    /**
     * Costruttore.
     * <p>
     * Inizializza una proprietà con nome, valore e tabella affitti.
     * La proprietà è inizialmente libera, non ipotecata e senza edifici.
     * </p>
     *
     * @param nome    nome della proprietà
     * @param valore  valore di acquisto
     * @param affitto array degli affitti in base al numero di edifici
     */
    public Proprietà(String nome, int valore, int[] affitto) {
        this.nome = nome;
        this.valore = valore;
        this.affitto = affitto;
        this.libera = true;
        this.ipotecata = false;
        this.edifici = 0;
    }

    /** @return nome della proprietà */
    public String getNome() { return nome; }

    /** @return valore di acquisto della proprietà */
    public int getValore() { return valore; }

    /** @return true se la proprietà è libera */
    public boolean isLibera() { return libera; }

    /** @return true se la proprietà è ipotecata */
    public boolean isIpotecata() { return ipotecata; }

    /** @return numero di edifici costruiti */
    public int getEdifici() { return edifici; }

    /** @return array degli affitti */
    public int[] getAffitto() { return affitto; }

    /** Imposta lo stato libero/occupato della proprietà. */
    public void setLibera(boolean libera) { this.libera = libera; }

    /** Imposta lo stato ipotecato della proprietà. */
    public void setIpotecata(boolean ipotecata) { this.ipotecata = ipotecata; }

    /** Imposta il numero di edifici costruiti. */
    public void setEdifici(int edifici) { this.edifici = edifici; }

    /**
     * Calcola l'affitto in base al numero di edifici.
     * <p>
     * Se {@code edifici} è minore della lunghezza dell'array {@code affitto},
     * restituisce il valore corrispondente. Altrimenti restituisce l'ultimo
     * valore disponibile.
     * </p>
     *
     * @return valore dell'affitto da pagare
     */
    public int calcolaAffitto() {
        if (edifici < affitto.length) {
            return affitto[edifici];
        }
        return affitto[affitto.length - 1];
    }
}
