package com.team11.kamisado.views;

import javafx.scene.paint.Color;

public enum Colors {
    
    ORANGE ("#ea6924"),
    NAVY ("#025391"),
    BLUE ("#70c3ab"),
    PINK ("#e53981"),
    YELLOW ("#fbdb06"),
    RED ("#c12127"),
    GREEN ("#00914a"),
    BROWN ("#3d271f"),
    WHITE ("#f2f2f2"),
    BLACK ("#333333"),
    TRUEBLACK ("#000000"),
    TRUEWHITE ("#ffffff"),
    TRANSPARENT ("T");
    
    private final String value;
    
    Colors(String value) {
        this.value = value;
    }
    
    public Color getValue() {
        if(value.equals("T")) {
            return Color.TRANSPARENT;
        }
        else
        {
            return Color.valueOf(value);
        }
    }
}
