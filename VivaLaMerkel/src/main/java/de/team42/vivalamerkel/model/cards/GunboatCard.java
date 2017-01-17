package de.team42.vivalamerkel.model.cards;

import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.global.DiscardPile;
import de.team42.vivalamerkel.model.global.GameField;
import de.team42.vivalamerkel.util.CardReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GunboatCard extends Card {
    private final Logger LOG = LogManager.getLogger(GunboatCard.class.getName());

    /**
     * Konstruktor
     */
    public GunboatCard() {
        super(  CardReader.getCardType("GUNBOAT_TYPE"),
                CardReader.getString("GUNBOAT_NAME"),
                CardReader.getPhase("GUNBOAT_PLAYAT"),
                CardReader.getString("GUNBOAT_FUNCTION"));
    }

    /**
     * Dein Kampfwert erhöht sich um +3
     */
    @Override
    public void execute(int playerId) {
        try {
            GameField.getInstance().getFights().peek().getFighterById(playerId).addFightPoints(3);
            GameField.getInstance().getPlayerById(playerId).getHand().removeCard(this.getId());
            DiscardPile.getInstance().addCard(this);
            LOG.info("Der Kampfwert wurde um 3 erhöht");
        }catch(NullPointerException ex){
            LOG.error(ex);
        }
    }
}
