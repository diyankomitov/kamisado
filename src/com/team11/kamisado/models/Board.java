package com.team11.kamisado.models;

import com.team11.kamisado.util.Coordinates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    private boolean matchWon;
    
    private int maxPoints;
    
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
        this.matchWon = false;
        this.maxPoints = 0;
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
        this.matchWon = false;
        this.maxPoints = board.maxPoints;
        
        
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
        boolean sumoPush = false;
        
        oldCoordinates.setCoordinates(currentCoordinates.getX(), currentCoordinates.getY());
        moveCoordinates.setCoordinates(x, y);
        
        int oldX = oldCoordinates.getX();
        int oldY = oldCoordinates.getY();
        int newX = moveCoordinates.getX();
        int newY = moveCoordinates.getY();
        
        int index = towerArray[oldY][oldX].getSumoLevel();
        int pushx = 0;
        int pushy = 0;
        if(index > 0 && newX == oldX && (newY == oldY+1 || newY == oldY-1) && towerArray[newY][newX] != EMPTY) {
            sumoPush = true;
            int toMove = 0;
    
            int i1 = currentPlayer.getPlayerColor().equals("B") ? 1 : -1;
            
            for(int i = i1; i <= index && i>=index*-1; i+=i1) {
                if(!towerArray[oldY+i][oldX].equals(EMPTY)) {
                    toMove++;
                }
            }
            toMove*=i1;
            
            pushx = oldX;
            pushy = oldY+toMove+i1;
            
            for(; toMove!= 0; toMove-=i1) {
                towerArray[oldY+toMove+i1][oldX] = towerArray[oldY+toMove][oldX];
                towerArray[oldY+toMove][oldX] = EMPTY;
            }
        }
        
        towerArray[newY][newX] = towerArray[oldY][oldX];
        towerArray[oldY][oldX] = EMPTY;
    
        if(sumoPush) {
            setCurrentSquare(pushx, pushy);
        }
        
        String currentPlayerColor = currentPlayer.getPlayerColor();
        
        if(currentPlayerColor.equals("W") && newY == 0 || currentPlayerColor.equals("B") && newY == BOARD_LENGTH - 1) {
            win = true;
            winner = currentPlayerColor.equals(playerOne.getPlayerColor()) ? playerOne : playerTwo;
            try {
                towerArray[newY][newX] = Towers.makeSumo(towerArray[newY][newX]);
            }
            catch(IllegalArgumentException e) {
                matchWon = true;
                winner.setScore(15);
            }
            
            winner.setScore(winner.getScore() + towerArray[newY][newX].getSumoLevel());
            
            if(winner.getScore() == maxPoints) {
                matchWon = true;
            }
            
            
        }
        else {
            if(!sumoPush) {
                switchCurrentPlayer();
                setCurrentSquare(newX,newY);
            }
    
            if(!setValidCoordinates()) {
                switchCurrentPlayer();
                setCurrentSquare(currentCoordinates.getX(),currentCoordinates.getY());
        
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
    
    public void setMaxPoints(int points) {
        this.maxPoints = points;
    }
    
    public void continueMatch(boolean left) {
        if(winner.getPlayerColor().equals("B")) {
            if(left) {
                replace(1);
                replace(2);
            }
            else {
                replace(3);
                replace(4);
            }
        }
        else {
            if(left) {
                replace(2);
                replace(1);
            }
            else {
                replace(4);
                replace(3);
            }
        }
        
        currentPlayer = winner.getPlayerColor().equals(playerOne.getPlayerColor()) ? playerTwo : playerOne;
        
        this.oldCoordinates = new Coordinates();
        this.currentCoordinates = new Coordinates();
        this.moveCoordinates = new Coordinates();
        this.validCoordinates = new ArrayList<>();
        this.winner = null;
        this.firstMove = true;
        this.lock = false;
        this.deadlock = false;
        this.win = false;
        
    }
    
    public void print() {                           //TODO: remove print
        for(int y = 0; y<BOARD_LENGTH; y++) {
            for(int x = 0; x<BOARD_LENGTH; x++) {
                if(towerArray[y][x] != EMPTY) {
                    System.out.print(towerArray[y][x].getAbbreviation() + towerArray[y][x].getSumoLevel() + " | ");
                }
                else {
                    System.out.print("  " + " | ");
                }
            }
            System.out.println();
            System.out.println("------------------------------------------");
        }
    }
    
    private void setCurrentCoordinates() {
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
        if(isFirstMove()) {
            currentCoordinates.setCoordinates(x,y);
        }
        else {
            setCurrentCoordinates();
        }
    }
    
    private void switchCurrentPlayer() {
        currentPlayer = currentPlayer.getPlayerColor().equals(playerOne.getPlayerColor()) ? playerTwo : playerOne;
    }
    
    public boolean isWin() {
        return win;
    }
    
    public boolean isMatchWon() {
        return matchWon;
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
    
                int sumoLevel = towerArray[y][x].getSumoLevel();
                if(sumoLevel == 1 && j==10 || sumoLevel == 2 && j==6 || sumoLevel == 3 && j==2) {
                    break;
                }
                
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
        
        addSumoCoordinates();
        return validCoordinates.size() != 0;
    }
    
    private void addSumoCoordinates() {
        int x = currentCoordinates.getX();
        int y = currentCoordinates.getY();
    
        int i = currentPlayer.getPlayerColor().equals("B") ? 1 : -1;
    
        int sumoLevel = towerArray[y][x].getSumoLevel();
        
        if(sumoLevel >= 1 && (i == 1 && y+2<BOARD_LENGTH || i == -1 && y-2>=0) && !towerArray[y+i][x].equals(EMPTY) && towerArray[y+(2*i)][x].equals(EMPTY)) {
            validCoordinates.add(new Coordinates(x, y+i));
        }
        if(sumoLevel >= 2 && (i == 1 && y+3<BOARD_LENGTH || i == -1 && y-3>=0) && !towerArray[y+i][x].equals(EMPTY) && !towerArray[y+(2*i)][x].equals(EMPTY) && towerArray[y+(3*i)][x].equals(EMPTY)) {
            validCoordinates.add(new Coordinates(x, y+i));
        }
        if(sumoLevel == 3 && (i == 1 && y+4<BOARD_LENGTH || i == -1 && y-4>=0) && !towerArray[y+i][x].equals(EMPTY) && !towerArray[y+(2*i)][x].equals(EMPTY) && !towerArray[y+(3*i)][x].equals(EMPTY) && towerArray[y+(4*i)][x].equals(EMPTY)) {
            validCoordinates.add(new Coordinates(x, y+i));
        }
        
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
    
    
    private void replace(int number) {
        int next;
        switch(number) {
            case 1:
                next = 0;
                for(int i = 0; i < BOARD_LENGTH; i++) {
                    for(int j = 0; j < BOARD_LENGTH; j++) {
                        if(towerArray[i][j].getType().equals("black")) {
                            towerArray[0][next] = towerArray[i][j];
                            if(i!=0) {
                                towerArray[i][j] = EMPTY;
                            }
                            next++;
                        }
                    }
                }
                break;
            case 2:
                next = BOARD_LENGTH-1;
                for(int i = BOARD_LENGTH-1; i >= 0; i--) {
                    for(int j = BOARD_LENGTH-1; j >= 0; j--) {
                        if(towerArray[i][j].getType().equals("white")) {
                            towerArray[BOARD_LENGTH-1][next] = towerArray[i][j];
                            if(i!=BOARD_LENGTH-1) {
                                towerArray[i][j] = EMPTY;
                            }
                            next--;
                        }
                    }
                }
                break;
            case 3:
                next = BOARD_LENGTH-1;
                for(int i = 0; i < BOARD_LENGTH; i++) {
                    for(int j = BOARD_LENGTH-1; j >=0; j--) {
                        if(towerArray[i][j].getType().equals("black")) {
                            towerArray[0][next] = towerArray[i][j];
                            if(i!=0) {
                                towerArray[i][j] = EMPTY;
                            }
                            next--;
                        }
                    }
                }
                break;
            case 4:
                next = 0;
                for(int i = BOARD_LENGTH-1; i >= 0; i--) {
                    for(int j = 0; j < BOARD_LENGTH; j++) {
                        if(towerArray[i][j].getType().equals("white")) {
                            towerArray[BOARD_LENGTH-1][next] = towerArray[i][j];
                            if(i!=BOARD_LENGTH-1) {
                                towerArray[i][j] = EMPTY;
                            }
                            next++;
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
}
