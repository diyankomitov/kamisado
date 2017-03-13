package develop.views;

import develop.models.Board;
import develop.controllers.GameController;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import static develop.views.Colors.TRANSPARENT;
import static develop.views.Colors.TRUEWHITE;

/**
 * Created by Diyan on 11/03/2017.
 */
public class GameView extends BorderPane{
    private static final double BOARDVIEWMARGIN = 10;
    private GameController controller;
    
    private StackPane root;
    private SquareView selector;
    private FadeTransition fadeTransition;
    private Board board;
    private BoardPane boardPane;
    
    public GameView(StackPane root, GameController gameController) {
        this.root = root;
        this.setId("gameView");
        this.controller = gameController;
        this.board = gameController.getBoard();
        initGameView();
    }
    
    public void initGameView() {
        boardPane = new BoardPane(board);
        boardPane.setId("boardPane");
        boardPane.prefHeightProperty().bind(this.heightProperty().subtract(BOARDVIEWMARGIN*2));
        boardPane.prefWidthProperty().bind(boardPane.prefHeightProperty());
        BorderPane.setMargin(boardPane,new Insets(BOARDVIEWMARGIN));
    
        selector = new SquareView(boardPane, 0, 0, TRANSPARENT);
        selector.setStroke(TRUEWHITE.getValue());
        selector.strokeWidthProperty().bind(selector.widthProperty().divide(10));
        selector.setArcHeight(20);
        selector.setArcWidth(20);
        boardPane.getChildren().addAll(selector);
    
        fadeTransition = new FadeTransition(Duration.millis(500), selector);
        fadeTransition.setFromValue(0.5);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
        
        
        VBox sidebar = new VBox();
        sidebar.setId("sidebar");
        
        Label player1 = new Label("Player 1");
        Label player2 = new Label("Player 2");
        sidebar.getChildren().addAll(player1,player2);
        
        this.setLeft(boardPane);
        this.setCenter(sidebar);
    }
    
    public void drawGameView() {
        root.getChildren().clear();
        root.getChildren().add(this);
    }
    
    
    public SquareView getSelector() {
        return selector;
    }
    
    public void moveSelector(int x, int y) {
        fadeTransition.stop();
        selector.moveSquare(x,y);
        fadeTransition.play();
    }
    
    public BoardPane getBoardPane() {
        return boardPane;
    }
}
