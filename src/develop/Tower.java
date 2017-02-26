package develop;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;

public class Tower extends Pane {
    private Square currentSquare;
    
    public Tower(Square square, Colors color, Colors type) {
        currentSquare = square;
        
        Ellipse outer = new Ellipse();
        outer.centerXProperty().bind(currentSquare.xProperty().add(currentSquare.widthProperty().divide(2)));
        outer.centerYProperty().bind(currentSquare.yProperty().add(currentSquare.heightProperty().divide(2)));
        outer.radiusXProperty().bind(currentSquare.widthProperty().divide(2).subtract(currentSquare.widthProperty().multiply(0.15)));
        outer.radiusYProperty().bind(outer.radiusXProperty().subtract(currentSquare.heightProperty().multiply(0.05)));
        outer.setFill(type.getValue());
        outer.setStroke(Colors.TRUEBLACK.getValue());
        outer.strokeWidthProperty().bind(outer.radiusXProperty().divide(12));
        
        Ellipse inner = new Ellipse();
        inner.centerXProperty().bind(outer.centerXProperty());
        inner.centerYProperty().bind(outer.centerYProperty());
        inner.radiusXProperty().bind(outer.radiusXProperty().subtract(currentSquare.widthProperty().multiply(0.1)));
        inner.radiusYProperty().bind(outer.radiusYProperty().subtract(currentSquare.heightProperty().multiply(0.1)));
        inner.setFill(color.getValue());
        inner.setStroke(Colors.TRUEBLACK.getValue());
        inner.strokeWidthProperty().bind(outer.strokeWidthProperty());
        
        Ellipse lower = new Ellipse();
        lower.centerXProperty().bind(outer.centerXProperty());
        lower.centerYProperty().bind(outer.centerYProperty().add(currentSquare.heightProperty().divide(15)));
        lower.radiusXProperty().bind(outer.radiusXProperty());
        lower.radiusYProperty().bind(outer.radiusYProperty());
        lower.setFill(type.getValue());
        lower.setStroke(Colors.TRUEBLACK.getValue());
        lower.strokeWidthProperty().bind(outer.strokeWidthProperty());
        
        getChildren().addAll(lower, outer, inner);
    }
    
    public void setCurrentSquare(Square currentSquare) {
        this.currentSquare = currentSquare;
    }
}