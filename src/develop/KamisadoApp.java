package develop;

import develop.controllers.MenuController;
import develop.views.MenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class KamisadoApp extends Application {
    private StackPane root;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new StackPane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/develop/AppStyle.css");
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Kamisado");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();
    
        MenuController menuController = new MenuController(this);
        MenuView menuView = new MenuView(primaryStage, root, menuController);
        menuController.addView(menuView);
    }
    
    public StackPane getRoot() {
        return root;
    }
}