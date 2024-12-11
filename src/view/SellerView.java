package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    private VBox createHeaderBox() {
        VBox headerBox = new VBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(0, 0, 20, 0));
        
        Label welcomeLabel = new Label("Welcome, " + seller.getUsername() + "!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        Label roleLabel = new Label("Seller Dashboard");
        roleLabel.setStyle("-fx-font-size: 18px;");
        
        headerBox.getChildren().addAll(welcomeLabel, roleLabel);
        return headerBox;
    }
    
    private VBox createMenuBox() {
        VBox menuBox = new VBox(15);
        menuBox.setAlignment(Pos.CENTER);
        
        Button uploadItemBtn = new Button("Upload New Item");
        Button viewItemsBtn = new Button("View My Items");
        Button logoutBtn = new Button("Logout");
        
        // Set button sizes
        uploadItemBtn.setPrefWidth(200);
        viewItemsBtn.setPrefWidth(200);
        logoutBtn.setPrefWidth(200);
        
        // Button actions
        uploadItemBtn.setOnAction(e -> showUploadItemView());
        viewItemsBtn.setOnAction(e -> showViewItemsView());
        logoutBtn.setOnAction(e -> handleLogout());
        
        menuBox.getChildren().addAll(uploadItemBtn, viewItemsBtn, logoutBtn);
        return menuBox;
    }
    
    private void showUploadItemView() {
        new UploadItemView(new Stage(), seller);
    }
    
    private void showViewItemsView() {
        new ViewItemsView(new Stage(), seller)
    }
    
    private void handleLogout() {
        stage.close();
        new LoginView(new Stage());
    }
    
    
}
