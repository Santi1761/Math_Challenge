package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.GameManager;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		GUIController controller = new GUIController();
		controller.setGod(primaryStage);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LeaderBoard.fxml"));
		loader.setController(controller);
		Parent parent = loader.load();
		Scene s = new Scene(parent);
		primaryStage.setScene(s);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
