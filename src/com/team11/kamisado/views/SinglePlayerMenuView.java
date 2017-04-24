package com.team11.kamisado.views;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class SinglePlayerMenuView extends MenuViewBase {
    
    private RadioButton easyRadio;
    private RadioButton hardRadio;
    private VBox difficultyRadioWrapper;
    
    private List<Node> handledNodes;
    
    public SinglePlayerMenuView() {
        super();
        this.handledNodes = getHandledNodes();
        
        initSinglePlayerScreen();
        
        this.setId("singlePlayerScreen");
        this.getStyleClass().add("transparentMenu");
    }
    
    public void drawSinglePlayerScreen() {
        this.getChildren().clear();
        this.getChildren().addAll(getMenuGameTitle(), getSinglePlayerNameWrapper(), getColorRadioWrapper(), difficultyRadioWrapper, getModeRadioWrapper(), getRandomButton(), getButtonsWrapper());
    }
    
    private void initSinglePlayerScreen() {
        Label difficultyLabel = new Label("Select the difficulty");
        difficultyLabel.getStyleClass().add("nameLabel");
        ToggleGroup difficultyToggleGroup = new ToggleGroup();
        easyRadio = new RadioButton("Easy");
        easyRadio.getStyleClass().add("radioButton");
        easyRadio.setToggleGroup(difficultyToggleGroup);
        easyRadio.setSelected(true);
        handledNodes.add(easyRadio);
        hardRadio = new RadioButton("Hard");
        hardRadio.getStyleClass().add("radioButton");
        hardRadio.setToggleGroup(difficultyToggleGroup);
        handledNodes.add(hardRadio);
        HBox difficultyRadioBox = new HBox(easyRadio, hardRadio);
        difficultyRadioBox.getStyleClass().add("radioBox");
        difficultyRadioWrapper = new VBox(difficultyLabel, difficultyRadioBox);
        difficultyRadioWrapper.getStyleClass().add("radioWrapper");
        
    }
    
    public RadioButton getEasyRadio() {
        return easyRadio;
    }
    
    public RadioButton getHardRadio() {
        return hardRadio;
    }
}
