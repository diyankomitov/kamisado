package com.team11.kamisado.util;

import com.team11.kamisado.AI.AIState;
import com.team11.kamisado.AI.MiniMax;
import com.team11.kamisado.controllers.GameController;
import com.team11.kamisado.models.Board;
import com.team11.kamisado.models.Player;

import java.util.Random;

/**
 * Created by Diyan on 22/04/2017.
 */
public class AIMode implements Mode {
    private final int difficulty;
    
    public AIMode(int difficulty) {
        this.difficulty = difficulty;
    }
    
    @Override
    public void move(GameController controller) {
        Board board = controller.getBoard();
        Player player = board.getCurrentPlayer();
    
        if(board.isFirstMove()) {
            Random random = new Random();
            int i = random.nextInt(7);
            player.setCoordinates(i,0);
            controller.onEnter();
        }
        
        if(difficulty == 0) {
            int bound = board.getValidCoordinates().size();
        
            Random random = new Random();
            int i = random.nextInt(bound);
            int x = board.getValidCoordinates().get(i).getX();
            int y = board.getValidCoordinates().get(i).getY();
            player.setCoordinates(x,y);
        }
        else {
            MiniMax algorithm = new MiniMax(controller.getBoard());
            AIState AIState = algorithm.getOptimalAIState();
            int x = AIState.getBoard().getMoveCoordinates().getX();
            int y = AIState.getBoard().getMoveCoordinates().getY();
            player.setCoordinates(x,y);
        }
    
        controller.onEnter();
    }
    
    @Override
    public String getName() {
        return this.getClass().toString();
    }
}
