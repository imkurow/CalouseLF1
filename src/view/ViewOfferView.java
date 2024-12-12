package view;

import controller.ItemController;
import controller.TransactionController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
	

}
