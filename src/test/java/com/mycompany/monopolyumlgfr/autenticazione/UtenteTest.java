/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.monopolyumlgfr.autenticazione;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe di test per {@link Utente}.
 * <p>
 * Questa suite verifica il corretto funzionamento del costruttore e dei metodi getter
 * della classe {@link Utente}. In particolare:
 * <ul>
 *   <li>Che i valori passati al costruttore vengano memorizzati correttamente.</li>
 *   <li>Che i campi non siano null quando inizializzati con valori validi.</li>
 *   <li>Che la classe gestisca correttamente stringhe vuote.</li>
 * </ul>
 * <!-- </p> -->
 */
public class UtenteTest {

    /**
     * Verifica che il costruttore assegni correttamente i valori
     * e che i metodi getter li restituiscano.
     * <p>
     * - Crea un utente con nome, username e password. <br>
     * - Controlla che i getter restituiscano gli stessi valori.
     * </p>
     */
    @Test
    public void testCostruttoreAndGetter() {
        Utente u = new Utente("Mario Rossi", "mario", "pwd123");

        assertEquals("Mario Rossi", u.getNome(), "Il nome deve corrispondere");
        assertEquals("mario", u.getUsername(), "Lo username deve corrispondere");
        assertEquals("pwd123", u.getPassword(), "La password deve corrispondere");
    }

    /**
     * Verifica che i campi non siano null quando l'utente
     * è inizializzato con valori validi.
     * <p>
     * - Crea un utente con valori non null. <br>
     * - Controlla che i getter non restituiscano {@code null}.
     * </p>
     */
    @Test
    public void testCampiNonNull() {
        Utente u = new Utente("Luigi Bianchi", "luigi", "pwd456");

        assertNotNull(u.getNome(), "Il nome non deve essere null");
        assertNotNull(u.getUsername(), "Lo username non deve essere null");
        assertNotNull(u.getPassword(), "La password non deve essere null");
    }

    /**
     * Verifica che la classe gestisca correttamente stringhe vuote.
     * <p>
     * - Crea un utente con valori vuoti. <br>
     * - Controlla che i getter restituiscano stringhe vuote
     *   senza sollevare eccezioni.
     * </p>
     */
    @Test
    public void testCampiVuoti() {
        Utente u = new Utente("", "", "");

        assertEquals("", u.getNome(), "Il nome può essere vuoto");
        assertEquals("", u.getUsername(), "Lo username può essere vuoto");
        assertEquals("", u.getPassword(), "La password può essere vuota");
    }
}

