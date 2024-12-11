package view;

import controller.ItemController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.User;

public class ViewItemsView {
	private Stage stage;
    private Scene scene;
    private User seller;
    private ItemController itemController;
    
    public ViewItemsView(Stage stage, User seller) {
        this.stage = stage;
        this.seller = seller;
        this.itemController = new ItemController();
        initializeView();
    }
    
    private void initializeView() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        
        // Header
        VBox headerBox = createHeaderBox();
        borderPane.setTop(headerBox);
        
        // Items Table
        VBox tableBox = createTableBox();
        borderPane.setCenter(tableBox);
        
        scene = new Scene(borderPane, 800, 600);
        stage.setScene(scene);
        stage.setTitle("My Items");
        stage.show();
    }
}
