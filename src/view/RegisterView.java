package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    private VBox createHeaderBox() {
        VBox headerBox = new VBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(0, 0, 20, 0));

        Label titleLabel = new Label("CaLouselF");
        Label subtitleLabel = new Label("Register New Account");
        
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        subtitleLabel.setStyle("-fx-font-size: 16px;");

        headerBox.getChildren().addAll(titleLabel, subtitleLabel);
        return headerBox;
    }
}
