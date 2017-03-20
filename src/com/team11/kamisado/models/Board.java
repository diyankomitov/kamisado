package com.team11.kamisado.models;

import com.team11.kamisado.util.Coordinates;
import com.team11.kamisado.views.BoardPane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.team11.kamisado.models.Towers.*;

public class Board implements Serializable{
    private Player playerOne;
    private Player playerTwo;
    private Player currentPlayer;
    
    private String currentSquare;
    
    private Coordinates currentCoordinates;
    private Coordinates moveCoordinates;
    private List<Coordinates> validCoordinates;
    
    private String[][] squareArray;
    private Towers[][] towerArray;
    
    private boolean firstMove;
    private boolean hasWon;
    
    public Board(Player playerOne, Player playerTwo) {
        this.squareArray = new String[][]{{"O", "N", "B", "P", "Y", "R", "G", "Br"},
                                          {"R", "O", "P", "G", "N", "Y", "Br", "B"},
                                          {"G", "P", "O", "R", "B", "Br", "Y", "N"},
                                          {"P", "B", "N", "O", "Br", "G", "R", "Y"},
                                          {"Y", "R", "G", "Br", "O", "N", "B", "P"},
                                          {"N", "Y", "Br", "B", "R", "O", "P", "G"},
                                          {"B", "Br", "Y", "N", "G", "P", "O", "R"},
                                          {"Br", "G", "R", "Y", "P", "B", "N", "O"}};
        
        this.towerArray = new Towers[][]{
            {BLACKORANGE, BLACKNAVY, BLACKBLUE, BLACKPINK, BLACKYELLOW, BLACKRED, BLACKGREEN, BLACKBROWN},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {WHITEBROWN, WHITEGREEN, WHITERED, WHITEYELLOW, WHITEPINK, WHITEBLUE, WHITENAVY, WHITEORANGE}
        };
    
        this.currentCoordinates = new Coordinates();
        this.moveCoordinates = new Coordinates();
        this.validCoordinates = new ArrayList<>();
        this.hasWon = false;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.currentPlayer = playerOne;
        this.firstMove = true;
    }
    
    public void move(int x, int y) {
        moveCoordinates.setCoordinates(x, y);
        
        int curX = currentCoordinates.getX();
        int curY = currentCoordinates.getY();
        
        towerArray[y][x] = towerArray[curY][curX];
        towerArray[curY][curX] = EMPTY;
        
        if(currentPlayer.getPlayerColor().equals("W") && moveCoordinates.getY() == 0 ||
                currentPlayer.getPlayerColor().equals
                ("B") && moveCoordinates.getY() == BoardPane.BOARD_LENGTH - 1) {
            hasWon = true;
        }
    }
    
    public void setCurrentTower() {
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                if(towerArray[y][x].getAbbreviation().equals(currentPlayer.getPlayerColor() + currentSquare)) {
                    currentCoordinates.setCoordinates(x, y);
                }
            }
        }
    }
    
    public void setCurrentSquare(int x, int y) {
        this.currentSquare = squareArray[y][x];
    }
    
    public void switchCurrentPlayer() {
        currentPlayer = currentPlayer == playerOne ? playerTwo : playerOne;
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
        int dY = currentPlayer == playerOne ? 1 : -1;
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
            return !towerArray[y][x].equals(EMPTY);
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
    
    public Player getPlayerOne() {
        return playerOne;
    }
    
    public Player getPlayerTwo() {
        return playerTwo;
    }
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    public Player getOtherPlayer() {
        return currentPlayer == playerOne ? playerTwo : playerOne;
    }
    
    public boolean isFirstMove() {
        return firstMove;
    }
    
    public void setFirstMoveToFalse() {
        this.firstMove = false;
    }
}