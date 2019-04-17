package hockey.java;


import java.lang.ModuleLayer.Controller;

import hockey.java.front.Network;
import hockey.java.front.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// ClientProgram
public class Hockey extends Application {
	
	private static Network network = new Network();
	private static User self = null; // instantiate when playAsGuest or Register or Login
	//private static Controller currentController;
	
	@Override
	public void start(Stage stage) {
// https://stackoverflow.com/questions/12804664/how-to-swap-screens-in-a-javafx-application-in-the-controller-class
		
		try {
			
			// FXML
			//Parent root = FXMLLoader.load(getClass().getResource("/hockey/fxml/Menu.fxml"));
			
			FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Menu.fxml"));
			Parent menuParent = (Parent)menuLoader.load();
			
			Scene menuScene = new Scene(menuParent);
						
			stage.setScene(menuScene);
			
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	public static Network getNetwork() {
		return network;
	}

	public static void setNetwork(Network network) {
		Hockey.network = network;
	}

	public static User getSelf() {
		return self;
	}

	public static void setSelf(User self) {
		Hockey.self = self;
	}

	
	
	
	
}