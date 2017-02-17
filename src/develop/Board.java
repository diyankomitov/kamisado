package develop;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.scene.layout.Pane;

import static develop.Colors.*;
import static develop.Square.SQUARE_SIZE;

public class Board extends Pane {
    private int BOARD_LENGTH = 8;
    private String[][] tileMap;
    private Square[][] board;
    
    public Board() {
        setPrefSize(SQUARE_SIZE * BOARD_LENGTH, SQUARE_SIZE * BOARD_LENGTH);
        setMinSize(SQUARE_SIZE * (BOARD_LENGTH - 2), SQUARE_SIZE * BOARD_LENGTH - 2);
        
        createTileMap();
        createBoard();
    }
    
    public Colors getSquareColor(int x, int y) {
        return board[y][x].getColor();
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
        Square square;
        board = new Square[BOARD_LENGTH][BOARD_LENGTH]; //array to store squares
        
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
                
                getChildren().add(square);  //add to pane
                board[y][x] = square;   //add to array
                
                //Bind square width to the min of width and height of board
                //Bind square height to square width
                NumberBinding minBoardDimension = Bindings.min(widthProperty(), heightProperty());
                square.widthProperty().bind(minBoardDimension.divide(BOARD_LENGTH));
                square.heightProperty().bind(square.widthProperty());
            }
        }
    }
}