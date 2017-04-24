package com.team11.kamisado.views;

import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

import static com.team11.kamisado.views.Colors.*;

public class TowerView extends Pane {
    private static final double SQUARE_Y_TO_TOWER_Y_RATIO = 2.1;
    private static final double RADIUS_X_TO_OUTER_RX_RATIO = 1.5;
    private static final int WIDTH_TO_SQUARE_WIDTH_RATIO = 3;
    private static final double HEIGHT_TO_SQUARE_WIDTH_RATIO = 3.5;
    private static final int STROKE_TO_RADIUS_X_RATIO = 12;
    private static final int LOWERY_TO_OUTER_Y_PLUS_OUTER_RADIUS_X_RATIO = 5;
    private static final double LINE_SIZE = 1.3;
    private static final double DIAGONAL_LINE_OFFSET = 0.1;
    
    private Ellipse outer;
    private Ellipse lower;
    
    public TowerView(SquareView square, Colors color, Colors type, int sumoLevel) {
        setCurrentSquare(square);
        
        outer = new Ellipse();
        binding(outer, 1, type);
        
        Ellipse inner = new Ellipse();
        binding(inner, RADIUS_X_TO_OUTER_RX_RATIO, color);
        
        lower = new Ellipse();
        binding(lower, 1, type);
    
        Colors lineStroke = type == BLACK ? WHITE : BLACK;
        
        Line lineOne = new Line();
        lineOne.startXProperty().bind(outer.centerXProperty());
        lineOne.startYProperty().bind(outer.centerYProperty().add(outer.radiusYProperty().divide(LINE_SIZE)));
        lineOne.endXProperty().bind(outer.centerXProperty());
        lineOne.endYProperty().bind(outer.centerYProperty().subtract(outer.radiusYProperty().divide(LINE_SIZE)));
        lineOne.strokeWidthProperty().bind(outer.radiusXProperty().divide(4));
        lineOne.setStroke(lineStroke.getValue());
    
        Line lineTwo = new Line();
        lineTwo.startXProperty().bind(outer.centerXProperty().add(outer.radiusXProperty().divide(LINE_SIZE)));
        lineTwo.startYProperty().bind(outer.centerYProperty());
        lineTwo.endXProperty().bind(outer.centerXProperty().subtract(outer.radiusXProperty().divide(LINE_SIZE)));
        lineTwo.endYProperty().bind(outer.centerYProperty());
        lineTwo.strokeWidthProperty().bind(outer.radiusXProperty().divide(4));
        lineTwo.setStroke(lineStroke.getValue());
        
        Line lineThree = new Line();
        lineThree.startXProperty().bind(outer.centerXProperty().add(outer.radiusXProperty().divide(LINE_SIZE).divide(LINE_SIZE + DIAGONAL_LINE_OFFSET)));
        lineThree.startYProperty().bind(outer.centerYProperty().add(outer.radiusYProperty().divide(LINE_SIZE).divide(LINE_SIZE + DIAGONAL_LINE_OFFSET)));
        lineThree.endXProperty().bind(outer.centerXProperty().subtract(outer.radiusXProperty().divide(LINE_SIZE).divide(LINE_SIZE + DIAGONAL_LINE_OFFSET)));
        lineThree.endYProperty().bind(outer.centerYProperty().subtract(outer.radiusYProperty().divide(LINE_SIZE).divide(LINE_SIZE + DIAGONAL_LINE_OFFSET)));
        lineThree.strokeWidthProperty().bind(outer.radiusXProperty().divide(4));
        lineThree.setStroke(lineStroke.getValue());
        
        getChildren().addAll(lower, outer);
        
        if(sumoLevel >= 1) {
            getChildren().add(lineOne);
        }
        if(sumoLevel >= 2) {
            getChildren().add(lineTwo);
        }
        if(sumoLevel == 3) {
            getChildren().add(lineThree);
        }
        
        getChildren().add(inner);
    }
    
    public void setCurrentSquare(SquareView currentSquare) {
        DoubleProperty currentSquareWidth = currentSquare.widthProperty();
        this.translateXProperty().bind(currentSquare.xProperty().add(currentSquareWidth.divide(2)));
        this.translateYProperty().bind(currentSquare.yProperty().add(currentSquareWidth.divide(SQUARE_Y_TO_TOWER_Y_RATIO)));
        this.prefWidthProperty().bind(currentSquareWidth.divide(WIDTH_TO_SQUARE_WIDTH_RATIO));
        this.prefHeightProperty().bind(currentSquareWidth.divide(HEIGHT_TO_SQUARE_WIDTH_RATIO));
    }
    
    private void binding(Ellipse ellipse, double radiusXToOuterRXRatio, Colors fill) {
        DoubleProperty outerRX = outer.radiusXProperty();
        DoubleProperty outerRY = outer.radiusYProperty();
        
        if(ellipse == outer) {
            outerRX.bind(this.prefWidthProperty());
            outerRY.bind(this.prefHeightProperty());
        }
        else {
            ellipse.radiusXProperty().bind(outerRX.divide(radiusXToOuterRXRatio));
            ellipse.radiusYProperty().bind(outerRY.divide(radiusXToOuterRXRatio));
        }
        
        if(ellipse == lower) {
            lower.centerYProperty().bind(outer.centerYProperty().add(outerRX.divide(LOWERY_TO_OUTER_Y_PLUS_OUTER_RADIUS_X_RATIO)));
        }
        
        ellipse.setFill(fill.getValue());
        ellipse.setStroke(TRUEBLACK.getValue());
        ellipse.strokeWidthProperty().bind(outerRX.divide(STROKE_TO_RADIUS_X_RATIO));
    }
}