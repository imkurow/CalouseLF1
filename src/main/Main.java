package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginView;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new LoginView(primaryStage);
//		new LoginView(primaryStage);
//		new LoginView(primaryStage);
		
	}

}
