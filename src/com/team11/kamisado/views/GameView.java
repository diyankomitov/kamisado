package com.team11.kamisado.views;

import com.team11.kamisado.models.Board;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GameView extends BorderPane {
    private StackPane root;
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
    
    public void initGameView() {
        boardPane = new BoardPane(this, board);
        boardPane.setId("boardPane");
        BorderPane.setMargin(boardPane, new Insets(BoardPane.BOARD_VIEW_MARGIN));
        
        namesLabel = new Label();
        namesLabel.setId("namesLabel");
        namesLabel.setWrapText(true);
        
        messageLabel = new Label();
        messageLabel.setId("message");
        messageLabel.setWrapText(true);
        timer = new Label("5");
        timer.setId("timer");
    
        sidebar = new VBox(namesLabel, messageLabel);
        sidebar.setId("sidebar");
        BorderPane.setMargin(sidebar, new Insets(BoardPane.BOARD_VIEW_MARGIN));
        
        this.setLeft(boardPane);
        this.setCenter(sidebar);
    }
    
    public void drawGameView() {
        root.getChildren().clear();
        root.getChildren().add(this);
    }
    
    public void drawTimer(IntegerProperty timeSeconds) {
        sidebar.getChildren().add(timer);
        timer.textProperty().bind(timeSeconds.asString());
    }
    
    public void setNames(String playerOne, String playerTwo) {
        namesLabel.setText("Now playing: '" + playerOne + "' vs '" + playerTwo + "'");
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
    
    public void setCurrent(int x, int y) {
        if(current == null) {
            current = boardPane.getSquare(x, y);
            current.setStroke(Colors.TRUEWHITE.getValue());
            boardPane.moveSelector(x,y);
        }
        else {
            current.setStroke(Colors.TRUEBLACK.getValue());
            current = boardPane.getSquare(x, y);
            current.setStroke(Colors.TRUEWHITE.getValue());
            boardPane.moveSelector(x,y);
        }
    }
    
    public void setBoard(Board board) {
        this.board = board;
    }
    
    public BoardPane getBoardPane() {
        return boardPane;
    }
}