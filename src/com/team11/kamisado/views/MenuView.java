package com.team11.kamisado.views;

import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MenuView extends MenuViewBase {
    private StackPane root;
    private TextFlow menuGameTitle;
    
    private Button resumeButton;
    private Button newGameButton;
    private Button leaderboardButton;
    private Button settingsButton;
    private Button exitButton;
    
    private Button versusPlayerButton;
    private Button versusAIButton;
    private Button normalGameButton;
    private Button speedGameButton;
    private Button cancelButton;
    
    private HBox namesWrapper;
    private TextField playerOneName;
    private TextField playerTwoName;
    private Label playerOneError;
    private Label playerTwoError;
    
    private Button playButton;
    private Button backButton;
    private HBox buttonsWrapper;
    
    private Button returnToMainMenuButton;
    
    private List<Node> handledNodes;
    
    public MenuView(StackPane root) {
        this.root = root;
        
        this.handledNodes = new ArrayList<>();
        
        initMenuElements();
        initPickModeScreen();
        initEnterNameScreen();
        initEndScreen();
        
        drawMainMenu();
        this.setId("menuView");
        this.getStyleClass().add("transparentMenu");
    }
    
    public void drawMainMenu() {
        setTransparent(false);
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
        this.getChildren().addAll(menuGameTitle, namesWrapper, buttonsWrapper);
    }
    
    public void drawNameErrorMessage(Label player, String errorMessage) {
        player.setText(errorMessage);
    }
    
    public void initPauseScreen() {
        setTransparent(true);
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
    
        setTransparent(true);
        root.getChildren().add(this);
        this.getChildren().clear();
        this.getChildren().addAll(menuGameTitle, label, returnToMainMenuButton);
    
        returnToMainMenuButton.requestFocus();
    }
    
    private void initEndScreen() {
        returnToMainMenuButton = new Button("Return to Main Menu");
        returnToMainMenuButton.getStyleClass().add("menuButton");
        handledNodes.add(returnToMainMenuButton);
    }
    
    private void initMenuElements() {
        menuGameTitle = drawTitle();
        menuGameTitle.setId("gameTitle");
        
        resumeButton = new Button("Resume Game");
        resumeButton.getStyleClass().add("menuButton");
        handledNodes.add(resumeButton);
        
        newGameButton = new Button("Play New Game");
        newGameButton.getStyleClass().add("menuButton");
        handledNodes.add(newGameButton);
        
        leaderboardButton = new Button("View Leaderboard");
        leaderboardButton.getStyleClass().add("menuButton");
        leaderboardButton.setDisable(true); //TODO enable button
        
        settingsButton = new Button("Settings");
        settingsButton.getStyleClass().add("menuButton");
        settingsButton.setDisable(true); //TODO enable button
        
        exitButton = new Button("Exit");
        exitButton.getStyleClass().add("menuButton");
        handledNodes.add(exitButton);
    }
    
    private void initPickModeScreen() {
        versusPlayerButton = new Button("Versus Player");
        versusPlayerButton.getStyleClass().add("menuButton");
        handledNodes.add(versusPlayerButton);
        
        versusAIButton = new Button("Versus AI");
        versusAIButton.getStyleClass().add("menuButton");
        handledNodes.add(versusAIButton);
        
        normalGameButton = new Button("Normal Game");
        normalGameButton.getStyleClass().add("menuButton");
        handledNodes.add(normalGameButton);
        
        speedGameButton = new Button("Speed Game");
        speedGameButton.getStyleClass().add("menuButton");
        handledNodes.add(speedGameButton);
        
        cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("menuButton");
        handledNodes.add(cancelButton);
    }
    
    private void initEnterNameScreen() {
        Label playerOneLabel = new Label("Player One [Black}");
        playerOneLabel.getStyleClass().add("nameLabel");
        
        playerOneName = new TextField();
        playerOneName.getStyleClass().add("nameField");
        playerOneName.setPromptText("Enter Name");
        handledNodes.add(playerOneName);
        
        playerOneError = new Label();
        playerOneError.getStyleClass().add("nameError");
        
        VBox playerOneNameBox = new VBox(playerOneLabel, playerOneName, playerOneError);
        playerOneNameBox.getStyleClass().add("nameBox");
        
        Label playerTwoLabel = new Label("Player Two [White}");
        playerTwoLabel.getStyleClass().add("nameLabel");
        
        playerTwoName = new TextField();
        playerTwoName.getStyleClass().add("nameField");
        playerTwoName.setPromptText("Enter Name");
        handledNodes.add(playerTwoName);
        
        playerTwoError = new Label();
        playerTwoError.getStyleClass().add("nameError");
        
        VBox playerTwoNameBox = new VBox(playerTwoLabel, playerTwoName, playerTwoError);
        playerTwoNameBox.getStyleClass().add("nameBox");
        
        namesWrapper = new HBox(playerOneNameBox, playerTwoNameBox);
        namesWrapper.setId("namesWrapper");
        
        playButton = new Button("Play");
        playButton.getStyleClass().add("nameButton");
        handledNodes.add(playButton);
        
        backButton = new Button("Go Back");
        backButton.getStyleClass().add("nameButton");
        handledNodes.add(backButton);
        
        buttonsWrapper = new HBox(playButton, backButton);
        buttonsWrapper.setId("buttonsWrapper");
    }
    
    public List<Node> getHandledNodes() {
        return handledNodes;
    }
    
    public TextFlow getMenuGameTitle() {
        return menuGameTitle;
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
    
    public Button getPlayButton() {
        return playButton;
    }
    
    public Button getBackButton() {
        return backButton;
    }
    
    public Button getReturnToMainMenuButton() {
        return returnToMainMenuButton;
    }
    
    public HBox getButtonsWrapper() {
        return buttonsWrapper;
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
