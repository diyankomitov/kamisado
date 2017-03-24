package com.team11.kamisado.controllers;

import com.team11.kamisado.AI.AIPlayer;
import com.team11.kamisado.main.KamisadoApp;
import com.team11.kamisado.models.Board;
import com.team11.kamisado.models.Player;
import com.team11.kamisado.models.Towers;
import com.team11.kamisado.util.Coordinates;
import com.team11.kamisado.util.Observable;
import com.team11.kamisado.util.Observer;
import com.team11.kamisado.views.BoardPane;
import com.team11.kamisado.views.GameView;
import javafx.scene.effect.GaussianBlur;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class GameController implements Observable{
    
    private static final int BLUR_RADIUS = 63;
    
    private MenuController menuController;
    private GameView view;
    private Board board;
    private int x;
    private int y;
    private List<Observer> observers;
    private boolean AIOnTurn;
    private Stack<Board> boardStack;
    
    public GameController(MenuController menuController, GameView gameView, Board board) {
        this.menuController = menuController;
        this.board = board;
    
        boardStack = new Stack<>();
        
        AIOnTurn = board.getCurrentPlayer() instanceof AIPlayer;
        
        this.view = gameView;
        view.setBoard(board);
        view.initGameView();
        view.setNames(board.getPlayerOne().getName(), board.getPlayerTwo().getName());
        view.setMessage(false, "Welcome to Kamisado!\n'" + board.getCurrentPlayer().getName() + "' please " +
                "select a black tower to move");
        this.observers = new ArrayList<>();
        this.subscribe(view.getBoardPane());
        
        if(!board.isFirstMove()) {
            x = board.getCurrentCoordinates().getX();
            y = board.getCurrentCoordinates().getY();
            view.setCurrent(x,y);
            view.moveSelector(x,y);
        }
    }
    
    public void setActiveController() {
        view.drawGameView();
        view.setEffect(null);
        view.requestFocus();
        this.handleKeyPressed();
        if(AIOnTurn) {
            onEnter();
        }
    }
    
    private void saveBoard() {
        boardStack.add(new Board(board));
    }
    

    private void undo() {
        try {
            if(board.getOtherPlayer() instanceof AIPlayer) {
                boardStack.pop();
            }
            board = boardStack.pop();
            menuController.undo(board, boardStack);
           
        }
        catch(EmptyStackException e) {
            view.setMessage(true, "You can't undo anymore, you've reached the start of the game!");
        }
    }
    
    private void handleKeyPressed() {
        view.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case ESCAPE: {
                    this.onEscape();
                    break;
                }
                case UP:
                    if(y > 0) {
                        y--;
                    }
                    view.moveSelector(x, y);
                    break;
                case DOWN:
                    if(y < BoardPane.BOARD_LENGTH - 1) {
                        y++;
                    }
                    view.moveSelector(x, y);
                    break;
                case LEFT:
                    if(x > 0) {
                        x--;
                    }
                    view.moveSelector(x, y);
                    break;
                case RIGHT:
                    if(x < BoardPane.BOARD_LENGTH - 1) {
                        x++;
                    }
                    view.moveSelector(x, y);
                    break;
                case ENTER:
                    this.onEnter();
                    break;
                case Z:
                    if(event.isControlDown()) {
                        undo();
                    }
                    break;
                case M:
                    AIPlayer player = new AIPlayer("Blah", "Blah", 0);
                    Coordinates aiToMove = player.getCoordinates(this);
                    break;
            }
        });
    }
    
    public void onEscape() {
        menuController.pause();
        view.setEffect(new GaussianBlur(BLUR_RADIUS));
    }
    
    public void setCoordinates(Coordinates coordinates) {
        x = coordinates.getX();
        y = coordinates.getY();
    }
    
    public void onEnter() {
        saveBoard();
        if(AIOnTurn) {
            AIPlayer aiPlayer = (AIPlayer) board.getCurrentPlayer();
            Coordinates toMove = aiPlayer.getCoordinates(this);
            x = toMove.getX();
            y = toMove.getY();
            AIOnTurn = false;
        }
        
        if(board.isFirstMove()) {
            if(y == 0) {
                board.setCurrentSquare(x, y);
                view.setCurrent(x,y);
                board.setCurrentCoordinates();
                board.setValidCoordinates();
                board.setFirstMoveToFalse();
                view.setMessage(false, "Now please choose a square to move to");
            }
            else {
                view.setMessage(true, "That is not a black tower '" + board.getCurrentPlayer().getName() + "'.\nPlease select a black tower to move");
            }
        }
        else {
            int code;
            if(board.areValidCoordinates(x, y)) {
                code = board.move(x, y);
                this.notifyAllObservers();
            }
            else if(board.getTower(x,y) == Towers.EMPTY){
                view.setMessage(true, "That is not a valid move.\nPlease select another square");
                return;
            }
            else {
                view.setMessage(true, "You can't move a tower on top of another tower.\nPlease " +
                        "select another square");
                return;
            }
            
            if(code == 0) {
                x = board.getCurrentCoordinates().getX();
                y = board.getCurrentCoordinates().getY();
                view.setMessage(false, "'" + board.getCurrentPlayer().getName() + "' you are now on turn\nplease select a square to move to");
                view.setCurrent(x,y);
            }
            else if(code == 1) {
                this.winGame(board.getCurrentPlayer());
                return;
            }
            else if(code == 2) {
                x = board.getCurrentCoordinates().getX();
                y = board.getCurrentCoordinates().getY();
                view.setMessage(true, "'" + board.getCurrentPlayer().getName() + "' it is your turn again since your opponent had no valid moves.\nPlease select a square to move to.");
            }
            else if(code == 3) {
                this.winGame(board.getOtherPlayer());
                return;
            }
        }
        
        if(board.getCurrentPlayer() instanceof AIPlayer) {
            AIOnTurn = true;
            onEnter();
        }
    }
    
    public void winGame(Player winner) {
        view.setEffect(new GaussianBlur(BLUR_RADIUS));
        view.stopFadeTransition();
        menuController.win(winner.getName());
    }
    
    public Board getBoard() {
        return board;
    }
    
    public void subscribe(Observer observer) {
        observers.add(observer);
    }
    
    @Override
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyAllObservers() {
        for(Observer observer : observers) {
            observer.update();
        }
    }
    
    public void setStack(Stack<Board> stack) {
        this.boardStack = stack;
    }
    
    public void undoAgain() {
        try {
            board = boardStack.pop();
            menuController.undo(board, boardStack);
        }
        catch(EmptyStackException e) {
            view.setMessage(true, "You can't undo anymore, you've reached the start of the game!");
        }
    }
    
    public Stack<Board> getBoardStack() {
        return boardStack;
    }
}