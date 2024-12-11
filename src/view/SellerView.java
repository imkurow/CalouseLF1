package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.User;

public class SellerView {
	 
    private Stage stage;
    private Scene scene;
    private User seller;
    
    public SellerView(Stage stage, User seller) {
        this.stage = stage;
        this.seller = seller;
        initializeSellerView();
    }
    
    private void initializeSellerView() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        
        // Header
        VBox headerBox = createHeaderBox();
        borderPane.setTop(headerBox);
        
        // Menu Buttons
        VBox menuBox = createMenuBox();
        borderPane.setCenter(menuBox);
        
        scene = new Scene(borderPane, 400, 500);
        stage.setScene(scene);
        stage.setTitle("Seller Dashboard");
        stage.show();
    }

}
