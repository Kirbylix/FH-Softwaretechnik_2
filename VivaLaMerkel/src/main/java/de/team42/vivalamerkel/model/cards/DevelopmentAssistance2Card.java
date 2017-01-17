package de.team42.vivalamerkel.model.cards;

import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.global.DiscardPile;
import de.team42.vivalamerkel.model.global.GameField;
import de.team42.vivalamerkel.util.CardReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DevelopmentAssistance2Card extends Card {
    private  final Logger LOG = LogManager.getLogger(DevelopmentAssistance2Card.class.getName());

    /**
     * Konstruktor
     */
    public DevelopmentAssistance2Card() {
        super(  CardReader.getCardType("DEVELOPMENTASSISTANCE2_TYPE"),
                CardReader.getString("DEVELOPMENTASSISTANCE2_NAME"),
                CardReader.getPhase("DEVELOPMENTASSISTANCE2_PLAYAT"),
                CardReader.getString("DEVELOPMENTASSISTANCE2_FUNCTION"));
    }

    /**
     * 2 Millionen Euro
     */
    @Override
    public void execute(int playerId) {
        try {
            GameField.getInstance().getPlayerById(playerId).addWealth(2000000);
            GameField.getInstance().getPlayerById(playerId).getHand().removeCard(this.getId());
            DiscardPile.getInstance().addCard(this);
            LOG.info("2 Millionen € wurden dem Spieler " + GameField.getInstance().getPlayerById(playerId).getName() + " hinzugefügt");
        }catch(NullPointerException ex){
            LOG.error(ex);
        }
    }
}
