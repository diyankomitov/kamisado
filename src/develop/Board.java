package develop;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.scene.layout.Pane;

import static develop.Colors.*;
import static develop.Square.SQUARE_SIZE;

public class Board extends Pane {
    public static final int BOARD_LENGTH = 8;
    private String[][] tileMap;
    private Square[][] squares;
    private Square square;
    
    public Board() {
        //temp
        setPrefSize(SQUARE_SIZE * BOARD_LENGTH, SQUARE_SIZE * BOARD_LENGTH);
        setMinSize(getPrefWidth()/2, getPrefHeight()/2);
        
        createTileMap();
        createBoard();
        placeTowers();
    }
    
    private void createTileMap() {
        tileMap = new String[][]{{"O", "N", "B", "P", "Y", "R", "G", "Br"},
                                 {"R", "O", "P", "G", "N", "Y", "Br", "B"},
                                 {"G", "P", "O", "R", "B", "Br", "Y", "N"},
                                 {"P", "B", "N", "O", "Br", "G", "R", "Y"},
                                 {"Y", "R", "G", "Br", "O", "N", "B", "P"},
                                 {"N", "Y", "Br", "B", "R", "O", "P", "G"},
                                 {"B", "Br", "Y", "N", "G", "P", "O", "R"},
                                 {"Br", "G", "R", "Y", "P", "B", "N", "O"}};
    }
    
    private void createBoard() {
        String tile;
        squares = new Square[BOARD_LENGTH][BOARD_LENGTH]; //array to store squares
        NumberBinding minBoardDimension = Bindings.min(this.widthProperty(),
                this.heightProperty());
        
        for(int y = 0; y < BOARD_LENGTH; y++) {
            for(int x = 0; x < BOARD_LENGTH; x++) {
                tile = tileMap[y][x];                   //iterate through the tilemap
                square = new Square(x, y);              //make square
                
                switch(tile) {                          //set color depending on tilemap
                    case "O":
                        square.setColor(ORANGE);
                        break;
                    case "N":
                        square.setColor(NAVY);
                        break;
                    case "B":
                        square.setColor(BLUE);
                        break;
                    case "P":
                        square.setColor(PINK);
                        break;
                    case "Y":
                        square.setColor(YELLOW);
                        break;
                    case "R":
                        square.setColor(RED);
                        break;
                    case "G":
                        square.setColor(GREEN);
                        break;
                    case "Br":
                        square.setColor(BROWN);
                        break;
                }
                
                square.widthProperty().bind(minBoardDimension.divide(BOARD_LENGTH));
    
                getChildren().add(square);  //add to pane
                squares[y][x] = square;   //add to array
            }
        }
    }
    
    private void placeTowers() {
        Tower tower;
        Tower[][] towers = new Tower[BOARD_LENGTH][BOARD_LENGTH];
        
        int topRow = 0;
        int bottomRow = BOARD_LENGTH-1;
    
        for(int x = 0; x < BOARD_LENGTH; x++) {
            square = squares[topRow][x];
            tower = new Tower(square, square.getColor(), BLACK);
            getChildren().add(tower);
            towers[topRow][x] = tower;
            
            square = squares[bottomRow][x];
            tower = new Tower(square, square.getColor(), WHITE);
            getChildren().add(tower);
            towers[bottomRow][x] = tower;
        }
    }
}