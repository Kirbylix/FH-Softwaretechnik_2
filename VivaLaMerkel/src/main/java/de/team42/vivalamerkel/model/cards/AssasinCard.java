package de.team42.vivalamerkel.model.cards;

import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.global.DiscardPile;
import de.team42.vivalamerkel.model.global.GameField;
import de.team42.vivalamerkel.util.CardReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AssasinCard extends Card {
    private  final Logger LOG = LogManager.getLogger(AssasinCard.class.getName());

    private int enemyId;

    /**
     * Konstruktor
     */
    public AssasinCard() {
        super(  CardReader.getCardType("ASSASIN_TYPE"),
                CardReader.getString("ASSASIN_NAME"),
                CardReader.getPhase("ASSASIN_PLAYAT"),
                CardReader.getString("ASSASIN_FUNCTION"));
    }

    /**
     * Set Gegner
     * @param id GegnerId
     */
    @Override
    public void setSettings(int id) {
        this.enemyId = id;
    }

    /**
     * Zerstöre 1 gegnerische Bürger
     */
    @Override
    public void execute(int playerId) {
        try {
            GameField.getInstance().getPlayerById(enemyId).getPlayerField().removeOneCitizien();
            GameField.getInstance().getPlayerById(playerId).getHand().removeCard(this.getId());
            DiscardPile.getInstance().addCard(this);
            LOG.info("1 Bürger von " + GameField.getInstance().getPlayerById(enemyId).getName() + " wurde zerstört");
        }catch(NullPointerException ex){
            LOG.error(ex);
        }
    }
}
