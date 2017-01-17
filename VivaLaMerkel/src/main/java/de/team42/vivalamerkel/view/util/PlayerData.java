package de.team42.vivalamerkel.view.util;


import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import de.team42.vivalamerkel.model.Player;
import de.team42.vivalamerkel.model.global.GameField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    private final Logger LOG = LogManager.getLogger(StrategyOverviewData.class);

    public static final Object PLAYERNAME = "Spielername";
    public static final Object PLAYERID = "Spieler ID";

    /**
     * Initialisierung
     * @return IndexedContainer
     */
    public static IndexedContainer getInitDataContainer() {
        List<String> data= new ArrayList<>();
        data.add("-");
        data.add("0");

        IndexedContainer container = new IndexedContainer();
        fillDataContainer(container, data);
        return container;
    }

    /**
     * aktuelle Daten der Spieler (Name, ID)
     * @return IndexedContainer
     */
    public static IndexedContainer getUpdateDataContainer() {
        List<String> data= new ArrayList<>();
        for (Player player : GameField.getInstance().getPlayers()) {
            data.add(player.getName());
            data.add(String.valueOf(player.getId()));
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
        container.addContainerProperty(PLAYERNAME, String.class, null);
        container.addContainerProperty(PLAYERID, Integer.class, null);
        for (int i = 0; i < data.size(); i+=2) {
            String spielername = data.get(i);
            Integer id = Integer.parseInt(data.get(i+1));

            Item item = container.addItem(id);
            item.getItemProperty(PLAYERNAME).setValue(spielername);
            item.getItemProperty(PLAYERID).setValue(id);
        }
        container.sort(new Object[]{PLAYERNAME}, new boolean[]{true});
    }
}