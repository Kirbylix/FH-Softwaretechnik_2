package de.team42.vivalamerkel.model.cards;

import de.team42.vivalamerkel.model.Building;
import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.global.DiscardPile;
import de.team42.vivalamerkel.model.global.GameField;
import de.team42.vivalamerkel.util.CardReader;
import de.team42.vivalamerkel.util.enums.BuildingType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PartyDonationCard extends Card {
    private final Logger LOG = LogManager.getLogger(PartyDonationCard.class.getName());

    /**
     * Konstruktor
     */
    public PartyDonationCard() {
        super(  CardReader.getCardType("PARTYDONATION_TYPE"),
                CardReader.getString("PARTYDONATION_NAME"),
                CardReader.getPhase("PARTYDONATION_PLAYAT"),
                CardReader.getString("PARTYDONATION_FUNCTION"));
    }

    /**
     * Du erhälst 1 Gebäude gratis
     */
    @Override
    public void execute(int playerId) {
        try {
            for (BuildingType buildingType : BuildingType.values()) {
                if (!GameField.getInstance().getPlayerById(playerId).getPlayerField().buildingExists(buildingType)) {
                    GameField.getInstance().getPlayerById(playerId).getPlayerField().addBuilding(new Building(buildingType));
                    GameField.getInstance().getPlayerById(playerId).getHand().removeCard(this.getId());
                    DiscardPile.getInstance().addCard(this);
                    LOG.info("Dem Spieler " + GameField.getInstance().getPlayerById(playerId).getName() + " wurde 1 Gebäude hinzugefügt");
                }
            }
        }catch(NullPointerException ex){
            LOG.error(ex);
        }
    }
}