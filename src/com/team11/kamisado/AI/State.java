package com.team11.kamisado.AI;

import com.team11.kamisado.models.Board;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Diyan on 20/03/2017.
 */
public class State {
    private LinkedList<State> children;
    private Board board;
    private Integer score;
    private int depth;
    
    public State(Board board) {
        this.board = board;
    }
    
    public State(State state) {
        this.board = new Board(state.getBoard());
    }
    
    public Board getBoard() {
        return board;
    }
    
    public Integer getScore() {
        return score;
    }
    
    public void addChild(State child) {
        children.add(child);
    }
    
    public State getChild(int index) {
        return children.get(index);
    }
    
    public void setChildren(LinkedList<State> children) {
        this.children = children;
    }
    
    public LinkedList<State> getChildren() {
        return children;
    }
    
    public void setBoard(Board board) {
        this.board = board;
    }
    
    public void setScore(Integer score) {
        this.score = score;
    }
    
    public boolean hasValue() {
        return score != null;
    }
    
    public int getDepth() {
        return depth;
    }
    
    public void setDepth(int depth) {
        this.depth = depth;
    }
}
