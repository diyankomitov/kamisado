package com.team11.kamisado.models;

import com.team11.kamisado.util.Coordinates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.team11.kamisado.models.Towers.*;

public class Board implements Serializable {
    public static final int BOARD_LENGTH = 8;
    
    private Coordinates currentCoordinates;
    private Coordinates moveCoordinates;
    private Coordinates oldCoordinates;
    private ArrayList<Coordinates> validCoordinates;
    
    private Player playerOne;
    private Player playerTwo;
    private Player currentPlayer;
    private Player winner;
    
    private String currentSquare;
    private String[][] squareArray;
    private Towers[][] towerArray;
    
    private boolean firstMove;
    private boolean win;
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
            {BLACKORANGE, BLACKNAVY, BLACKBLUE, BLACKPINK, BLACKYELLOW, BLACKRED, BLACKGREEN, BLACKBROWN},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {WHITEBROWN, WHITEGREEN, WHITERED, WHITEYELLOW, WHITEPINK, WHITEBLUE, WHITENAVY, WHITEORANGE}};
    
        this.oldCoordinates = new Coordinates();
        this.currentCoordinates = new Coordinates();
        this.moveCoordinates = new Coordinates();
        this.validCoordinates = new ArrayList<>();
        this.win = false;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.currentPlayer = playerOne;
        this.firstMove = true;
        this.lock = false;
        this.deadlock = false;
        this.winner = null;
    }
    
    public Board(Board board) {
        this.playerOne = new Player(board.playerOne);
        this.playerTwo = new Player(board.playerTwo);
        this.currentPlayer = new Player(board.currentPlayer);
        
        this.oldCoordinates = new Coordinates(board.oldCoordinates);
        this.currentCoordinates = new Coordinates(board.currentCoordinates);
        this.moveCoordinates = new Coordinates(board.moveCoordinates);
        
        this.squareArray = board.squareArray;
        this.currentSquare = board.currentSquare;
    
        this.towerArray = new Towers[BOARD_LENGTH][BOARD_LENGTH];
        int i = 0;
        for(Towers[] towers: board.towerArray) {
            int j = 0;
            for(Towers tower: towers) {
                this.towerArray[i][j] = tower;
                j++;
            }
            i++;
        }
    
        this.win = board.win;
        this.firstMove = board.firstMove;
        this.lock = board.lock;
        this.deadlock = board.deadlock;
        this.winner = null;
        
        
        this.validCoordinates = new ArrayList<>();
        List<Coordinates> tempCoordinates = board.getValidCoordinates();
        for(Coordinates tempCoordinate : tempCoordinates) {
            this.validCoordinates.add(tempCoordinate);
        }
    }
    
    public void randomizeBoard() {
        Arrays.asList(squareArray);
        Collections.shuffle(Arrays.asList(squareArray));
        
        for(int i = 0; i <8; i++) {
            towerArray[0][i] = Towers.getTower("B" + squareArray[0][i]);
            towerArray[BOARD_LENGTH-1][i] = Towers.getTower("W" + squareArray[BOARD_LENGTH-1][i]);
        }
    }
    
    public void move(int x, int y) {
        lock = false;
        
        oldCoordinates.setCoordinates(currentCoordinates.getX(), currentCoordinates.getY());
        moveCoordinates.setCoordinates(x, y);
        
        int oldX = oldCoordinates.getX();
        int oldY = oldCoordinates.getY();
        int newX = moveCoordinates.getX();
        int newY = moveCoordinates.getY();
        
        towerArray[newY][newX] = towerArray[oldY][oldX];
        towerArray[oldY][oldX] = EMPTY;
        
        String currentPlayerColor = currentPlayer.getPlayerColor();
        
        if(currentPlayerColor.equals("W") && newY == 0 || currentPlayerColor.equals("B") && newY == BOARD_LENGTH - 1) {
            win = true;
            winner = currentPlayerColor.equals(playerOne.getPlayerColor()) ? playerOne : playerTwo;
        }
        else {
            switchCurrentPlayer();
            setCurrentSquare(newX,newY);
            setCurrentCoordinates();
    
            if(!setValidCoordinates()) {
                switchCurrentPlayer();
                setCurrentSquare(currentCoordinates.getX(),currentCoordinates.getY());
                setCurrentCoordinates();
        
                if(!setValidCoordinates()) {
                    deadlock = true;
                    winner = currentPlayerColor.equals(playerOne.getPlayerColor()) ? playerTwo : playerOne;
                }
                else {
                    lock = true;
                }
            }
        }
    }
    
    public void setCurrentCoordinates() {
        for(int y = 0; y < BOARD_LENGTH; y++) {
            for(int x = 0; x < BOARD_LENGTH; x++) {
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
        currentPlayer = currentPlayer.getPlayerColor().equals(playerOne.getPlayerColor()) ? playerTwo : playerOne;
    }
    
    public boolean isWin() {
        return win;
    }
    
    public boolean areValidCoordinates(int x, int y) {
        return validCoordinates.contains(new Coordinates(x, y));
    }
    
    public boolean setValidCoordinates() {
        validCoordinates.clear();
        
        int[][] coordinateOffsets = new int[][]{{-1,+1, -2,+2, -3,+3, -4,+4, -5,+5, -6,+6, -7,+7},
                                                {+0,+1, +0,+2, +0,+3, +0,+4, +0,+5, +0,+6, +0,+7},
                                                {+1,+1, +2,+2, +3,+3, +4,+4, +5,+5, +6,+6, +7,+7},
                                                {-1,-1, -2,-2, -3,-3, -4,-4, -5,-5, -6,-6, -7,-7},
                                                {+0,-1, +0,-2, +0,-3, +0,-4, +0,-5, +0,-6, +0,-7},
                                                {+1,-1, +2,-2, +3,-3, +4,-4, +5,-5, +6,-6, +7,-7}};
    
        int i = currentPlayer.getPlayerColor().equals("B") ? 0 : 3;
        int i1 = i+3;
        
        for(; i<i1; i++) {
            for(int j = 0; j<coordinateOffsets[i].length; j++) {
                int x = currentCoordinates.getX();
                int y = currentCoordinates.getY();
                x = x + coordinateOffsets[i][j];
                y = y + coordinateOffsets[i][++j];
        
                if(x>=0 && x<BOARD_LENGTH && y>=0 && y<BOARD_LENGTH && towerArray[y][x].equals(EMPTY)) {
                    validCoordinates.add(new Coordinates(x,y));
                }
                else {
                    break;
                }
            }
        }
        return validCoordinates.size() != 0;
    }
    
    public Towers getTower(int x, int y) {
        return towerArray[y][x];
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
    
    public String[][] getSquareArray() {
        return squareArray;
    }
    
    public Towers[][] getTowerArray() {
        return towerArray;
    }
}
