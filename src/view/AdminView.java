package view;

import controller.ItemController;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
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
}
