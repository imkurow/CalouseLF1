package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterView {
	private Stage stage;
    private UserController userController;
    private Scene scene;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField phoneField;
    private TextArea addressArea;
    private ToggleGroup roleGroup;

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
    
    private GridPane createFormGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(20));

        // Username
        Label usernameLabel = new Label("Username:");
        usernameField = new TextField();
        usernameField.setPromptText("Enter username (min. 3 characters)");
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);

        // Password
        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();
        passwordField.setPromptText("Enter password (min. 8 characters)");
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);

        // Phone
        Label phoneLabel = new Label("Phone Number:");
        phoneField = new TextField();
        phoneField.setPromptText("+62xxxxxxxxxx");
        gridPane.add(phoneLabel, 0, 2);
        gridPane.add(phoneField, 1, 2);

        // Address
        Label addressLabel = new Label("Address:");
        addressArea = new TextArea();
        addressArea.setPromptText("Enter your address");
        addressArea.setPrefRowCount(3);
        addressArea.setWrapText(true);
        gridPane.add(addressLabel, 0, 3);
        gridPane.add(addressArea, 1, 3);

        // Role
        Label roleLabel = new Label("Role:");
        roleGroup = new ToggleGroup();
        
        RadioButton buyerRadio = new RadioButton("Buyer");
        buyerRadio.setToggleGroup(roleGroup);
        buyerRadio.setSelected(true);
        
        RadioButton sellerRadio = new RadioButton("Seller");
        sellerRadio.setToggleGroup(roleGroup);
        
        HBox roleBox = new HBox(20);
        roleBox.getChildren().addAll(buyerRadio, sellerRadio);
        gridPane.add(roleLabel, 0, 4);
        gridPane.add(roleBox, 1, 4);

        // Register Button
        Button registerBtn = new Button("Register");
        registerBtn.setPrefWidth(200);
        registerBtn.setOnAction(e -> handleRegister());
        
        HBox buttonBox = new HBox(registerBtn);
        buttonBox.setAlignment(Pos.CENTER);
        gridPane.add(buttonBox, 0, 5, 2, 1);

        return gridPane;
    }
}
