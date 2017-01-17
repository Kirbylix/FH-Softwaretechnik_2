package de.team42.vivalamerkel.model.cards;

import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.global.DiscardPile;
import de.team42.vivalamerkel.model.global.GameField;
import de.team42.vivalamerkel.util.CardReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DevelopmentAssistance3Card extends Card {
    private final Logger LOG = LogManager.getLogger(AssasinCard.class.getName());

    /**
     * Konstruktor
     */
    public DevelopmentAssistance3Card() {
        super(  CardReader.getCardType("DEVELOPMENTASSISTANCE3_TYPE"),
                CardReader.getString("DEVELOPMENTASSISTANCE3_NAME"),
                CardReader.getPhase("DEVELOPMENTASSISTANCE3_PLAYAT"),
                CardReader.getString("DEVELOPMENTASSISTANCE3_FUNCTION"));
    }

    /**
     * 3 Millionen Euro
     */
    @Override
    public void execute(int playerId) {
        try {
            GameField.getInstance().getPlayerById(playerId).addWealth(3000000);
            GameField.getInstance().getPlayerById(playerId).getHand().removeCard(this.getId());
            DiscardPile.getInstance().addCard(this);
            LOG.info("3 Millionen € wurden dem Spieler " + GameField.getInstance().getPlayerById(playerId).getName() + " hinzugefügt");
        }catch(NullPointerException ex){
            LOG.error(ex);
        }
    }
}
