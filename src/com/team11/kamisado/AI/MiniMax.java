package com.team11.kamisado.AI;

import com.team11.kamisado.models.Board;
import com.team11.kamisado.models.Player;
import com.team11.kamisado.util.Coordinates;

import java.util.LinkedList;
import java.util.List;

public class MiniMax {
    private AIState optimalAIState;
    private Player maximizer;
    private Player minimizer;
    
    public MiniMax(Board board) {
        
        maximizer = board.getCurrentPlayer();
        minimizer = board.getOtherPlayer();
        
        AIState currentAIState = new AIState(board);
        
        currentAIState.setChildren(generateNextStates(currentAIState));
        generateTree(currentAIState, 0);
        
        optimalAIState = miniMax(currentAIState, maximizer);
        
    }
    
    private void generateTree(AIState parent, int depth) {
        parent.setChildren(generateNextStates(parent));
        
        if(depth != 3) {
            for(AIState child : parent.getChildren()) {
                if(!child.getBoard().isWin() && !child.getBoard().isLock()) {
                    generateTree(child, depth + 1);
                }
            }
        }
    }
    
    public AIState getOptimalAIState() {
        return optimalAIState;
    }
    
    private AIState miniMax(AIState AIState, Player player) {
        
        Player currentPlayer = player == maximizer ? minimizer : maximizer;
        
        LinkedList<AIState> children = AIState.getChildren();
        if(children != null) {
            for(AIState child : children) {
                miniMax(child, currentPlayer);
            }
            if(AIState.getChildren().size() != 0) {
                return bestMove(AIState.getChildren(), currentPlayer);
            }
        }
        
        return AIState;
    }
    
    private AIState bestMove(LinkedList<AIState> statelist, Player player) {
        if(player == maximizer) {
            AIState max = statelist.get(0);
            
            for(AIState AIState : statelist) {
                max = AIState.getScore() > max.getScore() ? AIState : max;
            }
            
            return max;
        }
        else if(player == minimizer) {
            AIState min = statelist.get(0);
            
            for(AIState AIState : statelist) {
                min = AIState.getScore() > min.getScore() ? AIState : min;
            }
            
            return min;
        }
        return null;
    }
    
    private int evaluateBoard(AIState AIState) {
        Board board = AIState.getBoard();
        
        if(board.getWinner() == maximizer) {
            return +100;
        }
        if(board.getWinner() == minimizer) {
            return -100;
        }
        
        if(board.isLock()) {
            if(board.getCurrentPlayer() == maximizer) {
                return +10;
            }
            else if(board.getCurrentPlayer() == minimizer) {
                return -10;
            }
        }
        
        if(board.isLock()) {
            if(board.getCurrentPlayer() == maximizer) {
                return -1000;
            }
            else if(board.getCurrentPlayer() == minimizer) {
                return +1000;
            }
        }
        
        return 0;
    }
    
    private LinkedList<AIState> generateNextStates(AIState AIState) {
        
        LinkedList<AIState> AIStateList = new LinkedList<>();
        
        List<Coordinates> validCoordinates = AIState.getBoard().getValidCoordinates();
        for(Coordinates coordinates : validCoordinates) {
            AIState newAIState = new AIState(AIState);
            int x = coordinates.getX();
            int y = coordinates.getY();
            newAIState.getBoard().move(x, y);
            newAIState.setScore(evaluateBoard(newAIState));
            AIStateList.add(newAIState);
        }
        
        return AIStateList;
    }
}
