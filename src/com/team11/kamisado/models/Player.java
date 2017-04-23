package com.team11.kamisado.models;

import com.team11.kamisado.util.Coordinates;

import java.io.Serializable;

/**
 * Created by Diyan on 11/03/2017.
 */
public class Player implements Serializable{
    private String name;
    private String playerColor;
    private Coordinates coordinates;
    
    public Player(String name, String playerColor) {
        this.name = name;
        this.playerColor = playerColor;
    }
    
    public Player(Player player) {
        this.name = player.name;
        this.playerColor = player.playerColor;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPlayerColor() {
        return playerColor;
    }
    
    public Coordinates getCoordinates() {
        return coordinates;
    }
    
    public void setCoordinates(int x, int y) {
        coordinates = new Coordinates(x,y);
    }
}