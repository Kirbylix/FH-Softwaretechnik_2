package de.team42.vivalamerkel.view.util;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.Fighter;
import de.team42.vivalamerkel.model.Player;
import de.team42.vivalamerkel.model.global.GameField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class FighterData {
    private final Logger LOG = LogManager.getLogger(FighterData.class.getName());

    public static final Object FIGHTERID = "Kämpfer ID";
    public static final Object FIGHTERNAME = "Kämpfer";
    public static final Object CITIZENSCOUNT = "Bürger";
    public static final Object POINTS = "Punkte";

    /**
     * Initialisierung
     * @return IndexedContainer
     */
    public static IndexedContainer getInitDataContainer() {
        List<String> data= new ArrayList<>();
        for(int i=1; i<=4; i++){
            data.add(String.valueOf(i));
            data.add("Kämpfer" + i);
            data.add(String.valueOf(0));
            data.add(String.valueOf(0));
        }

        IndexedContainer container = new IndexedContainer();
        fillDataContainer(container, data);
        return container;
    }

    /**
     * aktuelle Daten der handkarten des Spielers
     * @return IndexedContainer
     */
    public static IndexedContainer getAttackerDataContainer() {
        List<String> data= new ArrayList<>();
        for(Fighter fighter: GameField.getInstance().getFights().peek().getAttacker()){
            data.add(String.valueOf(fighter.getPlayerId()));
            data.add(GameField.getInstance().getPlayerById(fighter.getPlayerId()).getName());
            data.add(String.valueOf(fighter.getSizeOfDices()));
            data.add(String.valueOf(fighter.getFightPoint()));
        }

        IndexedContainer container = new IndexedContainer();
        fillDataContainer(container, data);
        return container;
    }

    /**
     * aktuelle Daten der handkarten des Spielers
     * @return IndexedContainer
     */
    public static IndexedContainer getDefenderDataContainer() {
        List<String> data= new ArrayList<>();

        for(Fighter fighter: GameField.getInstance().getFights().peek().getDefender()){
            data.add(String.valueOf(fighter.getPlayerId()));
            data.add(GameField.getInstance().getPlayerById(fighter.getPlayerId()).getName());
            data.add(String.valueOf(fighter.getSizeOfDices()));
            data.add(String.valueOf(fighter.getFightPoint()));
        }

        IndexedContainer container = new IndexedContainer();
        fillDataContainer(container, data);
        return container;
    }

    /**
     * Container füllen
     * @param container IndexedContainer
     * @param data Daten
     */
    private static void fillDataContainer(IndexedContainer container, List<String> data) {
        container.addContainerProperty(FIGHTERID, Integer.class, null);
        container.addContainerProperty(FIGHTERNAME, String.class, null);
        container.addContainerProperty(CITIZENSCOUNT, Integer.class, null);
        container.addContainerProperty(POINTS, Integer.class, null);

        for (int i=0; i<data.size(); i+=4) {
            Integer fighterId = Integer.valueOf(data.get(i));
            String fighterName = data.get(i+1);
            Integer citizens = Integer.valueOf(data.get(i+2));
            Integer points = Integer.valueOf(data.get(i+3));

            Item item = container.addItem(fighterId);
            item.getItemProperty(FIGHTERID).setValue(fighterId);
            item.getItemProperty(FIGHTERNAME).setValue(fighterName);
            item.getItemProperty(CITIZENSCOUNT).setValue(citizens);
            item.getItemProperty(POINTS).setValue(points);
        }
        container.sort(new Object[]{FIGHTERID}, new boolean[]{true});
    }
}
