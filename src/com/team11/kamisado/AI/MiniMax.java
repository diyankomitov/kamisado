package com.team11.kamisado.AI;

import com.team11.kamisado.models.Board;
import com.team11.kamisado.models.Player;
import com.team11.kamisado.util.Coordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Diyan on 20/03/2017.
 */
public class MiniMax {
    private int depth;
    private State rightState;
    private Player maximizer;
    private Player minimizer;
    private int currentDepth;
    private int maxDepth = 2;
    
    public MiniMax(Board board) {
        
        maximizer = board.getCurrentPlayer();
        minimizer = board.getOtherPlayer();
        
        State currentState = new State(board);
        
        
        currentState.setChildren(generateNextStates(currentState));
        generateTree(currentState, 0);
        
        //int depth = 0;
        
        rightState = minimax(currentState, maximizer);
        
        
    }
    
    public void generateTree(State parent, int depth) {
            
            parent.setChildren(generateNextStates(parent));
            
            if(depth == 3) {
                return;
            }
            else {
                for(State child : parent.getChildren()) {
                    if(!child.getBoard().hasWon() && !child.getBoard().isLock()) {
                        generateTree(child, depth+1);
                    }
                }
            }
    }
    
    public State getRightState() {
        return rightState;
    }
    
    public State minimax(State state, Player player) {
    
        //this.depth = depth;

        Player currentPlayer = player == maximizer ? minimizer : maximizer;
    
        LinkedList<State> children = state.getChildren();
        if(children !=  null) {
            for(int i = 0; i < children.size(); i++) {
                State child = children.get(i);
                minimax(child, currentPlayer);
            }
            if(state.getChildren().size() != 0) {
                return bestMove(state.getChildren(), currentPlayer);
            }
        }
    
        return state;
        
//
//        LinkedList<State> statelist = generateNextStates(state);
//
//        //ArrayList<Integer> scorelist = new ArrayList<>();
//
//        for(int i = 0; i < statelist.size(); i++) {
//            if(statelist.get(i).getScore() != 0) {
//                return bestMove(statelist, currentPlayer);
//            }
//        }
//        if(depth == 6) {
//            return statelist.get(0);
//        }
//        else {
//            return minimax(statelist.get(0), player, depth+1);
//        }
    }
    
    
    public State bestMove(LinkedList<State> statelist, Player player) {
        if(player == maximizer) {
            State max = statelist.get(0);
    
            for(int i = 0; i < statelist.size(); i++) {
                State state = statelist.get(i);
                max = state.getScore() > max.getScore() ? state : max;
            }
            
            return max;
        }
        else if(player == minimizer){
            State min = statelist.get(0);
    
            for(int i = 0; i < statelist.size(); i++) {
                State state = statelist.get(i);
                min = state.getScore() > min.getScore() ? state : min;
            }
            
            return min;
        }
        return null;
    }
    
    
    
    
    public int evaluateBoard(State state) {
        Board board = state.getBoard();
        
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
    
    public LinkedList<State> generateNextStates(State state) {
        
        LinkedList<State> stateList = new LinkedList<>();
    
        List<Coordinates> validCoordinates = state.getBoard().getValidCoordinates();
        for(int i = 0; i < validCoordinates.size(); i++) {
            Coordinates coordinates = validCoordinates.get(i);
            State newState = new State(state);
            int x = coordinates.getX();
            int y = coordinates.getY();
            newState.getBoard().move(x, y);
            newState.setScore(evaluateBoard(newState));
            stateList.add(newState);
        }
        
        return stateList;
    }
    
    
    
    
    
    
    //public static void
    
//    public static void printStates(State parentState) {
//        for(State child: parentState.getChildren()) {
//            child.getBoard().print();
//            System.out.println();
//            printStates(child);
//        }
//    }
}
    
//    private Player maximizer;
//    private Player minimizer;
//    private Board correctBoard;
//    private Player player;
//    private List<Board> boards;
//    private List<Integer> scores;
//    private Integer score;
//    private ArrayList<State> states;
//
//    public MiniMax(Board currentBoard, Player player) {
//        this.player = player;
//
//        boards = new ArrayList<>();
//        scores = new ArrayList<>();
//
//        createPossibleBoards(currentBoard);
//
//        for(Board board : boards) {
//            score = 0;
//            runAlgorithm(new Board(board));
//            scores.add(score);
//        }
//        System.out.println("ammount of boards: " + boards.size() + " ammount of scores: " +
//                scores.size());
//
//        boolean found = false;
//        int i =  0;
//
//        int max = 0;
//        int maxIndex = 0;
//
//        for(Integer score : scores) {
//            if(score>=max) {
//                maxIndex = scores.indexOf(score);
//            }
//        }
//
//        correctBoard = boards.get(maxIndex);
//        correctBoard.print();
//    }
//
//    public MiniMax(Board currentBoard) {
//        maximizer = currentBoard.getCurrentPlayer();
//        minimizer = currentBoard.getOtherPlayer();
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    }
//
//    public void bar(State state) {
//
//
//
//    }
//
//    public State foo(Board currentBoard, Player player) {
//
//        ArrayList<State> states;
//        if(player == maximizer) {
//            states = generateStates(currentBoard);
//            State max = states.get(0);
//
//            for(State state : states) {
//                if(state.hasValue()) {
//                    if(state.getScore() > max.getScore()) {
//                        max = state;
//                    }
//                }
//                else {
//                    state.getBoard().hasWon();
//                }
//
//            }
//            return max;
//        }
//        else {
//            generateStates(currentBoard);
//            State min = states.get(0);
//
//            for(State state : states) {
////                state.getBoard().print();
////                System.out.println();
////                System.out.println("Evaluation: " + evaluateBoard(state.getBoard(), minimizer));
//
//                if(state.getScore() < min.getScore()) {
//                    min = state;
//                }
//            }
//            return min;
//        }
//    }
//
//    public ArrayList<State> generateStates(Board board) {
//        ArrayList<State> states = new ArrayList<>();
//
//        for(Coordinates coordinate : board.getValidCoordinates()) {
//            State state = new State();
//            Board newBoard = new Board(board);
//            newBoard.move(coordinate.getX(), coordinate.getY());
//            state.setBoard(newBoard);
//            states.add(state);
//        }
//
//        return states;
//    }
//
//    public int evaluateBoard(Board board, Player currentPlayer) {
//        if(board.hasWon() && currentPlayer == maximizer) {
//            return +10;
//        }
//        else if(board.hasWon() && currentPlayer == minimizer) {
//            return -10;
//        }
//        return 0;
//    }
//
//
//
//
//    public void run(Board board, Player currentPlayer) {
//
//
//
////        List<Coordinates> coordinatesList = new ArrayList<>();
////        for(Coordinates coordinate : board.getValidCoordinates()) {
////            coordinatesList.add(coordinate);
////        }
////
////        for(Coordinates coordinate : board.getValidCoordinates()) {
////            Board board1 = new Board(board);
////            board1.move(coordinate.getX(),coordinate.getY());
////
////            State state = new State();
////            states.add(state);
////            state.setBoard(board1);
////
////            state.setScore(run());
////        }
//
//
//
//    }
//
//    public Coordinates getCoordinates() {
//        System.out.println("coord x: " + correctBoard.getMoveCoordinates().getX() +
//                "coord y: " + correctBoard.getMoveCoordinates().getY());
//        return correctBoard.getMoveCoordinates();
//    }
//
//    private void runAlgorithm(Board board) {
//        List<Coordinates> coordinatesList = new ArrayList<>();
//
//        for(Coordinates coordinate : board.getValidCoordinates()) {
//            coordinatesList.add(coordinate);
//        }
//
//
//        if(board.hasWon()) {
//            score += board.getCurrentPlayer().equals(player)? 10 : (-10);
//        }
//        else {
//            for(Coordinates coordinate: coordinatesList) {
//                int x = coordinate.getX();
//                int y = coordinate.getY();
//                board.move(x,y);
//                board.setCurrentSquare(x,y);
//                board.setCurrentCoordinates();
//                board.setValidCoordinates();
//                board.switchCurrentPlayer();
//                runAlgorithm(board);
//            }
//        }
//    }
//
//    private void createPossibleBoards(Board board) {
//        Board newBoard = new Board(board);
//        List<Coordinates> coordinatesList = new ArrayList<>();
//
//        for(Coordinates coordinate : board.getValidCoordinates()) {
//            coordinatesList.add(coordinate);
//        }
//
//
//        if(newBoard.isFirstMove()) {
//            for(int i = 0; i < 8; i++) {
//                newBoard.setCurrentSquare(i, 0);
//                newBoard.setCurrentCoordinates();
//                newBoard.setValidCoordinates();
//                newBoard.setFirstMoveToFalse();
//                    boards.add(newBoard);
//            }
//        }
//        else {
//            for(Coordinates coordinate: coordinatesList) {
//                int x = coordinate.getX();
//                int y = coordinate.getY();
//                newBoard.move(x,y);
//                boards.add(newBoard);
//                newBoard = new Board(board);
//            }
//        }
//    }
//}