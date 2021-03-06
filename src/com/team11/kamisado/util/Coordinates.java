package com.team11.kamisado.util;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Diyan on 09/03/2017.
 */
public class Coordinates implements Serializable{
    private int x;
    private int y;
    
    public Coordinates() {
        
    }
    
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Coordinates(Coordinates coordinates) {
        this.x = coordinates.x;
        this.y = coordinates.y;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean equals(Object o) {
        return o instanceof Coordinates && this.getX() == ((Coordinates) o).getX() && this.getY() == ((Coordinates) o).getY();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}