package de.team42.vivalamerkel.model.cards;

import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.global.DiscardPile;
import de.team42.vivalamerkel.model.global.GameField;
import de.team42.vivalamerkel.util.CardReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StudentsDistributeLeafletsCard extends Card {
    private final Logger LOG = LogManager.getLogger(StudentsDistributeLeafletsCard.class.getName());

    /**
     * Konstruktor
     */
    public StudentsDistributeLeafletsCard() {
        super(  CardReader.getCardType("STUDENTSDISTRIBUTELEAFLETS_TYPE"),
                CardReader.getString("STUDENTSDISTRIBUTELEAFLETS_NAME"),
                CardReader.getPhase("STUDENTSDISTRIBUTELEAFLETS_PLAYAT"),
                CardReader.getString("STUDENTSDISTRIBUTELEAFLETS_FUNCTION"));
    }

    /**
     * Keine Auswirkungen
     */
    @Override
    public void execute(int playerId) {
        try {
            GameField.getInstance().getPlayerById(playerId).getHand().removeCard(this.getId());
            DiscardPile.getInstance().addCard(this);
            LOG.info("HAHA es passiert nichts");
        }catch(NullPointerException ex){
            LOG.error(ex);
        }
    }
}
