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
import javafx.scene.control.TableCell;

public class ViewItemsView {
    private Stage stage;
    private Scene scene;
    private User seller;
    private ItemController itemController;
    private TableView<Item> tableView;
    
    public ViewItemsView(Stage stage, User seller) {
        this.stage = stage;
        this.seller = seller;
        this.itemController = new ItemController();
        this.tableView = new TableView<>();
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
        
        // Add Edit button column
        TableColumn<Item, String> actionCol = new TableColumn<>("Action");
        actionCol.setPrefWidth(100);
        actionCol.setCellValueFactory(data -> new SimpleStringProperty("Edit"));
        actionCol.setCellFactory(tc -> {
            TableCell<Item, String> cell = new TableCell<Item, String>() {
                private final Button btn = new Button("Edit");
                
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        btn.setOnAction(event -> {
                            Item currentItem = getTableView().getItems().get(getIndex());
                            handleEditAction(currentItem);
                        });
                        setGraphic(btn);
                    }
                }
            };
            return cell;
        });
        
        // Set column widths
        idColumn.setPrefWidth(100);
        nameColumn.setPrefWidth(150);
        categoryColumn.setPrefWidth(150);
        sizeColumn.setPrefWidth(100);
        priceColumn.setPrefWidth(100);
        statusColumn.setPrefWidth(100);
        
        // Add columns to table
        tableView.getColumns().addAll(idColumn, nameColumn, categoryColumn, 
                                    sizeColumn, priceColumn, statusColumn, actionCol);
        
        refreshTableData();
        
        // Add refresh button
        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e -> refreshTableData());
        
        tableBox.getChildren().addAll(tableView, refreshBtn);
        return tableBox;
    }
    
    private void refreshTableData() {
        tableView.getItems().clear();
        tableView.getItems().addAll(itemController.getSellerItems(seller.getUserId()));
    }
    
    private void handleEditAction(Item item) {
        Stage editStage = new Stage();
        EditItemView editView = new EditItemView(editStage, item, () -> {
            refreshTableData();
        });
    }
}