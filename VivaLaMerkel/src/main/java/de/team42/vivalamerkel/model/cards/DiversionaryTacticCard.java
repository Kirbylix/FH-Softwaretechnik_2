package de.team42.vivalamerkel.model.cards;


import de.team42.vivalamerkel.controller.GameController;
import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.Fighter;
import de.team42.vivalamerkel.model.global.DiscardPile;
import de.team42.vivalamerkel.model.global.GameField;
import de.team42.vivalamerkel.util.CardReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DiversionaryTacticCard extends Card {
    private final Logger LOG = LogManager.getLogger(DiversionaryTacticCard.class.getName());

    /**
     * Konstruktor
     */
    public DiversionaryTacticCard() {
        super(  CardReader.getCardType("DIVERSIONARYTACTIC_TYPE"),
                CardReader.getString("DIVERSIONARYTACTIC_NAME"),
                CardReader.getPhase("DIVERSIONARYTACTIC_PLAYAT"),
                CardReader.getString("DIVERSIONARYTACTIC_FUNCTION"));
    }

    /**
     * Alle Würfel werden neu gewürfelt
     */
    @Override
    public void execute(int playerId) {
        try {

            for(Fighter def: GameField.getInstance().getFights().peek().getDefender()){
                def.rollDices();
                def.calculateFightPoints();
            }
            for(Fighter att: GameField.getInstance().getFights().peek().getAttacker()){
                att.rollDices();
                att.calculateFightPoints();
            }
            GameController.getInstance().getFightController().calculateAttackTeamPoints();
            GameController.getInstance().getFightController().calculateDefendTeamPoints();

            GameField.getInstance().getPlayerById(playerId).getHand().removeCard(this.getId());
            DiscardPile.getInstance().addCard(this);
            LOG.info("Alle Beteiligten in Kampf haben neu gewürfelt");
        }catch(NullPointerException ex){
            LOG.error(ex);
        }
    }
}
