package com.team11.kamisado.controllers;

import com.team11.kamisado.models.Board;
import com.team11.kamisado.models.Player;
import com.team11.kamisado.util.Observable;
import com.team11.kamisado.util.Observer;
import com.team11.kamisado.views.BoardPane;
import com.team11.kamisado.views.GameView;

import javafx.scene.effect.GaussianBlur;
import java.util.ArrayList;
import java.util.List;

public class GameController implements Observable{
    
    private static final int BLUR_RADIUS = 63;
    
    private MenuController menuController;
    private GameView view;
    private Board board;
    private int x;
    private int y;
    private List<Observer> observers;
    
    public GameController(MenuController menuController, GameView gameView, Board board) {
        this.menuController = menuController;
        this.board = board;
    
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
            }
        });
    }
    
    public void onEscape() {
        menuController.pause();
        view.setEffect(new GaussianBlur(BLUR_RADIUS));
    }
    
    public void onEnter() {
        if(board.isFirstMove()) {
            if(y == 0) {
                board.setCurrentSquare(x, y);
                view.setCurrent(x,y);
                board.setCurrentTower();
                board.setValidCoordinates();
                board.setFirstMove(false);
                view.setMessage(false, "Now please choose a square to move to");
            }
            else {
                view.setMessage(true, "That is not a black tower '" + board.getCurrentPlayer().getName() + "'" +
                        ".\nPlease " +
                        "select a black tower to move");
            }
        }
        else {
            if(board.areValidCoordinates(x, y)) {
                board.move(x, y);
                this.notifyAllObservers();
            }
            else {
                view.setMessage(true, "That is not a valid move.\nPlease select another square");
                return;
            }
        
            if(board.hasWon()) {
                this.winGame(board.getCurrentPlayer());
                return;
            }
        
            this.updateBoard();
        
            view.setMessage(false, "'" + board.getCurrentPlayer().getName() + "' you are now on turn\nplease " +
                    "select a " +
                    "square to move to");
        
            if(!board.setValidCoordinates()) {
                this.updateBoard();
            
                if(!board.setValidCoordinates()) {
                    this.winGame(board.getOtherPlayer());
                }
            
                view.setMessage(true, "'" + board.getCurrentPlayer().getName() + "' it is your turn again since " +
                        "your " +
                        "opponent had no valid moves.\nPlease select a square to move to.");
            }
            view.setCurrent(x,y);
        }
    }
    
    public void winGame(Player winner) {
        view.setEffect(new GaussianBlur(BLUR_RADIUS));
        view.stopFadeTransition();
        menuController.win(winner.getName());
    }
    
    private void updateBoard() {
        board.switchCurrentPlayer();
        board.setCurrentSquare(x, y);
        board.setCurrentTower();
        x = board.getCurrentCoordinates().getX();
        y = board.getCurrentCoordinates().getY();
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
}