package de.team42.vivalamerkel.model;


import de.team42.vivalamerkel.controller.GameController;
import de.team42.vivalamerkel.model.global.Deck;
import de.team42.vivalamerkel.util.enums.BuildingType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.Serializable;

/**
 * Spieler
 */
public class Player extends ReadyPlayer implements Serializable{
    private Logger LOG = LogManager.getLogger(Player.class.getName());

    private static int nextId = 1;
    private final int id;
    private final String name;
    private int victoryPoints;
    private int wealth;
    private Hand hand;
    private PlayerField playerField;

    /**
     * Konstruktor
     */
    public Player(String name){
        this.id = nextId;
        nextId++;
        this.name = name;
        this.victoryPoints = 0;
        this.wealth = 0;
        this.addListener(GameController.getInstance());
        this.hand = new Hand();
        this.playerField = new PlayerField();

        LOG.info("==> Initialisiere: " + getClass().getSimpleName());
    }

    /**
     * setzte Siegerpunkte
     * @param victoryPoints Siegerpunkte
     */
    public void addVictoryPoints(int victoryPoints){
        this.victoryPoints += victoryPoints;
        LOG.info(victoryPoints + " Siegpunkte wurden dem Spieler: " + this.name + " hinzugefügt: " + this.victoryPoints);
    }

    /**
     * Vermögen hinzufügen
     * @param value Wert
     */
    public void addWealth(int value){
        this.wealth += value;
        LOG.info(value + " € wurden dem Spieler: " + this.name + " berechnet: " + this.wealth);
    }

    /**
     * Gebäude kaufen
     */
    public boolean buyBuilding(){
        Building building = new Building();
        if(this.wealth >= building.getCost()){
            BuildingType[] buildings = BuildingType.values();
            for(BuildingType buildingType: buildings){
                if(!this.playerField.buildingExists(buildingType)){
                    this.addWealth(-building.getCost());
                    building.setType(buildingType);
                    this.playerField.addBuilding(building);
                    LOG.info("Gebäude gekauft: " + buildingType.getName());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Karte Kaufen
     */
    public boolean buyCard(){
        int cost = 1000000;
        if(this.wealth >= cost){
            this.hand.addCard(Deck.getInstance().getFirstCard());
            this.addWealth(-cost);
            LOG.info("Karte gekauft");
            return true;
        }
        return false;
    }

    /**
     * Bürger kaufen
     */
    public boolean buyCitizien(){
        int cost = 2000000;
        if(this.wealth >= cost){
            this.playerField.addCitizens(1);
            this.addWealth(-cost);
            LOG.info("Bürger gekauft");
            return true;
        }
        return false;
    }

    /**
     * get Hand
     * @return Hand
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * get Name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * get SpielerTablu
     * @return PlayerField
     */
    public PlayerField getPlayerField() {
        return playerField;
    }

    /**
     * get Siegpunkte
     * @return Siegerpunkte
     */
    public int getVictoryPoints() {
        return this.victoryPoints;
    }

    /**
     * get Vermögen
     * @return Vermögen
     */
    public int getWealth() {
        return this.wealth;
    }

    /**
     * get Id
     * @return Id
     */
    public int getId() {
        return this.id;
    }
}