package de.team42.vivalamerkel.view.field;

import com.vaadin.annotations.Push;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import de.team42.vivalamerkel.model.global.GameField;
import de.team42.vivalamerkel.util.enums.GamePhase;
import de.team42.vivalamerkel.view.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

import static de.team42.vivalamerkel.view.util.CardsData.*;
import static de.team42.vivalamerkel.view.util.StrategyOverviewData.*;
import static de.team42.vivalamerkel.view.util.FighterData.CITIZENSCOUNT;
import static de.team42.vivalamerkel.view.util.FighterData.FIGHTERNAME;
import static de.team42.vivalamerkel.view.util.FighterData.POINTS;
import static de.team42.vivalamerkel.view.util.PlayerData.PLAYERID;
import static de.team42.vivalamerkel.view.util.PlayerOverviewData.*;
import static de.team42.vivalamerkel.view.util.StrategyData.STRATEGY;

@Push
public class FieldView   extends VerticalLayout implements View {
    private final Logger LOG = LogManager.getLogger(FieldView.class);
    private List<FieldViewListener> listeners;

    private int playerId;
    private Label laTitle;
    private Label laPlayerName;
    private Label laChancelor;
    private Label laWealth;
    private Label laPhase;
    private Label laAttackerPoints;
    private Label laDefenderPoints;
    private Label laFightWinner;
    private Table taPlayerOverview;
    private Table taStrategyOverview;
    private Table taCards;
    private Table taAttacker;
    private Table taDefender;
    private Button btnBuyCard;
    private Button btnBuyBuilding;
    private Button btnBuyCitizen;
    private Button btnReady;
    private Button btnStartGame;
    private Button btnPlayCard;
    private Button btnPromise;
    private Button btnStrategy;
    private ComboBox cbPlayer;
    private ComboBox cbStrategy1;
    private ComboBox cbStrategy2;
    private ComboBox cbStrategy3;
    private ComboBox cbStrategy4;
    private GridLayout glField;
    private GridLayout glFight;
    private boolean bFight;

    /**
     * Konstruktor
     */
    FieldView(){
        this.setSpacing(true);
        this.setMargin(true);

        this.listeners = new LinkedList<>();
        this.setUpComponents();
        this.setLayout();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.playerId = (int) VaadinSession.getCurrent().getAttribute("userId");
        LOG.info("ID: " + playerId);
        this.laPlayerName.setValue("Spielername: " + GameField.getInstance().getPlayerById(playerId).getName());
    }

    /**
     * Komponenten Deklaration
     */
    private void setUpComponents(){
        this.laTitle = new Label("Viva La Merkel");
        this.laPlayerName = new Label();
        this.laChancelor = new Label("Kanzler: -");
        this.laWealth = new Label("Vermögen: 0");
        this.laPhase = new Label("Phase: START");
        this.laAttackerPoints = new Label("Angreifer Punkte: 0");
        this.laDefenderPoints = new Label("Verteidiger Punkte: 0");
        this.laFightWinner = new Label();
        this.taPlayerOverview = this.createTablePlayerOverview();
        this.taStrategyOverview = this.createTableFightOverview();
        this.taAttacker = this.createTableAttacker();
        this.taDefender = this.createTableDefender();
        this.taCards = this.createTableCards();
        this.btnBuyCard = this.createButtonBuyCard();
        this.btnBuyBuilding = this.createButtonBuyBuilding();
        this.btnBuyCitizen = this.createButtonBuyCitizen();
        this.btnReady = this.createButtonReady();
        this.btnStartGame = this.createButtonStartGame();
        this.btnPlayCard = this.createButtonPlayCard();
        this.btnPromise = this.createButtonPromise();
        this.btnStrategy = this.createButtonStratgy();
        this.cbPlayer = this.createComboBoxCardPlayer();
        this.cbStrategy1 = this.createComboBoxStrategy1();
        this.cbStrategy2 = this.createComboBoxStrategy2();
        this.cbStrategy3 = this.createComboBoxStrategy3();
        this.cbStrategy4 = this.createComboBoxStrategy4();
        this.glFight = new GridLayout(2 , 3);
        this.glField = new GridLayout(3 , 3);
        this.bFight = false;
    }

    /**
     * Set Layout
     */
    private void setLayout(){
        this.addComponent(laTitle);

        GridLayout playerInfo = new GridLayout(2 , 4);
        playerInfo.setSpacing(true);
        playerInfo.setSizeFull();
        playerInfo.addComponent(laPlayerName, 0, 0);
        playerInfo.addComponent(laWealth, 0, 1);
        playerInfo.addComponent(laChancelor, 0, 2);
        playerInfo.addComponent(laPhase, 0, 3);
        playerInfo.addComponent(this.taPlayerOverview, 1, 0, 1, 3);
        this.addComponent(playerInfo);
        this.setComponentAlignment(playerInfo, Alignment.MIDDLE_CENTER);

        this.addComponent(taCards);

        HorizontalLayout hlControle = new HorizontalLayout();
        hlControle.setSpacing(true);
        hlControle.addComponent(cbPlayer);
        hlControle.addComponent(btnPromise);
        hlControle.addComponent(btnPlayCard);
        hlControle.addComponent(btnBuyCard);
        hlControle.addComponent(btnBuyBuilding);
        hlControle.addComponent(btnBuyCitizen);
        hlControle.addComponent(btnReady);
        hlControle.addComponent(btnStartGame);
        this.addComponent(hlControle);
        this.setComponentAlignment(hlControle, Alignment.BOTTOM_CENTER);

        glField.setSpacing(true);
        glField.setSizeFull();
        glField.addComponent(taStrategyOverview, 0, 0, 0, 2);
        glField.addComponent(cbStrategy1, 1, 0);
        glField.addComponent(cbStrategy2, 2, 0);
        glField.addComponent(cbStrategy3, 1, 1);
        glField.addComponent(cbStrategy4, 2, 1);
        glField.addComponent(btnStrategy, 2, 2);
        this.addComponent(glField);

        glFight.setSpacing(true);
        glFight.setSizeFull();
        glFight.addComponent(taDefender , 0, 0);
        glFight.addComponent(taAttacker , 1, 0);
        glFight.addComponent(laDefenderPoints , 0, 1);
        glFight.addComponent(laAttackerPoints , 1, 1);
        glFight.addComponent(laFightWinner , 0, 2);
    }

    /**
     * erzeuge Tabelle Spielerübersicht
     * @return Tabelle
     */
    private Table createTablePlayerOverview(){
        final Table table = new Table("Spielerübersicht");
        table.setWidth("700px");

        table.setContainerDataSource(PlayerOverviewData.getInitDataContainer());
        table.setVisibleColumns(new Object[]{SPIELERNAME, BUILDING, KARTEN, CITIZENS, VERSPRECHEN});
        table.setColumnHeaders("Spielername", "Gebäude", "Karten", "Bürger", "Versprechen");
        table.setColumnReorderingAllowed(true);
        table.setColumnCollapsingAllowed(true);
        table.setPageLength(table.size());

        return table;
    }

    /**
     * erzeuge Tabelle Kampfübersicht
     * @return table
     */
    private Table createTableAttacker(){
        final Table table = new Table("Angreifer");
        table.setWidth("400px");

        table.setContainerDataSource(FighterData.getInitDataContainer());
        table.setVisibleColumns(new Object[]{FIGHTERNAME, CITIZENSCOUNT, POINTS});
        table.setColumnReorderingAllowed(true);
        table.setColumnCollapsingAllowed(true);
        table.setColumnHeaders("Kämpfer", "Bürger", "Punkte");
        table.setPageLength(table.size());

        return table;
    }

    /**
     * erzeuge Tabelle Kampfübersicht
     * @return table
     */
    private Table createTableDefender(){
        final Table table = new Table("Verteidiger");
        table.setWidth("400px");

        table.setContainerDataSource(FighterData.getInitDataContainer());
        table.setVisibleColumns(new Object[]{FIGHTERNAME, CITIZENSCOUNT, POINTS});
        table.setColumnHeaders("Kämpfer", "Bürger", "Punkte");
        table.setColumnReorderingAllowed(true);
        table.setColumnCollapsingAllowed(true);
        table.setPageLength(table.size());

        return table;
    }

    /**
     * erzeuge Tabelle Kampfübersicht
     * @return table
     */
    private Table createTableFightOverview(){
        final Table table = new Table("Kampfanzeige");
        table.setWidth("600px");

        table.setContainerDataSource(StrategyOverviewData.getInitDataContainer());
        table.setVisibleColumns(new Object[]{FIGHTERNAME, STRAGEGIE1, STRAGEGIE2, STRAGEGIE3, STRAGEGIE4});
        table.setColumnHeaders("Kämpfer", "Strategie 1", "Strategie 2", "Strategie 3", "Strategie 4");
        table.setColumnReorderingAllowed(true);
        table.setColumnCollapsingAllowed(true);
        table.setPageLength(table.size());

        return table;
    }

    /**
     * erzeuge Tabelle Karten
     * @return table
     */
    private Table createTableCards(){
        final Table table = new Table("Karten");
        table.setSelectable(true);
        table.setSizeFull();

        table.setContainerDataSource(new CardsData().getInitDataContainer());
        table.setVisibleColumns(new Object[]{CARDNAME, BESCHREIBUNG, PHASE});
        table.setColumnHeaders("Name", "Beschreibung", "Phase");
        table.setColumnReorderingAllowed(true);
        table.setColumnCollapsingAllowed(true);
        table.setPageLength(table.size());

        return table;
    }

    /**
     * erzeuge Button Karte kaufen
     * @return Button
     */
    private Button createButtonBuyCard() {
        Button button = new Button("Karte kaufen");
        button.addClickListener(
                event -> fireBuyCardClickedEvent());
        button.setIcon(FontAwesome.CREDIT_CARD);
        button.setEnabled(false);
        return button;
    }

    /**
     * Event Karte kaufen
     */
    private void fireBuyCardClickedEvent() {
        for (final FieldViewListener listener: this.listeners) {
            listener.buyCardClicked();
        }
    }

    /**
     * erzeuge Button Gebäude kaufen
     * @return Button
     */
    private Button createButtonBuyBuilding() {
        Button button = new Button("Gebäude kaufen");
        button.addClickListener(
                event -> fireBuyBuildingClickedEvent());
        button.setIcon(FontAwesome.HOME);
        button.setEnabled(false);
        return button;
    }

    /**
     * Event Gebäude kaufen
     */
    private void fireBuyBuildingClickedEvent() {
        for (final FieldViewListener listener: this.listeners) {
            listener.buyBuildingClicked();
        }
    }

    /**
     * erzeuge Button Bürger kaufen
     * @return Button
     */
    private Button createButtonBuyCitizen() {
        Button button = new Button("Bürger Kaufen");
        button.addClickListener(
                event -> fireBuyCitizenClickedEvent());
        button.setIcon(FontAwesome.MALE);
        button.setEnabled(false);
        return button;
    }

    /**
     * Event Bürger kaufen
     */
    private void fireBuyCitizenClickedEvent() {
        for (final FieldViewListener listener: this.listeners) {
            listener.buyCitizenClicked();
        }
    }

    /**
     * erzeuge Button nächste Phase
     * @return Button
     */
    private Button createButtonReady() {
        Button button = new Button("nächste Phase");
        button.addClickListener(
                event -> fireReadyClickedEvent());
        button.setIcon(FontAwesome.FORWARD);
        button.setEnabled(false);
        return button;
    }

    /**
     * Event nächste Phase
     */
    private void fireReadyClickedEvent() {
        for (final FieldViewListener listener: this.listeners) {
            listener.readyClicked();
        }
    }

    /**
     * erzeuge Button Spiel starten
     * @return Button
     */
    private Button createButtonStartGame() {
        Button button = new Button("Spiel starten");
        button.addClickListener(
                event -> fireStartGameClickedEvent());
        button.setIcon(FontAwesome.GAMEPAD);
        return button;
    }

    /**
     * Event Spiel starten
     */
    private void fireStartGameClickedEvent() {
        for (FieldViewListener listener: this.listeners) {
            listener.startGameClicked();
        }
    }

    /**
     * erzeuge Button Karte spielen
     * @return Button
     */
    private Button createButtonPlayCard() {
        Button button = new Button("Karte spielen");
        button.addClickListener(
                event -> firePlayCardClickedEvent());
        button.setEnabled(false);
        return button;
    }

    /**
     * Event Karte spielen
     */
    private void firePlayCardClickedEvent() {
        for (final FieldViewListener listener: this.listeners) {
            listener.playCardClicked();
        }
    }

    /**
     * erzeuge Button Versprechen
     * @return Button
     */
    private Button createButtonPromise() {
        Button button = new Button("Versprechen");
        button.addClickListener(
                event -> firePromiseClickedEvent());
        button.setEnabled(false);
        return button;
    }

    /**
     * Event Versprechen
     */
    private void firePromiseClickedEvent() {
        for (final FieldViewListener listener: this.listeners) {
            listener.promiseClicked();
        }
    }

    /**
     * erzeuge Button Strategie
     * @return Button
     */
    private Button createButtonStratgy() {
        Button button = new Button("Strategie abgeben");
        button.addClickListener(
                event -> fireSetStrategyClickedEvent());
        button.setEnabled(false);
        return button;
    }

    /**
     * Event Strategie
     */
    private void fireSetStrategyClickedEvent() {
        for (final FieldViewListener listener: this.listeners) {
            listener.setStrategyClicked();
        }
    }

    /**
     * erzeuge ComboBox Spieler
     * @return ComboBox
     */
    private ComboBox createComboBoxCardPlayer() {
        ComboBox cb = new ComboBox("Spieler", PlayerData.getInitDataContainer());
        cb.setItemCaptionPropertyId(PlayerData.PLAYERNAME);
        return cb;
    }

    /**
     * erzeuge ComboBox Strategie 1
     * @return ComboBox
     */
    private ComboBox createComboBoxStrategy1() {
        ComboBox cb = new ComboBox("Bürger 1", StrategyData.getInitDataContainer());
        cb.setItemCaptionPropertyId(STRATEGY);
        return cb;
    }

    /**
     * erzeuge ComboBox Strategie 2
     * @return ComboBox
     */
    private ComboBox createComboBoxStrategy2() {
        ComboBox cb = new ComboBox("Bürger 2", StrategyData.getInitDataContainer());
        cb.setItemCaptionPropertyId(STRATEGY);
        return cb;
    }

    /**
     * erzeuge ComboBox Strategie 3
     * @return ComboBox
     */
    private ComboBox createComboBoxStrategy3() {
        ComboBox cb = new ComboBox("Bürger 3", StrategyData.getInitDataContainer());
        cb.setItemCaptionPropertyId(STRATEGY);
        return cb;
    }

    /**
     * erzeuge ComboBox Strategie 4
     * @return ComboBox
     */
    private ComboBox createComboBoxStrategy4() {
        ComboBox cb = new ComboBox("Bürger 4", StrategyData.getInitDataContainer());
        cb.setItemCaptionPropertyId(STRATEGY);
        return cb;
    }

    /**
     * Listener hinzufügen
     * @param listener FieldViewListener
     */
    void addFieldViewListener(final FieldViewListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }
        if (listeners.contains(listener)) {
            LOG.warn("Listener already registered: " + listener);
            return;
        }
        this.listeners.add(listener);
    }

    /**
     * setzte Kanzler
     * @param value Kanzlername
     */
    void updateChanclor(String value){
        this.laChancelor.setValue("Kanzler: " + value);
    }

    /**
     * setzte Vermögen
     * @param value Vermögen
     */
    void updateWealth(String value){
        this.laWealth.setValue("Vermögen: " + value);
    }

    /**
     * setzte Phase
     * @param value Phase
     */
    void updatePhase(String value){
        this.laPhase.setValue("Phase: " + value);
    }

    /**
     * update Angreiferpunkte
     * @param value wert
     */
    void updateAttackerPoints(String value){
        this.laAttackerPoints.setValue("Angreiferpunkte " + value);
    }

    /**
     * update Verteidigerpunkte
     * @param value wert
     */
    void updateDefenderPoints(String value){
        this.laDefenderPoints.setValue("Verteidigerpunkte: " + value);
    }

    void setFightWinner(String value){
        this.laFightWinner.setValue("Sieger: " + value);
    }

    void resetFightOverview(){
        this.laFightWinner.setValue("");
        updateDefenderPoints("");
        updateAttackerPoints("");
    }

    /**
     * Get ausgewählte Karte
     * @return Karten ID
     */
    int getSelectedCard(){
        Object rowId = this.taCards.getValue();
        return (int) this.taCards.getContainerProperty(rowId, CARDID).getValue();
    }

    /**
     * Get ausgewählter Spieler
     * @return Spieler ID
     */
    int getSelectedPlayer(){
        Object rowId = this.cbPlayer.getValue();
        return (int) this.cbPlayer.getContainerProperty(rowId, PLAYERID).getValue();
    }

    /**
     * get ausgewälte Strategie
     * @return Strategie
     */
    int getSelectedStrategy1(){
        Object rowId = this.cbStrategy1.getValue();
        return (int) this.cbStrategy1.getContainerProperty(rowId, STRATEGY).getValue();
    }

    /**
     * get ausgewälte Strategie
     * @return Strategie
     */
    int getSelectedStrategy2(){
        Object rowId = this.cbStrategy2.getValue();
        return (int) this.cbStrategy2.getContainerProperty(rowId, STRATEGY).getValue();
    }

    /**
     * get ausgewälte Strategie
     * @return Strategie
     */
    int getSelectedStrategy3(){
        Object rowId = this.cbStrategy3.getValue();
        return (int) this.cbStrategy3.getContainerProperty(rowId, STRATEGY).getValue();
    }

    /**
     * get ausgewälte Strategie
     * @return Strategie
     */
    int getSelectedStrategy4(){
        Object rowId = this.cbStrategy4.getValue();
        return (int) this.cbStrategy4.getContainerProperty(rowId, STRATEGY).getValue();
    }

    /**
     * get Player Id
     * @return Player Id
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * update Player Data
     */
    void updateDataContainer() {
        // Spielerauswahl
        this.cbPlayer.setContainerDataSource(PlayerData.getUpdateDataContainer());
        //Spielerübersicht
        this.taPlayerOverview.setContainerDataSource(PlayerOverviewData.getUpdateDataContainer());
        //Karten
        this.taCards.setContainerDataSource(new CardsData().getUpdateDataContainer(this.playerId));
        // Kmapfübersicht
        if(bFight){
            //Angreifer
            this.taAttacker.setContainerDataSource(FighterData.getAttackerDataContainer());
            //Verteidiger
            this.taDefender.setContainerDataSource(FighterData.getDefenderDataContainer());
        }else{
            // Strategieübersicht
            this.taStrategyOverview.setContainerDataSource(StrategyOverviewData.getUpdateDataContainer());
        }
    }

    public void setbFight(boolean bFight) {
        this.bFight = bFight;
    }

    public void setStrategieView(){
        this.replaceComponent(this.glFight, this.glField);
    }

    public void setFightView(){
        this.replaceComponent(this.glField, this.glFight);
    }

    public boolean isbFight() {
        return bFight;
    }

    public void setButtonControll(GamePhase phase){
        switch (phase) {
            case PHASE1:
                btnStartGame.setEnabled(false);
                btnReady.setEnabled(true);
                break;
            case PHASE2:
                if(playerId == GameField.getInstance().getChancellorPlayerId()){
                    btnPromise.setEnabled(true);
                }
                btnPlayCard.setEnabled(true);
                break;
            case PHASE3:
                btnPromise.setEnabled(false);
                btnStrategy.setEnabled(true);
                break;
            case PHASE4:
                btnStrategy.setEnabled(false);
                break;
            case PHASE41:

                break;
            case PHASE42:

                break;
            case PHASE43:

                break;
            case PHASE44:

                break;
            case PHASE5:
                btnBuyBuilding.setEnabled(true);
                btnBuyCard.setEnabled(true);
                btnBuyCitizen.setEnabled(true);
                break;
            case PHASE6:
                btnBuyBuilding.setEnabled(false);
                btnBuyCard.setEnabled(false);
                btnBuyCitizen.setEnabled(false);
                break;
            case PHASE7:
                btnReady.setEnabled(false);
                btnPlayCard.setEnabled(false);
                break;
            default:
                LOG.error("Diese GamePhase wird nicht unterstützt: " + phase);
                break;
        }
    }

    /**
     * Zeige Error Nachricht
     * @param message Nachricht
     */
    void showErrorMessage(String message) {
        Notification notification = new Notification(message, Notification.Type.ERROR_MESSAGE);
        notification.show(Page.getCurrent());
    }

    /**
     * Zeige Nachricht
     * @param message Nachricht
     */
    void showMessage(String message) {
        Notification notification = new Notification(message, Notification.Type.HUMANIZED_MESSAGE);
        notification.show(Page.getCurrent());
    }
}