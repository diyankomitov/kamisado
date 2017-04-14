package com.team11.kamisado.controllers;

import com.team11.kamisado.AI.AIPlayer;
import com.team11.kamisado.main.KamisadoApp;
import com.team11.kamisado.models.Board;
import com.team11.kamisado.models.Player;
import com.team11.kamisado.util.SaveManager;
import com.team11.kamisado.views.GameView;
import com.team11.kamisado.views.MenuView;
import com.team11.kamisado.views.SinglePlayerMenuView;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

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
    private SinglePlayerMenuView singlePlayerMenuView;
    
    public MenuController(KamisadoApp application, MenuView menuView) {
        this.application = application;
        this.isPaused = false;
        this.isSpeed = false;
        this.AIGame = false;
        
        this.view = menuView;
        for(Node node : view.getHandledNodes()) {
            node.setOnKeyPressed(this);
            node.setOnMouseClicked(this);
        }
        
        this.singlePlayerMenuView = new SinglePlayerMenuView(view);
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
                System.out.println("you pressed ai button");
                singlePlayerMenuView.drawSinglePlayerScreen();
                application.getRoot().getChildren().clear();
                application.getRoot().getChildren().add(singlePlayerMenuView);
                if(isPaused) {
                    singlePlayerMenuView.setTransparent(true);
                }
                else {
                    singlePlayerMenuView.setTransparent(false);
                }
            }
            
            else if(source == singlePlayerMenuView.getSinglePlayerName()) {
                if(singlePlayerMenuView.getSinglePlayerName().getText().equals("")) {
                    singlePlayerMenuView.getSinglePlayerError().setText("Please enter a player name");
                }
                else {
                    singlePlayerMenuView.getSinglePlayerError().setText("");
                    singlePlayerMenuView.getEasyRadio().requestFocus();
                }
            }
            else if(source == singlePlayerMenuView.getEasyRadio()) {
                singlePlayerMenuView.getEasyRadio().setSelected(true);
                singlePlayerMenuView.getNormalRadio().requestFocus();
            }
            else if(source == singlePlayerMenuView.getHardRadio()) {
                singlePlayerMenuView.getHardRadio().setSelected(true);
                singlePlayerMenuView.getNormalRadio().requestFocus();
            }
            else if(source == singlePlayerMenuView.getNormalRadio()) {
                singlePlayerMenuView.getNormalRadio().setSelected(true);
                singlePlayerMenuView.getBlackRadio().requestFocus();
            }
            else if(source == singlePlayerMenuView.getSpeedRadio()) {
                singlePlayerMenuView.getSpeedRadio().setSelected(true);
                singlePlayerMenuView.getBlackRadio().requestFocus();
            }
            else if(source == singlePlayerMenuView.getBlackRadio()) {
                singlePlayerMenuView.getBlackRadio().setSelected(true);
                view.getPlayButton().requestFocus();
            }
            else if(source == singlePlayerMenuView.getWhiteRadio()) {
                singlePlayerMenuView.getWhiteRadio().setSelected(true);
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
                view.drawEnterNameScreen();
            }
            else if(source == view.getSpeedGameButton()) {
                isSpeed = true;
                view.drawEnterNameScreen();
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
                application.getRoot().getChildren().clear();
                application.getRoot().getChildren().add(view);
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
            if(singlePlayerMenuView.getEasyRadio().isSelected()) {
                difficulty = 0;
            }
            else if(singlePlayerMenuView.getHardRadio().isSelected()) {
                difficulty = 1;
            }
            
            if(singlePlayerMenuView.getNormalRadio().isSelected()) {
                isSpeed = false;
            }
            else if(singlePlayerMenuView.getSpeedRadio().isSelected()) {
                isSpeed = true;
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
            view.drawNameErrorMessage(view.getPlayerOneError(), "Please enter a different name than Player Two"); //TODO: don't allow play if names entered wrong
        }
        else {
            view.getPlayerTwoName().requestFocus();
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
