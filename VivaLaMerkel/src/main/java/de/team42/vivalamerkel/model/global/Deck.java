package de.team42.vivalamerkel.model.global;

import de.team42.vivalamerkel.model.Card;
import de.team42.vivalamerkel.model.cards.*;
import de.team42.vivalamerkel.util.listener.DeckListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.*;

/**
 * Nachziehstapel
 */
public class Deck implements Serializable {
    private final Logger LOG = LogManager.getLogger(Deck.class.getName());
    private Collection<DeckListener> listeners = new LinkedList<>();
    private Stack<Card> cards;
    private static Deck instance = null;

    /**
     * Konstruktor
     */
    private Deck() {
        this.cards = this.createCards();
        this.mixDeck();
        LOG.info("==> Initialisiere: " + getClass().getSimpleName());
    }

    /**
     * Get aktuelle Instance
     * @return Instance
     */
    public static Deck getInstance(){
        if (instance == null) {
            synchronized (Deck.class) {
                if (instance == null) {
                    instance = new Deck();
                }
            }
        }
        return instance;
    }

    /**
     * Karten Stack erzeugen
     * @return Karten
     */
    private Stack<Card> createCards() {
        Stack<Card> cards = new Stack<>();

        // Assasin
        int countAssassin = 2;
        for (int i = 0; i < countAssassin; i++) {
            cards.push(new AssasinCard());
        }
        // GunBoat
        int countGunboat = 2;
        for (int i = 0; i < countGunboat; i++) {
            cards.push(new GunboatCard());
        }
        //FarmerRebellion
        int countFarmerRebellion = 2;
        for (int i = 0; i < countFarmerRebellion; i++) {
            cards.push(new FarmerRebellionCard());
        }
        // Bribery
        int countBribery = 1;
        for (int i = 0; i < countBribery; i++) {
            cards.push(new BriberyCard());
        }
        // SurpriseAttach
        int countSurpriseAttack = 2;
        for (int i = 0; i < countSurpriseAttack; i++) {
            cards.push(new SurpriseAttackCard());
        }
        // DiversionaryTactic
        int countDiversionaryTactic = 2;
        for (int i = 0; i < countDiversionaryTactic; i++) {
            cards.push(new DiversionaryTacticCard());
        }
        // Burglar
        int countBurglar = 1;
        for (int i = 0; i < countBurglar; i++) {
            cards.push(new BurglarCard());
        }
        // Spy
        int countSpy = 1;
        for (int i = 0; i < countSpy; i++) {
            cards.push(new SpyCard());
        }
        // StudentsDistributeLeaflets
        int countStudentsDistributeLeaflets = 1;
        for (int i = 0; i < countStudentsDistributeLeaflets; i++) {
            cards.push(new StudentsDistributeLeafletsCard());
        }
        // Yacht
        int countYacht = 1;
        for (int i = 0; i < countYacht; i++) {
            cards.push(new YachtCard());
        }
        // LuxuryCar
        int countLuxuryCar = 1;
        for (int i = 0; i < countLuxuryCar; i++) {
            cards.push(new LuxuryCarCard());
        }
        // PrCampaign
        int countPrCampaign = 1;
        for (int i = 0; i < countPrCampaign; i++) {
            cards.push(new PrCampaignCard());
        }
        // PartyDonation
        int countPartyDonation = 1;
        for (int i = 0; i < countPartyDonation; i++) {
            cards.push(new PartyDonationCard());
        }
        // DevelopmentAssistance1
        int countDevelopmentAssistance1 = 12;
        for (int i = 0; i < countDevelopmentAssistance1; i++) {
            cards.push(new DevelopmentAssistance1Card());
        }
        // DevelopmentAssistance1
        int countDevelopmentAssistance2 = 11;
        for (int i = 0; i < countDevelopmentAssistance2; i++) {
            cards.push(new DevelopmentAssistance2Card());
        }
        // DevelopmentAssistance3
        int countDevelopmentAssistance3 = 3;
        for (int i = 0; i < countDevelopmentAssistance3; i++) {
            cards.push(new DevelopmentAssistance3Card());
        }

        LOG.info("Karten wurden erstellt");
        return cards;
    }

    /**
     * oberste Karte ziehen
     * @return Karte
     */
    public Card getFirstCard() {
        Card card = null;
        try {
            card = cards.pop();
            if (this.cards.empty()) {
                this.notifyListener();
            }
        } catch (EmptyStackException ex) {
            LOG.error("der Nachziehstapel ist Leer !!! \n", ex);
        }
        return card;
    }

    /**
     * Karten Stack ersetzten
     * @param cards Karten
     */
    public void addCards(Stack<Card> cards) {
        this.cards = cards;
        LOG.info(cards.size() + " Karten wurden dem 'Nachziehstapel' hinzugefügt");
    }

    /**
     * Karte mischen
     */
    public void mixDeck() {
        try {
            Collections.shuffle(cards);
            LOG.info("Karten wurden gemischt");
        } catch (NullPointerException ex) {
            LOG.error("der Nachziehstapel ist NULL !!! \n", ex);
        }
    }

    /**
     * Listener hinzufügen
     * @param listener DeckListener
     */
    public void addListener(DeckListener listener) {
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
     * informiert alle Listener
     */
    private void notifyListener(){
        for(DeckListener listener:listeners){
            listener.deckIsEmpty();
        }
    }
}