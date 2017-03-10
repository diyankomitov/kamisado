package develop;

import com.sun.xml.internal.bind.v2.TODO;
import javafx.event.ActionEvent;
import javafx.event.Event;

import javax.swing.event.*;
import java.util.ArrayList;
import java.util.List;

import static develop.views.BoardView.BOARD_LENGTH;

public class Board implements Observable {
    private List<Observer> observers;
    private String currentSquare;
    private String currentPlayer;
    
    private Coordinates currentCoordinates;
    private Coordinates moveCoordinates;
    private List<Coordinates> validCoordinates;
    
    private String[][] squareArray;
    private String[][] towerArray;
    
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
        
        towerArray = new String[][]{{"BO", "BN", "BB", "BP", "BY", "BR", "BG", "BBr"},
                                    {"", "", "", "", "", "", "", ""},
                                    {"", "", "", "", "", "", "", ""},
                                    {"", "", "", "", "", "", "", ""},
                                    {"", "", "", "", "", "", "", ""},
                                    {"", "", "", "", "", "", "", ""},
                                    {"", "", "", "", "", "", "", ""},
                                    {"WBr", "WG", "WR", "WY", "WP", "WB", "WN", "WO"}};
    
        currentPlayer = "B";
        currentCoordinates = new Coordinates();
        moveCoordinates = new Coordinates();
        validCoordinates = new ArrayList<>();
        hasWon = false;
        
        observers = new ArrayList<>();
    }
    
    public boolean hasWon() {
        return hasWon;
    }
    
    public void printTowers() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                System.out.print(towerArray[i][j] + " | ");
            }
            System.out.println();
            System.out.println("--------------------------------");
        }
    }
    
    public void setValidCoordinates() {
        validCoordinates.clear();
        
        int dX = 0;
        int dY = currentPlayer.compareTo("B")==0 ?1 :-1;
        int x = currentCoordinates.getX()+dX;
        
        boolean canAddForward = true;
        boolean canAddDiagPos = true;
        boolean canAddDiagNeg = true;
     
        for(int i = 1; i< BOARD_LENGTH; i++) {
            int y = currentCoordinates.getY()+i*dY;
            
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
    }
    
    private boolean addToValidCoordinatesList(boolean add, int x, int y) {
        if(!isTower(x,y) && add) {
            validCoordinates.add(new Coordinates(x, y));
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean isValidCoordinate(int x, int y) {
        return validCoordinates.contains(new Coordinates(x,y));
    }
    
    public boolean isTower(int x, int y) {
        try {
            return !towerArray[y][x].equals("");
        }
        catch(IndexOutOfBoundsException e) {
            return true;
        }
    }
    
    public String getTower(int x, int y) {
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
    
    public void move(int x, int y) {
        moveCoordinates.setCoordinates(x,y);
        
        int curX = currentCoordinates.getX();
        int curY = currentCoordinates.getY();
        
        towerArray[y][x] = towerArray[curY][curX];
        towerArray[curY][curX] = "";
        
        notifyAllObservers();
        
        if(currentPlayer.compareTo("W") == 0 && moveCoordinates.getY() == 0 ||
                currentPlayer.compareTo("B") == 0 && moveCoordinates.getY() == BOARD_LENGTH-1 ) {
            hasWon = true;
        }
        
        //TODO remove printtowers
        printTowers();
    }
    
    public void setCurrentTower() {
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                if(towerArray[y][x].compareTo(currentPlayer + currentSquare) == 0) {
                    currentCoordinates.setCoordinates(x,y);
                }
            }
        }
    }
    
    public void setCurrentSquare(int x, int y) {
        this.currentSquare = squareArray[y][x];
    }
    
    public void switchCurrentPlayer() {
        if(currentPlayer.compareTo("W") == 0) {
            currentPlayer = "B";
        }
        else {
            currentPlayer = "W";
        }
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