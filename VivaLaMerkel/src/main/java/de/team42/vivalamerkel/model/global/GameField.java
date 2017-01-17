package de.team42.vivalamerkel.model.global;

import de.team42.vivalamerkel.model.Fight;
import de.team42.vivalamerkel.model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Spielfeld
 */
public class GameField{
    private final Logger LOG = LogManager.getLogger(GameField.class.getName());

    private final int MAXPLAYERLIMIT = 5;
    private int round;
    private int chancellorPlayerId = 999;
    private static GameField instance = null;
    private Queue<Fight> fights;
    private List<Player> players;

    /**
     * Konstruktor
     */
    private GameField() {
        this.fights = new LinkedList<>();
        this.players = new ArrayList<>();
        this.round = 0;
        LOG.info("==> Initialisiere: " + getClass().getSimpleName());
    }

    /**
     * Get aktuelle Instance
     * @return Instance
     */
    public static GameField getInstance(){
        if (instance == null) {
            synchronized (GameField.class) {
                if (instance == null) {
                    instance = new GameField();
                }
            }
        }
        return instance;
    }

    /**
     * gib mir den Spieler mit der ID
     * @param id Spieler-Id
     * @return Spieler
     */
    public Player getPlayerById(int id){
        for(Player player : GameField.getInstance().getPlayers()){
            if(player.getId() == id){
                return player;
            }
        }
        LOG.error("Ein Spieler mit ID: " + id + " wurde nicht gefunden");
        return null;
    }

    /**
     * Spieler hinzufügen
     * @param player Spieler
     * @return <code>true</code> ok; <code>false</code> Spieler Limit erreicht
     */
    public boolean addPlayer(Player player) {
        assert player != null;
        if (players.size() <= MAXPLAYERLIMIT && this.round == 0) {
            players.add(player);
            LOG.info("Player: " + player.getName() + " ID: " + player.getId() + " wurde dem Spielfeld hinzugefügt");
            return true;
        }
        LOG.error("Player: " + player.getName() + " konnte nicht hinzugefügt werden");
        return false;
    }

    /**
     * erhöht den Counter der Runde
     */
    public void nextRound() {
        this.round++;
    }

    /**
     * Get Runde
     * @return Runde
     */
    public int getRound() {
        return round;
    }

    /**
     * Get alle Spieler
     * @return Spieler
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * get Spieleranzahl
     * @return Spieleranzahl
     */
    public int getSizeOfPlayer(){
        assert this.players != null;
        return this.players.size();
    }

    /**
     * Get alle Kämpfe
     * @return alle Kämpfe
     */
    public Queue<Fight> getFights() {
        return fights;
    }

    /**
     * Get Kanzler Id
     * @return Kanzler ID
     */
    public int getChancellorPlayerId() {
        return chancellorPlayerId;
    }

    /**
     * Set Kanzler ID
     * @param chancellorPlayerId Kanzler ID
     */
    public void setChancellorPlayerId(int chancellorPlayerId) {
        this.chancellorPlayerId = chancellorPlayerId;
    }
}