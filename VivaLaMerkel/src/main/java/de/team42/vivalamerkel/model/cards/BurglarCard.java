package de.team42.vivalamerkel.model.cards;

import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.global.DiscardPile;
import de.team42.vivalamerkel.model.global.GameField;
import de.team42.vivalamerkel.util.CardReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class BurglarCard extends Card {
    private  final Logger LOG = LogManager.getLogger(BurglarCard.class.getName());

    /**
     * Konstruktor
     */
    public BurglarCard() {
        super(  CardReader.getCardType("BURGLAR_TYPE"),
                CardReader.getString("BURGLAR_NAME"),
                CardReader.getPhase("BURGLAR_PLAYAT"),
                CardReader.getString("BURGLAR_FUNCTION"));
    }

    /**
     * Zufällig eine Handkarte von einem zufälligen Spieler ziehen
     */
    @Override
    public void execute(int playerId) {
        try {
            Random random = new Random();
            int randomPlayerNr = random.nextInt(GameField.getInstance().getSizeOfPlayer());
            int randomCardNr = random.nextInt(GameField.getInstance().getPlayerById(randomPlayerNr).getHand().getSize()-1);
            Card  c = GameField.getInstance().getPlayerById(randomPlayerNr).getHand().getCards().get(randomCardNr);
            GameField.getInstance().getPlayerById(playerId).getHand().addCard(c);
            GameField.getInstance().getPlayerById(randomPlayerNr).getHand().removeCard(c.getId());
            GameField.getInstance().getPlayerById(playerId).getHand().removeCard(this.getId());
            DiscardPile.getInstance().addCard(this);
            LOG.info("Spieler " + playerId + " hat eine Karte vom Spieler: " + randomPlayerNr + " gezogen." );
        }catch(NullPointerException ex){
            LOG.error(ex);
        }
    }
}
