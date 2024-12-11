package view;

import controller.ItemController;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import models.Item;

public class EditItemView {

	private Stage stage;
    private Scene scene;
    private Item item;
    private ItemController itemController;
    private Runnable onUpdateCallback;
    
    public EditItemView(Stage stage, Item item, Runnable onUpdateCallback) {
        this.stage = stage;
        this.item = item;
        this.itemController = new ItemController();
        this.onUpdateCallback = onUpdateCallback;
        
        if(!itemController.canEditItem(item.getItemId())) {
            showAlert("Error", "Can only edit accepted items!", Alert.AlertType.ERROR);
            stage.close();
            return;
        }
        
        initializeEditView();
    }
}
