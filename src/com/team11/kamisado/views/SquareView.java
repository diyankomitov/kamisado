package com.team11.kamisado.views;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class SquareView extends Rectangle {
    private static final double STROKETOWIDTHRATIO = 66;
    
    public SquareView(Pane parent, int x, int y, Colors color) {
        this.widthProperty().bind(parent.widthProperty().divide(BoardPane.BOARD_LENGTH));
        this.heightProperty().bind(this.widthProperty());
        this.xProperty().bind(this.widthProperty().multiply(x));
        this.yProperty().bind(this.heightProperty().multiply(y));
        this.setFill(color.getValue());
        this.setStroke(Colors.TRUEBLACK.getValue());
        this.setStrokeType(StrokeType.INSIDE);
        this.strokeWidthProperty().bind(this.widthProperty().divide(STROKETOWIDTHRATIO));
    }
    
    public void moveSquare(int x, int y) {
        this.xProperty().bind(this.widthProperty().multiply(x));
        this.yProperty().bind(this.heightProperty().multiply(y));
    }
}