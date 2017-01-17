package de.team42.vivalamerkel.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Fight  implements Serializable {
    private final Logger LOG = LogManager.getLogger(Fight.class.getName());

    private static int nextId = 1;
    private final int id;
    private List<Fighter> defender;
    private List<Fighter> attacker;
    private List<Player> neutral;
    private int attackPoints;
    private int defendPoints;
    private boolean chancelorFight;
    private boolean ignoreBuildings;

    /**
     * Konstruktor
     */
    public Fight(){
        this.id = nextId;
        nextId++;
        this.defender = new ArrayList<>();
        this.attacker = new ArrayList<>();
        this.neutral = new ArrayList<>();
        this.attackPoints = 0;
        this.defendPoints = 0;
        this.chancelorFight = false;
        this.ignoreBuildings = false;
    }

    /**
     * Fügt einen Angreifer der Angreifer-Liste hinzu
     * @param attacker Angreifer
     */
    public void addAttacker(Fighter attacker){
        this.attacker.add(attacker);
    }

    /**
     * Get alle Angreifer
     * @return Angreifer
     */
    public List<Fighter> getAttacker() {
        return attacker;
    }

    /**
     * get Alle Verteidiger
     * @return Verteidiger
     */
    public List<Fighter> getDefender() { return defender; }

    /**
     * get Punkte der Angreifer
     * @return Angriffspunkte
     */
    public int getAttackPoints() {
        return attackPoints;
    }

    /**
     * Get Punkte der Verteidiger
     * @return VerteidigerPunkte
     */
    public int getDefendPoints() {
        return defendPoints;
    }

    /**
     * set Punkte der Verteidiger
     * @param defendPoints Verteidigerpunkte
     */
    public void setDefendPoints(int defendPoints) {
        this.defendPoints = defendPoints;
    }

    /**
     * Fügt einen Verteidiger der Verteidiger-Liste hinzu
     * @param defender Verteidiger
     */
    public void addDefender(Fighter defender){
        this.getDefender().add(defender);
    }

    /**
     * Füght Spieler der neutralen liste hinzu die den Kanzler nicht angreifen
     * @param neutral Zuschauen
     */
    public void addNeutral(Player neutral){
        this.neutral.add(neutral);
    }

    /**
     * Gib mir den Angreifer mit der Id
     * @param id Id
     * @return Angreifer
     */
    public Fighter getAttackerById(int id){
        for(Fighter fighter : this.attacker){
            if(id == fighter.getPlayerId()){
                return fighter;
            }
        }
        LOG.error("Ein Angreifer mit der ID: " + id + " ist nicht in diesem Kampf vorhanden");
        return null;
    }

    /**
     * Gib mir den Kämpfer mit der Id
     * @param id Id
     * @return Kämpder
     */
    public Fighter getFighterById(int id){
        for(Fighter fighter : this.attacker){
            if(id == fighter.getPlayerId()){
                return fighter;
            }
        }
        for(Fighter fighter : this.defender){
            if(id == fighter.getPlayerId()){
                return fighter;
            }
        }
        LOG.error("Ein Angreifer mit der ID: " + id + " ist nicht in diesem Kampf vorhanden");
        return null;
    }

    public void removeDicesFromPlayer(int playerId){
        for(Fighter player: this.getDefender()){
            if(playerId == player.getPlayerId()){
                player.removeAllCitizens();
            }
        }
        for(Fighter player: getAttacker()){
            if(playerId == player.getPlayerId()){
                player.removeAllCitizens();
            }
        }
    }

    /**
     * Punkte zu den Angriffspunkte hinzufügen
     * @param value Wert
     */
    public void addAttackPoints(int value){
        this.attackPoints += value;
    }

    /**
     * Punkte zu den Verteidigungsprunkte hinzufügen
     * @param value Wert
     */
    public void addDefendPoints(int value){
        this.defendPoints += value;
    }

    /**
     * Get Liste der neutralen Spieler im Kampf
     * @return Liste von Spieler
     */
    public List<Player> getNeutral() {
        return neutral;
    }

    /**
     * Kampzler Kampf
     * @return <code>true</code> Kanzler Kampf; <code>false</code> Spieler Kampf
     */
    public boolean isChancelorFight() {
        return chancelorFight;
    }

    /**
     * Kanzler Kampf ?
     * @param chancelorFight <code>true</code> Kanzler Kampf; <code>false</code> Spieler Kampf
     */
    public void setChancelorFight(boolean chancelorFight) {
        this.chancelorFight = chancelorFight;
    }

    /**
     * Ignorierung der Geäudeanzahl bei der Kalkulation der Punkte
     * @param ignoreBuildings <code>true</code> Gebäude der Verteidiger werden ignoriert
     */
    public void setIgnoreBuildings(boolean ignoreBuildings) {
        this.ignoreBuildings = ignoreBuildings;
    }

    /**
     * die Gebäude werden bei der Berechnung der Punkte ignoriert
     * @return <code>true</code>die Gebäude werden ignoriert; <code>false</code> die Gebäude werden nicht ignoriert
     */
    public boolean isIgnoreBuildings() {
        return ignoreBuildings;
    }
}