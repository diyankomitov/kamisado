package com.team11.kamisado.controllers;

import com.team11.kamisado.models.Board;
import com.team11.kamisado.models.Player;
import com.team11.kamisado.models.Towers;
import com.team11.kamisado.util.Coordinates;
import com.team11.kamisado.util.GameMode;
import com.team11.kamisado.views.BoardPane;
import com.team11.kamisado.views.GameView;
import javafx.scene.effect.GaussianBlur;

import java.util.EmptyStackException;
import java.util.Stack;

import static com.team11.kamisado.models.Board.BOARD_LENGTH;

public class GameController {
    
    private static final int BLUR_RADIUS = 63;
    private final GameMode gameMode;
    
    private MenuController menuController;
    private GameView view;
    private BoardPane boardPane;
    private Board board;
    private int x;
    private int y;
    private Stack<Board> boardStack;
    
    public GameController(MenuController menuController, GameView gameView, Board board, GameMode gameMode) {
        this.gameMode = gameMode;
        this.menuController = menuController;
        this.board = board;
        
        boardStack = new Stack<>();
        
        
        this.view = gameView;
        view.initGameView();
        view.setNames(board.getPlayerOne().getName(), board.getPlayerTwo().getName());
        view.setMessage(false, "Welcome to Kamisado!\n'" + board.getCurrentPlayer().getName() + "' please select a black tower to move");
        
        this.boardPane = view.getBoardPane();
        boardPane = view.getBoardPane();
        boardPane.drawSquares(board.getSquareArray());
        boardPane.drawTowers(board.getTowerArray());
        boardPane.initSelector();
        
        if(!board.isFirstMove()) {
            x = board.getCurrentCoordinates().getX();
            y = board.getCurrentCoordinates().getY();
            view.setCurrent(x,y);
            boardPane.moveSelector(x, y);
        }
    }
    
    public void play() {
        view.drawGameView();
        view.setEffect(null);
        view.requestFocus();
        this.handleKeyPressed();
        gameMode.executeMove(this);
    }
    
    private void saveBoard() {
        boardStack.add(new Board(board));
    }

    private void undo() {
        try {
//            if(board.getOtherPlayer() instanceof AIPlayer) { TODO: add back
//                boardStack.pop();
//            }
            board = boardStack.pop();
            boardPane.drawTowers(board.getTowerArray());
        }
        catch(EmptyStackException e) {
            view.setMessage(true, "You can't undo anymore, you've reached the start of the game!");
        }
    }
    
    private void handleKeyPressed() {
        view.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case UP:
                    y -=  y >0 ? 1 : 0;
                    break;
                case DOWN:
                    y += y < BOARD_LENGTH-1 ? 1 : 0;
                    break;
                case LEFT:
                    x -= x > 0 ? 1 : 0;
                    break;
                case RIGHT:
                    x += x < BOARD_LENGTH-1 ? 1 : 0;
                    break;
                case ESCAPE: {
                    this.onEscape();
                    break;
                }
                case ENTER:
                    board.getCurrentPlayer().setCoordinates(x,y);
                    this.onEnter();
                    gameMode.executeMove(this);
                    break;
                case Z:
                    if(event.isControlDown()) {
                        undo();
                    }
                    break;
                default:
                    break;
            }
            
            switch(event.getCode()) {
                case UP:
                case DOWN:
                case LEFT:
                case RIGHT:
                    boardPane.moveSelector(x, y);
            }
        });
    }
    
    public void onEscape() {
        menuController.pause();
        view.setEffect(new GaussianBlur(BLUR_RADIUS));
    }
    
    public void onEnter() {
        saveBoard();
    
        x = board.getCurrentPlayer().getCoordinates().getX();
        y = board.getCurrentPlayer().getCoordinates().getY();
        
        System.out.println("x: " + x + "y: " + y);
    
        if(board.isFirstMove()) {
            if(y == 0) {
                board.setCurrentSquare(x, y);
                view.setCurrent(x, y);
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
            if(board.areValidCoordinates(x, y)) {
                board.move(x, y);
                System.out.println("boardx: " + board.getMoveCoordinates().getX() + "boardy: " + board.getMoveCoordinates().getY());
                view.getBoardPane().update(board.getOldCoordinates(), board.getMoveCoordinates());
            }
            else if(board.getTower(x, y) == Towers.EMPTY) {
                view.setMessage(true, "That is not a valid move.\nPlease select another square");
                return;
            }
            else {
                view.setMessage(true, "You can't move a tower on top of another tower.\nPlease " + "select another square");
                return;
            }
    
            if(board.isWin()) {
                this.winGame(board.getCurrentPlayer());
            }
            else if(board.isDeadlock()) {
                this.winGame(board.getOtherPlayer());
            }
            else if(board.isLock()) {
                System.out.println("lock happened");
                x = board.getCurrentCoordinates().getX();
                y = board.getCurrentCoordinates().getY();
                view.setMessage(true, "'" + board.getCurrentPlayer().getName() + "' it is your turn again since your opponent had no valid moves.\nPlease select a square to move to.");
                gameMode.executeMove(this);
            }
            else {
                x = board.getCurrentCoordinates().getX();
                y = board.getCurrentCoordinates().getY();
                view.setMessage(false, "'" + board.getCurrentPlayer().getName() + "' you are now on turn\nplease select a square to move to");
                view.setCurrent(x, y);
            }
        }
    }
    
    public void winGame(Player winner) {
        view.setEffect(new GaussianBlur(BLUR_RADIUS));
        view.getBoardPane().stopFadeTransition();
        menuController.win(winner.getName());
    }
    
    public Board getBoard() {
        return board;
    }
    
    public void setStack(Stack<Board> stack) {
        this.boardStack = stack;
    }
    
    public Stack<Board> getBoardStack() {
        return boardStack;
    }
}
