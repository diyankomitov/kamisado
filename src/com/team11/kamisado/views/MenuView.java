package com.team11.kamisado.views;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.File;

public class MenuView extends VBox {
    
    private static final double INITIALFONTSIZE = 10;
    
    private StackPane root;
    private TextFlow menuGameTitle;
    private Button newGameButton;
    private Button leaderboardButton;
    private Button settingsButton;
    private Button exitButton;
    private Button resumeButton;
    private Button versusPlayerButton;
    private Button versusAIButton;
    private Button cancelButton;
    private HBox namesWrapper;
    private TextField playerTwoName;
    private TextField playerOneName;
    private Label playerOneError;
    private Label playerTwoError;
    private HBox buttonsWrapper;
    private Button playButton;
    private Button backButton;
    
    private EventHandler<InputEvent> controller;
    private Button returnToMainMenuButton;
    private Button normalGameButton;
    private Button speedGameButton;
    
    public MenuView(StackPane root, EventHandler<InputEvent> controller) {
        this.root = root;
        this.controller = controller;
        this.setId("menuView");
        Font.loadFont(getClass().getResource("/fonts/Akashi.ttf").toString(), INITIALFONTSIZE);
        
        initMenuElements();
        drawMainMenu();
    }
    
    private void initMenuElements() {
        /* Title */
        menuGameTitle = drawTitle();
        menuGameTitle.setId("gameTitle");
    
        /* Main menu buttons */
        newGameButton = new Button("Play New Game");
        newGameButton.getStyleClass().add("menuButton");
        newGameButton.setOnMouseClicked(controller);
        newGameButton.setOnKeyPressed(controller);
        
        leaderboardButton = new Button("View Leaderboard");
        leaderboardButton.getStyleClass().add("menuButton");
        leaderboardButton.setDisable(true); //TODO enable button
        
        settingsButton = new Button("Settings");
        settingsButton.getStyleClass().add("menuButton");
        settingsButton.setDisable(true); //TODO enable button
        
        exitButton = new Button("Exit");
        exitButton.getStyleClass().add("menuButton");
        exitButton.setOnMouseClicked(controller);
        exitButton.setOnKeyPressed(controller);
    
        /* Pick mode buttons */
        versusPlayerButton = new Button("Versus Player");
        versusPlayerButton.getStyleClass().add("menuButton");
        versusPlayerButton.setOnMouseClicked(controller);
        versusPlayerButton.setOnKeyPressed(controller);
        
        normalGameButton = new Button("Normal Game");
        normalGameButton.getStyleClass().add("menuButton");
        normalGameButton.setOnMouseClicked(controller);
        normalGameButton.setOnKeyPressed(controller);
       
        speedGameButton = new Button("Speed Game");
        speedGameButton.getStyleClass().add("menuButton");
        speedGameButton.setOnMouseClicked(controller);
        speedGameButton.setOnKeyPressed(controller);
        
        versusAIButton = new Button("Versus AI");
        versusAIButton.getStyleClass().add("menuButton");
        versusAIButton.setDisable(true); //TODO enable button
        
        cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("menuButton");
        cancelButton.setOnMouseClicked(controller);
        cancelButton.setOnKeyPressed(controller);
        
        /* Resume Game */
        resumeButton = new Button("Resume Game");
        resumeButton.getStyleClass().add("menuButton");
        resumeButton.setOnMouseClicked(controller);
        resumeButton.setOnKeyPressed(controller);
        
        /* Enter Names */
        
        Label playerOneLabel = new Label("Player One [Black}");
        playerOneLabel.getStyleClass().add("nameLabel");
        
        playerOneName = new TextField();
        playerOneName.getStyleClass().add("nameField");
        playerOneName.setPromptText("Enter Name");
        playerOneName.setOnKeyPressed(controller);
        
        playerOneError = new Label();
        playerOneError.getStyleClass().add("nameError");
        
        Label playerTwoLabel = new Label("Player Two [White}");
        playerTwoLabel.getStyleClass().add("nameLabel");
        
        playerTwoName = new TextField();
        playerTwoName.getStyleClass().add("nameField");
        playerTwoName.setPromptText("Enter Name");
        playerTwoName.setOnKeyPressed(controller);
        
        playerTwoError = new Label();
        playerTwoError.getStyleClass().add("nameError");
        
        VBox playerOneNameBox = new VBox(playerOneLabel, playerOneName, playerOneError);
        playerOneNameBox.getStyleClass().add("nameBox");
        
        VBox playerTwoNameBox = new VBox(playerTwoLabel, playerTwoName, playerTwoError);
        playerTwoNameBox.getStyleClass().add("nameBox");
        
        namesWrapper = new HBox(playerOneNameBox, playerTwoNameBox);
        namesWrapper.setId("namesWrapper");
        
        playButton = new Button("Play");
        playButton.getStyleClass().add("nameButton");
        playButton.setOnMouseClicked(controller);
        playButton.setOnKeyPressed(controller);
        
        backButton = new Button("Go Back");
        backButton.getStyleClass().add("nameButton");
        backButton.setOnMouseClicked(controller);
        backButton.setOnKeyPressed(controller);
        
        buttonsWrapper = new HBox(playButton, backButton);
        buttonsWrapper.setId("buttonsWrapper");
    }
    
    public void drawMainMenu() {
        this.setStyle("-fx-background-color: rgba(56, 56, 56)");
        root.getChildren().clear();
        root.getChildren().add(this);
        this.getChildren().clear();
        this.getChildren().addAll(menuGameTitle, resumeButton, newGameButton, leaderboardButton,
                settingsButton, exitButton);
    
        File file = new File("saves/resume.ser");
        if(!file.exists()) {
            resumeButton.setDisable(true);
        }
    }
    
    public void drawSelectModeScreen() {
        this.getChildren().clear();
        this.getChildren().addAll(menuGameTitle, versusPlayerButton, versusAIButton, cancelButton);
    }
    
    public void drawSpeedSelectScreen() {
        this.getChildren().clear();
        this.getChildren().addAll(menuGameTitle, normalGameButton, speedGameButton, cancelButton);
    }
    
    public void drawEnterNameScreen() {
        this.getChildren().clear();
        this.getChildren().addAll(menuGameTitle, namesWrapper, buttonsWrapper);
    }
    
    public void drawNameErrorMessage(Label player, String errorMessage) {
        player.setText(errorMessage);
    }
    
    public void initPauseScreen() {
        this.setStyle("-fx-background-color: rgba(56, 56, 56, 0.5)");
        root.getChildren().add(this);
        drawPauseScreen();
    }
    
    public void drawPauseScreen() {
        this.getChildren().clear();
        this.getChildren().addAll(menuGameTitle, resumeButton, newGameButton, leaderboardButton, settingsButton, exitButton);
        resumeButton.setDisable(false);
        this.resumeButton.requestFocus();
    }
    
    public void drawEndScreen(String winner) {
        Label label = new Label(winner + " wins!");
        label.setId("winMessage");
        
        returnToMainMenuButton = new Button("Return to Main Menu");
        returnToMainMenuButton.getStyleClass().add("menuButton");
        returnToMainMenuButton.setOnMouseClicked(controller);
        returnToMainMenuButton.setOnKeyPressed(controller);
        
        this.setStyle("-fx-background-color: rgba(56, 56, 56, 0.5)");
        root.getChildren().add(this);
        this.getChildren().clear();
        this.getChildren().addAll(menuGameTitle, label, returnToMainMenuButton);
        
        returnToMainMenuButton.requestFocus();
    }
    
    public Button getNewGameButton() {
        return newGameButton;
    }
    
    public Button getLeaderboardButton() {
        return leaderboardButton;
    }
    
    public Button getSettingsButton() {
        return settingsButton;
    }
    
    public Button getExitButton() {
        return exitButton;
    }
    
    public Button getResumeButton() {
        return resumeButton;
    }
    
    public Button getVersusPlayerButton() {
        return versusPlayerButton;
    }
    
    public Button getVersusAIButton() {
        return versusAIButton;
    }
    
    public Button getCancelButton() {
        return cancelButton;
    }
    
    public Button getPlayButton() {
        return playButton;
    }
    
    public Button getBackButton() {
        return backButton;
    }
    
    public TextField getPlayerTwoName() {
        return playerTwoName;
    }
    
    public TextField getPlayerOneName() {
        return playerOneName;
    }
    
    public Label getPlayerOneError() {
        return playerOneError;
    }
    
    public Label getPlayerTwoError() {
        return playerTwoError;
    }
    
    public Button getReturnToMainMenuButton() {
        return returnToMainMenuButton;
    }
    
    public Button getNormalGameButton() {
        return normalGameButton;
    }
    
    public Button getSpeedGameButton() {
        return speedGameButton;
    }
    
    private Text drawLetter(String name, Colors color) {
        Text letter = new Text(name);
        letter.setFill(color.getValue());
        letter.getStyleClass().add("titleLetter");
        return letter;
    }
    
    private TextFlow drawTitle() {
        return new TextFlow(drawLetter("K", Colors.ORANGE), drawLetter("A", Colors.NAVY), drawLetter("M", Colors.BLUE), drawLetter("I", Colors.PINK), drawLetter("S", Colors.YELLOW), drawLetter("A", Colors.RED), drawLetter("D", Colors.GREEN), drawLetter("O", Colors.BROWN));
    }
}