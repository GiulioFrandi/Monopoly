/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.monopolyumlgfr.gioco;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe di test per {@link Proprietà}.
 * <p>
 * Questa suite verifica il corretto funzionamento della classe Proprietà:
 * <ul>
 *   <li>Inizializzazione con valori di default.</li>
 *   <li>Modifica dello stato tramite i setter.</li>
 *   <li>Calcolo dell'affitto in base al numero di edifici.</li>
 *   <li>Gestione dei casi limite (oltre la lunghezza dell'array affitti).</li>
 * </ul>
 * <!-- </p> -->
 */
public class ProprietàTest {

    /**
     * Verifica l'inizializzazione della proprietà.
     * <p>
     * - Crea una proprietà con nome, valore e array di affitti. <br>
     * - Controlla che i valori iniziali siano corretti: libera, non ipotecata,
     *   edifici = 0 e affitti corrispondenti.
     * </p>
     */
    @Test
    public void testInizializzazione() {
        int[] affitti = {10, 20, 30};
        Proprietà p = new Proprietà("Viale Test", 200, affitti);

        assertEquals("Viale Test", p.getNome(), "Il nome deve corrispondere");
        assertEquals(200, p.getValore(), "Il valore deve corrispondere");
        assertTrue(p.isLibera(), "La proprietà deve essere inizialmente libera");
        assertFalse(p.isIpotecata(), "La proprietà non deve essere ipotecata all'inizio");
        assertEquals(0, p.getEdifici(), "Il numero di edifici iniziale deve essere 0");
        assertArrayEquals(affitti, p.getAffitto(), "Gli affitti devono corrispondere");
    }

    /**
     * Verifica i metodi setter della proprietà.
     * <p>
     * - Imposta la proprietà come occupata. <br>
     * - Imposta la proprietà come ipotecata. <br>
     * - Aggiorna il numero di edifici. <br>
     * - Controlla che i valori siano aggiornati correttamente.
     * </p>
     */
    @Test
    public void testSetters() {
        Proprietà p = new Proprietà("Viale Test", 200, new int[]{10, 20, 30});

        p.setLibera(false);
        p.setIpotecata(true);
        p.setEdifici(2);

        assertFalse(p.isLibera(), "La proprietà deve risultare occupata");
        assertTrue(p.isIpotecata(), "La proprietà deve risultare ipotecata");
        assertEquals(2, p.getEdifici(), "Il numero di edifici deve essere aggiornato");
    }

    /**
     * Verifica il calcolo dell'affitto senza edifici.
     * <p>
     * - Imposta edifici = 0. <br>
     * - Controlla che l'affitto restituito sia il primo valore dell'array.
     * </p>
     */
    @Test
    public void testCalcolaAffittoSenzaEdifici() {
        Proprietà p = new Proprietà("Viale Test", 200, new int[]{10, 20, 30});
        p.setEdifici(0);
        assertEquals(10, p.calcolaAffitto(), "Affitto base deve essere 10");
    }

    /**
     * Verifica il calcolo dell'affitto con edifici.
     * <p>
     * - Imposta edifici = 1 e controlla che l'affitto sia 20. <br>
     * - Imposta edifici = 2 e controlla che l'affitto sia 30.
     * </p>
     */
    @Test
    public void testCalcolaAffittoConEdifici() {
        Proprietà p = new Proprietà("Viale Test", 200, new int[]{10, 20, 30});
        p.setEdifici(1);
        assertEquals(20, p.calcolaAffitto(), "Affitto con 1 edificio deve essere 20");

        p.setEdifici(2);
        assertEquals(30, p.calcolaAffitto(), "Affitto con 2 edifici deve essere 30");
    }

    /**
     * Verifica il calcolo dell'affitto oltre il limite dell'array.
     * <p>
     * - Imposta edifici = 5 (oltre la lunghezza dell'array). <br>
     * - Controlla che l'affitto restituito sia l'ultimo valore dell'array.
     * </p>
     */
    @Test
    public void testCalcolaAffittoOltreLimite() {
        Proprietà p = new Proprietà("Viale Test", 200, new int[]{10, 20, 30});
        p.setEdifici(5); // oltre la lunghezza dell'array
        assertEquals(30, p.calcolaAffitto(), "Affitto massimo deve essere l'ultimo valore (30)");
    }
}
