package de.team42.vivalamerkel.model.cards;

import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.global.DiscardPile;
import de.team42.vivalamerkel.model.global.GameField;
import de.team42.vivalamerkel.util.CardReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpyCard extends Card {
    private final Logger LOG = LogManager.getLogger(SpyCard.class.getName());

    /**
     * Konstruktor
     */
    public SpyCard() {
        super(  CardReader.getCardType("SPY_TYPE"),
                CardReader.getString("SPY_NAME"),
                CardReader.getPhase("SPY_PLAYAT"),
                CardReader.getString("SPY_FUNCTION"));
    }

    /**
     * Schau dir die Milizen aller Mitspieler an, bevor du deine eigenen befehligst. Die Mitspieler dürfen Ihre Bürger nicht mehr ändern
     */
    @Override
    public void execute(int playerId) {
        try {
            //TODO Karten Funktion
            GameField.getInstance().getPlayerById(playerId).getHand().removeCard(this.getId());
            DiscardPile.getInstance().addCard(this);
            LOG.info("");
        }catch(NullPointerException ex){
            LOG.error(ex);
        }
    }
}