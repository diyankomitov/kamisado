package develop;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum Colors {
    
    ORANGE ("#ea6924"),
    NAVY ("#025391"),
    BLUE ("#70c3ab"),
    PINK ("#e53981"),
    YELLOW ("#fbdb06"),
    RED ("#c12127"),
    GREEN ("#00914a"),
    BROWN ("#3d271f"),
    WHITE ("#ffffff"),
    BLACK ("#000000");
    
    private final String value;
    
    Colors(String value) {
        this.value = value;
    }
    
    public Color getValue() {
        return Color.valueOf(value);
    }
}
