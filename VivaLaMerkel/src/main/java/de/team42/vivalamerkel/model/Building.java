package de.team42.vivalamerkel.model;

import de.team42.vivalamerkel.util.enums.BuildingType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

/**
 * Gebäude
 */
public class Building implements Serializable {
    private final Logger LOG = LogManager.getLogger(Building.class.getName());

    private BuildingType type;
    private final int cost = 4000000;

    /**
     * Konstruktor
     */
    public Building() {
    }

    public Building(BuildingType type) {
        this.type = type;
    }

    /**
     * get Gebäudetype
     *
     * @return Gebäudetyp
     */
    public BuildingType getType() {
        return type;
    }

    /**
     * Set Gebäudetyp
     *
     * @param type Gebäudetyp
     */
    public void setType(BuildingType type) {
        this.type = type;
    }

    /**
     * get Kosten
     *
     * @return Kosten
     */
    public int getCost() {
        return this.cost;
    }
}