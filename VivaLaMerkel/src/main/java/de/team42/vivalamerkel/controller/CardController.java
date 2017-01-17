package de.team42.vivalamerkel.controller;

import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.global.GameField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

public class CardController implements Serializable {
    private final Logger LOG = LogManager.getLogger(CardController.class.getName());

    private final Card card;
    private final int playerId;

    /**
     * Konstruktor
     */
    public CardController(int playerId, int cardId) {
        card = GameField.getInstance().getPlayerById(playerId).getHand().getCard(cardId);
        this.playerId = playerId;
    }

    /**
     * prüft ob die Karte in der GamePhase spielbar ist
     * @return <code>true</code> Karte ist spielbar; <code>false</code> Karte ist nicht spielbar
     */
    public boolean playAble() {
        return card.getPlayAt() == GameController.getInstance().getPhase();
    }


    /**
     * Get Karte
     * @return Karte
     */
    public Card getCard() {
        return card;
    }

    /**
     * Kartenfunktion ausführen
     */
    public void execute() {
        this.card.execute(this.playerId);
    }
}