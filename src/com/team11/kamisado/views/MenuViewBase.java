package com.team11.kamisado.views;

import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;

public class MenuViewBase extends VBox{
    
    private static PseudoClass TRANSPARENT = PseudoClass.getPseudoClass("transparent");
    private final Button playButton;
    private final Button backButton;
    private final HBox buttonsWrapper;
    private final TextFlow menuGameTitle;
    private RadioButton normalRadio;
    private RadioButton speedRadio;
    private VBox modeRadioWrapper;
    private List<Node> handledNodes;
    
    public MenuViewBase() {
        handledNodes = new ArrayList<>();
    
        menuGameTitle = drawTitle();
        menuGameTitle.setId("gameTitle");
    
        playButton = new Button("Play");
        playButton.getStyleClass().add("nameButton");
        handledNodes.add(playButton);
        backButton = new Button("Go Back");
        backButton.getStyleClass().add("nameButton");
        handledNodes.add(backButton);
        buttonsWrapper = new HBox(playButton, backButton);
        buttonsWrapper.setId("buttonsWrapper");
    
        Label modeLabel = new Label("Select the game mode");
        modeLabel.getStyleClass().add("nameLabel");
        ToggleGroup modeToggleGroup = new ToggleGroup();
        normalRadio = new RadioButton("Normal");
        normalRadio.getStyleClass().add("radioButton");
        normalRadio.setToggleGroup(modeToggleGroup);
        normalRadio.setSelected(true);
        handledNodes.add(normalRadio);
        speedRadio = new RadioButton("Speed");
        speedRadio.getStyleClass().add("radioButton");
        speedRadio.setToggleGroup(modeToggleGroup);
        handledNodes.add(speedRadio);
        HBox modeRadioBox = new HBox(normalRadio, speedRadio);
        modeRadioBox.getStyleClass().add("radioBox");
        modeRadioWrapper = new VBox(modeLabel, modeRadioBox);
        modeRadioWrapper.getStyleClass().add("radioWrapper");
    }
    
    public VBox getModeRadioWrapper() {
        return modeRadioWrapper;
    }
    
    public RadioButton getNormalRadio() {
        return normalRadio;
    }
    
    public RadioButton getSpeedRadio() {
        return speedRadio;
    }
    
    public List<Node> getHandledNodes() {
        return handledNodes;
    }
    
    public TextFlow getMenuGameTitle() {
        return menuGameTitle;
    }
    
    public Button getPlayButton() {
        return playButton;
    }
    
    public Button getBackButton() {
        return backButton;
    }
    
    public HBox getButtonsWrapper() {
        return buttonsWrapper;
    }
    
    private Text drawLetter(String name, Colors color) {
        Text letter = new Text(name);
        letter.setFill(color.getValue());
        letter.getStyleClass().add("titleLetter");
        return letter;
    }
    
    private TextFlow drawTitle() {
        return new TextFlow(drawLetter("K", Colors.ORANGE), drawLetter("A", Colors.NAVY), drawLetter("M", Colors.BLUE), drawLetter("I", Colors.PINK), drawLetter("S", Colors.YELLOW), drawLetter("A", Colors.RED), drawLetter("D", Colors.GREEN), drawLetter("O", Colors.BROWN));
    }
    
    public void setTransparent(boolean transparent) {
        this.pseudoClassStateChanged(TRANSPARENT, transparent);
    }
}
