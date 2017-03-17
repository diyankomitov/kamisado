package com.team11.kamisado.controllers;

import com.team11.kamisado.models.Board;
import com.team11.kamisado.models.Player;
import com.team11.kamisado.views.BoardPane;
import com.team11.kamisado.views.GameView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.effect.GaussianBlur;
import javafx.util.Duration;

/**
 * Created by Diyan on 17/03/2017.
 */
public class SpeedGameController extends GameController {
    private static final int STARTTIME = 5;
    private static final double BLURRADIUS = 63;
    private final Timeline timeline;
    private IntegerProperty timeSeconds;
    private GameView view;
    private MenuController menuController;
    private int y;
    private int x;
    private boolean firstMove;
    private Board board;
    private Player playerOne;
    private Player currentPlayer;
    private Player playerTwo;
    
    public SpeedGameController(MenuController menuController, GameView gameView, Board board, Player playerOne, Player playerTwo) {
        super(menuController, gameView, board, playerOne, playerTwo);
        
        this.view = gameView;
        this.menuController = menuController;
        this.firstMove = true;
        this.board = board;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.currentPlayer = playerOne;
        
        
        timeSeconds = new SimpleIntegerProperty(STARTTIME);
        
        gameView.drawTimer(timeSeconds);
        
        timeSeconds.set(STARTTIME);
        
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(STARTTIME+1),
                        new KeyValue(timeSeconds, 0)));
        timeline.playFromStart();
        
        System.out.println(timeSeconds.toString());
    }
    
    @Override
    public void setActiveController() {
        view.drawGameView();
        view.setEffect(null);
        view.requestFocus();
        this.start();
        timeline.play();
    }
    
    @SuppressWarnings("Duplicates")
    @Override
    public void handle(long now) {
        if(timeSeconds.getValue() <= 0) {
            winGame(currentPlayer == playerOne ? playerTwo : playerOne);
        }
        view.requestFocus();
        view.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case ESCAPE: {
                    timeline.pause();
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
                            restartTimer();
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
                        restartTimer();
                    }
            }
        });
    }
    
    private void restartTimer() {
        timeline.stop();
        timeSeconds.set(5);
        timeline.playFromStart();
    }
    
    public void winGame(Player winner) {
        this.stop();
        timeline.stop();
        view.setEffect(new GaussianBlur(BLURRADIUS));
        view.stopFadeTransition();
        menuController.win(winner.getName());
    }
}
