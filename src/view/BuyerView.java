package view;

import java.util.Optional;

import controller.ItemController;
import controller.TransactionController;
import controller.WishlistController;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Item;
import models.User;

public class BuyerView {
	
	private Stage stage;
    private Scene scene;
    private User buyer;
    private ItemController itemController;
    private TransactionController transactionController;
    private WishlistController wishlistController;
    private TableView<Item> tableView;
    
    public BuyerView(Stage stage, User buyer) {
        this.stage = stage;
        this.buyer = buyer;
        this.itemController = new ItemController();
        this.transactionController = new TransactionController();
        this.wishlistController = new WishlistController();
        this.tableView = new TableView<>();
        initializeBuyerView();
    }
    
    private void initializeBuyerView() {
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
        stage.setTitle("Available Items");
        stage.show();
    }
    
    private VBox createHeaderBox() {
        VBox headerBox = new VBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(0, 0, 20, 0));
        
        Label welcomeLabel = new Label("Welcome, " + buyer.getUsername() + "!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        Label roleLabel = new Label("Buyer Dashboard");
        roleLabel.setStyle("-fx-font-size: 18px;");
        
        headerBox.getChildren().addAll(welcomeLabel, roleLabel);
        return headerBox;
    }
    
    private VBox createTableBox() {
        VBox tableBox = new VBox(10);
        tableBox.setAlignment(Pos.CENTER);
        
        // Define columns
        TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        
        TableColumn<Item, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory()));
        
        TableColumn<Item, String> sizeColumn = new TableColumn<>("Size");
        sizeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSize()));
        
        TableColumn<Item, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(data -> new SimpleStringProperty(
            String.format("%.2f", data.getValue().getPrice())
        ));
        
        // Add Purchase button column
        TableColumn<Item, Void> actionCol = new TableColumn<>("Action");
        actionCol.setPrefWidth(100);
        actionCol.setCellFactory(col -> {
            TableCell<Item, Void> cell = new TableCell<>() {
                private final Button purchaseBtn = new Button("Purchase");
                {
                    purchaseBtn.setOnAction(e -> {
                        Item item = getTableView().getItems().get(getIndex());
                        handlePurchase(item);
                    });
                }
                
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : purchaseBtn);
                }
            };
            return cell;
        });
        
        // Add make offer column
        TableColumn<Item, Void> offerCol = new TableColumn<>("Make Offer");
        offerCol.setPrefWidth(100);
        offerCol.setCellFactory(col -> {
            TableCell<Item, Void> cell = new TableCell<>() {
                private final Button offerBtn = new Button("Make Offer");
                {
                    offerBtn.setOnAction(e -> {
                        Item item = getTableView().getItems().get(getIndex());
                        showMakeOfferDialog(item);
                    });
                }
                
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : offerBtn);
                }
            };
            return cell;
        });
        
        // Add wishlist button column
        TableColumn<Item, Void> wishlistCol = new TableColumn<>("Wishlist");
        wishlistCol.setPrefWidth(100);
        wishlistCol.setCellFactory(col -> {
            TableCell<Item, Void> cell = new TableCell<>() {
                private final Button wishlistBtn = new Button("Add to Wishlist");
                {
                    wishlistBtn.setOnAction(e -> {
                        Item item = getTableView().getItems().get(getIndex());
                        handleAddToWishlist(item);
                    });
                }
                
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : wishlistBtn);
                }
            };
            return cell;
        });
        
        // Set column widths
        nameColumn.setPrefWidth(200);
        categoryColumn.setPrefWidth(150);
        sizeColumn.setPrefWidth(100);
        priceColumn.setPrefWidth(100);
        wishlistCol.setPrefWidth(100);
        
        tableView.getColumns().addAll(
            nameColumn, 
            categoryColumn, 
            sizeColumn, 
            priceColumn, 
            actionCol, 
            offerCol, 
            wishlistCol  
        );
        
        refreshTableData();
        
        // Add buttons
        Button refreshBtn = new Button("Refresh Items");
        refreshBtn.setOnAction(e -> refreshTableData());
        
        Button viewWishlistBtn = new Button("View Wishlist");
        viewWishlistBtn.setOnAction(e -> showWishlistView());
        
        tableBox.getChildren().addAll(tableView, refreshBtn, viewWishlistBtn);
        return tableBox;
    }
    
    
    private void handlePurchase(Item item) {
        Alert confirmDialog = new Alert(AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Purchase");
        confirmDialog.setHeaderText("Purchase Confirmation");
        confirmDialog.setContentText("Are you sure you want to purchase " + item.getName() + " for $" + item.getPrice() + "?");
        
        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean success = transactionController.createTransaction(buyer.getUserId(), item.getItemId());
                if (success) {
                    showAlert("Success", "Item purchased successfully!", AlertType.INFORMATION);
                    refreshTableData();
                } else {
                    showAlert("Error", "Failed to complete purchase!", AlertType.ERROR);
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "An error occurred during purchase!", AlertType.ERROR);
            }
        }
    }
    
    private void refreshTableData() {
        tableView.getItems().clear();
        tableView.getItems().addAll(itemController.getAvailableItems());
    }
    
    private void showAlert(String title, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }	
    
    private void showMakeOfferDialog(Item item) {
        Dialog<Double> dialog = new Dialog<>();
        dialog.setTitle("Make an Offer");
        dialog.setHeaderText("Make an offer for " + item.getName());

        ButtonType confirmButtonType = new ButtonType("Submit Offer", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField priceField = new TextField();
        priceField.setPromptText("Enter your offer price");

        double highestOffer = itemController.getHighestOffer(item.getItemId());
        String currentHighestOffer = highestOffer > 0 ? 
            String.format("Current Highest Offer: $%.2f", highestOffer) : 
            "No current offers";

        grid.add(new Label("Current Price: $" + item.getPrice()), 0, 0);
        grid.add(new Label(currentHighestOffer), 0, 1);
        grid.add(new Label("Your Offer:"), 0, 2);
        grid.add(priceField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                try {
                    return Double.parseDouble(priceField.getText());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        });

        Optional<Double> result = dialog.showAndWait();
        result.ifPresent(offerPrice -> {
            if (validateOffer(item.getItemId(), offerPrice)) {
                if (itemController.makeOffer(buyer.getUserId(), item.getItemId(), offerPrice)) {
                    showAlert("Success", "Offer submitted successfully!", AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to submit offer!", AlertType.ERROR);
                }
            }
        });
    }
    
    private boolean validateOffer(String itemId, double offerPrice) {
        if (offerPrice <= 0) {
            showAlert("Invalid Offer", "Offer price must be greater than zero!", AlertType.ERROR);
            return false;
        }

        double highestOffer = itemController.getHighestOffer(itemId);
        if (highestOffer >= offerPrice) {
            showAlert("Invalid Offer", 
                     String.format("Your offer must be higher than the current highest offer: $%.2f", highestOffer), 
                     AlertType.ERROR);
            return false;
        }

        return true;
    }
    
    private void handleAddToWishlist(Item item) {
        if(wishlistController.addToWishlist(buyer.getUserId(), item.getItemId())) {
            showAlert("Success", "Item berhasil ditambahkan ke wishlist!", AlertType.INFORMATION);
        } else {
            showAlert("Error", "Gagal menambahkan item ke wishlist!", AlertType.ERROR);
        }
    }
    
    private void showWishlistView() {
        new WishlistView(new Stage(), buyer);
    }

}
