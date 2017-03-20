package com.team11.kamisado.controllers;

import com.team11.kamisado.main.KamisadoApp;
import com.team11.kamisado.models.Board;
import com.team11.kamisado.models.Player;
import com.team11.kamisado.util.Coordinates;
import com.team11.kamisado.views.GameView;
import com.team11.kamisado.views.MenuView;
import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

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
    
    public MenuController(KamisadoApp application) {
        this.application = application;
        isPaused = false;
        isSpeed = false;
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
            if(source.equals(view.getNewGameButton())) {
                view.drawSelectModeScreen();
            }
            else if(source.equals(view.getExitButton())) {
                exitGame();
            }
            else if(source.equals(view.getVersusPlayerButton())) {
                view.drawSpeedSelectScreen();
            }
            else if(source.equals(view.getCancelButton())) {
                if(isPaused) {
                    view.drawPauseScreen();
                }
                else {
                    view.drawMainMenu();
                }
            }
            else if(source.equals(view.getNormalGameButton())) {
                isSpeed = false;
                view.drawEnterNameScreen();
            }
            else if(source.equals(view.getSpeedGameButton())) {
                isSpeed = true;
                view.drawEnterNameScreen();
            }
            else if(source.equals(view.getPlayButton())) {
                onPlayButton();
            }
            else if(source.equals(view.getPlayerOneName())) {
                onPlayerOneName();
            }
            else if(source.equals(view.getPlayerTwoName())) {
               onPlayerTwoName();
            }
            else if(source.equals(view.getBackButton())) {
                view.drawSelectModeScreen();
            }
            else if(source.equals(view.getResumeButton())) {
                if(gameInProgress) {
                    gameController.setActiveController();
                }
                else {
                    Board board = (Board)loadBoardFromFile().get(0);
                    isSpeed = (boolean)loadBoardFromFile().get(1);
                    
                    GameView gameView = new GameView(application.getRoot());
    
                    if(isSpeed) {
                        gameController = new SpeedGameController(this, gameView, board, (Integer)loadBoardFromFile().get(2));
                    }
                    else {
                        gameController = new GameController(this, gameView, board);
                    }
                    gameController.setActiveController();
                    gameInProgress = true;
                }
                isPaused = false;
            }
            else if(source.equals(view.getReturnToMainMenuButton())) {
                view.drawMainMenu();
            }
        }
    }
    
    public void win(String winner) {
        File file = new File("saves/resume.ser");
        file.delete();
        gameInProgress = false;
        
        view.drawEndScreen(winner);
    }
    
    public void pause() {
        isPaused = true;
        view.initPauseScreen();
    }
    
    private void onPlayButton() {
        Player playerOne = new Player(view.getPlayerOneName().getText(), "B");
        Player playerTwo = new Player(view.getPlayerTwoName().getText(), "W");
        
        //Board board = loadBoardFromFile();
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
    
    private ArrayList<Object> loadBoardFromFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream("saves/resume.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<Object> list = (ArrayList<Object>) objectInputStream.readObject();
            objectInputStream.close();
            return list;
        }
        catch(ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void onPlayerOneName() {
        pOneName = view.getPlayerOneName().getText().trim();
        if(pOneName.equals("")) {
            view.drawNameErrorMessage(view.getPlayerOneError(), "Please enter a player " + "name");
        }
        else if(pOneName.equals(pTwoName)) {
            view.drawNameErrorMessage(view.getPlayerOneError(), "Please enter a different" + " name than Player Two");
        }
        else {
            view.getPlayerTwoName().requestFocus();
            view.drawNameErrorMessage(view.getPlayerOneError(), "");
        }
    }
    
    private void onPlayerTwoName() {
        pTwoName = view.getPlayerTwoName().getText().trim();
        if(pTwoName.equals("")) {
            view.drawNameErrorMessage(view.getPlayerTwoError(), "Please enter a player "
                    + "name");
        }
        else if(pTwoName.equals(pOneName)) {
            view.drawNameErrorMessage(view.getPlayerTwoError(), "Please enter a different" + " name than Player One");
        }
        else {
            view.getPlayButton().requestFocus();
            view.drawNameErrorMessage(view.getPlayerTwoError(), "");
        }
    }
    
    private void exitGame() {
        //Towers[][] currentTowerArray = gameController.getCurrentTowers();
        if(gameInProgress) {
            ArrayList<Object> store = new ArrayList<>();
            Board board = gameController.getBoard();
            store.add(board);
            store.add(isSpeed);
            store.add(gameController instanceof SpeedGameController ? ((SpeedGameController)
                    gameController).getCurrentTime() : null);
    
            try {
                FileOutputStream fileOutputStream = new FileOutputStream("saves/resume.ser");
                ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
                outputStream.writeObject(store);
                outputStream.close();
                fileOutputStream.close();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        
        application.getStage().close();
    }
}