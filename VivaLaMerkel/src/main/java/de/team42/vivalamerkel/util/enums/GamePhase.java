package de.team42.vivalamerkel.util.enums;

public enum GamePhase{
    PHASE0("-"),
    PHASE1("1 - Karte ziehen"),
    PHASE2("2 - Versprechungen machen"),
    PHASE3("3 - Bürger befehligen"),
    PHASE4("4 - Kämpfe austragen"),
    PHASE41("41 - Karten des Verteidigers"),
    PHASE42("42 - Karten des Angreifers"),
    PHASE43("43 - Bürger würfeln"),
    PHASE44("44 - Beute machen"),
    PHASE5("5 - Geld ausgeben"),
    PHASE6("6 - Handlimit prüfen"),
    PHASE7("7 - Game Over");

    private String description;


    GamePhase(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static GamePhase getPhase(String type) {
        switch(type){
            case "PHASE0":
                return PHASE0;
            case "PHASE1":
                return PHASE1;
            case "PHASE2":
                return PHASE2;
            case "PHASE3":
                return PHASE3;
            case "PHASE4":
                return PHASE4;
            case "PHASE41":
                return PHASE41;
            case "PHASE42":
                return PHASE42;
            case "PHASE43":
                return PHASE43;
            case "PHASE44":
                return PHASE44;
            case "PHASE5":
                return PHASE5;
            case "PHASE6":
                return PHASE6;
            default:
                return null;
        }
    }
}