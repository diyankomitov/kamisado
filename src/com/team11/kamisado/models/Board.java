package com.team11.kamisado.models;

import com.team11.kamisado.util.Coordinates;
import com.team11.kamisado.util.Observable;
import com.team11.kamisado.util.Observer;
import com.team11.kamisado.views.BoardPane;

import java.util.ArrayList;
import java.util.List;

public class Board implements Observable {
    private List<Observer> observers;
    private String currentSquare;
    private String currentPlayerColor;
    
    private Coordinates currentCoordinates;
    private Coordinates moveCoordinates;
    private List<Coordinates> validCoordinates;
    
    private String[][] squareArray;
    private Towers[][] towerArray;
    
    private boolean hasWon;
    
    public Board() {
        squareArray = new String[][]{{"O", "N", "B", "P", "Y", "R", "G", "Br"},
                                     {"R", "O", "P", "G", "N", "Y", "Br", "B"},
                                     {"G", "P", "O", "R", "B", "Br", "Y", "N"},
                                     {"P", "B", "N", "O", "Br", "G", "R", "Y"},
                                     {"Y", "R", "G", "Br", "O", "N", "B", "P"},
                                     {"N", "Y", "Br", "B", "R", "O", "P", "G"},
                                     {"B", "Br", "Y", "N", "G", "P", "O", "R"},
                                     {"Br", "G", "R", "Y", "P", "B", "N", "O"}};
        
        towerArray = new Towers[][]{
            {Towers.BLACKORANGE, Towers.BLACKNAVY, Towers.BLACKBLUE, Towers.BLACKPINK, Towers.BLACKYELLOW, Towers.BLACKRED, Towers.BLACKGREEN, Towers.BLACKBROWN},
            {Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY},
            {Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY},
            {Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY},
            {Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY},
            {Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY},
            {Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY, Towers.EMPTY},
            {Towers.WHITEBROWN, Towers.WHITEGREEN, Towers.WHITERED, Towers.WHITEYELLOW, Towers.WHITEPINK, Towers.WHITEBLUE, Towers.WHITENAVY, Towers.WHITEORANGE}};
    
        currentCoordinates = new Coordinates();
        moveCoordinates = new Coordinates();
        validCoordinates = new ArrayList<>();
        hasWon = false;
        currentPlayerColor = "B";
    
        observers = new ArrayList<>();
    }
    
    public void move(int x, int y) {
        moveCoordinates.setCoordinates(x, y);
        
        int curX = currentCoordinates.getX();
        int curY = currentCoordinates.getY();
        
        towerArray[y][x] = towerArray[curY][curX];
        towerArray[curY][curX] = Towers.EMPTY;
        
        notifyAllObservers();
        
        if(currentPlayerColor.equals("W") && moveCoordinates.getY() == 0 || currentPlayerColor.equals("B") && moveCoordinates.getY() == BoardPane.BOARD_LENGTH - 1) {
            hasWon = true;
        }
    }
    
    public void setCurrentTower() {
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                if(towerArray[y][x].getAbbreviation().equals(currentPlayerColor + currentSquare)) {
                    currentCoordinates.setCoordinates(x, y);
                }
            }
        }
    }
    
    public void setCurrentSquare(int x, int y) {
        this.currentSquare = squareArray[y][x];
    }
    
    public void switchCurrentPlayerColor() {
        if(currentPlayerColor.equals("W")) {
            currentPlayerColor = "B";
        }
        else {
            currentPlayerColor = "W";
        }
    }
    
    public boolean hasWon() {
        return hasWon;
    }
    
    public boolean areValidCoordinates(int x, int y) {
        return validCoordinates.contains(new Coordinates(x, y));
    }
    
    public boolean setValidCoordinates() {
        validCoordinates.clear();
        
        int dX = 0;
        int dY = currentPlayerColor.equals("B") ? 1 : -1;
        int x = currentCoordinates.getX() + dX;
        
        boolean canAddForward = true;
        boolean canAddDiagPos = true;
        boolean canAddDiagNeg = true;
        
        for(int i = 1; i < BoardPane.BOARD_LENGTH; i++) {
            int y = currentCoordinates.getY() + i * dY;
            
            dX = 0;
            if(!addToValidCoordinatesList(canAddForward, (x + (i * dX)), y)) {
                canAddForward = false;
            }
            
            dX = 1;
            if(!addToValidCoordinatesList(canAddDiagPos, (x + (i * dX)), y)) {
                canAddDiagPos = false;
            }
            
            dX = -1;
            if(!addToValidCoordinatesList(canAddDiagNeg, (x + (i * dX)), y)) {
                canAddDiagNeg = false;
            }
        }
        
        return validCoordinates.size() != 0;
        
    }
    
    private boolean addToValidCoordinatesList(boolean add, int x, int y) {
        if(!isTower(x, y) && add) {
            validCoordinates.add(new Coordinates(x, y));
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean isTower(int x, int y) {
        try {
            return !towerArray[y][x].equals(Towers.EMPTY);
        }
        catch(IndexOutOfBoundsException e) {
            return true;
        }
    }
    
    public Towers getTower(int x, int y) {
        return towerArray[y][x];
    }
    
    public String getSquare(int x, int y) {
        return squareArray[y][x];
    }
    
    public Coordinates getMoveCoordinates() {
        return moveCoordinates;
    }
    
    public Coordinates getCurrentCoordinates() {
        return currentCoordinates;
    }
    
    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }
    
    @Override
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyAllObservers() {
        for(Observer observer : observers) {
            observer.update();
        }
    }
}