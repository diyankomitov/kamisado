package com.team11.kamisado.views;

import com.team11.kamisado.models.Board;
import com.team11.kamisado.models.Towers;
import com.team11.kamisado.util.Observer;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import static com.team11.kamisado.views.Colors.*;

public class BoardPane extends Pane implements Observer {
    public static final int BOARD_LENGTH = 8;
    public static final double BOARDVIEWMARGIN = 10;
    
    private SquareView[][] squares = new SquareView[BOARD_LENGTH][BOARD_LENGTH];
    private TowerView[][] towers = new TowerView[BOARD_LENGTH][BOARD_LENGTH];
    
    private Board board;
    
    public BoardPane(BorderPane parent, Board board) {
        this.board = board;
        
        this.prefHeightProperty().bind(parent.heightProperty().subtract(BOARDVIEWMARGIN * 2));
        this.prefWidthProperty().bind(this.prefHeightProperty());
        
        
        drawSquares();
        drawTowers();
    }
    
    public SquareView getSquare(int x, int y) {
        return squares[y][x];
    }
    
    @Override
    public void update() {
        int curX = board.getCurrentCoordinates().getX();
        int curY = board.getCurrentCoordinates().getY();
        int newX = board.getMoveCoordinates().getX();
        int newY = board.getMoveCoordinates().getY();
        
        TowerView curTower = towers[curY][curX];
        SquareView newSquare = squares[newY][newX];
        
        curTower.setCurrentSquare(newSquare);
        towers[newY][newX] = curTower;
        towers[curY][curX] = null;
    }
    
    private void drawSquares() {
        Colors color;
        
        for(int y = 0; y < BOARD_LENGTH; y++) {
            for(int x = 0; x < BOARD_LENGTH; x++) {
                switch(board.getSquare(x, y)) {
                    case "O":
                        color = ORANGE;
                        break;
                    case "N":
                        color = NAVY;
                        break;
                    case "B":
                        color = BLUE;
                        break;
                    case "P":
                        color = PINK;
                        break;
                    case "Y":
                        color = YELLOW;
                        break;
                    case "R":
                        color = RED;
                        break;
                    case "G":
                        color = GREEN;
                        break;
                    default:
                        color = BROWN;
                        break;
                }
                SquareView square = new SquareView(this, x, y, color);
                
                squares[y][x] = square;
                this.getChildren().add(square);
            }
        }
    }
    
    private void drawTowers() {
        Towers tower;
        Colors type;
        Colors color;
        
        for(int y = 0; y < BOARD_LENGTH; y++) {
            for(int x = 0; x < BOARD_LENGTH; x++) {
                this.getChildren().remove(towers[y][x]);
                towers[y][x] = null;
                
                if(board.isTower(x, y)) {
                    
                    tower = board.getTower(x, y);
                    
                    if(tower.getType().equals("white")) {
                        type = WHITE;
                    }
                    else {
                        type = BLACK;
                    }
                    
                    switch(tower.getColor()) {
                        case "orange":
                            color = ORANGE;
                            break;
                        case "navy":
                            color = NAVY;
                            break;
                        case "blue":
                            color = BLUE;
                            break;
                        case "pink":
                            color = PINK;
                            break;
                        case "yellow":
                            color = YELLOW;
                            break;
                        case "red":
                            color = RED;
                            break;
                        case "green":
                            color = GREEN;
                            break;
                        default:
                            color = BROWN;
                            break;
                    }
                    
                    TowerView towerView = new TowerView(squares[y][x], color, type);
                    towers[y][x] = towerView;
                    this.getChildren().add(towerView);
                }
            }
        }
    }
}