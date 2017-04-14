package com.team11.kamisado.controllers;

import com.team11.kamisado.AI.AIPlayer;
import com.team11.kamisado.models.Board;
import com.team11.kamisado.models.Player;
import com.team11.kamisado.models.Towers;
import com.team11.kamisado.util.Coordinates;
import com.team11.kamisado.views.BoardPane;
import com.team11.kamisado.views.GameView;
import javafx.scene.effect.GaussianBlur;

import java.util.EmptyStackException;
import java.util.Stack;

import static com.team11.kamisado.models.Board.BOARD_LENGTH;

public class GameController {
    
    private static final int BLUR_RADIUS = 63;
    
    private MenuController menuController;
    private GameView view;
    private BoardPane boardPane;
    private Board board;
    private int x;
    private int y;
    private boolean AIOnTurn;
    private Stack<Board> boardStack;
    
    public GameController(MenuController menuController, GameView gameView, Board board) {
        this.menuController = menuController;
        this.board = board;
        
        boardStack = new Stack<>();
        
        AIOnTurn = board.getCurrentPlayer() instanceof AIPlayer;
        
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
                    this.onEnter();
                    break;
                case Z:
                    if(event.isControlDown()) {
                        undo();
                    }
                    break;
                case A:
                    if(event.isControlDown() && event.isShiftDown()) {      //TODO: remove AI test
                        AIPlayer player = new AIPlayer("Blah", "Blah", 0);
                        Coordinates aiToMove = player.getCoordinates(this);
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
            if(board.areValidCoordinates(x, y)) {
                board.move(x, y);
                view.getBoardPane().update(board.getOldCoordinates(), board.getMoveCoordinates());
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
            }
            else {
                x = board.getCurrentCoordinates().getX();
                y = board.getCurrentCoordinates().getY();
                view.setMessage(false, "'" + board.getCurrentPlayer().getName() + "' you are now on turn\nplease select a square to move to");
                view.setCurrent(x,y);
            }
        }
        
        if(board.getCurrentPlayer() instanceof AIPlayer) {
            AIOnTurn = true;
            onEnter();
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
