package de.team42.vivalamerkel.controller;


import com.vaadin.ui.UI;
import de.team42.vivalamerkel.model.*;
import de.team42.vivalamerkel.model.global.Deck;
import de.team42.vivalamerkel.model.global.DiscardPile;
import de.team42.vivalamerkel.model.global.GameField;
import de.team42.vivalamerkel.util.enums.CardType;
import de.team42.vivalamerkel.util.enums.GamePhase;
import de.team42.vivalamerkel.util.listener.DeckListener;
import de.team42.vivalamerkel.util.listener.PlayerListener;
import de.team42.vivalamerkel.view.field.FieldViewListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * PhasenManagement
 */
public class GameController implements PlayerListener, DeckListener{
    private final Logger LOG = LogManager.getLogger(GameController.class.getName());

    private static GameController instance = null;
    private Collection<FieldViewListener> fieldListeners = new LinkedList<>();
    private FightController fightController;
    private GamePhase phase;
    private List<UI> uis = new ArrayList<>();

    /**
     * Konstruktor
     */
    private GameController() {
        phase = GamePhase.PHASE1;
        Deck.getInstance().addListener(this);
        LOG.info("==> Initialisiere: " + getClass().getSimpleName());
    }

    /**
     * Get aktuelle Instance
     * @return GameController
     */
    public static GameController getInstance(){
        if (instance == null) {
            synchronized (GameController.class) {
                if (instance == null) {
                    instance = new GameController();
                }
            }
        }
        return instance;
    }

    /**
     * Spiel starten
     * 1. Jeder Bürger erhält 1 Bürger Würfel
     * 2. würfeln wer Kanzler ist
     */
    public void startGame() {
        LOG.info("Spieleranzahl: " +  GameField.getInstance().getPlayers().size());
        GameField.getInstance().nextRound();
        // 1:
        for (Player player : GameField.getInstance().getPlayers()) {
            player.getPlayerField().addCitizens(1);
            LOG.info("Playername: " + player.getName() +"\n"+ "Playerid: " + player.getId());
        }
        // 2:
        GameField.getInstance().setChancellorPlayerId(new Random().nextInt(GameField.getInstance().getSizeOfPlayer())+ 1);

        this.phase1_DrawCard();
    }


    /**
     * GamePhase 1 - Karte ziehen
     * 1. Kanzler zieht erst Karte (Spieleranzahl + 2)
     * 2. Spieler ziehen Karten (1Runde = 2, Runde++ = 1)
     */
    private void phase1_DrawCard() {
        phase = GamePhase.PHASE1;
        for (Player player : GameField.getInstance().getPlayers()) {
            //1:
            if (GameField.getInstance().getChancellorPlayerId() == player.getId()) {
                for (int i = 0; i < GameField.getInstance().getSizeOfPlayer() + 2; i++) {
                    player.getHand().addCard(Deck.getInstance().getFirstCard());
                }
                // 2:
            } else {
                player.getHand().addCard(Deck.getInstance().getFirstCard());
                if (GameField.getInstance().getRound() == 1) {
                    player.getHand().addCard(Deck.getInstance().getFirstCard());
                }
            }
        }
        this.updateFieldView();
    }

    /**
     * GamePhase 2 - Versprechungen machen
     * 1. Kanzler legt pro Spieler min 1 Versprechung ab auf das Feld der jeweiligen Spieler
     * 2. Spieler dürfen sich die Versprechungen ansehen
     * 3. Karte "Einbrecher" kann hier gespielt werden
     */
    private void phase2_MakePromises() {
        phase = GamePhase.PHASE2;
        //Kanzler macht Versprechungen
        //Karte spielen
    }

    /**
     * GamePhase 3 - Bürger (Würfel) befehligen
     * Jeder Würfel repräsentiert 1 Bürger
     * 1. pro Bürger Strategie wählen (Angreifen/ Verteidigen/ Kanzler verteidigen)
     * 2. Stategie den anderen Spielern zeigen
     */
    private void phase3_CommandCitizens() {
        phase = GamePhase.PHASE3;
        //Strategie setzten
    }

    /**
     * GamePhase 4 - Kämpfe austragen
     * Reihenfolge der Kämpfe:
     * 1. Angriffe auf den Kanzler
     * 2. restliche Spieler die Angegriffen werden
     * (Greifen mehrere Spieler den selben Spieler an erfolgt dieser Angriff gemeinsam => 1 Kampf)
     * -> FightControllerPlayer
     */
    private void phase4_UnsubscribeFights() {
        phase = GamePhase.PHASE4;
/*      Bsp.: Spieler 2:
        1: Spieler 1 angreifen
        2: sich selbst verteidigen
        3: Spieler 3 angreifen
        4: Spieler 4 angreifen
        5: Spieler 5 angreifen
        6: Präsidenten verteidigen*/

        for(Player player: GameField.getInstance().getPlayers()) {
            LOG.info("Spieler: " + player.getName());
            for(Dice d: player.getPlayerField().getCitizens()){
                LOG.info("Strategie: " + d.getStrategie());
            }
        }


        Fight fight = null;
        //TODO TEST !!!!
        // Kämpfe erstellen
        for (int defenderId = 1; defenderId < GameField.getInstance().getSizeOfPlayer(); defenderId++) {
            LOG.info("Defender ID: " + defenderId);
            // Kampf für Kanzler
            if (GameField.getInstance().getChancellorPlayerId() == defenderId) {
                fight = new Fight();
                fight.setChancelorFight(true);
                //Spieler durchgehen
                for (Player player : GameField.getInstance().getPlayers()) {
                    Fighter fighter = new Fighter(player.getId());
                    boolean defender = false;
                    //Bürger durchgehen
                    for (Dice citizen : player.getPlayerField().getCitizens()) {
                        //Kanzler
                        if ((player.getId() == defenderId && citizen.getStrategie() == defenderId) || citizen.getStrategie() == 6) {
                            defender = true;
                            fighter.addDice(citizen);
                        } else if (citizen.getStrategie() == GameField.getInstance().getChancellorPlayerId()) {
                            fighter.addDice(citizen);
                        }
                    }
                    if (fighter.getDices().size() > 0) {
                        if (defender) {
                            fight.addDefender(fighter);
                        } else {
                            fight.addAttacker(fighter);
                        }
                    } else {
                        fight.addNeutral(player);
                    }
                }
                // Kampf für Spieler
            } else {
                fight = new Fight();
                //Spieler durchgehen
                for (Player player : GameField.getInstance().getPlayers()) {
                    Fighter fighter = new Fighter(player.getId());
                    //Bürger durchgehen
                    for (Dice citizen : player.getPlayerField().getCitizens()) {
                        if (citizen.getStrategie() == defenderId) fighter.addDice(citizen);
                    }
                    if (player.getId() == defenderId) {
                        fight.addDefender(new Fighter(player.getId()));
                    } else if (fighter.getDices().size() > 0) {
                        fight.addAttacker(new Fighter(player.getId()));
                    }
                }
            }
            if(fight != null) {
                GameField.getInstance().getFights().add(fight);
                fight = null;
            }
        }

        LOG.info("Anzahl Kämpfe: " + GameField.getInstance().getFights().size());

        for(Fight f: GameField.getInstance().getFights()){
            LOG.info("Angreifer: " + f.getAttacker().size());
            LOG.info(("Verteidiger: " + f.getDefender().size()));
        }



    }

    /**
     * GamePhase 5 - Geld ausgeben
     * 1. Falls jemand keine Bürger mehr hat, erhält er 1 neuer Bürger gratis
     * 2. Kanzler zuerst, Spieler in Uhrzeigersinn
     * 1 Gebäude: 4kk (+1 Verteidigungswert, +1 Siegpunkt)
     * 1 Bürger: 2kk (max. 4 Bürger besitzen)
     * 1 Karte: 1kk  falls "Entwicklungshilfe" -> Geld sofort ausgebbar (1 Karte pro runde kaufen)
     * 3. "Parteispende" / "PR-Kampagne" spielbar
     */
    private void phase5_SpendMoney() {
        phase = GamePhase.PHASE5;
        //1:
        for (Player player : GameField.getInstance().getPlayers()) {
            if (player.getPlayerField().getSizeOfCitizens() == 0) {
                player.getPlayerField().addCitizens(1);
            }
        }
        //kaufen: Gebäude/ Karte/ Bürger
        //karte spielen
    }

    /**
     * GamePhase 6 - Prüfe Handlimit
     * höchsten 4 Karten besitzen, der rest muss abgelegt werden
     * !! falls jemand 5++ Siegpunkte hat kein überprüfen des Handlimits
     */
    private void phase6_CheckHandlimit() {
        phase = GamePhase.PHASE6;
        for (Player player : GameField.getInstance().getPlayers()) {
            if (!player.getHand().checkHandLimit()) {
                //TODO Message
                //im.sendPrivateMessage("Handkarte Limit überschrieten, bitte Karte ablegen", player.getId());
            }
        }
        //karte ablegen
    }

    /**
     * GamePhase 7 - Spielende
     * 1. Ende = ein Spieler hat 5++ Siegpunkte
     * je Gebäude = 1 Siegpunkt
     * Karten "Yacht" & "Luxuskarosse" je 1 Siegpunkt
     * 2. falls gleichstand -> wer am meisten Geld auf der Hand hat
     */
    private void phase7_EndOfGame() {
        phase = GamePhase.PHASE7;
        for (Player player : GameField.getInstance().getPlayers()) {
            //Handkarten können sich positiv auf die Siegpunkte auswirken
            for (Card c : player.getHand().getCards()) {
                if (c.getType() == CardType.LUXURYCAR || c.getType() == CardType.YACHT) {
                    player.addVictoryPoints(1);
                }
            }
        }
        // 2.
        Comparator<Player> ascWealthComp = (Player p1, Player p2) -> (int) (p2.getWealth() - p1.getWealth());
        Collections.sort(GameField.getInstance().getPlayers(), ascWealthComp);
        int i = 1;
        //TODO Message im.sendPublicMessage("Game Over");
        //TODO Message im.sendPublicMessage("Siegerliste:");
        for (Player player : GameField.getInstance().getPlayers()) {
            LOG.info("Platz " + i + " : " + player.getName());
            //TODO Message im.sendPublicMessage(i + ". Platz: " + player.getName() + " Punkte: " + player.getVictoryPoints());
            i++;
        }
    }

    /**
     * startet die nächste GamePhase
     */
    private void nextPhase() {
        switch (phase) {
            case PHASE1:
                //GamePhase 2:
                LOG.info("nächste GamePhase " + phase + " -> " + GamePhase.PHASE2);
                this.phase2_MakePromises();
                this.updateFieldView();
                break;
            case PHASE2:
                //GamePhase 3:
                LOG.info("nächste GamePhase " + phase + " -> " + GamePhase.PHASE3);
                this.phase3_CommandCitizens();
                this.updateFieldView();
                break;
            case PHASE3:
                //GamePhase 4:
                LOG.info("nächste GamePhase " + phase + " -> " + GamePhase.PHASE4);
                this.phase4_UnsubscribeFights();
                this.updateFieldView();
                break;
            case PHASE4:
                //GamePhase 41:
                LOG.info("nächste GamePhase " + phase + " -> " + GamePhase.PHASE41);
                this.setFightController();
                this.fightController.phase41_PlayReinforcementCards();
                this.updateFieldView();
                break;
            case PHASE41:
                //GamePhase 42:
                LOG.info("nächste GamePhase " + phase + " -> " + GamePhase.PHASE42);
                if (GameField.getInstance().getFights().peek().getAttacker().size() == 0) {
                    this.exitFight();
                } else {
                    this.fightController.phase42_PlayAttackCards();
                    this.updateFieldView();
                }
                break;
            case PHASE42:
                //GamePhase 43:
                LOG.info("nächste GamePhase " + phase + " -> " + GamePhase.PHASE43);
                this.fightController.phase43_DiceCitizens();
                this.updateFieldView();
                break;
            case PHASE43:
                //GamePhase 44:
                LOG.info("nächste GamePhase " + phase + " -> " + GamePhase.PHASE44);
                /*if(GameField.getInstance().getFights().peek().getDefendPoints() <= GameField.getInstance().getFights().peek().getAttackPoints()){
                    this.exitFight();
                } else {*/
                    this.fightController.phase44_PreyCard();
                    this.updateFieldView();
                //}
                break;
            case PHASE44:
                GameField.getInstance().getFights().remove();
                // es sind noch Kämpfe vorhanden
                if (GameField.getInstance().getFights().size() > 0) {
                    LOG.info("nächste GamePhase " + phase + " -> " + GamePhase.PHASE4);
                    phase = GamePhase.PHASE4;
                }else{
                    LOG.info("nächste GamePhase " + phase + " -> " + GamePhase.PHASE5);
                    phase = GamePhase.PHASE5;
                    this.phase5_SpendMoney();
                }
                this.updateFieldView();
                //this.exitFight();
                break;
            case PHASE5:
                //GamePhase 6:
                LOG.info("nächste GamePhase " + phase + " -> " + GamePhase.PHASE6);
                if (!this.weHaveAWinner()) {
                    this.phase6_CheckHandlimit();
                    this.updateFieldView();
                } else {
                    this.nextPhase();
                }
                break;
            case PHASE6:
                //GamePhase 42:
                if (this.weHaveAWinner()) {
                    LOG.info("nächste GamePhase " + phase + " -> " + GamePhase.PHASE7);
                    this.phase7_EndOfGame();
                } else {
                    LOG.info("nächste GamePhase " + phase + " -> " + GamePhase.PHASE1);
                    GameField.getInstance().nextRound();
                    this.phase1_DrawCard();
                    this.updateFieldView();
                }
                break;
            default:
                LOG.error("Diese GamePhase wird nicht unterstützt: " + phase);
                break;
        }
    }

    /**
     * Beendet einen Kampf und geht in den neuen Kampf/ nächste GamePhase
     */
    private void exitFight(){
        GameField.getInstance().getFights().remove();
        // es sind noch Kämpfe vorhanden
        if (GameField.getInstance().getFights().size() > 0) {
            LOG.info("nächste GamePhase " + phase + " -> " + GamePhase.PHASE4);
            phase = GamePhase.PHASE4;
        }else{
            LOG.info("nächste GamePhase " + phase + " -> " + GamePhase.PHASE5);
            phase = GamePhase.PHASE5;
            this.phase5_SpendMoney();
        }
        this.updateFieldView();
    }

    /**
     * prüft ob ein Gewinner vorhanden ist
     * @return <code>true</code> Gewinner vorhanden; <code>false</code> kein Gewinner vorhanden
     */
    private boolean weHaveAWinner() {
        for (Player player : GameField.getInstance().getPlayers()) {
            int vPoints = player.getVictoryPoints();
            //Handkarten können sich positiv auf die Siegpunkte auswirken
            for (Card c : player.getHand().getCards()) {
                if (c.getType() == CardType.LUXURYCAR || c.getType() == CardType.YACHT) {
                    vPoints++;
                }
            }
            if (vPoints >= 5) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prüft ob alle Spieler für nächste Phase bereit sind, dann weiter
     */
    @Override
    public void playerReady() {
        boolean nextPhase = true;
        for (Player player : GameField.getInstance().getPlayers()) {
            if (!player.isReadyPhase()) {
                nextPhase = false;
            }
        }
        if (nextPhase) {
            resetPlayerReady();
            this.nextPhase();
        }
    }

    /**
     * Reset des Status "Bereit für nächste GamePhase" -> false
     */
    private void resetPlayerReady() {
        for (Player player : GameField.getInstance().getPlayers()) {
            player.setReadyPhase(false);
        }
    }

    /**
     * setzte entsprechender FightController
     */
    private void setFightController() {
        if (GameField.getInstance().getFights().peek().isChancelorFight()) {
            LOG.info("Angriff auf Kanzler");
            this.fightController = new FightControllerChancelor();
        } else {
            LOG.info("Angriff auf Spieler");
            this.fightController = new FightControllerPlayer();
        }
    }

    /**
     * Get FightController
     * @return FightController
     */
    public FightController getFightController() {
        return fightController;
    }

    /**
     * Listener hinzufügen
     * @param listener Listener
     */
    public void addListener(FieldViewListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }
        if (fieldListeners.contains(listener)) {
            LOG.warn("Listener already registered: " + listener);
            return;
        }
        this.fieldListeners.add(listener);
    }

    /**
     * informiert alle Listener
     */
    public void updateFieldView(){
        for(FieldViewListener listener:fieldListeners){
            listener.updateView();
        }
    }

    /**
     * Get Phase
     * @return aktuelle Phase
     */
    public  GamePhase getPhase() {
        return phase;
    }

    /**
     * set Phase
     * @param phase Phase
     */
    public  void setPhase(GamePhase phase) {
        this.phase = phase;
    }

    /**
     * Wenn das Deck leer ist, werden die Karten vom Ablegestapel auf das Deck gelegt und gemischt
     */
    @Override
    public void deckIsEmpty() {
        LOG.info("'Deck' ist leer");
        Deck.getInstance().addCards(DiscardPile.getInstance().removeDeck());
        Deck.getInstance().mixDeck();
    }

    public List<UI> getUis() {
        return uis;
    }

    public void setUis(List<UI> uis) {
        this.uis = uis;
    }
}