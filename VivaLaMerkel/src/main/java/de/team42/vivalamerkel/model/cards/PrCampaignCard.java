package de.team42.vivalamerkel.model.cards;

import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.global.DiscardPile;
import de.team42.vivalamerkel.model.global.GameField;
import de.team42.vivalamerkel.util.CardReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrCampaignCard extends Card {
    private final Logger LOG = LogManager.getLogger(PrCampaignCard.class.getName());

    /**
     * Konstruktor
     */
    public PrCampaignCard() {
        super(  CardReader.getCardType("PRCAMPAIGN_TYPE"),
                CardReader.getString("PRCAMPAIGN_NAME"),
                CardReader.getPhase("PRCAMPAIGN_PLAYAT"),
                CardReader.getString("PRCAMPAIGN_FUNCTION"));
    }

    /**
     * Du erhälst 1 Bürger gratis
     */
    @Override
    public void execute(int playerId) {
        try {
            GameField.getInstance().getPlayerById(playerId).getPlayerField().addCitizens(1);
            DiscardPile.getInstance().addCard(this);
            LOG.info("Dem Spieler " + GameField.getInstance().getPlayerById(playerId).getName() + " wurde 1 Bürger hinzugefügt");
        }catch(NullPointerException ex){
            LOG.error(ex);
        }
    }
}
