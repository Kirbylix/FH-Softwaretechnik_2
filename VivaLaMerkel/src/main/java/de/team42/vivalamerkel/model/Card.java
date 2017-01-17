package de.team42.vivalamerkel.model;

import de.team42.vivalamerkel.util.enums.CardType;
import de.team42.vivalamerkel.util.enums.GamePhase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Karte
 */
public abstract class Card{
    private Logger LOG = LogManager.getLogger(Card.class.getName());

    private static int nextId = 1;
    private final int id;
    private final CardType type;
    private final String title;
    private final GamePhase playAt;
    private final String function;
    private final int cost = 1000000;


    /**
     * Konstruktor
     * @param type   Kartentype
     * @param title  Titel
     * @param playAt wann ist die Karte spielbar
     */
    public Card(CardType type, String title, GamePhase playAt, String function) {
        this.id = nextId;
        nextId++;
        this.type = type;
        this.title = title;
        this.playAt = playAt;
        this.function = function;
    }

    /**
     * get Id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * get Type
     * @return Type
     */
    public CardType getType() {
        return type;
    }

    /**
     * get Title
     * @return Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * get wann spielbar
     * @return GamePhase
     */
    public GamePhase getPlayAt() {
        return playAt;
    }

    /**
     * get Funktion
     * @return Funktion
     */
    public String getFunction() {
        return function;
    }

    /**
     * get Kosten
     * @return Kosten
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Funktion der Karte ausführen
     * @param playerId Kartenspieler Id
     */
    public abstract void execute(int playerId);

    /**
     * Wert für Funktion setzten
     * @param value wert
     */
    public void setSettings(int value) {}
}