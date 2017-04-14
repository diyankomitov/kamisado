package com.team11.kamisado.views;

import javafx.css.PseudoClass;
import javafx.scene.layout.VBox;

public class MenuViewBase extends VBox{
    
    private static PseudoClass TRANSPARENT = PseudoClass.getPseudoClass("transparent");
    
    public void setTransparent(boolean transparent) {
        this.pseudoClassStateChanged(TRANSPARENT, transparent);
    }
}
