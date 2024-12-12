package view;

import controller.ItemController;
import controller.TransactionController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Item;
import models.Offer;
import models.User;

public class ViewOfferView {
	
	private Stage stage;
    private Scene scene;
    private User seller;
    private ItemController itemController;
    private TransactionController transactionController;
    
    public ViewOfferView(Stage stage, User seller) {
        this.stage = stage;
        this.seller = seller;
        this.itemController = new ItemController();
        this.transactionController = new TransactionController();
        initialize();
    }
    
    private void initialize() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        
        Label titleLabel = new Label("Offered Items");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        VBox offersContainer = new VBox(15);
        
        // Ambil semua item dari seller
        for(Item item : itemController.getSellerItems(seller.getUserId())) {
            // Untuk setiap item, ambil penawarannya
            for(Offer offer : itemController.getItemOffers(item.getItemId())) {
                if(offer.getOfferStatus().equals("pending")) {
                    offersContainer.getChildren().add(createOfferPane(item, offer));
                }
            }
        }
        
        root.getChildren().addAll(titleLabel, offersContainer);
        
        scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("View Offers");
        stage.show();
    }
    
    private GridPane createOfferPane(Item item, Offer offer) {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(5);
        pane.setPadding(new Insets(10));
        pane.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5;");
        
        // Item details
        pane.add(new Label("Item Name: " + item.getName()), 0, 0);
        pane.add(new Label("Category: " + item.getCategory()), 0, 1);
        pane.add(new Label("Size: " + item.getSize()), 0, 2);
        pane.add(new Label("Initial Price: $" + item.getPrice()), 0, 3);
        pane.add(new Label("Offered Price: $" + offer.getOfferPrice()), 0, 4);
        
        Button acceptBtn = new Button("Accept");
        Button declineBtn = new Button("Decline");
        
        acceptBtn.setOnAction(e -> handleAcceptOffer(offer, item));
        declineBtn.setOnAction(e -> handleDeclineOffer(offer));
        
        pane.add(acceptBtn, 1, 2);
        pane.add(declineBtn, 1, 3);
        
        return pane;
    }
	

}
