package develop.views;

import develop.models.Board;
import develop.util.Observer;
import javafx.scene.layout.Pane;

import static develop.views.Colors.*;

public class BoardPane extends Pane implements Observer {
    public static final int BOARD_LENGTH = 8;
    
    private SquareView[][] squares = new SquareView[BOARD_LENGTH][BOARD_LENGTH];
    private TowerView[][] towers = new TowerView[BOARD_LENGTH][BOARD_LENGTH];
    
    private Board board;
    
    public BoardPane(Board board) {
        this.board = board;
        this.board.subscribe(this);
        
        createBoard();
        drawTowers();
    }
    
    @Override
    public void update() {
        int curX = board.getCurrentCoordinates().getX();
        int curY = board.getCurrentCoordinates().getY();
        int newX = board.getMoveCoordinates().getX();
        int newY = board.getMoveCoordinates().getY();
        
        TowerView curTower = towers[curY][curX];
        
        SquareView newSquare = squares[newY][newX];
        System.out.println("newY: " + newY + " New X: " + newX + "   " + newSquare.toString());
        
        curTower.setCurrentSquare(newSquare);
        towers[newY][newX] = curTower;
        towers[curY][curX] = null;
    }
    
    private void createBoard() {
        Colors color;
        
        for(int y = 0; y < BOARD_LENGTH; y++) {
            for(int x = 0; x < BOARD_LENGTH; x++) {
                switch(board.getSquare(x,y)) {
                    case "O":
                        color = ORANGE;
                        break;
                    case "N":
                        color = NAVY;
                        break;
                    case "B":
                        color = BLUE;
                        break;
                    case "P":
                        color = PINK;
                        break;
                    case "Y":
                        color = YELLOW;
                        break;
                    case "R":
                        color = RED;
                        break;
                    case "G":
                        color = GREEN;
                        break;
                    default:
                        color = BROWN;
                        break;
                }
                SquareView square = new SquareView(this, x, y, color);
    
                squares[y][x] = square;
                this.getChildren().add(square);
            }
        }
    }
    
    private void drawTowers() {
        String tower;
        Colors type;
        Colors color;
        
        for(int y = 0; y < BOARD_LENGTH; y++) {
            for(int x = 0; x < BOARD_LENGTH; x++) {
                this.getChildren().remove(towers[y][x]);
                towers[y][x] = null;
                
                if(board.isTower(x,y)) {
                    
                    tower = board.getTower(x,y);
    
                    if(tower.startsWith("W")) {
                        type = WHITE;
                    }
                    else {
                        type = BLACK;
                    }
    
                    if(tower.endsWith("O")) {
                        color = ORANGE;
                    }
                    else if(tower.endsWith("N")) {
                        color = NAVY;
                    }
                    else if(tower.endsWith("B")) {
                        color = BLUE;
                    }
                    else if(tower.endsWith("P")) {
                        color = PINK;
                    }
                    else if(tower.endsWith("Y")) {
                        color = YELLOW;
                    }
                    else if(tower.endsWith("R")) {
                        color = RED;
                    }
                    else if(tower.endsWith("G")) {
                        color = GREEN;
                    }
                    else {
                        color = BROWN;
                    }
                    
                    TowerView towerView = new TowerView(squares[y][x], color, type);
                    towers[y][x] = towerView;
                    this.getChildren().add(towerView);
                }
            }
        }
    }
}