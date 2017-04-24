package com.team11.kamisado.controllers;

import com.team11.kamisado.models.Board;
import com.team11.kamisado.models.Player;
import com.team11.kamisado.models.Towers;
import com.team11.kamisado.util.GameMode;
import com.team11.kamisado.views.BoardPane;
import com.team11.kamisado.views.Colors;
import com.team11.kamisado.views.GameView;
import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;

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
    private int round;
    
    public GameController(MenuController menuController, GameView gameView, Board board, GameMode gameMode) {
        this.gameMode = gameMode;
        this.menuController = menuController;
        this.board = board;
        this.round = 1;
        
        boardStack = new Stack<>();
        
        this.view = gameView;
        view.initGameView();
        view.setNames(board.getPlayerOne().getName(), board.getPlayerTwo().getName());
        view.setMessage(false, "Welcome to Kamisado!\n'" + board.getCurrentPlayer().getName() + "' please select a black tower to move");
        
        this.boardPane = view.getBoardPane();
        boardPane.drawSquares(board.getSquareArray());
        boardPane.drawTowers(board.getTowerArray());
        boardPane.initSelector();
        
        if(!board.isFirstMove()) {
            x = board.getCurrentCoordinates().getX();
            y = board.getCurrentCoordinates().getY();
            boardPane.setCurrent(x,y);
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
        if(!gameMode.getMode().equals("OnlineMode")) {
            boardStack.add(new Board(board));
        }
    }

    private void undo() {
        try {
            if(gameMode.getMode().equals("AIMode")) {
             boardStack.pop();
            }
            board = boardStack.pop();
            boardPane.drawTowers(board.getTowerArray());
            x = board.getCurrentCoordinates().getX();
            y = board.getCurrentCoordinates().getY();
            boardPane.setCurrent(x,y);
            if(board.isFirstMove()) {
                boardPane.getSquare(x,y).setStroke(Colors.TRUEBLACK.getValue());
            }
            view.setMessage(false, "You undid a move.\nYou can now move again");
        }
        catch(EmptyStackException e) {
            view.setMessage(true, "You can't undo anymore, you've reached the start of the game!");
            if(gameMode.getMode().equals("OnlineMode")) {
                view.setMessage(true, "You can't undo if you are playing online");
            }
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
        x = board.getCurrentPlayer().getCoordinates().getX();
        y = board.getCurrentPlayer().getCoordinates().getY();
        
        if(board.isFirstMove()) {
            if(board.getCurrentPlayer().getPlayerColor().equals("B") && y == 0 || board.getCurrentPlayer().getPlayerColor().equals("W") && y == BOARD_LENGTH-1) {
                saveBoard();
                board.setCurrentSquare(x, y);
                board.setValidCoordinates();
                board.setFirstMoveToFalse();
                boardPane.setCurrent(x, y);
                view.setMessage(false, "Now please choose a square to move to");
            }
            else {
                String color = board.getCurrentPlayer().getPlayerColor().equals("B") ? "black" : "white";
                view.setMessage(true, "That is not a " + color + " tower '" + board.getCurrentPlayer().getName() + "'.\nPlease select a " + color + " tower to move");
            }
        }
        else {
            if(board.areValidCoordinates(x, y)) {
                saveBoard();
                board.move(x, y);
                boardPane.drawTowers(board.getTowerArray());
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
                x = board.getCurrentCoordinates().getX();
                y = board.getCurrentCoordinates().getY();
                view.setMessage(true, "'" + board.getCurrentPlayer().getName() + "' it is your turn again since your opponent had no valid moves.\nPlease select a square to move to.");
                gameMode.executeMove(this);
            }
            else {
                x = board.getCurrentCoordinates().getX();
                y = board.getCurrentCoordinates().getY();
                view.setMessage(false, "'" + board.getCurrentPlayer().getName() + "' you are now on turn\nplease select a square to move to");
                boardPane.setCurrent(x, y);
            }
        }
    }
    
    public void winGame(Player winner) {
        view.setEffect(new GaussianBlur(BLUR_RADIUS));
        boardPane.stopFadeTransition();
        
        menuController.win(winner.getName(), board.isMatchWon());
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
    
    public void continueMatch() {
        view.drawGameView();
        view.setEffect(null);
        view.setOnKeyPressed(event -> {});
        Player pl1 = board.getPlayerOne();
        Player pl2 = board.getPlayerTwo();
        view.setNames(pl1.getName() + " (" + pl1.getScore() + ")", pl2.getName() + " (" + pl2.getScore() + ")");
        view.setMessage(false, board.getWinner().getName() + " please select a fill order for your home row!");
        view.drawOrderButtons();
        view.getLeftButton().requestFocus();
        view.getLeftButton().setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                event.consume();
                startNextRound(true);
            }
        });
        view.getRightButton().setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                event.consume();
                startNextRound(false);
            }
        });
    }
    
    private void startNextRound(boolean left) {
        view.drawSideBar();
        board.continueMatch(left);
        board = new Board(board);
        round++;
        String color = board.getCurrentPlayer().getPlayerColor().equals("B") ? "black" : "white";
        view.setMessage(false, "Welcome to round " + round + " ! '" + board.getCurrentPlayer().getName() + "' please select a " + color + " tower to move");
        boardPane.drawTowers(board.getTowerArray());
        x = 0;
        y = 0;
        boardPane.moveSelector(x,y);
        boardPane.setCurrent(x,y);
        boardPane.getSquare(x,y).setStroke(Colors.TRUEBLACK.getValue());
        this.play();
    }
}
