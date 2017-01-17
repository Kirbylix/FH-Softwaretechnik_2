package de.team42.vivalamerkel.model.cards;

import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.Fighter;
import de.team42.vivalamerkel.model.global.DiscardPile;
import de.team42.vivalamerkel.model.global.GameField;
import de.team42.vivalamerkel.util.CardReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BriberyCard extends Card {
    private  final Logger LOG = LogManager.getLogger(BriberyCard.class.getName());

    /**
     * Konstruktor
     */
    public BriberyCard() {
        super(  CardReader.getCardType("BRIBERY_TYPE"),
                CardReader.getString("BRIBERY_NAME"),
                CardReader.getPhase("BRIBERY_PLAYAT"),
                CardReader.getString("BRIBERY_FUNCTION"));
    }

    /**
     * Ignoriere in diesem Kampf die Bürger des Kanzlers
     */
    @Override
    public void execute(int playerId) {
        try {
            for( Fighter fighter : GameField.getInstance().getFights().peek().getDefender()) {
                if (fighter.getPlayerId() == GameField.getInstance().getChancellorPlayerId()) {
                    fighter.removeAllCitizens();
                    GameField.getInstance().getPlayerById(playerId).getHand().removeCard(this.getId());
                    DiscardPile.getInstance().addCard(this);
                    LOG.info("alle Bürger von " + GameField.getInstance().getPlayerById(GameField.getInstance().getChancellorPlayerId()).getName() + " (Kanzler) wurde entfernt");
                }
            }
        }catch(NullPointerException ex){
            LOG.error(ex);
        }
    }
}