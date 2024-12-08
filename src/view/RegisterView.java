package view;

import controller.UserController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterView {
	private Stage stage;
    private UserController userController;
    private Scene scene;

    public RegisterView(Stage stage) {
        this.stage = stage;
        this.userController = new UserController();
        initializeRegisterView();
    }
    
    private void initializeRegisterView() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));

        // Header
        VBox headerBox = createHeaderBox();
        borderPane.setTop(headerBox);

        // Center - Form
        GridPane gridPane = createFormGrid();
        borderPane.setCenter(gridPane);

        // Footer
        HBox footerBox = createFooterBox();
        borderPane.setBottom(footerBox);

        scene = new Scene(borderPane, 400, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        
        stage.setScene(scene);
        stage.setTitle("CaLouselF - Register");
        stage.setResizable(false);
        stage.show();
    }
}
