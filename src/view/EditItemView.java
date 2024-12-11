package view;

import controller.ItemController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
    
    private void initializeEditView() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        
        // Header
        VBox headerBox = createHeaderBox();
        borderPane.setTop(headerBox);
        
        // Form
        GridPane formGrid = createFormGrid();
        borderPane.setCenter(formGrid);
        
        scene = new Scene(borderPane, 400, 500);
        stage.setScene(scene);
        stage.setTitle("Edit Item");
        stage.show();
    }
    
}
