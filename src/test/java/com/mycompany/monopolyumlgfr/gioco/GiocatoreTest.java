/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.monopolyumlgfr.gioco;

import com.mycompany.monopolyumlgfr.autenticazione.Utente;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe di test per {@link Giocatore}.
 * <p>
 * Questa suite verifica il comportamento principale della classe Giocatore:
 * <ul>
 *   <li>Inizializzazione corretta con valori di default.</li>
 *   <li>Movimento della pedina sul tabellone con gestione del wrap-around.</li>
 *   <li>Acquisto di proprietà con denaro sufficiente e insufficiente.</li>
 *   <li>Pagamento dell'affitto.</li>
 *   <li>Abbandono della partita e liberazione delle proprietà.</li>
 * </ul>
 * <!-- </p> -->
 */
public class GiocatoreTest {

    /**
     * Verifica l'inizializzazione del giocatore.
     * <p>
     * - Crea un utente e un giocatore associato. <br>
     * - Controlla che il giocatore sia associato all'utente. <br>
     * - Verifica che il denaro iniziale sia 1500, la posizione 0
     *   e che non possieda proprietà.
     * </p>
     */
    @Test
    public void testInizializzazione() {
        Utente u = new Utente("Mario Rossi", "mario", "pwd123");
        Giocatore g = new Giocatore(u);

        assertEquals(u, g.getUtente(), "Il giocatore deve essere associato all'utente");
        assertEquals(1500, g.getDenaro(), "Il denaro iniziale deve essere 1500");
        assertEquals(0, g.getPosizione(), "La posizione iniziale deve essere 0");
        assertTrue(g.getProprietàPossedute().isEmpty(), "Il giocatore non deve avere proprietà iniziali");
    }

    /**
     * Verifica il movimento della pedina.
     * <p>
     * - Muove la pedina di 5 posizioni. <br>
     * - Muove la pedina di 40 posizioni, verificando il wrap-around
     *   (modulo 40) che riporta la posizione a 5.
     * </p>
     */
    @Test
    public void testMuoverePedina() {
        Utente u = new Utente("Mario Rossi", "mario", "pwd123");
        Giocatore g = new Giocatore(u);

        g.muoverePedina(5);
        assertEquals(5, g.getPosizione(), "La pedina deve muoversi di 5 posizioni");

        g.muoverePedina(40);
        assertEquals(5, g.getPosizione(), "La posizione deve tornare a 5 (mod 40)");
    }

    /**
     * Verifica l'acquisto di una proprietà con denaro sufficiente.
     * <p>
     * - Crea una proprietà dal valore 200. <br>
     * - Il giocatore la acquista. <br>
     * - Controlla che il denaro diminuisca di 200, la proprietà
     *   risulti occupata e sia presente nella lista delle proprietà possedute.
     * </p>
     */
    @Test
    public void testAcquistaProprietà() {
        Utente u = new Utente("Mario Rossi", "mario", "pwd123");
        Giocatore g = new Giocatore(u);

        Proprietà p = new Proprietà("Viale Test", 200, new int[]{20,40,60});
        g.acquistaProprietà(p);

        assertEquals(1300, g.getDenaro(), "Il denaro deve diminuire di 200");
        assertFalse(p.isLibera(), "La proprietà deve risultare occupata");
        assertTrue(g.getProprietàPossedute().contains(p), "Il giocatore deve possedere la proprietà");
    }

    /**
     * Verifica il tentativo di acquisto di una proprietà senza denaro sufficiente.
     * <p>
     * - Imposta il denaro del giocatore a 100. <br>
     * - Tenta di acquistare una proprietà dal valore 200. <br>
     * - Controlla che il denaro non cambi, la proprietà rimanga libera
     *   e non venga aggiunta alla lista delle proprietà possedute.
     * </p>
     */
    @Test
    public void testAcquistaProprietàSenzaDenaro() {
        Utente u = new Utente("Mario Rossi", "mario", "pwd123");
        Giocatore g = new Giocatore(u);
        g.setDenaro(100);

        Proprietà p = new Proprietà("Viale Test", 200, new int[]{20,40,60});
        g.acquistaProprietà(p);

        assertEquals(100, g.getDenaro(), "Il denaro non deve cambiare");
        assertTrue(p.isLibera(), "La proprietà deve rimanere libera");
        assertFalse(g.getProprietàPossedute().contains(p), "Il giocatore non deve possedere la proprietà");
    }

    /**
     * Verifica il pagamento dell'affitto.
     * <p>
     * - Crea una proprietà con affitto base 50. <br>
     * - Il giocatore paga l'affitto. <br>
     * - Controlla che il denaro diminuisca di 50.
     * </p>
     */
    @Test
    public void testPagaAffitto() {
        Utente u = new Utente("Mario Rossi", "mario", "pwd123");
        Giocatore g = new Giocatore(u);

        Proprietà p = new Proprietà("Viale Test", 200, new int[]{50,100,150});
        g.pagaAffitto(p);

        assertEquals(1450, g.getDenaro(), "Il denaro deve diminuire dell'affitto (50)");
    }

    /**
     * Verifica l'abbandono della partita da parte del giocatore.
     * <p>
     * - Crea una proprietà e la assegna al giocatore. <br>
     * - Il giocatore abbandona la partita. <br>
     * - Controlla che le proprietà vengano liberate e che la posizione
     *   sia impostata a -1.
     * </p>
     */
    @Test
    public void testAbbandonaPartita() {
        Utente u = new Utente("Mario Rossi", "mario", "pwd123");
        Giocatore g = new Giocatore(u);

        Proprietà p = new Proprietà("Viale Test", 200, new int[]{20,40,60});
        g.acquistaProprietà(p);

        g.abbandonaPartita();

        assertTrue(g.getProprietàPossedute().isEmpty(), "Le proprietà devono essere liberate");
        assertEquals(-1, g.getPosizione(), "La posizione deve essere -1 dopo l'abbandono");
    }
}

