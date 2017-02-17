package develop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class KamisadoApp extends Application {
    
    private Stage stage;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        BorderPane root = new BorderPane();
        
        /* Create nav menu */
        MenuBar navMenu = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu viewMenu = new Menu("View");
        navMenu.getMenus().addAll(fileMenu, editMenu, viewMenu);
        
        /* Create board */
        Board board = new Board();
        
        /* Create sidebar */
        VBox sidebar = new VBox();
        Label random = new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        sidebar.getChildren().add(random);
        
        /* Create footer */
        HBox footer = new HBox();
        
        Label info = new Label("Here is some info that changes depending on your mouse");
        footer.getChildren().add(info);
        
        /* Add all elements */
        root.setTop(navMenu);
        root.setCenter(board);
        root.setRight(sidebar);
        root.setBottom(footer);
        
        /* Set up the stage and scene */
        Scene scene = new Scene(root);
        stage = primaryStage;
        stage.setTitle("Kamisado");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> stage.close());
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}