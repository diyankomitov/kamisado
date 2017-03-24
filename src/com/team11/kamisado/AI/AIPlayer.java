package com.team11.kamisado.AI;

import com.team11.kamisado.controllers.GameController;
import com.team11.kamisado.models.Player;
import com.team11.kamisado.util.Coordinates;

import java.util.LinkedList;
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
            MiniMax algo = new MiniMax(controller.getBoard());
            State state = algo.getRightState();
            return state.getBoard().getMoveCoordinates();
        }
        
    }
    
    public State generateInitialMoves(State state) {
        
        LinkedList<State> stateList = new LinkedList<>();
        
        for(int i = 0; i < 8; i++) {
            Coordinates coordinates = new Coordinates(0,i);
            State newState = new State(state);
            int x = coordinates.getX();
            int y = coordinates.getY();
            newState.getBoard().setCurrentSquare(x, y);
            newState.getBoard().setCurrentCoordinates();
            newState.getBoard().setValidCoordinates();
            newState.getBoard().setFirstMoveToFalse();
            newState.setScore(0);
            stateList.add(newState);
        }
        
        state.setChildren(stateList);
        
        return state;
        
    }
}
