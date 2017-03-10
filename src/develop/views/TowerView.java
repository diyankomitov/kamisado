package develop.views;

import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;

public class TowerView extends Pane{
    private Ellipse outer;
    private Ellipse lower;
    
    private static final int WIDTHTOSQUAREWIDTHRATIO = 3;
    private static final double HEIGHTTOSQUAREWIDTHRATIO = 3.5;
    private static final int STROKETORADIUSXRATIO = 12;
    private static final int LOWERYTOOUTERYPLUSOUTERRADIUSXRATIO = 5;
    
    
    public TowerView(SquareView square, Colors color, Colors type) {
        setCurrentSquare(square);
        
        outer = new Ellipse();
        binding(outer, 1, type);
        
        Ellipse inner = new Ellipse();
        binding(inner, 1.5, color);
        
        lower = new Ellipse();
        binding(lower, 1, type);
        
        getChildren().addAll(lower, outer, inner);
    }
    
    public void setCurrentSquare(SquareView currentSquare) {
        DoubleProperty currentSquareWidth = currentSquare.widthProperty();
        this.translateXProperty().bind(currentSquare.xProperty().add(currentSquareWidth.divide(2)));
        this.translateYProperty().bind(currentSquare.yProperty().add(currentSquareWidth.divide(2)));
        this.prefWidthProperty().bind(currentSquareWidth.divide(WIDTHTOSQUAREWIDTHRATIO));
        this.prefHeightProperty().bind(currentSquareWidth.divide(HEIGHTTOSQUAREWIDTHRATIO));
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
            lower.centerYProperty().bind(outer.centerYProperty().add(outerRX.divide(LOWERYTOOUTERYPLUSOUTERRADIUSXRATIO)));
        }
        
        ellipse.setFill(fill.getValue());
        ellipse.setStroke(Colors.TRUEBLACK.getValue());
        ellipse.strokeWidthProperty().bind(outerRX.divide(STROKETORADIUSXRATIO));
    }
}