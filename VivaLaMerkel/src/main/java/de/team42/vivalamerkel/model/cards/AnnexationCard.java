package de.team42.vivalamerkel.model.cards;

import de.team42.vivalamerkel.model.Building;
import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.global.DiscardPile;
import de.team42.vivalamerkel.model.global.GameField;
import de.team42.vivalamerkel.util.CardReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AnnexationCard extends Card {
    private  final Logger LOG = LogManager.getLogger(AnnexationCard.class.getName());

    private boolean attackSucess = false;
    private int defenderId;

    /**
     * Konstruktor
     */
    public AnnexationCard() {
        super(  CardReader.getCardType("ANNEXATION_TYPE"),
                CardReader.getString("ANNEXATION_NAME"),
                CardReader.getPhase("ANNEXATION_PLAYAT"),
                CardReader.getString("ANNEXATION_FUNCTION"));
    }

    /**
     * Angraiff war erfolgreich
     */
    public void setAttackSucess() {
        this.attackSucess = true;
    }

    /**
     * Verteidiger von dem ein Gebäude geraubt werden soll
     * @param defenderId Verteidiger
     */
    @Override
    public void setSettings(int defenderId) {
        this.defenderId = defenderId;
    }

    /**
     * Ist dein Angriff erfolgreich, übernimm 1 Gebäude des Verteidigers
     */
    @Override
    public void execute(int playerId) {
        try {
            if(this.attackSucess) {
                List<Building> bPlayer = GameField.getInstance().getPlayerById(playerId).getPlayerField().getBuildings();
                List<Building> bDefender = GameField.getInstance().getPlayerById(this.defenderId).getPlayerField().getBuildings();
                // vorhandene Gebäude entfernen
                bPlayer.forEach(bDefender::remove);
                if(bDefender.size() > 0){
                    Building b = bDefender.get(0);
                    GameField.getInstance().getPlayerById(playerId).getPlayerField().addBuilding(b);
                    GameField.getInstance().getPlayerById(defenderId).getPlayerField().removeBuilding(b);
                    LOG.info("Ein Gebäude von " + GameField.getInstance().getPlayerById(defenderId).getName() + " wurde eingenommen");
                }else{
                    LOG.info("Der Spieler hat schon alle Gebäude des Types vom Verteidiger");
                }
            }
            GameField.getInstance().getPlayerById(playerId).getHand().removeCard(this.getId());
            DiscardPile.getInstance().addCard(this);
        }catch(NullPointerException ex){
            LOG.error(ex);
        }
    }
}