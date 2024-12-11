package view;

import controller.ItemController;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
}
