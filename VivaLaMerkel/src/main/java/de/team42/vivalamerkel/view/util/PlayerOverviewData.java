package de.team42.vivalamerkel.view.util;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import de.team42.vivalamerkel.model.Player;
import de.team42.vivalamerkel.model.global.GameField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class PlayerOverviewData {
    private static final Logger LOG = LogManager.getLogger(PlayerOverviewData.class);

    public static final Object SPIELERNAME = "Spielername";
    public static final Object BUILDING = "Gebäude";
    public static final Object KARTEN = "Karten";
    public static final Object CITIZENS = "Bürger";
    public static final Object VERSPRECHEN = "Versprechen";

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
     * aktuelle Daten der Spielerübersicht
     * @return IndexedContainer
     */
    public static IndexedContainer getUpdateDataContainer() {
        List<String> data= new ArrayList<>();
        for (Player player : GameField.getInstance().getPlayers()) {
            data.add(player.getName());
            data.add(String.valueOf(player.getPlayerField().getSizeOfBuilding()));
            data.add(String.valueOf(player.getHand().getSize()));
            data.add(String.valueOf(player.getPlayerField().getSizeOfCitizens()));
            data.add(String.valueOf(player.getPlayerField().getPromises().size()));
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
        container.addContainerProperty(SPIELERNAME, String.class, null);
        container.addContainerProperty(BUILDING, Integer.class, null);
        container.addContainerProperty(KARTEN, Integer.class, null);
        container.addContainerProperty(CITIZENS, Integer.class, null);
        container.addContainerProperty(VERSPRECHEN, Integer.class, null);

        for (int i = 0; i < data.size(); i+=5) {
            String playername = data.get(i);
            Integer building = Integer.parseInt(data.get(i+1));
            Integer cards = Integer.parseInt(data.get(i+2));
            Integer citizens = Integer.parseInt(data.get(i + 3));
            Integer promise = Integer.parseInt(data.get(i + 4));

            Item item = container.addItem(playername);
            item.getItemProperty(SPIELERNAME).setValue(playername);
            item.getItemProperty(BUILDING).setValue(building);
            item.getItemProperty(KARTEN).setValue(cards);
            item.getItemProperty(CITIZENS).setValue(citizens);
            item.getItemProperty(VERSPRECHEN).setValue(promise);

        }
        container.sort(new Object[]{SPIELERNAME}, new boolean[]{true});
    }
}