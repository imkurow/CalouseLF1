package view;

import controller.ItemController;
import controller.TransactionController;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import models.Item;
import models.User;

public class BuyerView {
	
	private Stage stage;
    private Scene scene;
    private User buyer;
    private ItemController itemController;
    private TransactionController transactionController;
    private TableView<Item> tableView;
    
    public BuyerView(Stage stage, User buyer) {
        this.stage = stage;
        this.buyer = buyer;
        this.itemController = new ItemController();
        this.transactionController = new TransactionController();
        this.tableView = new TableView<>();
        initializeBuyerView();
    }
}
