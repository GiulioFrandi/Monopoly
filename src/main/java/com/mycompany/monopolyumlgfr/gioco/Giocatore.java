/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.monopolyumlgfr.gioco;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.monopolyumlgfr.autenticazione.Utente;

/**
 * Rappresenta un giocatore del Monopoly UML GFR.
 * <p>
 * Ogni {@code Giocatore} è associato a un {@link Utente} autenticato
 * e mantiene lo stato di gioco relativo a:
 * <ul>
 *   <li>Denaro disponibile.</li>
 *   <li>Proprietà possedute.</li>
 *   <li>Posizione corrente sul tabellone.</li>
 * </ul>
 * <!-- </p> -->
 *
 * <h2>Funzionalità principali</h2>
 * <ul>
 *   <li>Lancio dei dadi per determinare i movimenti.</li>
 *   <li>Spostamento della pedina sul tabellone.</li>
 *   <li>Acquisto di proprietà libere se il denaro è sufficiente.</li>
 *   <li>Pagamento dell'affitto quando si capita su proprietà altrui.</li>
 *   <li>Abbandono della partita con liberazione delle proprietà.</li>
 * </ul>
 *
 * <h2>Note</h2>
 * <ul>
 *   <li>Il denaro iniziale è fissato a 1500 unità.</li>
 *   <li>La posizione iniziale è 0 (casella di partenza).</li>
 *   <li>La lista delle proprietà è inizialmente vuota.</li>
 *   <li>In caso di abbandono, la posizione viene impostata a -1 per indicare
 *       che il giocatore non è più attivo.</li>
 * </ul>
 *
 * @author Giulio
 */
public class Giocatore {

    /**
     * Utente associato al giocatore.
     */
    private Utente utente;

    /**
     * Denaro disponibile del giocatore.
     */
    private int denaro;

    /**
     * Lista delle proprietà possedute.
     */
    private List<Proprietà> proprietàPossedute;

    /**
     * Posizione corrente sul tabellone (0–39).
     */
    private int posizione;

    /**
     * Costruttore.
     * <p>
     * Inizializza il giocatore con:
     * <ul>
     *   <li>Denaro iniziale: 1500.</li>
     *   <li>Posizione iniziale: 0.</li>
     *   <li>Nessuna proprietà posseduta.</li>
     * </ul>
     * <!-- </p> -->
     *
     * @param u utente associato al giocatore
     */
    public Giocatore(Utente u) {
        this.utente = u;
        this.denaro = 1500;
        this.proprietàPossedute = new ArrayList<>();
        this.posizione = 0;
    }

    /** @return l'utente associato al giocatore */
    public Utente getUtente() { return utente; }

    /** @return denaro disponibile */
    public int getDenaro() { return denaro; }

    /**
     * Imposta il denaro disponibile.
     *
     * @param denaro nuovo valore del denaro
     */
    public void setDenaro(int denaro) { this.denaro = denaro; }

    /** @return posizione corrente sul tabellone */
    public int getPosizione() { return posizione; }

    /**
     * Imposta la posizione sul tabellone.
     *
     * @param posizione nuova posizione (0–39)
     */
    public void setPosizione(int posizione) { this.posizione = posizione; }

    /** @return lista delle proprietà possedute */
    public List<Proprietà> getProprietàPossedute() { return proprietàPossedute; }

    /**
     * Lancia due dadi e restituisce la somma.
     *
     * @return valore casuale tra 2 e 12
     */
    public int lanciaDadi() {
        return (int)(Math.random() * 6 + 1) + (int)(Math.random() * 6 + 1);
    }

    /**
     * Muove la pedina di un certo numero di passi.
     * <p>
     * La posizione viene calcolata modulo 40 per rimanere entro i limiti del tabellone.
     * </p>
     *
     * @param passi numero di passi da compiere
     */
    public void muoverePedina(int passi) {
        this.posizione = (this.posizione + passi) % 40;
    }

    /**
     * Acquista una proprietà se libera e se il denaro è sufficiente.
     *
     * @param p proprietà da acquistare
     */
    public void acquistaProprietà(Proprietà p) {
        if (denaro >= p.getValore() && p.isLibera()) {
            denaro -= p.getValore();
            proprietàPossedute.add(p);
            p.setLibera(false);
        }
    }

    /**
     * Paga l'affitto relativo a una proprietà.
     *
     * @param p proprietà su cui si è capitati
     */
    public void pagaAffitto(Proprietà p) {
        int affitto = p.calcolaAffitto();
        denaro -= affitto;
    }

    /**
     * Abbandona la partita.
     * <p>
     * Tutte le proprietà vengono liberate e la posizione viene impostata a -1.
     * </p>
     */
    public void abbandonaPartita() {
        proprietàPossedute.clear();
        posizione = -1;
        System.out.println("Giocatore " + utente.getUsername() + " ha abbandonato la partita.");
    }
}