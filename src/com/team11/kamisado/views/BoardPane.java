package com.team11.kamisado.views;

import com.team11.kamisado.models.Board;
import com.team11.kamisado.models.Towers;
import com.team11.kamisado.util.Observer;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import static com.team11.kamisado.views.Colors.*;

public class BoardPane extends Pane implements Observer {
    private static final int SELECTOR_ARC = 20;
    private static final int FADE_DURATION = 500;
    private static final double FADE_TO_VALUE = 0.5;
    public static final int BOARD_LENGTH = 8;
    public static final double BOARD_VIEW_MARGIN = 10;
    
    private SquareView[][] squares;
    private TowerView[][] towers;
    
    private Board board;
    private SquareView selector;
    private FadeTransition fadeTransition;
    
    public BoardPane(BorderPane parent, Board board) {
        this.board = board;
        
        this.prefHeightProperty().bind(parent.heightProperty().subtract(BOARD_VIEW_MARGIN * 2));
        this.prefWidthProperty().bind(this.prefHeightProperty());
    
        squares = new SquareView[BOARD_LENGTH][BOARD_LENGTH];
        towers = new TowerView[BOARD_LENGTH][BOARD_LENGTH];
        
        drawSquares();
        drawTowers();
        
        initSelector();
    }
    
    @Override
    public void update() {
        int oldX = board.getOldCoordinates().getX();
        int oldY = board.getOldCoordinates().getY();
        int newX = board.getMoveCoordinates().getX();
        int newY = board.getMoveCoordinates().getY();
        
        TowerView curTower = towers[oldY][oldX];
        SquareView newSquare = squares[newY][newX];
        
        curTower.setCurrentSquare(newSquare);
        towers[newY][newX] = curTower;
        towers[oldY][oldX] = null;
    }
    
    public void stopFadeTransition() {
        fadeTransition.stop();
    }
    
    public void moveSelector(int x, int y) {
        fadeTransition.stop();
        selector.moveSquare(x, y);
        fadeTransition.play();
    }
    
    public SquareView getSquare(int x, int y) {
        return squares[y][x];
    }
    
    private void initSelector() {
        selector = new SquareView(this, 0, 0, Colors.TRANSPARENT);
        selector.setStroke(Colors.TRUEWHITE.getValue());
        selector.strokeWidthProperty().bind(selector.widthProperty().divide(10));
        selector.setArcHeight(SELECTOR_ARC);
        selector.setArcWidth(SELECTOR_ARC);
        this.getChildren().addAll(selector);
    
        fadeTransition = new FadeTransition(Duration.millis(FADE_DURATION), selector);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(FADE_TO_VALUE);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
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