package com.team11.kamisado.views;

import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;

public class TowerView extends Pane {
    private static final double SQUARE_Y_TO_TOWER_Y_RATIO = 2.1;
    private static final double RADIUS_X_TO_OUTER_RX_RATIO = 1.5;
    private static final int WIDTH_TO_SQUARE_WIDTH_RATIO = 3;
    private static final double HEIGHT_TO_SQUARE_WIDTH_RATIO = 3.5;
    private static final int STROKE_TO_RADIUS_X_RATIO = 12;
    private static final int LOWERY_TO_OUTER_Y_PLUS_OUTER_RADIUS_X_RATIO = 5;
    
    private Ellipse outer;
    private Ellipse lower;
    
    public TowerView(SquareView square, Colors color, Colors type) {
        setCurrentSquare(square);
        
        outer = new Ellipse();
        binding(outer, 1, type);
        
        Ellipse inner = new Ellipse();
        binding(inner, RADIUS_X_TO_OUTER_RX_RATIO, color);
        
        lower = new Ellipse();
        binding(lower, 1, type);
        
        getChildren().addAll(lower, outer, inner);
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
        ellipse.setStroke(Colors.TRUEBLACK.getValue());
        ellipse.strokeWidthProperty().bind(outerRX.divide(STROKE_TO_RADIUS_X_RATIO));
    }
}