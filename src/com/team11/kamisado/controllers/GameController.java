package com.team11.kamisado.controllers;

import com.team11.kamisado.models.Board;
import com.team11.kamisado.views.BoardPane;
import com.team11.kamisado.models.Player;
import com.team11.kamisado.views.GameView;
import javafx.animation.AnimationTimer;
import javafx.scene.effect.GaussianBlur;

public class GameController extends AnimationTimer {
    
    private static final int BLURRADIUS = 63;
    
    private MenuController menuController;
    private GameView view;
    private Board board;
    private Player playerOne;
    private Player playerTwo;
    private Player currentPlayer;
    private int x;
    private int y;
    private boolean firstMove;
    
    public GameController(MenuController menuController, GameView gameView, Board board, Player playerOne, Player playerTwo) {
        this.menuController = menuController;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.currentPlayer = playerOne;
        this.firstMove = true;
        this.board = board;
        
        this.view = gameView;
        view.setBoard(board);
        view.initGameView();
        view.setNames(playerOne.getName(), playerTwo.getName());
        view.setMessage(false, "Welcome to Kamisado!\n'" + playerOne.getName() + "' please select a black tower to move");
    }
    
    public void setActiveController() {
        view.drawGameView();
        view.setEffect(null);
        view.requestFocus();
        this.start();
    }
    
    @Override
    public void handle(long now) {
        view.requestFocus();
        view.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case ESCAPE: {
                    menuController.pause();
                    view.setEffect(new GaussianBlur(BLURRADIUS));
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
                    if(firstMove) {
                        if(y == 0) {
                            board.setCurrentSquare(x, y);
                            view.switchSquareBorder(x,y);
                            board.setCurrentTower();
                            board.setValidCoordinates();
                            firstMove = false;
                            view.setMessage(false, "Now please choose a square to move to");
                        }
                        else {
                            view.setMessage(true, "That is not a black tower '" + playerOne.getName() + "'.\nPlease select a black tower to move");
                        }
                    }
                    else {
                        if(board.areValidCoordinates(x, y)) {
                            board.move(x, y);
                        }
                        else {
                            view.setMessage(true, "That is not a valid move.\nPlease select another square");
                            break;
                        }
                        
                        if(board.hasWon()) {
                            winGame(currentPlayer);
                            break;
                        }
                        
                        updateBoard();
                        
                        view.setMessage(false, "'" + currentPlayer.getName() + "' you are now on turn\nplease select a square to move to");
                        
                        if(!board.setValidCoordinates()) {
                            updateBoard();
                            
                            if(!board.setValidCoordinates()) {
                                winGame(currentPlayer == playerOne ? playerTwo : playerOne);
                            }
                            
                            view.setMessage(true, "'" + currentPlayer.getName() + "' it is your turn again since your opponent had no valid moves.\nPlease select a square to move to.");
                        }
                        view.moveSelector(x, y);
                        view.switchSquareBorder(x,y);
                    }
            }
        });
    }
    
    private void updateBoard() {
        board.switchCurrentPlayerColor();
        currentPlayer = currentPlayer == playerOne ? playerTwo : playerOne;
        board.setCurrentSquare(x, y);
        board.setCurrentTower();
        x = board.getCurrentCoordinates().getX();
        y = board.getCurrentCoordinates().getY();
    }
    
    private void winGame(Player winner) {
        this.stop();
        view.setEffect(new GaussianBlur(BLURRADIUS));
        view.stopFadeTransition();
        menuController.win(winner.getName());
    }
}