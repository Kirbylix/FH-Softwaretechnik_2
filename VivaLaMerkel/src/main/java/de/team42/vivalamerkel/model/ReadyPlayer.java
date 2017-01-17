package de.team42.vivalamerkel.model;

import de.team42.vivalamerkel.util.listener.PlayerListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

public abstract class ReadyPlayer  implements Serializable {
    private final Logger LOG = LogManager.getLogger(ReadyPlayer.class.getName());
    private boolean readyPhase;
    private Collection<PlayerListener> listeners = new LinkedList<>();

    /**
     * Konstruktor
     */
    public ReadyPlayer(){
        this.readyPhase = false;
        System.out.println("==> Initialisiere: " + getClass().getSimpleName() +"\n" );
    }

    /**
     * der Spieler ist bereit für die nächste GamePhase
     */
    public void setReadyForNextPhase() {
        this.readyPhase = true;
        this.notifyListener();
    }

    /**
     * get bereit für nächste GamePhase
     * @return <code>true</code> bereit für nächste GamePhase; <code>false</code> nicht bereit
     */
    public boolean isReadyPhase() {
        return readyPhase;
    }

    /**
     * der Spieler ist nicht bereit für nächste GamePhase
     */
    public void setReadyPhase(boolean value) {
        this.readyPhase = value;
    }

    /**
     * fügt einen Listener hinzu
     * @param listener Listener
     */
    public void addListener(PlayerListener listener){
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
        for(PlayerListener listener:listeners){
            listener.playerReady();
        }
    }
}
