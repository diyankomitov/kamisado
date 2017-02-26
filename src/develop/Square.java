package develop;

import javafx.scene.shape.Rectangle;

public class Square extends Rectangle {
    public static final int SQUARE_SIZE = 100;
    private Colors color;
    
    public Square(int x, int y) {
        setStroke(Colors.TRUEBLACK.getValue());
        strokeWidthProperty().bind(widthProperty().divide(33));
        
        this.heightProperty().bind(this.widthProperty());
        this.xProperty().bind(this.widthProperty().multiply(x));
        this.yProperty().bind(this.heightProperty().multiply(y));
    }
    
    public Colors getColor() {
        return color;
    }
    
    public void setColor(Colors c) {
        color = c;
        setFill(color.getValue());
    }
}