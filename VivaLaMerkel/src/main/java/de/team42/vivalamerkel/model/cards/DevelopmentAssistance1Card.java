package de.team42.vivalamerkel.model.cards;

import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.global.DiscardPile;
import de.team42.vivalamerkel.model.global.GameField;
import de.team42.vivalamerkel.util.CardReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DevelopmentAssistance1Card extends Card {
    private  final Logger LOG = LogManager.getLogger(DevelopmentAssistance1Card.class.getName());

    /**
     * Konstruktor
     */
    public DevelopmentAssistance1Card() {
        super(  CardReader.getCardType("DEVELOPMENTASSISTANCE1_TYPE"),
                CardReader.getString("DEVELOPMENTASSISTANCE1_NAME"),
                CardReader.getPhase("DEVELOPMENTASSISTANCE1_PLAYAT"),
                CardReader.getString("DEVELOPMENTASSISTANCE1_FUNCTION"));
    }

    /**
     * 1 Millionen Euro
     */
    @Override
    public void execute(int playerId) {
        try {
            GameField.getInstance().getPlayerById(playerId).addWealth(1000000);
            GameField.getInstance().getPlayerById(playerId).getHand().removeCard(this.getId());
            DiscardPile.getInstance().addCard(this);
            LOG.info("1 Millionen € wurden dem Spieler " + GameField.getInstance().getPlayerById(playerId).getName() + " hinzugefügt");
        }catch(NullPointerException ex){
            LOG.error(ex);
        }
    }
}
