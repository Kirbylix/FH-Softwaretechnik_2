package de.team42.vivalamerkel.controller;

import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.Fighter;
import de.team42.vivalamerkel.model.Player;
import de.team42.vivalamerkel.model.global.DiscardPile;
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
public class FightControllerChancelor extends FightController  implements Serializable {
    private final Logger LOG = LogManager.getLogger(FightControllerChancelor.class.getName());

    /**
     * Konstruktor
     */
    public FightControllerChancelor(){
        super();
    }

    /**
     * GamePhase 1 - Verstärkungskarten spielen
     * 1. Der verteidiger darf beliebig Viele Karten von seiner Hand ausspielen
     * sofern diese "vor einem Kampf" ausspielbar sind
     * Angreifer verliert alle Bürger -> vom Kampf ausgeschlossen
     * kein Angreifer mehr vorhanden -> Kampf beendet
     * Angriff auf Kanzler:
     * alle Spieler die den Kanzler verteidigen dürfen auch Karten spielen
     */
    public  void phase41_PlayReinforcementCards(){
        GameController.getInstance().setPhase(GamePhase.PHASE41);
    }

    /**
     * GamePhase 2 - Angriffskarten spielen
     * 1. Alle angreifenden Spieler dürfen beliebig viele Karten von der Hand ausspielen
     * sofern diese "vor einem Kampf" ausspielbar sind
     * Angriff auf Kanzler:
     * Ein Spieler verliert alle seine Bürger => von weiteren Handlungen im Kampf ausgeschlossen
     * (Seine Karten bleiben weiterhin wirksam)
     */
    public void phase42_PlayAttackCards(){
        GameController.getInstance().setPhase(GamePhase.PHASE42);

        // keine Bürger -> vom Kampf ausgeschlossen
        for(Fighter def: GameField.getInstance().getFights().peek().getDefender()){
            if(!(GameField.getInstance().getChancellorPlayerId() == def.getPlayerId())){
                if(def.getSizeOfDices() == 0){
                    GameField.getInstance().getFights().peek().getDefender().remove(def);
                }
            }
        }
    }

    /**
     * GamePhase 3 - Bürger würfeln
     * 1. Alle Kampf beteiligten Spieler würfeln ihre angreifenden/ verteidigenden Bürger
     * 2. nach dem Würfeln kann die Karte "Ablenkungsmanöver" ausgespielt werden
     * 3. Wert aller Angreifer zusammenzählen => Angriffswert
     * 4. Wert + Anzahl Gebäude => Verteidigungswert
     * 5. Angriffswert <= Verteidungswert -> fail, Angriffswert > Verteidungswert -> Beute machen
     * Angriff auf Kanzler:
     * Kanzler darf nicht würfeln => seine Bürger zählen jeweils als 1
     */
    public  void phase43_DiceCitizens(){
        GameController.getInstance().setPhase(GamePhase.PHASE43);
        //Würfeln
        for(Fighter def: GameField.getInstance().getFights().peek().getDefender()){
            def.rollDices();
            def.calculateFightPoints();
        }
        for(Fighter att: GameField.getInstance().getFights().peek().getAttacker()){
            att.rollDices();
            att.calculateFightPoints();
        }

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
     * Angriff auf den Kanzler:
     * Angriff erfolgreich:
     * 1. ALLE Spieler legen die Versprechungen (dieser Runde) des Kanzlers auf den Ablegestapel
     * 2. der Spieler mit dem höchsten Kampfwert ist der neue Kanzler, ab der nächsten Hauptphase
     * Angriff nicht erfolgreich:
     * 1. alle Spieler die den Kanzler nicht angegriffen haben (auch Verteidiger des Kanzlers)
     * dürfen die Versprechungen auf die Hand nehmen
     * 2. Angreifer müssen ihre Versprechungen auf den Ablegestapel legen
     */
    public void phase44_PreyCard(){
        GameController.getInstance().setPhase(GamePhase.PHASE44);
        Comparator<Fighter> ascFightPointsComp = (Fighter f1, Fighter f2) -> (int) (f2.getFightPoint() - f1.getFightPoint());
        Collections.sort(GameField.getInstance().getFights().peek().getAttacker(), ascFightPointsComp);
        int defenderId = GameField.getInstance().getFights().peek().getDefender().get(0).getPlayerId();
        List<Card> cards = GameField.getInstance().getPlayerById(defenderId).getHand().getCards();

        //TODO
        for(Fighter att: GameField.getInstance().getFights().peek().getAttacker()){
            if(cards.size() > 0){
                GameField.getInstance().getPlayerById(att.getPlayerId()).getHand().addCard(cards.get(cards.size()-1));
                GameField.getInstance().getPlayerById(defenderId).getHand().removeCard(cards.get(cards.size()-1).getId());
                cards.remove(cards.get(cards.size()-1));
            }else{

            }
        }

        if(GameField.getInstance().getFights().peek().getAttackPoints() <= GameField.getInstance().getFights().peek().getDefendPoints()){
            this.fightDefeat();
        }else{
            this.fightVictory();
        }
    }

    /**
     * die Angreifer haben gewonne
     * 1. ALLE Spieler legen die Versprechungen (dieser Runde) des Kanzlers auf den Ablegestapel
     * 2. der Spieler mit dem höchsten Kampfwert ist der neue Kanzler, ab der nächsten Hauptphase
     */
    private void fightVictory(){
        for(Fighter att: GameField.getInstance().getFights().peek().getAttacker()){
            DiscardPile.getInstance().addCards(GameField.getInstance().getPlayerById(att.getPlayerId()).getPlayerField().getPromises());
            GameField.getInstance().getPlayerById(att.getPlayerId()).getPlayerField().removePromises();
        }
        for(Fighter def: GameField.getInstance().getFights().peek().getDefender()){
            DiscardPile.getInstance().addCards(GameField.getInstance().getPlayerById(def.getPlayerId()).getPlayerField().getPromises());
            GameField.getInstance().getPlayerById(def.getPlayerId()).getPlayerField().removePromises();
        }
        for(Player neutral: GameField.getInstance().getFights().peek().getNeutral()){
            DiscardPile.getInstance().addCards(GameField.getInstance().getPlayerById(neutral.getId()).getPlayerField().getPromises());
            GameField.getInstance().getPlayerById(neutral.getId()).getPlayerField().removePromises();
        }
        GameField.getInstance().setChancellorPlayerId(GameField.getInstance().getFights().peek().getAttacker().get(0).getPlayerId());
    }

    /**
     * die Angreifer haben verloren
     * 1. alle Spieler die den Kanzler nicht angegriffen haben (auch Verteidiger des Kanzlers)
     * dürfen die Versprechungen auf die Hand nehmen
     * 2. Angreifer müssen ihre Versprechungen auf den Ablegestapel legen
     */
    private void fightDefeat(){
        // Die neutralen Spieler dürfen die Versprechungen auf die Hand nehmen
        for(Player player: GameField.getInstance().getFights().peek().getNeutral()) {
            List<Card> promises = player.getPlayerField().getPromises();
            for(Card c: promises) {
                player.getHand().addCard(c);
            }
            player.getPlayerField().removePromises();
        }
        // Die verteidigen Spieler dürfen die Versprechungen auf die Hand nehmen
        for(Fighter def: GameField.getInstance().getFights().peek().getDefender()){
            List<Card> promises = GameField.getInstance().getPlayerById(def.getPlayerId()).getPlayerField().getPromises();
            for(Card c: promises) {
                GameField.getInstance().getPlayerById(def.getPlayerId()).getHand().addCard(c);
            }
            GameField.getInstance().getPlayerById(def.getPlayerId()).getPlayerField().removePromises();
        }
    }

    /**
     * Verteidigungspunkte berechnen
     */
    public void calculateDefendTeamPoints(){
        for(Fighter def: GameField.getInstance().getFights().peek().getDefender()){
            if(GameField.getInstance().getChancellorPlayerId() == def.getPlayerId()){
                GameField.getInstance().getFights().peek().addDefendPoints(def.getSizeOfDices());
            }else{
                GameField.getInstance().getFights().peek().addDefendPoints(def.getFightPoint());
            }
            if(!GameField.getInstance().getFights().peek().isIgnoreBuildings()) {
                GameField.getInstance().getFights().peek().addDefendPoints(GameField.getInstance().getPlayerById(def.getPlayerId()).getPlayerField().getSizeOfBuilding());
            }
        }
        LOG.info("Verteidigungs Team Punkte wurden berechnet");
    }
}