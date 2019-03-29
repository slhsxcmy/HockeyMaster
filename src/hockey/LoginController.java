package hockey;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private Label appTitle;

	@FXML
	private Label pageTitle;
	
	@FXML
	private TextField username;
	
	@FXML
	private TextField password;
	
	@FXML
	private Button login;
	
	public void goLogin(ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
		
		Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
		Scene scene = new Scene(root);
		
		scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void goRegister(ActionEvent event) throws Exception{
		
	}
	
}
