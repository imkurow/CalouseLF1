package view;

import controller.ItemController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.User;

public class UploadItemView {
	
	private Stage stage;
	private ItemController itemController;
	private User seller; 
	private Scene scene;
	
	public UploadItemView(Stage stage, User seller) {
        this.stage = stage;
        this.seller = seller;
        this.itemController = new ItemController();
//        initializeUploadView();
    }
	
}
