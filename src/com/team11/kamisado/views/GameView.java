package com.team11.kamisado.views;

import com.team11.kamisado.models.Board;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class GameView extends BorderPane {
    private static final int SELECTORARC = 20;
    private static final int FADEDURATION = 500;
    private static final double FADETOVALUE = 0.5;
    private StackPane root;
    private SquareView selector;
    private FadeTransition fadeTransition;
    private Board board;
    private BoardPane boardPane;
    private Label messageLabel;
    private SquareView current;
    private Label namesLabel;
    private VBox sidebar;
    private Label timer;
    
    public GameView(StackPane root) {
        this.root = root;
        this.setId("gameView");
    }
    
    public void setBoard(Board board) {
        this.board = board;
    }
    
    public void switchSquareBorder(int x, int y) {
        if(current == null) {
            current = boardPane.getSquare(x, y);
            current.setStroke(Colors.TRUEWHITE.getValue());
        }
        else {
            current.setStroke(Colors.TRUEBLACK.getValue());
            current = boardPane.getSquare(x, y);
            current.setStroke(Colors.TRUEWHITE.getValue());
        }
    }
    
    public void initGameView() {
        boardPane = new BoardPane(this, board);
        boardPane.setId("boardPane");
        BorderPane.setMargin(boardPane, new Insets(BoardPane.BOARDVIEWMARGIN));
        
        selector = new SquareView(boardPane, 0, 0, Colors.TRANSPARENT);
        selector.setStroke(Colors.TRUEWHITE.getValue());
        selector.strokeWidthProperty().bind(selector.widthProperty().divide(10));
        selector.setArcHeight(SELECTORARC);
        selector.setArcWidth(SELECTORARC);
        boardPane.getChildren().addAll(selector);
        
        fadeTransition = new FadeTransition(Duration.millis(FADEDURATION), selector);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(FADETOVALUE);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
        
        namesLabel = new Label();
        namesLabel.setId("namesLabel");
        
        messageLabel = new Label();
        messageLabel.setId("message");
        messageLabel.setWrapText(true);
        timer = new Label("5");
        timer.setId("timer");
    
        sidebar = new VBox(namesLabel, messageLabel);
        sidebar.setId("sidebar");
        BorderPane.setMargin(sidebar, new Insets(BoardPane.BOARDVIEWMARGIN));
        
        this.setLeft(boardPane);
        this.setCenter(sidebar);
    }
    
    public void drawTimer(IntegerProperty timeSeconds) {
        sidebar.getChildren().add(timer);
        timer.textProperty().bind(timeSeconds.asString());
    }
    
    public void setNames(String playerOne, String playerTwo) {
        namesLabel.setText("Now playing: '" + playerOne + "' vs '" + playerTwo + "'");
    }
    
    public void drawGameView() {
        root.getChildren().clear();
        root.getChildren().add(this);
    }
    
    public void setMessage(boolean isError, String message) {
        if(isError) {
            messageLabel.setId("errorMessage");
        }
        else {
            messageLabel.setId("message");
        }
        messageLabel.setText(message);
    }
    
    public void moveSelector(int x, int y) {
        fadeTransition.stop();
        selector.moveSquare(x, y);
        fadeTransition.play();
    }
    
    public void stopFadeTransition() {
        fadeTransition.stop();
    }
}