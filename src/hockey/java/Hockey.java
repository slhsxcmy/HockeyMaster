package hockey.java;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import hockey.java.front.Network;
import hockey.java.front.User;

// ClientProgram
public class Hockey extends Application {
	
	static Network network = new Network();
	static User thisUser = new User();
	static User otherUser = new User();
	
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

