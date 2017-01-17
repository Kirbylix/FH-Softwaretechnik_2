package de.team42.vivalamerkel.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.Serializable;
import java.util.Random;

/**
 * Würfel/ Bürger
 */
public class Dice  implements Serializable {
    private final Logger LOG = LogManager.getLogger(Dice.class.getName());

    private static int nextId = 1;
    private final int id;
    private int count;
    /**
     * strategy: z.b: für Spieler 2
     * 1: Spieler 1 angreifen
     * 2: sich selbst verteidigen
     * 3: Spieler 3 angreifen
     * 4: Spieler 4 angreifen
     * 5: Spieler 5 angreifen
     * 6: Kanzler verteidigen
     */
    private int strategy;
    private final int cost = 2000000;

    /**
     * Konstruktor
     */
    public Dice(){
        this.id = nextId;
        nextId++;
        this.count = 0;
        this.strategy = 0;
    }

    /**
     * würfeln
     */
    public void roll(){
        this.count = new Random().nextInt(6) + 1;
    }

    /**
     * get Strategie
     * @return Strategie
     */
    public int getStrategie() {
        return strategy;
    }

    /**
     * set Strategie
     * @param strategie Strategie
     */
    public void setStrategie(int strategie) {
        this.strategy = strategie;
    }

    /**
     * get Wert
     * @return Wert
     */
    public int getCount(){
        return this.count;
    }

    /**
     * get ID
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * set Wert
     * @param count Wert
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * get Kosten
     * @return Kosten
     */
    public int getCost() {
        return this.cost;
    }
}