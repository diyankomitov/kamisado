package com.team11.kamisado.AI;

import com.team11.kamisado.models.Board;

import java.util.LinkedList;

public class AIState {
    private LinkedList<AIState> children;
    private Board board;
    private Integer score;
    
    public AIState(Board board) {
        this.board = board;
    }
    
    public AIState(AIState AIState) {
        this.board = new Board(AIState.getBoard());
    }
    
    public Board getBoard() {
        return board;
    }
    
    public Integer getScore() {
        return score;
    }
    
    public void setScore(Integer score) {
        this.score = score;
    }
    
    public LinkedList<AIState> getChildren() {
        return children;
    }
    
    public void setChildren(LinkedList<AIState> children) {
        this.children = children;
    }
}
