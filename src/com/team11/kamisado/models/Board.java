package com.team11.kamisado.models;

import com.team11.kamisado.util.Coordinates;
import com.team11.kamisado.views.BoardPane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.team11.kamisado.models.Towers.*;

public class Board implements Serializable{
    private Coordinates currentCoordinates;
    private Coordinates moveCoordinates;
    private Coordinates oldCoordinates;
    
    private final Player playerOne;
    private final Player playerTwo;
    
    private Player currentPlayer;
    private Player winner;
    
    private final String[][] squareArray;
    
    private Towers[][] towerArray;
    
    private String currentSquare;
    private ArrayList<Coordinates> validCoordinates;
    private boolean firstMove;
    private boolean hasWon;
    private boolean deadlock;
    private boolean lock;
    
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
            {BLACKORANGE, BLACKNAVY, BLACKBLUE, BLACKPINK, BLACKYELLOW, BLACKRED, BLACKGREEN,
             BLACKBROWN},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {WHITEBROWN, WHITEGREEN, WHITERED, WHITEYELLOW, WHITEPINK, WHITEBLUE, WHITENAVY, WHITEORANGE}
        };
    
        this.oldCoordinates = new Coordinates();
        this.currentCoordinates = new Coordinates();
        this.moveCoordinates = new Coordinates();
        this.validCoordinates = new ArrayList<>();
        this.hasWon = false;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.currentPlayer = playerOne;
        this.firstMove = true;
        this.lock = false;
        this.deadlock = false;
        this.winner = null;
    }
    
    public Board(Board board) {
        this.playerOne = board.playerOne;
        this.playerTwo = board.playerTwo;
        this.currentPlayer = new Player(board.currentPlayer);
        
        this.oldCoordinates = new Coordinates(board.oldCoordinates);
        this.currentCoordinates = new Coordinates(board.currentCoordinates);
        this.moveCoordinates = new Coordinates(board.moveCoordinates);
        
        this.squareArray = board.squareArray;
        this.currentSquare = board.currentSquare;
    
        this.towerArray = new Towers[8][8];
        int i = 0;
        for(Towers[] towers: board.towerArray) {
            int j = 0;
            for(Towers tower: towers) {
                this.towerArray[i][j] = tower;
                j++;
            }
            i++;
        }
    
        this.hasWon = board.hasWon;
        this.firstMove = board.firstMove;
        this.lock = board.lock;
        this.deadlock = board.deadlock;
        this.winner = board.winner;
    
        this.validCoordinates = new ArrayList<>();
        
        
        List<Coordinates> tempCoordinates = board.getValidCoordinates();
        
        for(int index = 0; index < tempCoordinates.size(); index++) {
            this.validCoordinates.add(tempCoordinates.get(index));
        }
    }
    
    public void print() {
        for(int y = 0; y<8; y++) {
            for(int x = 0; x<8; x++) {
                if(towerArray[y][x] != EMPTY) {
                    System.out.print(towerArray[y][x].getAbbreviation() + " | ");
                }
                else {
                    System.out.print("  " + " | ");
                }
            }
            System.out.println();
            System.out.println("------------------------------------------");
        }
    }
    
    public int move(int x, int y) {
        lock = false;
        deadlock = false;
        
        oldCoordinates.setCoordinates(currentCoordinates.getX(), currentCoordinates.getY());
        moveCoordinates.setCoordinates(x, y);
        
        int oldX = oldCoordinates.getX();
        int oldY = oldCoordinates.getY();
        int newX = moveCoordinates.getX();
        int newY = moveCoordinates.getY();
        
        towerArray[newY][newX] = towerArray[oldY][oldX];
        towerArray[oldY][oldX] = EMPTY;
        
        if(currentPlayer.getPlayerColor().equals("W") && moveCoordinates.getY() == 0 ||
                currentPlayer.getPlayerColor().equals
                ("B") && moveCoordinates.getY() == BoardPane.BOARD_LENGTH - 1) {
            hasWon = true;
            winner = currentPlayer.getPlayerColor().equals(playerOne.getPlayerColor()) ? playerOne : playerTwo;
            return 1;
        }
        
        switchCurrentPlayer();
        setCurrentSquare(newX,newY);
        setCurrentCoordinates();
        
        if(!setValidCoordinates()) {
            switchCurrentPlayer();
            setCurrentSquare(currentCoordinates.getX(),currentCoordinates.getY());
            setCurrentCoordinates();
        
            if(!setValidCoordinates()) {
                deadlock = true;
                winner = currentPlayer.getPlayerColor().equals(playerOne.getPlayerColor()) ? playerTwo : playerOne;
                return 3;
            }
            lock = true;
            return 2;
        }
        
        return 0;
    }
    
    public void setCurrentCoordinates() {
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
    
    public Coordinates getOldCoordinates() {
        return oldCoordinates;
    }
    
    public Coordinates getMoveCoordinates() {
        return moveCoordinates;
    }
    
    public Coordinates getCurrentCoordinates() {
        return currentCoordinates;
    }
    
    public List<Coordinates> getValidCoordinates() {
        return validCoordinates;
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
    
    public boolean isLock() {
        return lock;
    }
    public boolean isDeadlock() {
        return deadlock;
    }
    
    public boolean isFirstMove() {
        return firstMove;
    }
    
    public void setFirstMoveToFalse() {
        this.firstMove = false;
    }
    public Player getWinner() {
        return winner;
    }
}