package com.team11.kamisado.views;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    
    private Button resumeButton;
    private Button newGameButton;
    private Button leaderboardButton;
    private Button settingsButton;
    private Button exitButton;
    
    private Button versusPlayerButton;
    private Button versusAIButton;
    private Button easyButton;
    private Button hardButton;
    private Button normalGameButton;
    private Button speedGameButton;
    private Button cancelButton;
    
    private HBox namesWrapper;
    private Label playerOneLabel;
    private TextField playerOneName;
    private TextField playerTwoName;
    private Label playerOneError;
    private Label playerTwoError;
    
    private RadioButton blackRadio;
    private RadioButton whiteRadio;
    private VBox radioWrapper;
    private VBox playerOneNameBox;
    private HBox versusAINameWrapper;
    
    private Button playButton;
    private Button backButton;
    private HBox buttonsWrapper;
    
    private Button returnToMainMenuButton;
    
    private EventHandler<InputEvent> controller;
    
    public MenuView(StackPane root, EventHandler<InputEvent> controller) {
        this.root = root;
        this.controller = controller;
        this.setId("menuView");
        Font.loadFont(getClass().getResource("/fonts/Akashi.ttf").toString(), INITIALFONTSIZE);
        
        initMenuElements();
        initPickModeScreen();
        initEnterNameScreen();
        initEnterNameVsAIScreen();
        
        drawMainMenu();
    }
    
    public void drawMainMenu() {
        this.setStyle("-fx-background-color: rgba(56, 56, 56)");
        root.getChildren().clear();
        root.getChildren().add(this);
        this.getChildren().clear();
        this.getChildren().addAll(menuGameTitle, resumeButton, newGameButton, leaderboardButton, settingsButton, exitButton);
        
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
        playerOneNameBox.getChildren().clear();
        playerOneNameBox.getChildren().addAll(playerOneLabel, playerOneName, playerOneError);
        this.getChildren().addAll(menuGameTitle, namesWrapper, buttonsWrapper);
    }
    
    public void drawSelectDifficultyScreen() {
        this.getChildren().clear();
        this.getChildren().addAll(menuGameTitle, easyButton, hardButton, cancelButton);
    }
    
    public void drawEnterNameVersusAIScreen() {
        this.getChildren().clear();
        this.getChildren().addAll(menuGameTitle, versusAINameWrapper, radioWrapper, buttonsWrapper);
        
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
    
    private void initMenuElements() {
        menuGameTitle = drawTitle();
        menuGameTitle.setId("gameTitle");
        
        resumeButton = new Button("Resume Game");
        resumeButton.getStyleClass().add("menuButton");
        resumeButton.setOnMouseClicked(controller);
        resumeButton.setOnKeyPressed(controller);
        
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
    }
    
    private void initPickModeScreen() {
        versusPlayerButton = new Button("Versus Player");
        versusPlayerButton.getStyleClass().add("menuButton");
        versusPlayerButton.setOnMouseClicked(controller);
        versusPlayerButton.setOnKeyPressed(controller);
        
        versusAIButton = new Button("Versus AI");
        versusAIButton.getStyleClass().add("menuButton");
        versusAIButton.setOnMouseClicked(controller);
        versusAIButton.setOnKeyPressed(controller);
        
        easyButton = new Button("Easy");
        easyButton.getStyleClass().add("menuButton");
        easyButton.setOnMouseClicked(controller);
        easyButton.setOnKeyPressed(controller);
        
        hardButton = new Button("Hard");
        hardButton.getStyleClass().add("menuButton");
        hardButton.setOnMouseClicked(controller);
        hardButton.setOnKeyPressed(controller);
        
        normalGameButton = new Button("Normal Game");
        normalGameButton.getStyleClass().add("menuButton");
        normalGameButton.setOnMouseClicked(controller);
        normalGameButton.setOnKeyPressed(controller);
        
        speedGameButton = new Button("Speed Game");
        speedGameButton.getStyleClass().add("menuButton");
        speedGameButton.setOnMouseClicked(controller);
        speedGameButton.setOnKeyPressed(controller);
        
        cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("menuButton");
        cancelButton.setOnMouseClicked(controller);
        cancelButton.setOnKeyPressed(controller);
    }
    
    private void initEnterNameScreen() {
        playerOneLabel = new Label("Player One [Black}");
        playerOneLabel.getStyleClass().add("nameLabel");
        
        playerOneName = new TextField();
        playerOneName.getStyleClass().add("nameField");
        playerOneName.setPromptText("Enter Name");
        playerOneName.setOnKeyPressed(controller);
        
        playerOneError = new Label();
        playerOneError.getStyleClass().add("nameError");
        
        playerOneNameBox = new VBox(playerOneLabel, playerOneName, playerOneError);
        playerOneNameBox.getStyleClass().add("nameBox");
        
        Label playerTwoLabel = new Label("Player Two [White}");
        playerTwoLabel.getStyleClass().add("nameLabel");
        
        playerTwoName = new TextField();
        playerTwoName.getStyleClass().add("nameField");
        playerTwoName.setPromptText("Enter Name");
        playerTwoName.setOnKeyPressed(controller);
        
        playerTwoError = new Label();
        playerTwoError.getStyleClass().add("nameError");
        
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
    
    private void initEnterNameVsAIScreen() {
        Label singlePlayerLabel = new Label("Player");
        singlePlayerLabel.getStyleClass().add("nameLabel");
        
        VBox singlePlayerNameBox = new VBox(singlePlayerLabel, playerOneName, playerOneError);
        singlePlayerNameBox.setId("AINameBox");
        
        versusAINameWrapper = new HBox(singlePlayerNameBox);
        versusAINameWrapper.setId("versusAINameWrapper");
        
        Label radioLabel = new Label("Select your color");
        radioLabel.getStyleClass().add("nameLabel");
        
        ToggleGroup toggleGroup = new ToggleGroup();
        
        blackRadio = new RadioButton("Black");
        blackRadio.getStyleClass().add("radioButton");
        blackRadio.setToggleGroup(toggleGroup);
        blackRadio.setOnMouseClicked(controller);
        blackRadio.setOnKeyPressed(controller);
        
        whiteRadio = new RadioButton("White");
        whiteRadio.getStyleClass().add("radioButton");
        whiteRadio.setToggleGroup(toggleGroup);
        whiteRadio.setOnMouseClicked(controller);
        whiteRadio.setOnKeyPressed(controller);
        
        HBox radioBox = new HBox(blackRadio, whiteRadio);
        radioBox.setId("radioBox");
        
        radioWrapper = new VBox(radioLabel, radioBox);
        radioWrapper.setId("radioWrapper");
    }
    
    public Button getResumeButton() {
        return resumeButton;
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
    
    public Button getVersusPlayerButton() {
        return versusPlayerButton;
    }
    
    public Button getVersusAIButton() {
        return versusAIButton;
    }
    
    public Button getEasyButton() {
        return easyButton;
    }
    
    public Button getHardButton() {
        return hardButton;
    }
    
    public Button getNormalGameButton() {
        return normalGameButton;
    }
    
    public Button getSpeedGameButton() {
        return speedGameButton;
    }
    
    public Button getCancelButton() {
        return cancelButton;
    }
    
    public TextField getPlayerOneName() {
        return playerOneName;
    }
    
    public TextField getPlayerTwoName() {
        return playerTwoName;
    }
    
    public Label getPlayerOneError() {
        return playerOneError;
    }
    
    public Label getPlayerTwoError() {
        return playerTwoError;
    }
    
    public RadioButton getBlackRadio() {
        return blackRadio;
    }
    
    public RadioButton getWhiteRadio() {
        return whiteRadio;
    }
    
    public Button getPlayButton() {
        return playButton;
    }
    
    public Button getBackButton() {
        return backButton;
    }
    
    public Button getReturnToMainMenuButton() {
        return returnToMainMenuButton;
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