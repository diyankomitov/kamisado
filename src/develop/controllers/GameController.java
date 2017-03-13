package develop.controllers;

import develop.models.Board;
import develop.views.GameView;
import javafx.animation.AnimationTimer;

import static develop.views.BoardPane.BOARD_LENGTH;

public class GameController extends AnimationTimer {
    
    private MenuController menuController;
    private GameView view;
    private Board board;
    private boolean selected;
    
    private int x;
    private int y;
    
    public GameController(MenuController menuController, Board board) {
        this.menuController = menuController;
        this.selected = false;
        this.board = board;
    }
    
    public void addView(GameView view) {
        this.view = view;
    }
    
    public Board getBoard() {
        return board;
    }
    
    public void setActiveController() {
        view.drawGameView();
    }
    
    @Override
    public void handle(long now) {
        view.requestFocus();
        view.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case UP:
                    if(y > 0) {
                        y--;
                    }
                    view.moveSelector(x,y);
                    System.out.println("Key pressed");
                    
                    break;
                case DOWN:
                    if(y < BOARD_LENGTH-1) {
                        y++;
                    }
                    view.moveSelector(x,y);
                    System.out.println("Key pressed");
                    break;
                case LEFT:
                    if(x > 0) {
                        x--;
                    }
                    view.moveSelector(x,y);
                    System.out.println("Key pressed");
                    break;
                case RIGHT:
                    if(x < BOARD_LENGTH-1) {
                        x++;
                    }
                    view.moveSelector(x,y);
                    System.out.println("Key pressed");
                    break;
                case ENTER:
                    if(selected) {
                        if(board.isValidCoordinate(x, y)) {
                            board.move(x, y);
                            board.switchCurrentPlayer();
                            board.setCurrentSquare(x, y);
                            board.setCurrentTower();
                            board.setValidCoordinates();
                            x = board.getCurrentCoordinates().getX();
                            y = board.getCurrentCoordinates().getY();
                            view.moveSelector(x,y);
                            System.out.println("Key pressed");
                        }
                        else {
                            System.out.println("Not a valid move");
                        }
                    }
                    else {
                        if(board.isTower(x, y)) {
                            board.setCurrentSquare(x, y);
                            board.setCurrentTower();
                            board.setValidCoordinates();
                            selected = true;
                            System.out.println("Key pressed");
                        }
                        else {
                            System.out.println("Not a tower");
                        }
                        
                    }
                    break;
                case ESCAPE: {
                    System.out.println("Game is paused");
                    menuController.pause();
                    System.out.println("Key pressed");
                }
                    
            }
        });
        if(board.hasWon()) {
            System.out.println(board.getCurrentPlayer() + " won!");
            this.stop();
            menuController.setCurrentController();
        }
    }
}
