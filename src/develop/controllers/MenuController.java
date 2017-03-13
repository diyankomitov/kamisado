package develop.controllers;

import develop.KamisadoApp;
import develop.controllers.GameController;
import develop.models.Board;
import develop.views.GameView;
import develop.views.MenuView;
import javafx.event.EventHandler;
import javafx.scene.input.*;

import static javafx.scene.input.KeyCode.ENTER;

public class MenuController implements EventHandler<InputEvent> {
    
    //private GameController gameController;
    private MenuView view;
    private boolean currentController;
    private KamisadoApp application;
    private GameController gameController;
    private boolean isPaused;
    
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
        
        System.out.println("handling stuff from: " + source.toString());
        
        if(clicked || pressed) {
            if(source.equals(view.getNewGameButton())) {
                view.drawSelectModeScreen();
            }
            else if(source.equals(view.getExitButton())) {
                view.exit();
            }
            else if(source.equals(view.getSinglePlayerButton())) {
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
                Board board = new Board();
                gameController = new GameController(this, board);
                GameView gameView = new GameView(application.getRoot(), gameController);
                gameController.addView(gameView);
                gameController.setActiveController();
                gameController.start();
            }
            else if(source.equals(view.getPlayerOneName())) {
                view.getPlayerTwoName().requestFocus();
            }
            else if(source.equals(view.getPlayerTwoName())) {
                view.getPlayButton().requestFocus();
            }
            else if(source.equals(view.getBackButton())) {
                view.drawSelectModeScreen();
            }
            else if(source.equals(view.getResumeButton())) {
                gameController.setActiveController();
            }
        }
    }
    
    public void setCurrentController() {
        view.drawMainMenu();
    }
    
    public void pause() {
        isPaused = true;
        view.drawPauseScreen();
    }
}
