package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {

	private Stage stage;
    private UserController userController;
    private Scene scene;

    public LoginView(Stage stage) {
        this.stage = stage;
        this.userController = new UserController();
        initializeLoginView();
    }

    private void initializeLoginView() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));

        // Header
        VBox headerBox = new VBox(10);
        headerBox.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("CaLouselF");
        Label subtitleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        subtitleLabel.setStyle("-fx-font-size: 18px;");
        headerBox.getChildren().addAll(titleLabel, subtitleLabel);
        borderPane.setTop(headerBox);

        // Center content
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        // Username
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPrefWidth(200);
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);

        // Password
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(200);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);

        // Login button
        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(100);
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(loginBtn);
        gridPane.add(buttonBox, 0, 2, 2, 1);

        loginBtn.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));

        borderPane.setCenter(gridPane);

        // Footer
        HBox footerBox = new HBox(5);
        footerBox.setAlignment(Pos.CENTER);
        Label registerLabel = new Label("Don't have an account?");
        Hyperlink registerLink = new Hyperlink("Register");
        registerLink.setOnAction(e -> showRegisterView());
        footerBox.getChildren().addAll(registerLabel, registerLink);
        borderPane.setBottom(footerBox);

        scene = new Scene(borderPane, 400, 500);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        
        stage.setScene(scene);
        stage.setTitle("CaLouselF - Login");
        stage.show();
    }
}
