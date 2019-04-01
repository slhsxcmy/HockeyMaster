package hockey.java;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable{
	private Model model = new Model();
	
	@FXML
	private Label appTitle;

	@FXML
	private Label pageTitle;
	
	@FXML
	private Label errorMessage;
	
	@FXML
	private TextField username;
	
	@FXML
	private TextField password;
	
	@FXML
	private Button login;
	
	public void login(ActionEvent event) throws IOException{
		System.out.println(username.getText() + " " + password.getText());
		if(!model.checkLogin(username.getText(), password.getText())) {
			errorMessage.setText("Username doesn't match with password. Please try again.");
		}		
		else {
			Parent root = FXMLLoader.load(getClass().getResource("/hockey/fxml/Logged.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/hockey/css/Logged.css").toExternalForm());
			
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(scene);
			window.show();			
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(model.isDbConnected()) {
			errorMessage.setText("Connected");
		} else {
			errorMessage.setText("Not Connected");
		}
		
	}
	
}
