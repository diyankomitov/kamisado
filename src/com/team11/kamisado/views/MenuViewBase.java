package com.team11.kamisado.views;

import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
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
    private final CheckBox randomButton;
    private RadioButton normalRadio;
    private RadioButton speedRadio;
    private VBox modeRadioWrapper;
    private List<Node> handledNodes;
    
    private RadioButton blackRadio;
    private RadioButton whiteRadio;
    private VBox colorRadioWrapper;
    private TextField singlePlayerName;
    private Label singlePlayerError;
    private VBox singlePlayerNameWrapper;
    
    public MenuViewBase() {
        handledNodes = new ArrayList<>();
    
        menuGameTitle = drawTitle();
        menuGameTitle.setId("gameTitle");
    
        Label singlePlayerLabel = new Label("Player");
        singlePlayerLabel.getStyleClass().add("nameLabel");
        singlePlayerName = new TextField();
        singlePlayerName.getStyleClass().add("nameField");
        singlePlayerName.setPromptText("Enter Name");
        handledNodes.add(singlePlayerName);
        singlePlayerError = new Label("");
        singlePlayerError.getStyleClass().add("nameError");
        singlePlayerNameWrapper = new VBox(singlePlayerLabel, singlePlayerName, singlePlayerError);
        singlePlayerNameWrapper.setId("singlePlayerNameWrapper");
        
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
    
        Label colorRadioLabel = new Label("Select your color");
        colorRadioLabel.getStyleClass().add("nameLabel");
        ToggleGroup colorToggleGroup = new ToggleGroup();
        blackRadio = new RadioButton("Black");
        blackRadio.getStyleClass().add("radioButton");
        blackRadio.setToggleGroup(colorToggleGroup);
        blackRadio.setSelected(true);
        handledNodes.add(blackRadio);
        whiteRadio = new RadioButton("White");
        whiteRadio.getStyleClass().add("radioButton");
        whiteRadio.setToggleGroup(colorToggleGroup);
        handledNodes.add(whiteRadio);
        HBox radioBox = new HBox(blackRadio, whiteRadio);
        radioBox.getStyleClass().add("radioBox");
        colorRadioWrapper = new VBox(colorRadioLabel, radioBox);
        colorRadioWrapper.getStyleClass().add("radioWrapper");
        colorRadioWrapper.setId("colorRadioWrapper");
    
        randomButton = new CheckBox("Randomize the Board");
        randomButton.getStyleClass().add("checkButton");
        handledNodes.add(randomButton);
    }
    
    public CheckBox getRandomButton() {
        return randomButton;
    }
    
    public VBox getSinglePlayerNameWrapper() {
        return singlePlayerNameWrapper;
    }
    
    public TextField getSinglePlayerName() {
        return singlePlayerName;
    }
    
    public Label getSinglePlayerError() {
        return singlePlayerError;
    }
    
    public VBox getColorRadioWrapper() {
        return colorRadioWrapper;
    }
    
    public RadioButton getBlackRadio() {
        return blackRadio;
    }
    
    public RadioButton getWhiteRadio() {
        return whiteRadio;
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
