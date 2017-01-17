package de.team42.vivalamerkel.util.enums;

public enum BuildingType {

    STUTTGART21("Stuttgart 21"),
    GORLEBEN("Gorleben"),
    BER("Berliner Flughafen"),
    ELBPHILHARMONIE("Elphilarmonie"),
    EZB("Europäsche Zentral Bank"),
    PARTEISITZ("Parteisitz");

    private String type;

    BuildingType(String type){
        this.type = type;
    }

    public String getName(){
        return this.type;
    }
}
