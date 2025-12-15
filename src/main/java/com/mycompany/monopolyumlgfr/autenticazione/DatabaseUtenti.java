/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.monopolyumlgfr.autenticazione;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Archivio utenti basato su file XML per il gioco Monopoly UML GFR.
 * <p>
 * Questa classe gestisce la persistenza degli utenti tramite un documento XML,
 * offrendo operazioni di lettura ({@link #caricaUtenti()}) e scrittura
 * ({@link #salvaUtente(Utente)}). Se il file non è presente nella working directory,
 * viene copiato dalle risorse del classpath (es. {@code /utenti.xml}).
 * </p>
 *
 * <h2>Formato atteso del file XML</h2>
 * <pre>{@code
 * <utenti>
 *   <utente>
 *     <nome>Mario Rossi</nome>
 *     <username>mrossi</username>
 *     <password>pwd</password>
 *   </utente>
 *   ...
 * </utenti>
 * }</pre>
 *
 * <h2>Note</h2>
 * <ul>
 *   <li>La classe non è thread-safe: se usata in contesti concorrenti, proteggere accessi con sincronizzazione esterna.</li>
 *   <li>Gli errori di I/O e parsing XML vengono stampati su stderr tramite {@code ex.printStackTrace()}.</li>
 *   <li>Non viene effettuato il controllo di unicità su {@code username} in fase di salvataggio.</li>
 * </ul>
 *
 * @author giuli
 */
public class DatabaseUtenti {

    /**
     * Nome della risorsa (es. {@code "utenti.xml"}) usata per individuare
     * il file nella working directory e nel classpath.
     */
    private final String resourceName;

    /**
     * Riferimento al file XML nella working directory.
     */
    private final File xmlFile;

    /**
     * Crea un archivio utenti basato su un file XML.
     * <p>
     * Se il file indicato da {@code resourceName} non esiste nella working directory,
     * tenta di copiarlo dalle risorse del classpath (percorso {@code "/" + resourceName}).
     * </p>
     *
     * @param resourceName nome del file XML (e risorsa nel classpath), es. {@code "utenti.xml"}
     * @throws IllegalStateException se la risorsa non è trovata nel classpath
     */
    public DatabaseUtenti(String resourceName) {
        this.resourceName = resourceName;
        this.xmlFile = new File(resourceName);

        // Se il file non esiste nella working dir, copia da resources
        if (!xmlFile.exists()) {
            try (InputStream is = getClass().getResourceAsStream("/" + resourceName)) {
                if (is == null) {
                    throw new IllegalStateException("Risorsa non trovata: " + resourceName);
                }
                Files.copy(is, xmlFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Carica tutti gli utenti dal file XML.
     * <p>
     * Effettua il parsing del documento, estrae i nodi {@code <utente>} e
     * costruisce la lista di istanze {@link Utente}. Gli elementi attesi sono
     * {@code <nome>}, {@code <username>} e {@code <password>}.
     * </p>
     *
     * @return lista di utenti caricati; se si verifica un errore, ritorna una lista vuota
     */
    public List<Utente> caricaUtenti() {
        List<Utente> utenti = new ArrayList<>();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList lista = doc.getElementsByTagName("utente");
            for (int i = 0; i < lista.getLength(); i++) {
                Element e = (Element) lista.item(i);
                String nome = e.getElementsByTagName("nome").item(0).getTextContent();
                String username = e.getElementsByTagName("username").item(0).getTextContent();
                String password = e.getElementsByTagName("password").item(0).getTextContent();
                utenti.add(new Utente(nome, username, password));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return utenti;
    }

    /**
     * Aggiunge un nuovo utente al file XML e persiste la modifica.
     * <p>
     * L'utente viene serializzato come nuovo nodo {@code <utente>} sotto il root.
     * Il documento risultante viene scritto su disco con indentazione abilitata.
     * </p>
     *
     * @param u utente da salvare; deve avere campi valorizzati (nome, username, password)
     */
    public void salvaUtente(Utente u) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            Element root = doc.getDocumentElement();
            Element nuovo = doc.createElement("utente");

            Element nome = doc.createElement("nome");
            nome.appendChild(doc.createTextNode(u.getNome()));
            nuovo.appendChild(nome);

            Element username = doc.createElement("username");
            username.appendChild(doc.createTextNode(u.getUsername()));
            nuovo.appendChild(username);

            Element password = doc.createElement("password");
            password.appendChild(doc.createTextNode(u.getPassword()));
            nuovo.appendChild(password);

            root.appendChild(nuovo);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // formattazione
            transformer.transform(new DOMSource(doc), new StreamResult(xmlFile));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
