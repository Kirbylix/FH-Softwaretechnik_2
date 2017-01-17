package de.team42.vivalamerkel.view.util;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;

import java.util.ArrayList;
import java.util.List;

public class StrategyData {

    public static final Object STRATEGY = "Strategie";

    /**
     * Initialisierung
     * @return IndexedContainer
     */
    public static IndexedContainer getInitDataContainer() {
        List<String> data= new ArrayList<>();
        data.add(String.valueOf(1));
        data.add(String.valueOf(2));
        data.add(String.valueOf(3));
        data.add(String.valueOf(4));
        data.add(String.valueOf(5));
        data.add(String.valueOf(6));

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
        container.addContainerProperty(STRATEGY, Integer.class, null);

        for (int i=0; i<data.size(); i++) {
            Integer strategy = Integer.valueOf(data.get(i));

            Item item = container.addItem(strategy);
            item.getItemProperty(STRATEGY).setValue(strategy);
        }
        container.sort(new Object[]{STRATEGY}, new boolean[]{true});
    }
}
