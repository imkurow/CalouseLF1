package view;

import controller.ItemController;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Item;

public class AdminView {
	private Stage stage;
    private Scene scene;
    private ItemController itemController;
    private TableView<Item> tableView;
    
    public AdminView(Stage stage) {
        this.stage = stage;
        this.itemController = new ItemController();
        this.tableView = new TableView<>();
        initializeAdminView();
    }
    private void initializeAdminView() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        
        // Header
        VBox headerBox = createHeaderBox();
        borderPane.setTop(headerBox);
        
        // Items Table
        VBox tableBox = createTableBox();
        borderPane.setCenter(tableBox);
        
        scene = new Scene(borderPane, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Admin Dashboard - Item Review");
        stage.show();
    }
    
    private VBox createHeaderBox() {
        VBox headerBox = new VBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(0, 0, 20, 0));
        
        Label titleLabel = new Label("Admin Dashboard");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        Label subtitleLabel = new Label("Item Review Management");
        subtitleLabel.setStyle("-fx-font-size: 18px;");
        
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> handleLogout());
        
        headerBox.getChildren().addAll(titleLabel, subtitleLabel, logoutBtn);
        return headerBox;
    }
    
    private VBox createTableBox() {
        VBox tableBox = new VBox(10);
        tableBox.setAlignment(Pos.CENTER);
        
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
        
        // Add Approve button column
        TableColumn<Item, Void> approveCol = new TableColumn<>("Approve");
        approveCol.setPrefWidth(100);
        approveCol.setCellFactory(col -> {
            TableCell<Item, Void> cell = new TableCell<>() {
                private final Button approveBtn = new Button("Approve");
                {
                    approveBtn.setOnAction(e -> {
                        Item item = getTableView().getItems().get(getIndex());
                        handleApprove(item);
                    });
                }
                
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : approveBtn);
                }
            };
            return cell;
        });
        
        // Add Decline button column
        TableColumn<Item, Void> declineCol = new TableColumn<>("Decline");
        declineCol.setPrefWidth(100);
        declineCol.setCellFactory(col -> {
            TableCell<Item, Void> cell = new TableCell<>() {
                private final Button declineBtn = new Button("Decline");
                {
                    declineBtn.setOnAction(e -> {
                        Item item = getTableView().getItems().get(getIndex());
                        handleDecline(item);
                    });
                }
                
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : declineBtn);
                }
            };
            return cell;
        });
        
        // Set column widths
        idColumn.setPrefWidth(100);
        nameColumn.setPrefWidth(200);
        categoryColumn.setPrefWidth(150);
        sizeColumn.setPrefWidth(100);
        priceColumn.setPrefWidth(100);
        
        tableView.getColumns().addAll(idColumn, nameColumn, categoryColumn, 
                                    sizeColumn, priceColumn, approveCol, declineCol);
        
        refreshTableData();
        
        // Add refresh button
        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e -> refreshTableData());
        
        tableBox.getChildren().addAll(tableView, refreshBtn);
        return tableBox;
    }
    
    private void handleApprove(Item item) {
        Alert confirmDialog = new Alert(AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Approval");
        confirmDialog.setHeaderText("Approve Item");
        confirmDialog.setContentText("Are you sure you want to approve this item: " + item.getName() + "?");
        
        if (confirmDialog.showAndWait().get() == ButtonType.OK) {
            boolean success = itemController.approveItem(item.getItemId());
            if (success) {
                showAlert("Success", "Item has been approved!", AlertType.INFORMATION);
                refreshTableData();
            } else {
                showAlert("Error", "Failed to approve item!", AlertType.ERROR);
            }
        }
    }
}
