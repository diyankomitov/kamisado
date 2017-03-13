package develop.views;

import develop.views.Colors;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import static develop.views.Colors.*;

public class MenuView extends VBox{
    
    private static final double INITIALFONTSIZE = 10;
    
    private Stage stage;
    private StackPane root;
    private TextFlow menuGameTitle;
    private Button newGameButton;
    private Button leaderboardButton;
    private Button settingsButton;
    private Button exitButton;
    private Button resumeButton;
    private Button singlePlayerButton;
    private Button multiPlayerButton;
    private Button cancelButton;
    private EventHandler<InputEvent> controller;
    private HBox namesWrapper;
    private HBox buttonsWrapper;
    private Button playButton;
    private Button backButton;
    private TextField playerTwoName;
    private TextField playerOneName;
    
    public MenuView(Stage stage, StackPane root, EventHandler<InputEvent> controller) {
        this.setId("menuView");
        this.stage = stage;
        this.root = root;
        this.controller = controller;
        Font.loadFont(getClass().getResource("/fonts/Akashi.ttf").toExternalForm(), INITIALFONTSIZE);
        
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
        singlePlayerButton = new Button("Singleplayer");
        singlePlayerButton.getStyleClass().add("menuButton");
        singlePlayerButton.setOnMouseClicked(controller);
        singlePlayerButton.setOnKeyPressed(controller);
        
        multiPlayerButton = new Button("Multiplayer");
        multiPlayerButton.getStyleClass().add("menuButton");
        multiPlayerButton.setDisable(true); //TODO enable button
        
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
    
        Label playerTwoLabel = new Label("Player Two [White}");
        playerTwoLabel.getStyleClass().add("nameLabel");
    
        playerTwoName = new TextField();
        playerTwoName.getStyleClass().add("nameField");
        playerTwoName.setPromptText("Enter Name");
        playerTwoName.setOnKeyPressed(controller);
    
        VBox playerOneNameBox = new VBox(playerOneLabel, playerOneName);
        playerOneNameBox.getStyleClass().add("nameBox");
        
        VBox playerTwoNameBox = new VBox(playerTwoLabel, playerTwoName);
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
        root.getChildren().clear();
        root.getChildren().add(this);
        this.getChildren().clear();
        this.getChildren().addAll(menuGameTitle, newGameButton, leaderboardButton, settingsButton,
                exitButton);
    }
    
    public void drawSelectModeScreen() {
//        root.getChildren().clear();
//        root.getChildren().add(this);
        this.getChildren().clear();
        this.getChildren().addAll(menuGameTitle, singlePlayerButton, multiPlayerButton,
                cancelButton);
    }
    
    public void drawEnterNameScreen() {
        this.getChildren().clear();
        this.getChildren().addAll(menuGameTitle, namesWrapper, buttonsWrapper);
    }
    
    public void drawPauseScreen() {
        root.getChildren().clear();
        root.getChildren().add(this);
        this.getChildren().clear();
        this.getChildren().addAll(menuGameTitle, resumeButton, newGameButton, leaderboardButton,
                settingsButton, exitButton);
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
    
    public Button getSinglePlayerButton() {
        return singlePlayerButton;
    }
    
    public Button getMultiPlayerButton() {
        return multiPlayerButton;
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
    
    private Text drawLetter(String name, Colors color) {
        Text letter = new Text(name);
        letter.setFill(color.getValue());
        letter.getStyleClass().add("titleLetter");
        return letter;
    }
    
    private TextFlow drawTitle() {
        return new TextFlow(
                drawLetter("K", ORANGE),
                drawLetter("A", NAVY),
                drawLetter("M", BLUE),
                drawLetter("I", PINK),
                drawLetter("S", YELLOW),
                drawLetter("A", RED),
                drawLetter("D", GREEN),
                drawLetter("O", BROWN));
    }
    
    public void exit() {
        stage.close();
    }
}