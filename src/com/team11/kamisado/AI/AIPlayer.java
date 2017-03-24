package com.team11.kamisado.AI;

import com.team11.kamisado.controllers.GameController;
import com.team11.kamisado.models.Player;
import com.team11.kamisado.util.Coordinates;

import java.util.Random;

/**
 * Created by Diyan on 20/03/2017.
 */
public class AIPlayer extends Player {
    
    private int difficulty;
    
    public AIPlayer(String name, String playerColor, int difficulty) {
        super(name, playerColor);
        
        this.difficulty = difficulty;
    }
    
    public Coordinates getCoordinates(GameController controller) {
        
        if(controller.getBoard().isFirstMove()) {
            Random random = new Random();
            int i = random.nextInt(7);
            return new Coordinates(i, 0);
        }
        
        if(difficulty == 0) {
            int bound = controller.getBoard().getValidCoordinates().size();
    
            Random random = new Random();
            int i = random.nextInt(bound);
    
            return controller.getBoard().getValidCoordinates().get(i);
        }
        else {
            MiniMax algorithm = new MiniMax(controller.getBoard());
            AIState AIState = algorithm.getOptimalAIState();
            return AIState.getBoard().getMoveCoordinates();
        }
        
    }
}
