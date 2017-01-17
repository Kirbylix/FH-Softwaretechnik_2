package de.team42.vivalamerkel.model.cards;

import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.util.CardReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LuxuryCarCard extends Card {
    private final Logger LOG = LogManager.getLogger(LuxuryCarCard.class.getName());

    /**
     * Konstruktor
     */
    public LuxuryCarCard() {
        super(  CardReader.getCardType("LUXURYCAR_TYPE"),
                CardReader.getString("LUXURYCAR_NAME"),
                CardReader.getPhase("LUXURYCAR_PLAYAT"),
                CardReader.getString("LUXURYCAR_FUNCTION"));
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
