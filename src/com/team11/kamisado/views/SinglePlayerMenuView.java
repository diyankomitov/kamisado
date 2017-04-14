package com.team11.kamisado.views;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class SinglePlayerMenuView extends MenuViewBase {
    
    private TextField singlePlayerName;
    private Label singlePlayerError;
    private VBox singlePlayerNameWrapper;
    private RadioButton easyRadio;
    private RadioButton hardRadio;
    private VBox difficultyRadioWrapper;
    private RadioButton normalRadio;
    private RadioButton speedRadio;
    private VBox modeRadioWrapper;
    private RadioButton blackRadio;
    private RadioButton whiteRadio;
    private VBox colorRadioWrapper;
    private MenuView menuView;
    
    private ArrayList<Node> handledNodes;
    
    public SinglePlayerMenuView(MenuView menuView) {
        this.menuView = menuView;
        this.handledNodes = new ArrayList<>();
        
        initSinglePlayerScreen();
        
        this.setId("singlePlayerScreen");
        this.getStyleClass().add("transparentMenu");
    }
    
    public void drawSinglePlayerScreen() {
        this.getChildren().clear();
        this.getChildren().addAll(menuView.getMenuGameTitle(), singlePlayerNameWrapper, difficultyRadioWrapper, modeRadioWrapper, colorRadioWrapper, menuView.getButtonsWrapper());
    }
    
    private void initSinglePlayerScreen() {
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
    }
    
    public TextField getSinglePlayerName() {
        return singlePlayerName;
    }
    
    public Label getSinglePlayerError() {
        return singlePlayerError;
    }
    
    public RadioButton getEasyRadio() {
        return easyRadio;
    }
    
    public RadioButton getHardRadio() {
        return hardRadio;
    }
    
    public RadioButton getNormalRadio() {
        return normalRadio;
    }
    
    public RadioButton getSpeedRadio() {
        return speedRadio;
    }
    
    public RadioButton getBlackRadio() {
        return blackRadio;
    }
    
    public RadioButton getWhiteRadio() {
        return whiteRadio;
    }
    
    public ArrayList<Node> getHandledNodes() {
        return handledNodes;
    }
}
