package com.team11.kamisado.main;

import com.team11.kamisado.controllers.MenuController;
import com.team11.kamisado.views.MenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class KamisadoApp extends Application {
    private StackPane root;
    private Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.root = new StackPane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/AppStyle.css");
        
//        primaryStage.setFullScreen(true);
//        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Kamisado");
        primaryStage.show();
        
        MenuController menuController = new MenuController(this);
        MenuView menuView = new MenuView(root, menuController);
        menuController.addView(menuView);
    }
    
    public StackPane getRoot() {
        return root;
    }
    
    public Stage getStage() {
        return primaryStage;
    }
}