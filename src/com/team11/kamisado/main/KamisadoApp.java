package com.team11.kamisado.main;

import com.team11.kamisado.controllers.MenuController;
import com.team11.kamisado.views.MenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class KamisadoApp extends Application {
    private static final double INITIALFONTSIZE = 10;
    
    private StackPane root;
    private Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.root = new StackPane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/AppStyle.css");
        Font.loadFont(getClass().getResource("/fonts/Akashi.ttf").toString(), INITIALFONTSIZE);
        
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Kamisado");
        primaryStage.show();
        
        MenuView menuView = new MenuView(root);
        new MenuController(this, menuView);
    }
    
    public StackPane getRoot() {
        return root;
    }
    
    public Stage getStage() {
        return primaryStage;
    }
}
