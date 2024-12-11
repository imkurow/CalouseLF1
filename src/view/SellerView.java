package view;

import javafx.scene.Scene;
import javafx.stage.Stage;
import models.User;

public class SellerView {
	 
    private Stage stage;
    private Scene scene;
    private User seller;
    
    public SellerView(Stage stage, User seller) {
        this.stage = stage;
        this.seller = seller;
        initializeSellerView();
    }

}
