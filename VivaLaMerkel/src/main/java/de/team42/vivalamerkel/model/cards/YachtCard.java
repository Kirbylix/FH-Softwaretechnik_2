package de.team42.vivalamerkel.model.cards;

import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.util.CardReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class YachtCard extends Card {
    private final Logger LOG = LogManager.getLogger(YachtCard.class.getName());

    /**
     * Konstruktor
     */
    public YachtCard() {
        super(  CardReader.getCardType("YACHT_TYPE"),
                CardReader.getString("YACHT_NAME"),
                CardReader.getPhase("YACHT_PLAYAT"),
                CardReader.getString("YACHT_FUNCTION"));
    }

    /**
     * 1 Siegpunkt
     */
    @Override
    public void execute(int playerId) {
        //Diese Karte kann nicht ausgespielt werden
        //sie kann ledeglich den Besitzer wechseln
        //oder versprochen werden
    }
}