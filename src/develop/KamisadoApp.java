package develop;

import develop.views.BoardView;
import develop.views.Colors;
import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import static develop.views.Colors.*;

public class KamisadoApp extends Application {
    
    private static final double BOARDVIEWMARGIN = 10;
    private static final double INITIALFONTSIZE = 10;
    
    private Stage stage;
    private Scene scene;
    private Board board;
    private VBox menuRoot;
    private Button multiPlayerButton;
    private Button cancelButton;
    private Button singlePlayerButton;
    private Button exitButton;
    private Button settingsButton;
    private Button leaderboardButton;
    private Button playButton;
    private TextFlow menuGameTitle;
    private GameDriver gameDriver;
    private BorderPane gameRoot;
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Font.loadFont(getClass().getResource("/fonts/Akashi.ttf").toExternalForm(), INITIALFONTSIZE);
        
        drawWindow(primaryStage);
    }
    
    private void drawWindow(Stage primaryStage) {
        stage = primaryStage;
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        
        initMenuElements();
        drawMainMenu();
        
        /* Set up the stage and playGameScene */
        stage.setScene(scene);
        stage.setTitle("Kamisado");
        stage.setOnCloseRequest(event -> stage.close());
        stage.show();
    }
    
    private void initMenuElements() {
        menuRoot = new VBox();
        menuRoot.setId("menuRoot");
        scene = new Scene(menuRoot);
        scene.getStylesheets().add("/develop/AppStyle.css");
        
        /* Title */
        menuGameTitle = drawTitle();
        menuGameTitle.setId("gameTitle");
    
        /* Main menu buttons */
        playButton = new Button("Play Game");
        playButton.getStyleClass().add("menuButton");
        playButton.setOnAction(event -> drawSelectModeScreen());
        playButton.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                drawSelectModeScreen();
            }
        });
    
        leaderboardButton = new Button("View Leaderboard");
        leaderboardButton.getStyleClass().add("menuButton");
    
        settingsButton = new Button("Settings");
        settingsButton.getStyleClass().add("menuButton");
        
        exitButton = new Button("Exit");
        exitButton.getStyleClass().add("menuButton");
        exitButton.setOnAction(event -> quit());
        exitButton.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                quit();
            }
        });
    
        /* Pick mode buttons */
        singlePlayerButton = new Button("Singleplayer");
        singlePlayerButton.getStyleClass().add("menuButton");
        singlePlayerButton.setOnAction(event -> drawGameScreen());
        singlePlayerButton.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                drawGameScreen();
            }
        });
    
        multiPlayerButton = new Button("Multiplayer");
        multiPlayerButton.getStyleClass().add("menuButton");
        
        cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("menuButton");
        cancelButton.setOnAction(event -> drawMainMenu());
        cancelButton.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                drawMainMenu();
            }
        });
    }
    
    private void drawMainMenu() {
        menuRoot.getChildren().clear();
        menuRoot.getChildren().addAll(menuGameTitle, playButton, leaderboardButton,
                settingsButton,
                exitButton);
    }
    
    private void drawSelectModeScreen() {
        menuRoot.getChildren().clear();
        menuRoot.getChildren().addAll(menuGameTitle, singlePlayerButton, multiPlayerButton,
                cancelButton);
    }
    
    private void drawGameScreen() {
        gameRoot = new BorderPane();
        gameRoot.setId("gameRoot");
        scene.setRoot(gameRoot);
    
        board = new Board();
        BoardView boardView = new BoardView(board);
        boardView.setId("boardView");
        boardView.prefHeightProperty().bind(gameRoot.heightProperty().subtract(BOARDVIEWMARGIN*2));
        boardView.prefWidthProperty().bind(boardView.prefHeightProperty());
        BorderPane.setMargin(boardView,new Insets(BOARDVIEWMARGIN));
        
        VBox sidebar = new VBox();
        Label player1 = new Label("Player 1");
        Label player2 = new Label("Player 2");
        Button quitButton = new Button("Quit Game");
        quitButton.setFocusTraversable(false);
        quitButton.setId("quitButton");
        quitButton.setOnAction(event -> {
            scene.setRoot(menuRoot);
            drawMainMenu();
        });
        quitButton.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                scene.setRoot(menuRoot);
                drawMainMenu();
            }
        });
        sidebar.getChildren().addAll(player1,player2,quitButton);
        sidebar.setId("sidebar");
        
        gameRoot.setLeft(boardView);
        gameRoot.setCenter(sidebar);
        
        gameDriver= new GameDriver(scene, boardView, board);
        gameDriver.start();
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
    
    private void quit() {
        stage.close();
    }
}