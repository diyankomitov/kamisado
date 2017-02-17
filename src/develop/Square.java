package develop;

import javafx.scene.shape.Rectangle;

public class Square extends Rectangle {
    public static final int SQUARE_SIZE = 100;
    private Colors color;
    
    public Square(int x, int y) {
        //set x, y, width, height
        super(x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        
        setStroke(Colors.BLACK.getValue());
        setStrokeWidth(3);
        
        // when width changes, x adjusts accordingly
        xProperty().bind(widthProperty().multiply(x));
        // when height changes, y adjusts accordingly
        yProperty().bind(heightProperty().multiply(y));
    }
    
    public Colors getColor() {
        return color;
    }
    
    public void setColor(Colors c) {
        color = c;
        setFill(color.getValue());
    }
}
    