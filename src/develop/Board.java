package develop;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.scene.layout.Pane;

import static develop.Colors.*;
import static develop.Square.SQUARE_SIZE;

public class Board extends Pane {
    private static final int BOARD_LENGTH = 8;
    private String[][] tileMap;
    private Square[][] squares;
    private Square square;
    private Tower[][] towers;
    
    public Board() {
        //temp
        setPrefSize(SQUARE_SIZE * BOARD_LENGTH, SQUARE_SIZE * BOARD_LENGTH);
        setMinSize(getPrefWidth()/2, getPrefHeight()/2);
        
        createTileMap();
        createBoard();
        placeTowers();
    }
    
    public Tower getTower(int x, int y) {
        return towers[y][x];
    }
    public Square getSquare(int x, int y) {
        return squares[y][x];
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
        squares = new Square[BOARD_LENGTH][BOARD_LENGTH];
        NumberBinding minBoardDimension = Bindings.min(this.widthProperty(), this.heightProperty());
        
        for(int y = 0; y < BOARD_LENGTH; y++) {
            for(int x = 0; x < BOARD_LENGTH; x++) {
                tile = tileMap[y][x];
                square = new Square(x, y);
    
                switch(tile) {
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
    
                getChildren().add(square);
                squares[y][x] = square;
            }
        }
    }
    
    private void placeTowers() {
        Tower tower;
        towers = new Tower[BOARD_LENGTH][BOARD_LENGTH];
        int y = 0;
        Colors type = BLACK;
        
        for(int i = 0; i < 2; i++) {
            for(int x = 0; x < BOARD_LENGTH; x++) {
                square = squares[y][x];
                tower = new Tower(square, square.getColor(), type);
                getChildren().add(tower);
                towers[y][x] = tower;
            }
            y = BOARD_LENGTH-1;
            type = WHITE;
        }
    }
}