package com.team11.kamisado.controllers;

import com.team11.kamisado.AI.AIPlayer;
import com.team11.kamisado.main.KamisadoApp;
import com.team11.kamisado.models.Board;
import com.team11.kamisado.models.Player;
import com.team11.kamisado.util.SaveManager;
import com.team11.kamisado.views.GameView;
import com.team11.kamisado.views.MenuView;
import com.team11.kamisado.views.MenuViewBase;
import com.team11.kamisado.views.SinglePlayerMenuView;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.Stack;

import static javafx.scene.input.KeyCode.ENTER;

public class MenuController implements EventHandler<InputEvent> {
    
    private StackPane root;
    private MenuViewBase view;
    private MenuView menuView;
    private SinglePlayerMenuView singlePlayerMenuView;
    private KamisadoApp application;
    private GameController gameController;
    private boolean isPaused;
    private String pTwoName;
    private String pOneName;
    private boolean isSpeed;
    private boolean gameInProgress;
    private boolean AIGame;
    private boolean onlineGame;
    private int difficulty;
    
    public MenuController(KamisadoApp application, MenuView menuView) {
        this.application = application;
        this.root = application.getRoot();
        this.isPaused = false;
        this.isSpeed = false;
        this.AIGame = false;
        this.onlineGame = false;
        
        this.menuView = menuView;
        for(Node node : this.menuView.getHandledNodes()) {
            node.setOnKeyPressed(this);
            node.setOnMouseClicked(this);
        }
        this.view = menuView;
        
        this.singlePlayerMenuView = new SinglePlayerMenuView();
        for(Node node : singlePlayerMenuView.getHandledNodes()) {
            node.setOnKeyPressed(this);
            node.setOnMouseClicked(this);
        }
    }
    
    @Override
    public void handle(InputEvent event) {
        final Object source = event.getSource();
        
        boolean pressed = false;
        boolean clicked = false;
        
        if(event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            clicked = true;
        }
        else if(((KeyEvent) event).getCode() == ENTER) {
            pressed = true;
        }
        
        if(clicked || pressed) {
            if(source == menuView.getNewGameButton()) {
                menuView.drawSelectModeScreen();
            }
            else if(source == menuView.getExitButton()) {
                this.exitGame();
            }
            else if(source == menuView.getVersusPlayerButton()) {
                AIGame = false;
                menuView.drawMultiplayerModeScreen();
                view = menuView;
            }
            else if(source == menuView.getOnlineButton()) {
                onlineGame = true;
                menuView.drawOnlineScreen();
                menuView.getHostButton().setSelected(false);
                view.getBlackRadio().setDisable(true);
                view.getWhiteRadio().setDisable(true);
            }
            else if(source == menuView.getHostButton()) {
                boolean isSelected = menuView.getHostButton().isSelected();
                menuView.getHostButton().setSelected(!isSelected);
                view.getBlackRadio().setDisable(isSelected);
                view.getWhiteRadio().setDisable(isSelected);
            }
            else if(source == menuView.getLocalButton()) {
                menuView.drawEnterNameScreen();
            }
            else if(source == menuView.getVersusAIButton()) {
                view.getBlackRadio().setDisable(true);
                view.getWhiteRadio().setDisable(true);
                AIGame = true;
                singlePlayerMenuView.setTransparent(isPaused);
                root.getChildren().remove(menuView);
                root.getChildren().add(singlePlayerMenuView);
                singlePlayerMenuView.drawSinglePlayerScreen();
                view = singlePlayerMenuView;
            }
            else if(source == view.getSinglePlayerName()) {
                if(view.getSinglePlayerName().getText().equals("")) {
                    view.getSinglePlayerError().setText("Please enter a player name");
                }
                else {
                    view.getSinglePlayerError().setText("");
                    if(view == singlePlayerMenuView) {
                        singlePlayerMenuView.getEasyRadio().requestFocus();
                    }
                    else {
                        menuView.getHostButton().requestFocus();
                    }
                }
            }
            else if(source == view.getBlackRadio()) {
                if(view == singlePlayerMenuView) {
                    singlePlayerMenuView.getEasyRadio().requestFocus();
                }
                else {
                    view.getBlackRadio().requestFocus();
                }
            }
            else if(source == view.getWhiteRadio()) {
                if(view == singlePlayerMenuView) {
                    singlePlayerMenuView.getEasyRadio().requestFocus();
                }
                else {
                    view.getBlackRadio().requestFocus();
                }
            }
            else if(source == singlePlayerMenuView.getEasyRadio()) {
                singlePlayerMenuView.getNormalRadio().requestFocus();
            }
            else if(source == singlePlayerMenuView.getHardRadio()) {
                singlePlayerMenuView.getNormalRadio().requestFocus();
            }
            else if(source == view.getNormalRadio()) {
                view.getPlayButton().requestFocus();
            }
            else if(source == view.getSpeedRadio()) {
                view.getPlayButton().requestFocus();
            }
            else if(source == view.getPlayButton()) {
                this.onPlayButton();
            }
            else if(source == view.getBackButton()) {
                if(view == singlePlayerMenuView) {
                    menuView.drawSelectModeScreen();
                    root.getChildren().remove(singlePlayerMenuView);
                }
                else {
                    menuView.drawMultiplayerModeScreen();
                    root.getChildren().remove(menuView);
                }
                
                if(!isPaused) {
                    root.getChildren().clear();
                }
                root.getChildren().add(menuView);
                menuView.setTransparent(isPaused);
                view = menuView;
            }
            else if(source == menuView.getCancelButton()) {
                menuView.drawMainMenu();
            }
            else if(source == menuView.getPlayerOneName()) {
                this.onPlayerOneName();
            }
            else if(source == menuView.getPlayerTwoName()) {
                this.onPlayerTwoName();
            }
            else if(source == menuView.getResumeButton()) {
                this.onResume();
            }
            else if(source == menuView.getReturnToMainMenuButton()) {
                menuView.setTransparent(false);
                root.getChildren().clear();
                root.getChildren().add(menuView);
                menuView.drawMainMenu();
            }
        }
    }
    
    private void onResume() {
        if(gameInProgress) {
            gameController.setActiveController();
        }
        else {
            Board board = (Board) SaveManager.loadFromFile().get(0);
            isSpeed = (boolean) SaveManager.loadFromFile().get(1);
            Stack<Board> stack = (Stack<Board>) SaveManager.loadFromFile().get(3);
            
            GameView gameView = new GameView(root);
            
            if(isSpeed) {
                gameController = new SpeedGameController(this, gameView, board, (Integer)SaveManager.loadFromFile().get(2));
            }
            else {
                gameController = new GameController(this, gameView, board);
            }
            gameController.setActiveController();
            gameInProgress = true;
            gameController.setStack(stack);
        }
        isPaused = false;
    }
    
    public void undo(Board board, Stack<Board> boardStack) {
        if(board.getCurrentPlayer().getPlayerColor().equals("B")) {
            board.switchCurrentPlayer();
        }
        GameView gameView = new GameView(root);
        gameController = null;
        gameController = new GameController(this, gameView, board);
        gameController.setActiveController();
        gameInProgress = true;
        gameController.setStack(boardStack);
        gameView.setMessage(false, "You undid a move.\nYou can now move again");
    }
    
    public void win(String winner) {
        SaveManager.deleteFile();
        gameInProgress = false;
        menuView.drawEndScreen(winner);
    }
    
    public void pause() {
        isPaused = true;
        menuView.setTransparent(true);
        menuView.drawMainMenu();
        root.getChildren().add(menuView);
        menuView.getResumeButton().setDisable(false);
        menuView.getResumeButton().requestFocus();
    }
    
    private void onPlayButton() {
        Player playerOne = null;
        Player playerTwo = null;
        
        
        
        if(AIGame) {
            if(singlePlayerMenuView.getEasyRadio().isSelected()) {
                difficulty = 0;
            }
            else if(singlePlayerMenuView.getHardRadio().isSelected()) {
                difficulty = 1;
            }
            
            if(singlePlayerMenuView.getBlackRadio().isSelected()) {
                playerOne = new Player(singlePlayerMenuView.getSinglePlayerName().getText(), "B");
                playerTwo = new AIPlayer("AI", "W", difficulty);
            }
            else if(singlePlayerMenuView.getWhiteRadio().isSelected()) {
                playerOne = new AIPlayer("AI", "B", difficulty);
                playerTwo = new Player(singlePlayerMenuView.getSinglePlayerName().getText(), "W");
            }
        }
        else {
            playerOne = new Player(menuView.getPlayerOneName().getText(), "B");
            playerTwo = new Player(menuView.getPlayerTwoName().getText(), "W");
        }
    
        if(view.getNormalRadio().isSelected()) {
            isSpeed = false;
        }
        else if(view.getSpeedRadio().isSelected()) {
            isSpeed = true;
        }
        
        Board board = new Board(playerOne, playerTwo);
        GameView gameView = new GameView(root);
        
        if(isSpeed) {
            gameController = new SpeedGameController(this, gameView, board, 5);
        }
        else {
            gameController = new GameController(this, gameView, board);
        }
        gameController.setActiveController();
        gameInProgress = true;
    }
    
    private void onPlayerOneName() {
        pOneName = menuView.getPlayerOneName().getText().trim();
        if(pOneName.equals("")) {
            menuView.drawNameErrorMessage(menuView.getPlayerOneError(), "Please enter a player name");
        }
        else if(pOneName.equals(pTwoName)) {
            menuView.drawNameErrorMessage(menuView.getPlayerOneError(), "Please enter a different name than Player Two"); //TODO: don't allow play if names entered wrong
        }
        else {
            menuView.getPlayerTwoName().requestFocus();
            menuView.drawNameErrorMessage(menuView.getPlayerOneError(), "");
        }
    }
    
    private void onPlayerTwoName() {
        pTwoName = menuView.getPlayerTwoName().getText().trim();
        if(pTwoName.equals("")) {
            menuView.drawNameErrorMessage(menuView.getPlayerTwoError(), "Please enter a player name");
        }
        else if(pTwoName.equals(pOneName)) {
            menuView.drawNameErrorMessage(menuView.getPlayerTwoError(), "Please enter a different name than Player One");
        }
        else {
            view.getNormalRadio().requestFocus();
            menuView.drawNameErrorMessage(menuView.getPlayerTwoError(), "");
        }
    }
    
    private void exitGame() {
        if(gameInProgress) {
            
            Board board = gameController.getBoard();
            int time = gameController instanceof SpeedGameController ?
                    ((SpeedGameController) gameController).getCurrentTime() : 0;
            
            SaveManager.saveToFile(board, isSpeed, time, gameController.getBoardStack());
        }
        application.getStage().close();
    }
}
