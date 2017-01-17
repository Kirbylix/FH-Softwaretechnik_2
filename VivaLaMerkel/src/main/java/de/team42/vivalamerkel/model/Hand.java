package de.team42.vivalamerkel.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hand implements Serializable{
    private final Logger LOG = LogManager.getLogger(Hand.class.getName());

    private final static int HANDLIMIT = 4;
    private List<Card> cards;

    /**
     * Konstruktor
     */
    public Hand(){
        this.cards = new ArrayList<>();
        LOG.info("==> Initialisiere: " + getClass().getSimpleName());
    }

    /**
     * Prüfe Handlimit
     * @return <code>true</code> OK; <code>false</code> limit überschritten
     */
    public boolean checkHandLimit(){
        return this.cards.size() >= HANDLIMIT;
    }

    /**
     * Karte aufnehmen
     * @param card Karte
     */
    public void addCard(Card card){
        try {
            this.cards.add(card);
        }catch (NullPointerException ex){
            LOG.error("Cards = NULL \n", ex);
        }
    }

    /**
     * Karte entfernen
     * @param cardId Karten Id
     * @return <code>true</code> Karte erfolgreich entfernt; <code>false</code> Karte (ID) nicht vorhanden
     */
    public boolean removeCard(int cardId){
        for(Card c: this.cards){
            if(c.getId() == cardId){
                this.cards.remove(c);
                return true;
            }
        }
        LOG.error("eine Karte mit der ID: " + cardId + " ist nicht auf der Hand");
        return false;
    }

    /**
     * getCard
     * @param cardId Karten ID
     * @return Karte
     */
    public Card getCard(int cardId){
        assert this.cards.size() > 0;
        for(Card c: this.cards){
            if(c.getId() == cardId) return c;
        }
        LOG.error("Eine Karte mit der ID: " + cardId + " wurde nicht gefunden!");
        return null;
    }

    /**
     * Get Anzahl der Handkarten
     * @return Anzahl der Handkarten
     */
    public int getSize() {
        return cards.size();
    }

    /**
     * Get alle Handkarten
     * @return Karten
     */
    public List<Card> getCards() {
        return cards;
    }
}