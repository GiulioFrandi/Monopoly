/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.monopolyumlgfr.gioco;

import com.mycompany.monopolyumlgfr.autenticazione.Utente;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


/**
 * Classe di test per {@link Tabellone}.
 * <p>
 * Questa suite verifica il corretto funzionamento del tabellone di gioco:
 * <ul>
 *   <li>Inizializzazione delle caselle con valori attesi.</li>
 *   <li>Calcolo della nuova posizione del giocatore.</li>
 *   <li>Gestione del wrap-around quando si supera il numero di caselle.</li>
 *   <li>Rimozione della pedina dal tabellone.</li>
 * </ul>
 * <!-- </p> -->
 */
public class TabelloneTest {

    /**
     * Verifica l'inizializzazione delle caselle del tabellone.
     * <p>
     * - Crea un nuovo tabellone. <br>
     * - Controlla che la lista delle caselle non sia null né vuota. <br>
     * - Verifica che la prima casella sia "Vicolo Corto". <br>
     * - Verifica che l'ultima casella sia "Società Acqua Potabile".
     * </p>
     */
    @Test
    public void testInizializzazioneCaselle() {
        Tabellone tabellone = new Tabellone();
        List<Proprietà> caselle = tabellone.getCaselle();

        assertNotNull(caselle, "La lista delle caselle non deve essere null");
        assertFalse(caselle.isEmpty(), "Il tabellone deve contenere caselle");
        assertEquals("Vicolo Corto", caselle.get(0).getNome(), "La prima casella deve essere 'Vicolo Corto'");
        assertEquals("Società Acqua Potabile", caselle.get(caselle.size() - 1).getNome(),
                     "L'ultima casella deve essere 'Società Acqua Potabile'");
    }

    /**
     * Verifica il calcolo della nuova posizione del giocatore.
     * <p>
     * - Imposta la posizione iniziale del giocatore a 0. <br>
     * - Muove la pedina di 3 posizioni. <br>
     * - Controlla che la nuova posizione sia 3 e che la proprietà restituita
     *   corrisponda alla casella 3 del tabellone.
     * </p>
     */
    @Test
    public void testCalcolaNuovaPosizione() {
        Tabellone tabellone = new Tabellone();
        Utente u = new Utente("Mario Rossi", "mario", "pwd123");
        Giocatore g = new Giocatore(u);

        g.setPosizione(0);
        Proprietà p = tabellone.calcolaNuovaPosizione(g, 3);

        assertEquals(3, g.getPosizione(), "La nuova posizione deve essere 3");
        assertEquals(tabellone.getCaselle().get(3), p, "La proprietà restituita deve corrispondere alla casella 3");
    }

    /**
     * Verifica il calcolo della nuova posizione con overflow.
     * <p>
     * - Imposta la posizione del giocatore all'ultima casella. <br>
     * - Muove la pedina di 2 posizioni. <br>
     * - Controlla che la nuova posizione sia 1 (wrap-around modulo numero caselle). <br>
     * - Verifica che la proprietà restituita corrisponda alla casella 1.
     * </p>
     */
    @Test
    public void testCalcolaNuovaPosizioneConOverflow() {
        Tabellone tabellone = new Tabellone();
        Utente u = new Utente("Mario Rossi", "mario", "pwd123");
        Giocatore g = new Giocatore(u);

        g.setPosizione(tabellone.getCaselle().size() - 1);
        Proprietà p = tabellone.calcolaNuovaPosizione(g, 2);

        assertEquals(1, g.getPosizione(), "La posizione deve fare wrap-around (modulo caselle.size)");
        assertEquals(tabellone.getCaselle().get(1), p, "La proprietà deve corrispondere alla casella 1");
    }

    /**
     * Verifica la rimozione della pedina dal tabellone.
     * <p>
     * - Imposta la posizione del giocatore a 5. <br>
     * - Invoca {@code rimuoviPedina}. <br>
     * - Controlla che la posizione del giocatore sia -1,
     *   indicando che la pedina è stata rimossa.
     * </p>
     */
    @Test
    public void testRimuoviPedina() {
        Tabellone tabellone = new Tabellone();
        Utente u = new Utente("Mario Rossi", "mario", "pwd123");
        Giocatore g = new Giocatore(u);

        g.setPosizione(5);
        tabellone.rimuoviPedina(g);

        assertEquals(-1, g.getPosizione(), "La posizione deve essere -1 dopo la rimozione della pedina");
    }
}
