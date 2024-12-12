package view;

import controller.WishlistController;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Item;
import models.User;

public class WishlistView {

	private Stage stage;
    private Scene scene;
    private User buyer;
    private WishlistController wishlistController;
    private TableView<Item> tableView;
    
    public WishlistView(Stage stage, User buyer) {
        this.stage = stage;
        this.buyer = buyer;
        this.wishlistController = new WishlistController();
        this.tableView = new TableView<>();
        initializeView();
    }
    
    private void initializeView() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        
        Label titleLabel = new Label("My Wishlist");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        VBox headerBox = new VBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().add(titleLabel);
        
        borderPane.setTop(headerBox);
        borderPane.setCenter(createTableView());
        
        scene = new Scene(borderPane, 600, 400);
        stage.setScene(scene);
        stage.setTitle("My Wishlist");
        stage.show();
    }
    
    private VBox createTableView() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        
        TableColumn<Item, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getName()));
        
        TableColumn<Item, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getCategory()));
        
        TableColumn<Item, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(data -> 
            new SimpleStringProperty(String.format("$%.2f", data.getValue().getPrice())));
        
        TableColumn<Item, Void> removeCol = new TableColumn<>("Remove");
        removeCol.setCellFactory(col -> {
            TableCell<Item, Void> cell = new TableCell<>() {
                private final Button removeBtn = new Button("Remove");
                {
                    removeBtn.setOnAction(e -> {
                        Item item = getTableView().getItems().get(getIndex());
                        handleRemoveFromWishlist(item);
                    });
                }
                
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : removeBtn);
                }
            };
            return cell;
        });
        
        tableView.getColumns().addAll(nameCol, categoryCol, priceCol, removeCol);
        refreshTableData();
        
        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e -> refreshTableData());
        
        vbox.getChildren().addAll(tableView, refreshBtn);
        return vbox;
    }
    
    private void handleRemoveFromWishlist(Item item) {
        if(wishlistController.removeFromWishlist(buyer.getUserId(), item.getItemId())) {
            refreshTableData();
            showAlert("Success", "Item removed from wishlist!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to remove item!", Alert.AlertType.ERROR);
        }
    }
    
    private void refreshTableData() {
        tableView.getItems().clear();
        tableView.getItems().addAll(wishlistController.getWishlistItems(buyer.getUserId()));
    }
    
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
