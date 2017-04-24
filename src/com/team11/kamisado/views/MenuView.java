package com.team11.kamisado.views;

import com.team11.kamisado.util.SaveManager;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class MenuView extends MenuViewBase {
    private StackPane root;
    
    private Button resumeButton;
    private Button newGameButton;
    private Button leaderboardButton;
    private Button settingsButton;
    private Button exitButton;
    
    private Button versusPlayerButton;
    private Button versusAIButton;
    private Button cancelButton;
    
    private HBox namesWrapper;
    private TextField playerOneName;
    private TextField playerTwoName;
    private Label playerOneError;
    private Label playerTwoError;
    
    private Button returnToMainMenuButton;
    
    private List<Node> handledNodes;
    private Button onlineButton;
    private Button localButton;
    private CheckBox hostButton;
    private Button continueMatchButton;
    private RadioButton oneRadio;
    private RadioButton threeRadio;
    private RadioButton sevenRadio;
    private RadioButton fifteenRadio;
    private VBox pointsRadioWrapper;
    
    public MenuView(StackPane root) {
        super();
        this.root = root;
        
        this.handledNodes = getHandledNodes();
        
        initMenuElements();
        initPickModeScreen();
        initMultplayerModeScreen();
        initOnlineScreen();
        initEnterNameScreen();
        initEndScreen();
        
        root.getChildren().add(this);
        drawMainMenu();
        this.setId("menuView");
        this.getStyleClass().add("transparentMenu");
    }
    
    public void drawMainMenu() {
        this.getChildren().clear();
        this.getChildren().addAll(getMenuGameTitle(), resumeButton, newGameButton, leaderboardButton, settingsButton, exitButton);
        
        if(!SaveManager.fileExists()) {
            resumeButton.setDisable(true);
        }
        else {
            resumeButton.setDisable(false);
        }
    }
    
    public void drawSelectModeScreen() {
        this.getChildren().clear();
        this.getChildren().addAll(getMenuGameTitle(), versusPlayerButton, versusAIButton, cancelButton);
    }
    
    public void drawMultiplayerModeScreen() {
        this.getChildren().clear();
        this.getChildren().addAll(getMenuGameTitle(), onlineButton, localButton, cancelButton);
    }
    
    public void drawOnlineScreen() {
        this.getChildren().clear();
        this.getChildren().addAll(getMenuGameTitle(), getSinglePlayerNameWrapper(), hostButton, getColorRadioWrapper(), getModeRadioWrapper(), getButtonsWrapper());
    }
    
    public void drawEnterNameScreen() {
        this.getChildren().clear();
        this.getChildren().addAll(getMenuGameTitle(), namesWrapper, getModeRadioWrapper(), getRandomButton(), pointsRadioWrapper, getButtonsWrapper());
    }
    
    public void drawNameErrorMessage(Label player, String errorMessage) {
        player.setText(errorMessage);
    }
    
    public void drawEndScreen(String winner, boolean matchOver) {
        Label label = new Label(winner + " wins!");
        label.setId("winMessage");
    
        setTransparent(true);
        root.getChildren().add(this);
        this.getChildren().clear();
        if(matchOver) {
            this.getChildren().addAll(getMenuGameTitle(), label, returnToMainMenuButton);
            returnToMainMenuButton.requestFocus();
        }
        else {
            this.getChildren().addAll(getMenuGameTitle(), label, continueMatchButton, returnToMainMenuButton);
    
            continueMatchButton.requestFocus();
        }
    }
    
    private void initEndScreen() {
        returnToMainMenuButton = new Button("Return to Main Menu");
        returnToMainMenuButton.getStyleClass().add("menuButton");
        handledNodes.add(returnToMainMenuButton);
    
        continueMatchButton = new Button("Continue Match");
        continueMatchButton.getStyleClass().add("menuButton");
        handledNodes.add(continueMatchButton);
    }
    
    private void initMenuElements() {
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
        
        cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("menuButton");
        handledNodes.add(cancelButton);
    }
    
    private void initMultplayerModeScreen() {
        onlineButton = new Button("Play Online");
        onlineButton.getStyleClass().add("menuButton");
        handledNodes.add(onlineButton);
        
        localButton = new Button("Play on this Computer");
        localButton.getStyleClass().add("menuButton");
        handledNodes.add(localButton);
    }
    
    private void initOnlineScreen() {
        hostButton = new CheckBox("Host a game");
        hostButton.getStyleClass().add("checkButton");
        handledNodes.add(hostButton);
        
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
    
        Label pointsRadioLabel = new Label("Select the max score");
        pointsRadioLabel.getStyleClass().add("nameLabel");
        ToggleGroup pointsToggleGroup = new ToggleGroup();
        oneRadio = new RadioButton("1");
        oneRadio.getStyleClass().add("radioButton");
        oneRadio.setToggleGroup(pointsToggleGroup);
        oneRadio.setSelected(true);
        handledNodes.add(oneRadio);
        threeRadio = new RadioButton("3");
        threeRadio.getStyleClass().add("radioButton");
        threeRadio.setToggleGroup(pointsToggleGroup);
        handledNodes.add(threeRadio);
        sevenRadio = new RadioButton("7");
        sevenRadio.getStyleClass().add("radioButton");
        sevenRadio.setToggleGroup(pointsToggleGroup);
        handledNodes.add(sevenRadio);
        fifteenRadio = new RadioButton("15");
        fifteenRadio.getStyleClass().add("radioButton");
        fifteenRadio.setToggleGroup(pointsToggleGroup);
        handledNodes.add(fifteenRadio);
        HBox pointsRadioBox = new HBox(oneRadio, threeRadio, sevenRadio, fifteenRadio);
        pointsRadioBox.getStyleClass().add("radioBox");
        pointsRadioWrapper = new VBox(pointsRadioLabel, pointsRadioBox);
        pointsRadioWrapper.getStyleClass().add("radioWrapper");
        pointsRadioWrapper.setId("pointsRadioWrapper");
        
    }
    
    public RadioButton getOneRadio() {
        return oneRadio;
    }
    
    public RadioButton getThreeRadio() {
        return threeRadio;
    }
    
    public RadioButton getSevenRadio() {
        return sevenRadio;
    }
    
    public RadioButton getFifteenRadio() {
        return fifteenRadio;
    }
    
    public VBox getPointsRadioWrapper() {
        return pointsRadioWrapper;
    }
    
    public CheckBox getHostButton() {
        return hostButton;
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
    
    public Button getOnlineButton() {
        return onlineButton;
    }
    
    public Button getLocalButton() {
        return localButton;
    }
    
    public Button getVersusAIButton() {
        return versusAIButton;
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
    
    public Button getReturnToMainMenuButton() {
        return returnToMainMenuButton;
    }
    
    public Button getContinueMatchButton() {
        return continueMatchButton;
    }
}
