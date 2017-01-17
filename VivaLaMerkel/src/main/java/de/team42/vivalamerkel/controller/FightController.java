package de.team42.vivalamerkel.controller;

import de.team42.vivalamerkel.model.Fighter;
import de.team42.vivalamerkel.model.global.GameField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

public abstract class FightController implements Serializable {
    private final Logger LOG = LogManager.getLogger(FightController.class.getName());

    /**
     * Konstruktor
     */
    public FightController() {
    }

    /**
     * GamePhase 1 - Verstärkungskarten spielen
     */
    public abstract void phase41_PlayReinforcementCards();

    /**
     * GamePhase 2 - Angriffskarten spielen
     */
    public abstract void phase42_PlayAttackCards();

    /**
     * GamePhase 3 - Bürger würfeln
     */
    public abstract void phase43_DiceCitizens();

    /**
     * GamePhase 4 - Beute Karte nehmen
     */
    public abstract void phase44_PreyCard();

    /**
     * Verteidigungspunkte berechnen
     */
    public abstract void calculateDefendTeamPoints();

    /**
     * Team Punkte berechnen
     */
    public void calculateAttackTeamPoints() {
        for (Fighter att : GameField.getInstance().getFights().peek().getAttacker()) {
            GameField.getInstance().getFights().peek().addAttackPoints(att.getFightPoint());
        }
        LOG.info("Angriffs Team Punkte wurden berechnet");
    }
}