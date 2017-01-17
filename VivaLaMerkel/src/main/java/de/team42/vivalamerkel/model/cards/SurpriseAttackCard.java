package de.team42.vivalamerkel.model.cards;

import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.global.DiscardPile;
import de.team42.vivalamerkel.model.global.GameField;
import de.team42.vivalamerkel.util.CardReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SurpriseAttackCard extends Card {
    private final Logger LOG = LogManager.getLogger(SurpriseAttackCard.class.getName());

    /**
     * Konstruktor
     */
    public SurpriseAttackCard() {
        super(  CardReader.getCardType("SURPRISEATTACK_TYPE"),
                CardReader.getString("SURPRISEATTACK_NAME"),
                CardReader.getPhase("SURPRISEATTACK_PLAYAT"),
                CardReader.getString("SURPRISEATTACK_FUNCTION"));
    }

    /**
     * Ignoriere in diesem Kampf alle Gebäude des Verteidigers
     */
    @Override
    public void execute(int playerId) {
        try {
            GameField.getInstance().getFights().peek().setIgnoreBuildings(true);
            GameField.getInstance().getPlayerById(playerId).getHand().removeCard(this.getId());
            DiscardPile.getInstance().addCard(this);
            LOG.info("In diesem Kampf werden alle Gebäude der Verteidiger ignoriert");
        }catch(NullPointerException ex){
            LOG.error(ex);
        }
    }
}