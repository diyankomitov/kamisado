package com.team11.kamisado.controllers;

import com.team11.kamisado.main.KamisadoApp;
import com.team11.kamisado.models.Board;
import com.team11.kamisado.models.Player;
import com.team11.kamisado.views.GameView;
import com.team11.kamisado.views.MenuView;
import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import static javafx.scene.input.KeyCode.ENTER;

public class MenuController implements EventHandler<InputEvent> {
    
    private MenuView view;
    private KamisadoApp application;
    private GameController gameController;
    private boolean isPaused;
    private String pTwoName;
    private String pOneName;
    
    public MenuController(KamisadoApp application) {
        this.application = application;
        isPaused = false;
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
                application.getStage().close();
            }
            else if(source.equals(view.getVersusPlayerButton())) {
                view.drawEnterNameScreen();
            }
            else if(source.equals(view.getCancelButton())) {
                if(isPaused) {
                    view.drawPauseScreen();
                }
                else {
                    view.drawMainMenu();
                }
            }
            else if(source.equals(view.getPlayButton())) {
                
                Player playerOne = new Player(view.getPlayerOneName().getText(), "B");
                Player playerTwo = new Player(view.getPlayerTwoName().getText(), "W");
                
                Board board = new Board();
                GameView gameView = new GameView(application.getRoot());
                gameController = new GameController(this, gameView, board, playerOne, playerTwo);
                gameController.setActiveController();
                gameController.start();
            }
            else if(source.equals(view.getPlayerOneName())) {
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
            else if(source.equals(view.getPlayerTwoName())) {
                
                pTwoName = view.getPlayerTwoName().getText().trim();
                if(pTwoName.equals("")) {
                    view.drawNameErrorMessage(view.getPlayerTwoError(), "Please enter a player " + "name");
                }
                else if(pTwoName.equals(pOneName)) {
                    view.drawNameErrorMessage(view.getPlayerTwoError(), "Please enter a different" + " name than Player One");
                }
                else {
                    view.getPlayButton().requestFocus();
                    view.drawNameErrorMessage(view.getPlayerTwoError(), "");
                }
            }
            else if(source.equals(view.getBackButton())) {
                view.drawSelectModeScreen();
            }
            else if(source.equals(view.getResumeButton())) {
                gameController.setActiveController();
            }
            else if(source.equals(view.getReturnToMainMenuButton())) {
                view.drawMainMenu();
            }
        }
    }
    
    public void win(String winner) {
        view.drawEndScreen(winner);
    }
    
    public void pause() {
        gameController.stop();
        isPaused = true;
        view.initPauseScreen();
    }
}
