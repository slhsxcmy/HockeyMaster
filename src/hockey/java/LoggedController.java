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
import javafx.stage.Stage;

public class LoggedController {
	@FXML
	private Label appTitle;

	@FXML
	private Label pageTitle;

	@FXML
	private Button join;
	
	@FXML
	private Button stats;
	
	@FXML
	private Button signout;
	
	public void join(ActionEvent event) throws IOException{
		
	
	}
	
	public void goStats(ActionEvent event) throws IOException{

	}
	
	public void signout(ActionEvent event) throws IOException{
		// add sign out logic
		
		Parent root = FXMLLoader.load(getClass().getResource("/hockey/fxml/Menu.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/hockey/css/Menu.css").toExternalForm());
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();	
	}
	
}
