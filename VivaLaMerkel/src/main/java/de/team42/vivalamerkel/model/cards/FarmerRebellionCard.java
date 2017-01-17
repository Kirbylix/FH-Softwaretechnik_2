package de.team42.vivalamerkel.model.cards;

import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.Dice;
import de.team42.vivalamerkel.model.Fighter;
import de.team42.vivalamerkel.model.global.DiscardPile;
import de.team42.vivalamerkel.model.global.GameField;
import de.team42.vivalamerkel.util.CardReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FarmerRebellionCard extends Card {
    private final Logger LOG = LogManager.getLogger(FarmerRebellionCard.class.getName());

    /**
     * Konstruktor
     */
    public FarmerRebellionCard() {
        super(  CardReader.getCardType("FARMERREBELLION_TYPE"),
                CardReader.getString("FARMERREBELLION_NAME"),
                CardReader.getPhase("FARMERREBELLION_PLAYAT"),
                CardReader.getString("FARMERREBELLION_FUNCTION"));
    }

    /**
     * Alle verteidigenden Bürger haben in diesem Kampf den Wert 2
     */
    @Override
    public void execute(int playerId) {
        try {
            for(Fighter def: GameField.getInstance().getFights().peek().getDefender()){
                for(Dice d: def.getDices()){
                    d.setCount(2);
                }
            }
            GameField.getInstance().getPlayerById(playerId).getHand().removeCard(this.getId());
            DiscardPile.getInstance().addCard(this);
            LOG.info("Alle verteidigenden Bürger haben in diesem Kampf den Wert 2");
        }catch(NullPointerException ex){
            LOG.error(ex);
        }
    }
}