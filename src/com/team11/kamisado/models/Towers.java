package com.team11.kamisado.models;

import java.io.Serializable;

public enum Towers implements Serializable{
    
    BLACKORANGE ("orange", "black", "BO"),
    BLACKNAVY ("navy", "black", "BN"),
    BLACKBLUE ("blue", "black", "BB"),
    BLACKPINK ("pink", "black", "BP"),
    BLACKYELLOW ("yellow", "black", "BY"),
    BLACKRED ("red", "black", "BR"),
    BLACKGREEN ("green", "black", "BG"),
    BLACKBROWN ("brown", "black", "BBr"),
    WHITEORANGE ("orange", "white", "WO"),
    WHITENAVY ("navy", "white", "WN"),
    WHITEBLUE ("blue", "white", "WB"),
    WHITEPINK ("pink", "white", "WP"),
    WHITEYELLOW ("yellow", "white", "WY"),
    WHITERED ("red", "white", "WR"),
    WHITEGREEN ("green", "white", "WG"),
    WHITEBROWN ("brown", "white", "WBr"),
    EMPTY ("", "", "");
    
    private final String color;
    private final String type;
    private final String abbreviation;
    
    Towers(String color, String type, String abbreviation) {
        this.color = color;
        this.type = type;
        this.abbreviation = abbreviation;
    }
    
    public String getColor() {
        return color;
    }
    
    public String getType() {
        return type;
    }
    
    public String getAbbreviation() {
        return abbreviation;
    }
}
