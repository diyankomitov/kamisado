package develop.views;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import static develop.views.BoardView.BOARD_LENGTH;

public class SquareView extends Rectangle {
    private static final double STROKETOWIDTHRATIO = 0.015;
    
    public SquareView(Pane parent, int x, int y, Colors color) {
//        NumberBinding minBoardDimension = Bindings.min(parent.widthProperty(), parent.heightProperty());
        this.widthProperty().bind(parent.widthProperty().divide(BOARD_LENGTH));
        this.heightProperty().bind(this.widthProperty());
        this.xProperty().bind(this.widthProperty().multiply(x));
        this.yProperty().bind(this.heightProperty().multiply(y));
        this.setFill(color.getValue());
        this.setStroke(Colors.TRUEBLACK.getValue());
        this.setStrokeType(StrokeType.INSIDE);
        this.strokeWidthProperty().bind(this.widthProperty().multiply(STROKETOWIDTHRATIO));
    }
    
    public void moveSquare(int x, int y) {
        this.xProperty().bind(this.widthProperty().multiply(x));
        this.yProperty().bind(this.heightProperty().multiply(y));
    }
}