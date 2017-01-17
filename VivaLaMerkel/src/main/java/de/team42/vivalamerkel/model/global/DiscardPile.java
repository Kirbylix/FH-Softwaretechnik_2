package de.team42.vivalamerkel.model.global;

import de.team42.vivalamerkel.model.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.Serializable;
import java.util.List;
import java.util.Stack;

/**
 * Ablegestapel
 */
public class DiscardPile  implements Serializable {
    private final Logger LOG = LogManager.getLogger(DiscardPile.class.getName());

    private Stack<Card> cards;
    private static DiscardPile instance = null;

    /**
     * Konstruktor
     */
    private  DiscardPile(){
        this.cards = new Stack<Card>();
        System.out.println("==> Initialisiere: " + getClass().getSimpleName() +"\n" );
    }

    /**
     * Get aktuelle Instance
     * @return Instance
     */
    public static DiscardPile getInstance(){
        if (instance == null) {
            synchronized (DiscardPile.class) {
                if (instance == null) {
                    instance = new DiscardPile();
                }
            }
        }
        return instance;
    }

    /**
     * Karte dem Stapel hinzufügen
     * @param card Karte
     */
    public void addCard(Card card){
        try{
            this.cards.push(card);
        }catch(NullPointerException ex) {
            LOG.error("Ablegestapel = NULL \n", ex);
        }
    }

    /**
     * Karten dem Stapel hinzufügen
     * @param cards Karten
     */
    public void addCards(List<Card> cards){
        for(Card card: cards){
            this.cards.add(card);
        }
    }

    /**
     * gemischter Stapel zurückgeben & Stepel leeren
     * @return Karten
     */
    public Stack<Card> removeDeck(){
        Stack<Card> c = this.cards;
        this.cards = new Stack<>();
        LOG.info("Karten wurdem vom Ablegestapel entfernt");
        return c;
    }
}