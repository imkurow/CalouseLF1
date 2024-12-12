package view;

import controller.ItemController;
import controller.TransactionController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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

}
