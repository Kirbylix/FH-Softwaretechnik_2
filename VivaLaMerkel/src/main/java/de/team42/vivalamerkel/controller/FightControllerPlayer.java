package de.team42.vivalamerkel.controller;

import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.Fighter;
import de.team42.vivalamerkel.model.global.GameField;
import de.team42.vivalamerkel.util.enums.GamePhase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Kampf Management
 */
public class FightControllerPlayer extends FightController  implements Serializable {
    private final Logger LOG = LogManager.getLogger(FightControllerPlayer.class.getName());

    public FightControllerPlayer(){
        super();
    }

    /**
     * GamePhase 1 - Verstärkungskarten spielen
     * 1. Der verteidiger darf beliebig Viele Karten von seiner Hand ausspielen
     * sofern diese "vor einem Kampf" ausspielbar sind
     * Angreifer verliert alle Bürger -> vom Kampf ausgeschlossen
     * kein Angreifer mehr vorhanden -> Kampf beendet
     */
    public void phase41_PlayReinforcementCards(){
        GameController.getInstance().setPhase(GamePhase.PHASE41);
        // Karte Spielen
    }

    /**
     * GamePhase 2 - Angriffskarten spielen
     * 1. Alle angreifenden Spieler dürfen beliebig viele Karten von der Hand ausspielen
     * sofern diese "vor einem Kampf" ausspielbar sind
     */
    public  void phase42_PlayAttackCards(){
        GameController.getInstance().setPhase(GamePhase.PHASE42);
        // Karte spielen
    }

    /**
     * GamePhase 3 - Bürger würfeln
     * 1. Alle Kampf beteiligten Spieler würfeln ihre angreifenden/ verteidigenden Bürger
     * 2. nach dem Würfeln kann die Karte "Ablenkungsmanöver" ausgespielt werden
     * 3. Wert aller Angreifer zusammenzählen => Angriffswert
     * 4. Wert + Anzahl Gebäude => Verteidigungswert
     * 5. Angriffswert <= Verteidungswert -> fail, Angriffswert > Verteidungswert -> Beute machen
     */
    public void phase43_DiceCitizens(){
        GameController.getInstance().setPhase(GamePhase.PHASE43);
        //Würfeln
        GameField.getInstance().getFights().peek().getDefender().get(0).rollDices();
        GameField.getInstance().getFights().peek().getDefender().get(0).calculateFightPoints();

        for(Fighter att: GameField.getInstance().getFights().peek().getAttacker()){
            att.rollDices();
            att.calculateFightPoints();
        }
        // Karte spielen

        // 3:
        this.calculateAttackTeamPoints();
        // 4:
        this.calculateDefendTeamPoints();
    }

    /**
     * GamePhase 4 - Beute Karte nehmen
     * 1. jeder Angreifer darf 1 Karte vom Verteidiger ziehen
     * in der Reihenfolge der persöhnlichen Kampfwerte
     * bei gleichstand würfeln mit 1 Würfel
     * 2. hat der Verteidiger keine Karten mehr -> pech
     */
    public void phase44_PreyCard(){
        GameController.getInstance().setPhase(GamePhase.PHASE44);
        Comparator<Fighter> ascFightPointsComp = (Fighter f1, Fighter f2) -> (int) (f2.getFightPoint() - f1.getFightPoint());
        Collections.sort(GameField.getInstance().getFights().peek().getAttacker(), ascFightPointsComp);
        int defenderId = GameField.getInstance().getFights().peek().getDefender().get(0).getPlayerId();
        List<Card> cards = GameField.getInstance().getPlayerById(defenderId).getHand().getCards();
        for(Fighter att: GameField.getInstance().getFights().peek().getAttacker()){
            if(cards.size() > 0){
                GameField.getInstance().getPlayerById(att.getPlayerId()).getHand().addCard(cards.get(cards.size()-1));
                GameField.getInstance().getPlayerById(defenderId).getHand().removeCard(cards.get(cards.size()-1).getId());
                cards.remove(cards.get(cards.size()-1));
            }
        }
    }

    /**
     * Verteidigungspunkte berechnen
     */
    public void calculateDefendTeamPoints(){
        GameField.getInstance().getFights().peek().addDefendPoints(GameField.getInstance().getFights().peek().getDefender().get(0).getFightPoint());
        if(!GameField.getInstance().getFights().peek().isIgnoreBuildings()) {
            GameField.getInstance().getFights().peek().addDefendPoints(GameField.getInstance().getPlayerById(GameField.getInstance().getFights().peek().getDefender().get(0).getPlayerId()).getPlayerField().getSizeOfBuilding());
        }
    }
}