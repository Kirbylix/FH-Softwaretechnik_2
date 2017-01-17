package de.team42.vivalamerkel.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Kämpfer
 */
public class Fighter{
    private final Logger LOG = LogManager.getLogger(Fighter.class.getName());

    private final int playerId;
    private List<Dice> dices;
    private int fightPoint;

    /**
     * Konstruktor
     */
    public Fighter(int playerId){
        this.playerId = playerId;
        this.dices = new ArrayList<>();
        this.fightPoint = 0;
    }

    /**
     * alle Würfel würfeln
     */
    public void rollDices(){
        this.dices.forEach(Dice::roll);
    }

    /**
     * berechnet die Kampfpunkte
     */
    public void calculateFightPoints(){
        for(Dice dice: this.dices){
            this.fightPoint += dice.getCount();
        }
        LOG.info("Kampfwerte wurden berechnet für Spieler " + playerId);
    }

    /**
     * get Kampfpunkte
     * @return Kampfpunkte
     */
    public int getFightPoint() {
        return fightPoint;
    }

    /**
     * get Spieler ID
     * @return Spieler ID
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Anzahl der Bürger (Würfel)
     * @return Anzahl
     */
    public int getSizeOfDices(){
        return this.dices.size();
    }

    /**
     * hinzufügen von Kampfpunkten
     * @param value Wert
     */
    public void addFightPoints(int value){
        this.fightPoint += value;
        LOG.info(value + " wurden den Kampfpunkten hinzugefügt");
    }

    /**
     * Bürger/ Würfel hinzufügen
     * @param dice Bürger/ Würfel
     */
    public void addDice(Dice dice){
        this.dices.add(dice);
    }

    /**
     * get Würfel/ Bürger
     * @return Liste Würfel/ Bürger
     */
    public List<Dice> getDices() {
        return dices;
    }

    /**
     * Alle Bürger werden entfernt
     */
    public void removeAllCitizens(){
        this.dices.clear();
    }
}