package develop;

import develop.views.BoardView;
import develop.views.SquareView;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


import static develop.views.Colors.*;

public class GameDriver extends AnimationTimer {
    
    private final Board board;
    private final Scene scene;
    private final BoardView boardView;
    private FadeTransition ft;
    private boolean selected = false;
    private SquareView selector;
    private int y;
    private int x;
    
    public GameDriver(Scene scene, BoardView boardView, Board board) {
        this.scene = scene;
        this.boardView = boardView;
        this.board = board;
    
        selector = new SquareView(boardView, 0, 0, TRANSPARENT);
        selector.setStroke(TRUEWHITE.getValue());
        selector.strokeWidthProperty().bind(selector.widthProperty().divide(10));
        selector.setArcHeight(20);
        selector.setArcWidth(20);
        boardView.getChildren().addAll(selector);
        
        ft = new FadeTransition(Duration.millis(500), selector);
        ft.setFromValue(0.5);
        ft.setToValue(0.0);
        ft.setCycleCount(Animation.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
    }
    
    
    @Override
    public void handle(long now) {
        
        scene.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case UP:
                    y--;
                    if(y < 0) {
                        y = 7;
                    }
                    selector.moveSquare(x, y);
                    //restartFadeAnimation();
                    
                    break;
                case DOWN:
                    y++;
                    if(y > 7) {
                        y = 0;
                    }
                    selector.moveSquare(x, y);
                    //restartFadeAnimation();
                    break;
                case LEFT:
                    x--;
                    if(x < 0) {
                        x = 7;
                    }
                    selector.moveSquare(x, y);
                    //restartFadeAnimation();
                    break;
                case RIGHT:
                    x++;
                    if(x > 7) {
                        x = 0;
                    }
                    selector.moveSquare(x, y);
                    //restartFadeAnimation();
                    break;
                case ENTER:
                    if(selected) {
                        if(board.isValidCoordinate(x, y)) {
                            board.move(x, y);
                            board.switchCurrentPlayer();
                            board.setCurrentSquare(x, y);
                            board.setCurrentTower();
                            board.setValidCoordinates();
                            x = board.getCurrentCoordinates().getX();
                            y = board.getCurrentCoordinates().getY();
                            selector.moveSquare(x, y);
                        }
                        else {
                            System.out.println("Not a valid move");
                        }
                    }
                    else {
                        if(board.isTower(x, y)) {
                            board.setCurrentSquare(x, y);
                            board.setCurrentTower();
                            board.setValidCoordinates();
                            selected = true;
                        }
                        else {
                            System.out.println("Not a tower");
                        }
                        
                    }
                    break;
            }
        });
        
        this.stop();
    }
    
    private void restartFadeAnimation() {
        ft.stop();
        ft.play();
    }
}
