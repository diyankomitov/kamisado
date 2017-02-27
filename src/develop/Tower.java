package develop;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;

public class Tower extends Pane {
    private Ellipse outer;
    
    public Tower(Square square, Colors color, Colors type) {
        setCurrentSquare(square);
        
        outer = new Ellipse();
        outer.radiusXProperty().bind(this.prefWidthProperty());
        outer.radiusYProperty().bind(this.prefHeightProperty());
        bindColor(outer, type);
        
        Ellipse inner = new Ellipse();
        inner.radiusXProperty().bind(outer.radiusXProperty().divide(1.5));
        inner.radiusYProperty().bind(outer.radiusYProperty().divide(1.5));
        bindColor(inner, color);
        
        Ellipse lower = new Ellipse();
        lower.centerYProperty().bind(outer.centerYProperty().add(outer.radiusYProperty().divide(5)));
        lower.radiusXProperty().bind(outer.radiusXProperty());
        lower.radiusYProperty().bind(outer.radiusYProperty());
        bindColor(lower, type);
        
        getChildren().addAll(lower, outer, inner);
    }
    
    public void setCurrentSquare(Square currentSquare) {
        this.translateXProperty().bind(currentSquare.xProperty().add(currentSquare.widthProperty().divide(2)));
        this.translateYProperty().bind(currentSquare.yProperty().add(currentSquare.widthProperty().divide(2)));
        this.prefWidthProperty().bind(currentSquare.widthProperty().divide(3));
        this.prefHeightProperty().bind(currentSquare.widthProperty().divide(3.5));
    }
    
    private void bindColor(Ellipse ellipse, Colors fill) {
        ellipse.setFill(fill.getValue());
        ellipse.setStroke(Colors.TRUEBLACK.getValue());
        ellipse.strokeWidthProperty().bind(outer.radiusXProperty().divide(12));
    }
}