package de.team42.vivalamerkel.model;

import de.team42.vivalamerkel.util.enums.BuildingType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Spielertaplu
 */
public class PlayerField implements Serializable {
    private final Logger LOG = LogManager.getLogger(PlayerField.class.getName());

    private List<Building> buildings;
    private List<Card> promises;
    private List<Dice> citizens;

    /**
     * Konstruktor
     */
    public PlayerField(){
        this.buildings = new ArrayList<>();
        this.citizens = new ArrayList<>();
        this.promises = new ArrayList<>();
        Building building = new Building();
        building.setType(BuildingType.PARTEISITZ);
        this.addBuilding(building);
        LOG.info("==> Initialisiere: " + getClass().getSimpleName());
    }

    /**
     * Gebäude hinzufügen
     * @param building Gebäude
     * @return <code>true</code> Gebäude erfolgreich hinzugefügt; <code>false</code> der GebäudeTyp ist schon vorhanden
     */
    public boolean addBuilding(Building building){
        try {
            if(!this.buildings.equals(building)){
                this.buildings.add(building);
                LOG.info(building.getType().getName() + " wurde hinzugefügt");
                return true;
            }
        }catch (NullPointerException ex){
            LOG.error("Gebäude-Liste = NULL \n", ex);
        }
        return false;
    }

    /**
     * prüft ob das Gebäude schon vorhanden ist
     * @param buildingType Gebäudetyp
     * @return <code>true</code> Gebäude Typ ist schon vorhanden; <code>false</code> der GebäudeTyp ist noch nicht vorhanden
     */
    public boolean buildingExists(BuildingType buildingType){
        for(Building building: this.buildings){
            if(building.getType() == buildingType){
                return true;
            }
        }
        return false;
    }

    /**
     * Gebäude entfernen
     * @param building Gebäude
     */
    public void removeBuilding(Building building){
        try {
            this.buildings.remove(building);
        }catch (NullPointerException ex){
            LOG.error("Gebäude-Liste = NULL \n", ex);
        }
    }

    /**
     * Versprechungs Karte hinzufügen
     * @param card Versprechen
     */
    public void addPromise(Card card){
        try {
            this.promises.add(card);
        }catch (NullPointerException ex){
            LOG.error("Versprechungskarten = NULL \n", ex);
        }
    }

    /**
     * getPromises
     * @return Versprechungen
     */
    public List<Card> getPromises(){
        return this.promises;
    }

    /**
     * Versprechens Karte entfernen
     */
    public void removePromises(){
        this.promises = new ArrayList<>();
    }

    /**
     * Bürger hinzufügen
     * @param count Anzahl
     */
    public void addCitizens(int count){
        for(int i = 0; i< count; i++){
            this.citizens.add(new Dice());
        }
    }

    /**
     * Anzahl der Gebäude
     * @return Anzahl
     */
    public int getSizeOfBuilding(){
        return this.buildings.size();
    }

    /**
     * Anzahl der Bürger
     * @return Anzahl
     */
    public int getSizeOfCitizens(){
        return this.citizens.size();
    }

    /**
     * get Bürger
     * @return List<Dice>
     */
    public List<Dice> getCitizens(){
        return this.citizens;
    }

    /**
     * 1 Bürger entfernen
     * @return <code>true</code> ausgewählter Bürger wurde entfernt; <code>false</code> Bürger nicht vorhanden
     */
    public boolean removeOneCitizien(){
        return this.citizens.remove(this.citizens.get(0));
    }

    /**
     * Get alle Gebäude
     * @return alle Gebäude
     */
    public List<Building> getBuildings() {
        return buildings;
    }
}
