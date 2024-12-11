package view;

import java.util.ArrayList;

import controller.ItemController;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Item;
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
    
    private VBox createHeaderBox() {
        VBox headerBox = new VBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(0, 0, 20, 0));
        
        Label titleLabel = new Label("My Items");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        headerBox.getChildren().add(titleLabel);
        return headerBox;
    }
    
    private VBox createTableBox() {
        VBox tableBox = new VBox(10);
        tableBox.setAlignment(Pos.CENTER);
        
        // Create TableView
        TableView<Item> tableView = new TableView<>();
        
        // Define columns
        TableColumn<Item, String> idColumn = new TableColumn<>("Item ID");
        idColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItemId()));
        
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
        
        TableColumn<Item, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
        
        // Add columns to table
        tableView.getColumns().addAll(idColumn, nameColumn, categoryColumn, sizeColumn, priceColumn, statusColumn);
        
        // Set column widths
        idColumn.setPrefWidth(100);
        nameColumn.setPrefWidth(150);
        categoryColumn.setPrefWidth(150);
        sizeColumn.setPrefWidth(100);
        priceColumn.setPrefWidth(100);
        statusColumn.setPrefWidth(100);
        
        // Load data
        ArrayList<Item> items = itemController.getSellerItems(seller.getUserId());
        tableView.getItems().addAll(items);
        
        // Add refresh button
        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e -> {
            tableView.getItems().clear();
            tableView.getItems().addAll(itemController.getSellerItems(seller.getUserId()));
        });
        
        tableBox.getChildren().addAll(tableView, refreshBtn);
        return tableBox;
    }
}
