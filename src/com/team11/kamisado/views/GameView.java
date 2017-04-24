package com.team11.kamisado.views;

import javafx.beans.property.IntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GameView extends BorderPane {
    private StackPane root;
    private BoardPane boardPane;
    private Label messageLabel;
    private Label namesLabel;
    private VBox sidebar;
    private Label timer;
    private Button rightButton;
    private Button leftButton;
    
    public GameView(StackPane root) {
        this.root = root;
        this.setId("gameView");
    }
    
    public void initGameView() {
        boardPane = new BoardPane(this);
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
    
        sidebar = new VBox();
        sidebar.setId("sidebar");
        drawSideBar();
        BorderPane.setMargin(sidebar, new Insets(BoardPane.BOARD_VIEW_MARGIN));
        
        this.setLeft(boardPane);
        this.setCenter(sidebar);
    }
    
    public void drawOrderButtons() {
        leftButton = new Button("Fill from the left");
        leftButton.getStyleClass().add("menuButton");
        rightButton = new Button("Fill from the right");
        rightButton.getStyleClass().add("menuButton");
        
        sidebar.getChildren().clear();
        sidebar.getChildren().addAll(namesLabel, messageLabel, leftButton, rightButton);
    }
    
    public void drawSideBar() {
        sidebar.getChildren().clear();
        sidebar.getChildren().addAll(namesLabel, messageLabel);
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
    
    public BoardPane getBoardPane() {
        return boardPane;
    }
    
    public Button getLeftButton() {
        return leftButton;
    }
    
    public Button getRightButton() {
        return rightButton;
    }
    
    public VBox getSidebar() {
        return sidebar;
    }
}
