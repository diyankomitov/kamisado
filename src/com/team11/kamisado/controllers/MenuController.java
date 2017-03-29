package com.team11.kamisado.controllers;

import com.team11.kamisado.AI.AIPlayer;
import com.team11.kamisado.main.KamisadoApp;
import com.team11.kamisado.models.Board;
import com.team11.kamisado.models.Player;
import com.team11.kamisado.util.SaveManager;
import com.team11.kamisado.views.GameView;
import com.team11.kamisado.views.MenuView;
import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.util.Stack;

import static javafx.scene.input.KeyCode.ENTER;

public class MenuController implements EventHandler<InputEvent> {
    
    private MenuView view;
    private KamisadoApp application;
    private GameController gameController;
    private boolean isPaused;
    private String pTwoName;
    private String pOneName;
    private boolean isSpeed;
    private boolean gameInProgress;
    private boolean AIGame;
    private int difficulty;
    
    public MenuController(KamisadoApp application) {
        this.application = application;
        isPaused = false;
        isSpeed = false;
        AIGame = false;
    }
    
    public void addView(MenuView view) {
        this.view = view;
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
            if(source == view.getNewGameButton()) {
                view.drawSelectModeScreen();
            }
            else if(source == view.getExitButton()) {
                this.exitGame();
            }
            else if(source == view.getVersusPlayerButton()) {
                AIGame = false;
                view.drawSpeedSelectScreen();
            }
            else if(source == view.getVersusAIButton()) {
                AIGame = true;
                view.drawSelectDifficultyScreen();
            }
            else if(source == view.getEasyButton()) {
                difficulty = 0;
                view.drawSpeedSelectScreen();
            }
            else if(source == view.getHardButton()) {
                difficulty = 1;
                view.drawSpeedSelectScreen();
            }
            else if(source == view.getWhiteRadio()) {
                view.getWhiteRadio().setSelected(true);
                view.getPlayButton().requestFocus();
            }
            else if(source == view.getBlackRadio()) {
                view.getBlackRadio().setSelected(true);
                view.getPlayButton().requestFocus();
            }
            else if(source == view.getCancelButton()) {
                if(isPaused) {
                    view.drawPauseScreen();
                }
                else {
                    view.drawMainMenu();
                }
            }
            else if(source == view.getNormalGameButton()) {
                isSpeed = false;
                if(AIGame) {
                    view.drawEnterNameVersusAIScreen();
                }
                else {
                    view.drawEnterNameScreen();
                }
            }
            else if(source == view.getSpeedGameButton()) {
                isSpeed = true;
                if(AIGame) {
                    view.drawEnterNameVersusAIScreen();
                }
                else {
                    view.drawEnterNameScreen();
                }
            }
            else if(source == view.getPlayButton()) {
                this.onPlayButton();
            }
            else if(source == view.getPlayerOneName()) {
                this.onPlayerOneName();
            }
            else if(source == view.getPlayerTwoName()) {
                this.onPlayerTwoName();
            }
            else if(source == view.getBackButton()) {
                view.drawSelectModeScreen();
            }
            else if(source == view.getResumeButton()) {
                this.onResume();
            }
            else if(source == view.getReturnToMainMenuButton()) {
                view.drawMainMenu();
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
        
            GameView gameView = new GameView(application.getRoot());
        
            if(isSpeed) {
                gameController = new SpeedGameController(this, gameView, board, (Integer) SaveManager.loadFromFile().get(2));
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
        GameView gameView = new GameView(application.getRoot());
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
        view.drawEndScreen(winner);
    }
    
    public void pause() {
        isPaused = true;
        view.initPauseScreen();
    }
    
    private void onPlayButton() {
        Player playerOne = null;
        Player playerTwo = null;
        
        if(AIGame) {
            if(view.getBlackRadio().isSelected()) {
                playerOne = new Player(view.getPlayerOneName().getText(), "B");
                playerTwo = new AIPlayer("AI", "W", difficulty);
            }
            else if(view.getWhiteRadio().isSelected()) {
                playerOne = new AIPlayer("AI", "B", difficulty);
                playerTwo = new Player(view.getPlayerOneName().getText(), "W");
            }
        }
        else {
            playerOne = new Player(view.getPlayerOneName().getText(), "B");
            playerTwo = new Player(view.getPlayerTwoName().getText(), "W");
        }
        
        Board board = new Board(playerOne, playerTwo);
        GameView gameView = new GameView(application.getRoot());
        
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
        pOneName = view.getPlayerOneName().getText().trim();
        if(pOneName.equals("")) {
            view.drawNameErrorMessage(view.getPlayerOneError(), "Please enter a player name");
        }
        else if(pOneName.equals(pTwoName)) {
            view.drawNameErrorMessage(view.getPlayerOneError(), "Please enter a different name than Player Two");
        }
        else {
            if(AIGame) {
                view.getBlackRadio().requestFocus();
            }
            else {
                view.getPlayerTwoName().requestFocus();
            }
            view.drawNameErrorMessage(view.getPlayerOneError(), "");
        }
    }
    
    private void onPlayerTwoName() {
        pTwoName = view.getPlayerTwoName().getText().trim();
        if(pTwoName.equals("")) {
            view.drawNameErrorMessage(view.getPlayerTwoError(), "Please enter a player name");
        }
        else if(pTwoName.equals(pOneName)) {
            view.drawNameErrorMessage(view.getPlayerTwoError(), "Please enter a different name than Player One");
        }
        else {
            view.getPlayButton().requestFocus();
            view.drawNameErrorMessage(view.getPlayerTwoError(), "");
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