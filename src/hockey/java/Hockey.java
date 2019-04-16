package hockey.java;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// ClientProgram
public class Hockey extends Application {
	
	static Network network = new Network();
	//static Player otherPlayer = new Player("name");
	
	@Override
	public void start(Stage stage) {
		try {

			// FXML
			Parent root = FXMLLoader.load(getClass().getResource("/hockey/fxml/Menu.fxml"));
			
			Scene scene = new Scene(root);
						
			stage.setScene(scene);
			
					stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

