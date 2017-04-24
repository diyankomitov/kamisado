package com.team11.kamisado.controllers;

import com.team11.kamisado.main.KamisadoApp;
import com.team11.kamisado.models.Board;
import com.team11.kamisado.models.Player;
import com.team11.kamisado.network.Client;
import com.team11.kamisado.util.AIMode;
import com.team11.kamisado.util.GameMode;
import com.team11.kamisado.util.NormalMode;
import com.team11.kamisado.util.OnlineMode;
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

import java.awt.*;
import java.util.Stack;

import static javafx.scene.input.KeyCode.*;

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
    private GameMode gameMode;
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
        Object source = event.getSource();
        
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
                    focusNext();
                }
            }
            else if(source == view.getPlayButton()) {
                this.onPlayButton();
            }
            else if(source == view.getBackButton()) {
                onlineGame = false;
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
            else if(source == menuView.getContinueMatchButton()) {
                gameController.continueMatch();
            }
            else if(source == view.getRandomButton()) {
                view.getRandomButton().setSelected(!view.getRandomButton().isSelected());
            }
            else {
                focusNext();
            }
        }
    }
    
    private void onResume() {
        if(gameInProgress) {
            gameController.play();
        }
        else {
            Board board = (Board) SaveManager.loadFromFile().get(0);
            isSpeed = (boolean) SaveManager.loadFromFile().get(1);
            Stack<Board> stack = (Stack<Board>) SaveManager.loadFromFile().get(3);
            AIGame = (boolean) SaveManager.loadFromFile().get(4);
            difficulty = (int) SaveManager.loadFromFile().get(5);
            
            GameView gameView = new GameView(root);
            
            if(AIGame) {
                gameMode = new GameMode(new AIMode(difficulty));
            }
            else {
                gameMode = new GameMode(new NormalMode());
            }
            
            if(isSpeed) {
                gameController = new SpeedGameController(this, gameView, board, gameMode, (Integer)SaveManager.loadFromFile().get(2));
            }
            else {
                gameController = new GameController(this, gameView, board, gameMode);
            }
            gameController.play();
            gameInProgress = true;
            gameController.setStack(stack);
        }
        isPaused = false;
    }
    
    public void win(String winner, boolean matchOver) {
        SaveManager.deleteFile();
        gameInProgress = false;
        menuView.drawEndScreen(winner, matchOver);
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
        boolean isBlack;
        
        isSpeed = view.getSpeedRadio().isSelected();
        
        if(onlineGame) {
    
            boolean isHost = menuView.getHostButton().isSelected();
            isBlack = menuView.getBlackRadio().isSelected();
    
    
            Client.connectToServer(menuView.getSinglePlayerName().getText(), isHost, isBlack, isSpeed);
            isBlack = Client.getBlack();
            gameMode = new GameMode(new OnlineMode(isBlack));
            System.out.println("continuing");
            if(isBlack) {
                playerOne = new Player(menuView.getSinglePlayerName().getText(), "B");
                playerTwo = new Player(Client.getOtherPlayerName(), "W");
                gameMode.setPlayer(playerTwo);
            }
            else {
                playerOne = new Player(Client.getOtherPlayerName(), "B");
                playerTwo = new Player(menuView.getSinglePlayerName().getText(), "W");
                gameMode.setPlayer(playerOne);
            }
            System.out.println("continuing2" +
                    "");
        }
        else if(AIGame) {
            difficulty = singlePlayerMenuView.getEasyRadio().isSelected() ? 0 : 1;
            gameMode = new GameMode(new AIMode(difficulty));
            
            if(singlePlayerMenuView.getBlackRadio().isSelected()) {
                playerOne = new Player(singlePlayerMenuView.getSinglePlayerName().getText(), "B");
                playerTwo = new Player("AI", "W");
                gameMode.setPlayer(playerTwo);
            }
            else if(singlePlayerMenuView.getWhiteRadio().isSelected()) {
                playerOne = new Player("AI", "B");
                playerTwo = new Player(singlePlayerMenuView.getSinglePlayerName().getText(), "W");
                gameMode.setPlayer(playerOne);
            }
        }
        else {
            playerOne = new Player(menuView.getPlayerOneName().getText(), "B");
            playerTwo = new Player(menuView.getPlayerTwoName().getText(), "W");
            gameMode = new GameMode(new NormalMode());
        }
        
        Board board = new Board(playerOne, playerTwo);
        
        int points;
        
       
        if(menuView.getThreeRadio().isSelected()) {
            points = 3;
        }
        else if(menuView.getSevenRadio().isSelected()) {
            points = 7;
        }
        else if(menuView.getFifteenRadio().isSelected()) {
            points = 15;
        }
        else {
            points = 1;
        }
        
        board.setMaxPoints(points);
        
        if(view.getRandomButton().isSelected()) {
            board.randomizeBoard();
        }
        
        GameView gameView = new GameView(root);
        
        if(isSpeed) {
            gameController = new SpeedGameController(this, gameView, board, gameMode, 5);
        }
        else {
            gameController = new GameController(this, gameView, board, gameMode);
        }
        
        gameController.play();
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
            focusNext();
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
            focusNext();
            menuView.drawNameErrorMessage(menuView.getPlayerTwoError(), "");
        }
    }
    
    private void exitGame() {
        if(gameInProgress) {
            
            Board board = gameController.getBoard();
            int time = isSpeed ? ((SpeedGameController) gameController).getCurrentTime() : 0;
            
            if(!onlineGame) {
                SaveManager.saveToFile(board, isSpeed, time, gameController.getBoardStack(), AIGame, difficulty);
            }
        }
        application.getStage().close();
    }
    
    private void focusNext() {
        try {
            Robot robot = new Robot();
            robot.keyPress(java.awt.event.KeyEvent.VK_TAB);
            robot.keyRelease(java.awt.event.KeyEvent.VK_TAB);
        }
        catch(AWTException e) {
            e.printStackTrace();
        }
    }
    
    private void focusPrevious() {
        try {
            Robot robot = new Robot();
            robot.keyPress(java.awt.event.KeyEvent.VK_SHIFT);
            robot.keyPress(java.awt.event.KeyEvent.VK_TAB);
            robot.keyRelease(java.awt.event.KeyEvent.VK_TAB);
            robot.keyRelease(java.awt.event.KeyEvent.VK_SHIFT);
        }
        catch(AWTException e) {
            e.printStackTrace();
        }
    }
}
