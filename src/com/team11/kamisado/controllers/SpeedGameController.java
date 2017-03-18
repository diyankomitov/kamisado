package com.team11.kamisado.controllers;

import com.team11.kamisado.models.Board;
import com.team11.kamisado.models.Player;
import com.team11.kamisado.views.GameView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

public class SpeedGameController extends GameController {
    private static final int TIME_LIMIT = 5;
    private final Timeline timeline;
    private IntegerProperty timeSeconds;
    private boolean firstMove;
    private Player currentPlayer;
    
    public SpeedGameController(MenuController menuController, GameView gameView, Board board, Player playerOne, Player playerTwo) {
        super(menuController, gameView, board, playerOne, playerTwo);
        
        this.firstMove = true;
        this.currentPlayer = playerOne;
        
        timeSeconds = new SimpleIntegerProperty(TIME_LIMIT);
        gameView.drawTimer(timeSeconds);
        
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
            timeSeconds.set(timeSeconds.getValue() - 1);
            if(timeSeconds.getValue() <= 0) {
                winGame(currentPlayer == playerOne ? playerTwo : playerOne);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    @Override
    public void setActiveController() {
        super.setActiveController();
        timeline.play();
    }
    
    public void onEscape() {
        timeline.stop();
        super.onEscape();
    }
    
    public void onEnter() {
        if(firstMove) {
            firstMove = false;
        }
        else {
            restartTimer();
        }
        super.onEnter();
    }
    
    public void winGame(Player winner) {
        timeline.stop();
        super.winGame(winner);
    }
    
    private void restartTimer() {
        timeline.stop();
        timeSeconds.set(TIME_LIMIT);
        timeline.play();
    }
}