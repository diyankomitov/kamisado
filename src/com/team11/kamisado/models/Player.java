package com.team11.kamisado.models;

import java.io.Serializable;

/**
 * Created by Diyan on 11/03/2017.
 */
public class Player implements Serializable{
    private String name;
    private String playerColor;
    
    public Player(String name, String playerColor) {
        this.name = name;
        this.playerColor = playerColor;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPlayerColor() {
        return playerColor;
    }
}