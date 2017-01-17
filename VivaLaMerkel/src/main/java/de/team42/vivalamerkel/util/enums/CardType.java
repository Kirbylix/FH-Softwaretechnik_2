package de.team42.vivalamerkel.util.enums;

public enum CardType {
    ASSASIN,
    GUNBOAT,
    FARMERREBELLION,
    BRIBERY,
    SURPRISEATTACK,
    ANNEXATION,
    DIVERSIONARYTACTIC,
    BURGLAR,
    SPY,
    STUDENTSDISTRIBUTELEAFLETS,
    YACHT,
    LUXURYCAR,
    PRCAMPAIGN,
    PARTYDONATION,
    DEVELOPMENTASSISTANCE1,
    DEVELOPMENTASSISTANCE2,
    DEVELOPMENTASSISTANCE3;

    public static CardType getCardType(String type) {
        switch(type){
            case "ASSASIN":
                return ASSASIN;
            case "GUNBOAT":
                return GUNBOAT;
            case "FARMERREBELLION":
                return FARMERREBELLION;
            case "BRIBERY":
                return BRIBERY;
            case "SURPRISEATTACK":
                return SURPRISEATTACK;
            case "ANNEXATION":
                return ANNEXATION;
            case "DIVERSIONARYTACTIC":
                return DIVERSIONARYTACTIC;
            case "BURGLAR":
                return BURGLAR;
            case "SPY":
                return SPY;
            case "STUDENTSDISTRIBUTELEAFLETS":
                return STUDENTSDISTRIBUTELEAFLETS;
            case "YACHT":
                return YACHT;
            case "LUXURYCAR":
                return LUXURYCAR;
            case "PRCAMPAIGN":
                return PRCAMPAIGN;
            case "PARTYDONATION":
                return PARTYDONATION;
            case "DEVELOPMENTASSISTANCE1":
                return DEVELOPMENTASSISTANCE1;
            case "DEVELOPMENTASSISTANCE2":
                return DEVELOPMENTASSISTANCE2;
            case "DEVELOPMENTASSISTANCE3":
                return DEVELOPMENTASSISTANCE3;
            default:
                return null;
        }
    }
}
