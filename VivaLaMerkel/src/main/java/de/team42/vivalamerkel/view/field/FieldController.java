package de.team42.vivalamerkel.view.field;

import com.vaadin.annotations.Push;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import de.team42.vivalamerkel.controller.CardController;
import de.team42.vivalamerkel.controller.GameController;
import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.Player;
import de.team42.vivalamerkel.model.global.GameField;
import de.team42.vivalamerkel.util.enums.CardType;
import de.team42.vivalamerkel.util.enums.GamePhase;
import de.team42.vivalamerkel.view.login.LoginController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Push
public class FieldController {
    private final Logger LOG = LogManager.getLogger(LoginController.class);
    private final ViewListener viewListener;
    private FieldView fieldView;


    /**
     * Konstruktor
     */
    public FieldController() {
        this.viewListener = new ViewListener();
        this.fieldView = new FieldView();
        GameController.getInstance().addListener(this.viewListener);
        this.fieldView.addFieldViewListener(this.viewListener);
    }

    /**
     * Get view
     * @return view
     */
    public View getView() {
        return this.fieldView;
    }

    /**
     * Update view
     */
    private void cUpdateView() {
        fieldView.setButtonControll(GameController.getInstance().getPhase());
        if(GameController.getInstance().getPhase() == GamePhase.PHASE41){
            fieldView.resetFightOverview();
            fieldView.setFightView();
            fieldView.setbFight(true);
        }else if(GameController.getInstance().getPhase() == GamePhase.PHASE4 || GameController.getInstance().getPhase() == GamePhase.PHASE5){
            fieldView.setStrategieView();
            fieldView.setbFight(false);
        }

        if(fieldView.isbFight()){
            fieldView.updateAttackerPoints(String.valueOf(GameField.getInstance().getFights().peek().getAttackPoints()));
            fieldView.updateDefenderPoints(String.valueOf(GameField.getInstance().getFights().peek().getDefendPoints()));
            if(GameController.getInstance().getPhase() == GamePhase.PHASE44) {
                if (GameField.getInstance().getFights().peek().getAttackPoints() <= GameField.getInstance().getFights().peek().getDefendPoints()) {
                    fieldView.setFightWinner("die Verteidiger");
                } else {
                    fieldView.setFightWinner("die Angreifer");
                }
            }
        }

        this.fieldView.updateDataContainer();
        int chancelorId = GameField.getInstance().getChancellorPlayerId();
        Player player = GameField.getInstance().getPlayerById(fieldView.getPlayerId());
        this.fieldView.updateChanclor(GameField.getInstance().getPlayerById(chancelorId).getName());
        this.fieldView.updateWealth(String.valueOf(player.getWealth()));
        this.fieldView.updatePhase(GameController.getInstance().getPhase().getDescription());
        LOG.info("Update view Spieler: " + fieldView.getPlayerId());
    }

    /**
     * Karte Kaufen
     */
    private void cBuyCardClicked(){
        GameField.getInstance().getPlayerById(fieldView.getPlayerId()).buyCard();
        this.fieldView.showMessage("Karte wurde gekauft");
        cUpdateView();
    }

    /**
     * Gebäude kaufen
     */
    private void cBuyBuildingClicked(){
        GameField.getInstance().getPlayerById(fieldView.getPlayerId()).buyBuilding();
        this.fieldView.showMessage("Gebäude wurde gekauft");
        cUpdateView();
    }

    /**
     * Bürger kaufen
     */
    private void cBuyCitizenClicked(){
        GameField.getInstance().getPlayerById(fieldView.getPlayerId()).buyCitizien();
        this.fieldView.showMessage("Bürger wurde gekauft");
        cUpdateView();
    }

    /**
     * Spieler ist bereit für nächste Phase
     */
    private void cReadyClicked(){
        GameField.getInstance().getPlayerById(fieldView.getPlayerId()).setReadyForNextPhase();
        this.fieldView.showMessage("Nächste Phase, warten auf Mitspieler");
    }

    /**
     * Spiel starte
     */
    private void cStartGameClicked(){
        //TODO max 3 Player
        if(GameField.getInstance().getRound() == 0) {
            GameController.getInstance().startGame();
            this.fieldView.showMessage("Spiel wurde gestartet");
        }else{
            this.fieldView.showErrorMessage("Das Spiel kann noch nicht gestartet werden oder lauft schon !!");
        }
    }

    /**
     * Karte Spielen
     */
    private void cPlayCardClicked(){
        try {
            int cardId = this.fieldView.getSelectedCard();
            LOG.info("Karte Spielen:");
            LOG.info("Spieler ID: " + fieldView.getPlayerId());
            CardController cardController = new CardController(fieldView.getPlayerId(), cardId);
                if (cardController.getCard().getType() == CardType.ASSASIN) {
                    int enemy = this.fieldView.getSelectedPlayer();
                    cardController.getCard().setSettings(enemy);
                }
                if (cardController.playAble()) {
                    cardController.execute();
                    this.cUpdateView();
                } else {
                    this.fieldView.showMessage("Karte in dieser Phase nicht spielbar !");
                }
        }catch(NullPointerException ex){
            this.fieldView.showErrorMessage("KARTE auswählen !!!");
        }
    }

    /**
     * Versprechungen machen
     */
    private void cPromiseClicked(){
        try{
            if(fieldView.getPlayerId() == GameField.getInstance().getChancellorPlayerId()) {
                int enemy = this.fieldView.getSelectedPlayer();
                if (enemy != fieldView.getPlayerId()) {
                    int cardId = this.fieldView.getSelectedCard();

                    Card card = GameField.getInstance().getPlayerById(fieldView.getPlayerId()).getHand().getCard(cardId);
                    GameField.getInstance().getPlayerById(fieldView.getPlayerId()).getHand().removeCard(cardId);
                    GameField.getInstance().getPlayerById(enemy).getPlayerField().addPromise(card);

                    this.fieldView.showMessage("Die Karte wurde abgegeben");
                    cUpdateView();
                } else {
                    this.fieldView.showMessage("GEGNER auswählen !!");
                }
            }else{
                this.fieldView.showErrorMessage("Sie sind kein Kanzler !!");
            }
        }catch(NullPointerException ex){
            this.fieldView.showErrorMessage("GEGENSPIELER auswählen !!");
        }
    }

    /**
     * Strategie setzten
     */
    private void cSetStrategyClicked() {
        try{
            int strategy = 9999;
            LOG.info("SETZTE Strategie für Spieler: " + fieldView.getPlayerId());
            for(int i=0; i<GameField.getInstance().getPlayerById(fieldView.getPlayerId()).getPlayerField().getCitizens().size(); i++){
                switch(i){
                    case 0:
                        strategy = this.fieldView.getSelectedStrategy1();
                        break;
                    case 1:
                        strategy = this.fieldView.getSelectedStrategy2();
                        break;
                    case 2:
                        strategy = this.fieldView.getSelectedStrategy3();
                        break;
                    case 3:
                        strategy = this.fieldView.getSelectedStrategy4();
                        break;
                    default:
                        LOG.error(i + " gibt es nicht !!!!");
                        break;
                }
                LOG.info("STRATEGIE: " + strategy);
                LOG.info("anzahl Bürger: " + GameField.getInstance().getPlayerById(fieldView.getPlayerId()).getPlayerField().getCitizens().size());
                LOG.info("iiii " + i);
                GameField.getInstance().getPlayerById(fieldView.getPlayerId()).getPlayerField().getCitizens().get(i).setStrategie(strategy);
            }
            this.fieldView.showMessage("Strategie wurde gesetzt");
        }catch (NullPointerException ex){
            this.fieldView.showErrorMessage("Bürger Strategie auswählen !!");
        }
    }

    /**
     * Listener Klasse
     */
    private class ViewListener implements FieldViewListener {

        @Override
        public void buyCardClicked() {
            cBuyCardClicked();
        }

        @Override
        public void buyBuildingClicked() {
            cBuyBuildingClicked();
        }

        @Override
        public void buyCitizenClicked() {
            cBuyCitizenClicked();
        }

        @Override
        public void readyClicked() {
            cReadyClicked();
        }

        @Override
        public void startGameClicked() {
            cStartGameClicked();
        }

        @Override
        public void playCardClicked() {
            cPlayCardClicked();
        }

        @Override
        public void promiseClicked() {
            cPromiseClicked();
        }

        @Override
        public void updateView() {
            cUpdateView();
        }

        @Override
        public void setStrategyClicked() {
            cSetStrategyClicked();
        }
    }
}