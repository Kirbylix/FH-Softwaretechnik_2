package de.team42.vivalamerkel.view.util;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.Player;
import de.team42.vivalamerkel.model.global.GameField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CardsData{
    private final Logger LOG = LogManager.getLogger(CardsData.class.getName());

    public static final Object CARDNAME = "Name";
    public static final Object CARDID = "Karten ID";
    public static final Object BESCHREIBUNG = "Beschreibung";
    public static final Object PHASE = "Phase";

    public CardsData(){}

    /**
     * Initialisierung
     * @return IndexedContainer
     */
    public static IndexedContainer getInitDataContainer() {
        List<String> data= new ArrayList<>();
        for(int i=1; i<=4; i++){
            data.add("Karte " + i);
            data.add(String.valueOf(i));
            data.add("-");
            data.add("-");
        }

        IndexedContainer container = new IndexedContainer();
        fillDataContainer(container, data);
        return container;
    }

    public static IndexedContainer getUpdateDataContainer(int playerId) {
        List<String> data= new ArrayList<>();

            for (Card card : GameField.getInstance().getPlayerById(playerId).getHand().getCards()) {
                data.add(card.getTitle());
                data.add(String.valueOf(card.getId()));
                data.add(card.getFunction());
                data.add(card.getPlayAt().getDescription());
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
        container.addContainerProperty(CARDNAME, String.class, null);
        container.addContainerProperty(CARDID, Integer.class, null);
        container.addContainerProperty(BESCHREIBUNG, String.class, null);
        container.addContainerProperty(PHASE, String.class, null);

        for (int i=0; i<data.size(); i+=4) {
            String cardName = data.get(i);
            Integer cardId = Integer.valueOf(data.get(i+1));
            String beschreibung = data.get(i+2);
            String phase = data.get(i+3);

            Item item = container.addItem(cardId);
            item.getItemProperty(CARDNAME).setValue(cardName);
            item.getItemProperty(CARDID).setValue(cardId);
            item.getItemProperty(BESCHREIBUNG).setValue(beschreibung);
            item.getItemProperty(PHASE).setValue(phase);
        }
        container.sort(new Object[]{CARDID}, new boolean[]{true});
    }
}