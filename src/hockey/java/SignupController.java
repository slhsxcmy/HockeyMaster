package hockey.java;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignupController {
	@FXML
	private Label appTitle;

	@FXML
	private Label pageTitle;
	
	@FXML
	private TextField username;

	@FXML
	private TextField password;
	
	@FXML
	private TextField passwordc;
	
	@FXML
	private Button signup;
	
	public void signup(ActionEvent event) throws IOException{
		if(true) {
			Parent root = FXMLLoader.load(getClass().getResource("/hockey/fxml/Logged.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/hockey/css/Logged.css").toExternalForm());
			
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(scene);
			window.show();
			
			
		}
	}
	
}
