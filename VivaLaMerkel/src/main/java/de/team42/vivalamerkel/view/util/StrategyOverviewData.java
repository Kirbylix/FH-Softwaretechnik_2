package de.team42.vivalamerkel.view.util;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import de.team42.vivalamerkel.model.Dice;
import de.team42.vivalamerkel.model.Player;
import de.team42.vivalamerkel.model.global.GameField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class StrategyOverviewData {
    private static final Logger LOG = LogManager.getLogger(StrategyOverviewData.class);

    public static final Object FIGHTERNAME = "Kämpfer";
    public static final Object STRAGEGIE1 = "Strategie 1";
    public static final Object STRAGEGIE2 = "Strategie 2";
    public static final Object STRAGEGIE3 = "Strategie 3";
    public static final Object STRAGEGIE4 = "Strategie 4";

    /**
     * Initialisierung
     * @return IndexedContainer
     */
    public static IndexedContainer getInitDataContainer() {
        List<String> data= new ArrayList<>();
        for(int i=1; i<6; i++){
            data.add("Spieler " + i);
            data.add("0");
            data.add("0");
            data.add("0");
            data.add("0");
        }

        IndexedContainer container = new IndexedContainer();
        fillDataContainer(container, data);
        return container;
    }

    /**
     * aktuelle Daten der Kampfübersicht
     * @return IndexedContainer
     */
    public static IndexedContainer getUpdateDataContainer() {
        List<String> data= new ArrayList<>();
        for (Player player : GameField.getInstance().getPlayers()) {
            data.add(player.getName());
            for(Dice dice : player.getPlayerField().getCitizens()){
                data.add(String.valueOf(dice.getStrategie()));
            }
            for(int i=0; i<4-player.getPlayerField().getCitizens().size(); i++){
                data.add("0");
            }
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
        container.addContainerProperty(FIGHTERNAME, String.class, null);
        container.addContainerProperty(STRAGEGIE1, Integer.class, null);
        container.addContainerProperty(STRAGEGIE2, Integer.class, null);
        container.addContainerProperty(STRAGEGIE3, Integer.class, null);
        container.addContainerProperty(STRAGEGIE4, Integer.class, null);

        for (int i = 0; i < data.size(); i+=5) {
            String spielername = data.get(i);
            Integer strategie1 = Integer.parseInt(data.get(i+1));
            Integer strategie2 = Integer.parseInt(data.get(i+2));
            Integer strategie3 = Integer.parseInt(data.get(i + 3));
            Integer strategie4 = Integer.parseInt(data.get(i + 4));

            Item item = container.addItem(spielername);
            item.getItemProperty(FIGHTERNAME).setValue(spielername);
            item.getItemProperty(STRAGEGIE1).setValue(strategie1);
            item.getItemProperty(STRAGEGIE2).setValue(strategie2);
            item.getItemProperty(STRAGEGIE3).setValue(strategie3);
            item.getItemProperty(STRAGEGIE4).setValue(strategie4);
        }
        container.sort(new Object[]{FIGHTERNAME}, new boolean[]{true});
    }
}