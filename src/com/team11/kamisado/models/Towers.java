package com.team11.kamisado.models;

import javax.naming.CannotProceedException;
import java.io.Serializable;
import java.rmi.NoSuchObjectException;
import java.util.NoSuchElementException;

public enum Towers implements Serializable{
    
    BLACKORANGE ("orange", "black", "BO", 0),
    BLACKNAVY ("navy", "black", "BN", 0),
    BLACKBLUE ("blue", "black", "BB", 0),
    BLACKPINK ("pink", "black", "BP", 0),
    BLACKYELLOW ("yellow", "black", "BY", 0),
    BLACKRED ("red", "black", "BR", 0),
    BLACKGREEN ("green", "black", "BG", 0),
    BLACKBROWN ("brown", "black", "BBr", 0),
    WHITEORANGE ("orange", "white", "WO", 0),
    WHITENAVY ("navy", "white", "WN", 0),
    WHITEBLUE ("blue", "white", "WB", 0),
    WHITEPINK ("pink", "white", "WP", 0),
    WHITEYELLOW ("yellow", "white", "WY", 0),
    WHITERED ("red", "white", "WR", 0),
    WHITEGREEN ("green", "white", "WG", 0),
    WHITEBROWN ("brown", "white", "WBr", 0),
    SUMOBLACKORANGE ("orange", "black", "BO", 1),
    SUMOBLACKNAVY ("navy", "black", "BN", 1),
    SUMOBLACKBLUE ("blue", "black", "BB", 1),
    SUMOBLACKPINK ("pink", "black", "BP", 1),
    SUMOBLACKYELLOW ("yellow", "black", "BY", 1),
    SUMOBLACKRED ("red", "black", "BR", 1),
    SUMOBLACKGREEN ("green", "black", "BG", 1),
    SUMOBLACKBROWN ("brown", "black", "BBr", 1),
    SUMOWHITEORANGE ("orange", "white", "WO", 1),
    SUMOWHITENAVY ("navy", "white", "WN", 1),
    SUMOWHITEBLUE ("blue", "white", "WB", 1),
    SUMOWHITEPINK ("pink", "white", "WP", 1),
    SUMOWHITEYELLOW ("yellow", "white", "WY", 1),
    SUMOWHITERED ("red", "white", "WR", 1),
    SUMOWHITEGREEN ("green", "white", "WG", 1),
    SUMOWHITEBROWN ("brown", "white", "WBr", 1),
    DOUBLESUMOBLACKORANGE ("orange", "black", "BO", 2),
    DOUBLESUMOBLACKNAVY ("navy", "black", "BN", 2),
    DOUBLESUMOBLACKBLUE ("blue", "black", "BB", 2),
    DOUBLESUMOBLACKPINK ("pink", "black", "BP", 2),
    DOUBLESUMOBLACKYELLOW ("yellow", "black", "BY", 2),
    DOUBLESUMOBLACKRED ("red", "black", "BR", 2),
    DOUBLESUMOBLACKGREEN ("green", "black", "BG", 2),
    DOUBLESUMOBLACKBROWN ("brown", "black", "BBr", 2),
    DOUBLESUMOWHITEORANGE ("orange", "white", "WO", 2),
    DOUBLESUMOWHITENAVY ("navy", "white", "WN", 2),
    DOUBLESUMOWHITEBLUE ("blue", "white", "WB", 2),
    DOUBLESUMOWHITEPINK ("pink", "white", "WP", 2),
    DOUBLESUMOWHITEYELLOW ("yellow", "white", "WY", 2),
    DOUBLESUMOWHITERED ("red", "white", "WR", 2),
    DOUBLESUMOWHITEGREEN ("green", "white", "WG", 2),
    DOUBLESUMOWHITEBROWN ("brown", "white", "WBr", 2),
    TRIPPLESUMOBLACKORANGE ("orange", "black", "BO", 3),
    TRIPPLESUMOBLACKNAVY ("navy", "black", "BN", 3),
    TRIPPLESUMOBLACKBLUE ("blue", "black", "BB", 3),
    TRIPPLESUMOBLACKPINK ("pink", "black", "BP", 3),
    TRIPPLESUMOBLACKYELLOW ("yellow", "black", "BY", 3),
    TRIPPLESUMOBLACKRED ("red", "black", "BR", 3),
    TRIPPLESUMOBLACKGREEN ("green", "black", "BG", 3),
    TRIPPLESUMOBLACKBROWN ("brown", "black", "BBr", 3),
    TRIPPLESUMOWHITEORANGE ("orange", "white", "WO", 3),
    TRIPPLESUMOWHITENAVY ("navy", "white", "WN", 3),
    TRIPPLESUMOWHITEBLUE ("blue", "white", "WB", 3),
    TRIPPLESUMOWHITEPINK ("pink", "white", "WP", 3),
    TRIPPLESUMOWHITEYELLOW ("yellow", "white", "WY", 3),
    TRIPPLESUMOWHITERED ("red", "white", "WR", 3),
    TRIPPLESUMOWHITEGREEN ("green", "white", "WG", 3),
    TRIPPLESUMOWHITEBROWN ("brown", "white", "WBr", 3),
    EMPTY ("", "", "", 0);
    
    private final String color;
    private final String type;
    private final String abbreviation;
    private final int sumoLevel;
    
    Towers(String color, String type, String abbreviation, int sumoLevel) {
        this.color = color;
        this.type = type;
        this.abbreviation = abbreviation;
        this.sumoLevel = sumoLevel;
    }
    
    public static Towers getTower(String abbreviation) {
        for(Towers tower: Towers.values()) {
            if(tower.getAbbreviation().equals(abbreviation)) {
                return tower;
            }
        }
        throw new IllegalArgumentException("Tower not found: " + abbreviation);
    }
    
    public static Towers makeSumo(Towers tower) {
        String abbreviation = tower.getAbbreviation();
        int sumoLevel = tower.getSumoLevel();
        for(Towers towers : Towers.values()) {
            if(towers.getAbbreviation().equals(abbreviation) && towers.getSumoLevel() == sumoLevel+1) {
                return towers;
            }
        }
        throw new IllegalArgumentException("Can't upgrade sumo level: " + tower);
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
    
    public int getSumoLevel() {
        return sumoLevel;
    }
}
