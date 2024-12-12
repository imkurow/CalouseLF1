package view;

import controller.ItemController;
import controller.TransactionController;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Item;
import models.Transaction;
import models.User;

public class PurchaseHistoryView {
	
	private Stage stage;
    private Scene scene;
    private User buyer;
    private TransactionController transactionController;
    private ItemController itemController;
    private TableView<Transaction> tableView;
    
    public PurchaseHistoryView(Stage stage, User buyer) {
        this.stage = stage;
        this.buyer = buyer;
        this.transactionController = new TransactionController();
        this.itemController = new ItemController();
        this.tableView = new TableView<>();
        initializeView();
    }
    
    private void initializeView() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        
        VBox headerBox = new VBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().add(new javafx.scene.control.Label("Purchase History"));
        
        borderPane.setTop(headerBox);
        borderPane.setCenter(createTableView());
        
        scene = new Scene(borderPane, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Purchase History");
        stage.show();
    }
    
    private VBox createTableView() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        
        TableColumn<Transaction, String> transactionIdCol = new TableColumn<>("Transaction ID");
        transactionIdCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTransactionId()));
        
        TableColumn<Transaction, String> itemNameCol = new TableColumn<>("Item Name");
        itemNameCol.setCellValueFactory(data -> {
            Item item = itemController.getItemById(data.getValue().getItemId());
            return new SimpleStringProperty(item != null ? item.getName() : "Unknown");
        });
        
        TableColumn<Transaction, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(data -> {
            Item item = itemController.getItemById(data.getValue().getItemId());
            return new SimpleStringProperty(item != null ? item.getCategory() : "Unknown");
        });
        
        TableColumn<Transaction, String> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(data -> {
            Item item = itemController.getItemById(data.getValue().getItemId());
            return new SimpleStringProperty(item != null ? item.getSize() : "Unknown");
        });
        
        TableColumn<Transaction, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(data -> {
            Item item = itemController.getItemById(data.getValue().getItemId());
            return new SimpleStringProperty(item != null ? String.format("$%.2f", item.getPrice()) : "Unknown");
        });
        
        tableView.getColumns().addAll(transactionIdCol, itemNameCol, categoryCol, sizeCol, priceCol);
        refreshTableData();
        
        vbox.getChildren().add(tableView);
        return vbox;
    }

}
