package hockey.java.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import hockey.java.database.SQLModel;
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

public class SignupController implements Initializable{
	
	private SQLModel model = new SQLModel();
	
	@FXML
	private Label appTitle;

	@FXML
	private Label pageTitle;
	
	@FXML
	private static Label message;
	
	@FXML
	private TextField username;

	@FXML
	private TextField password;
	
	@FXML
	private TextField passwordc;
	
	@FXML
	private Button signup;
	
	@FXML
	private Button back;
	
	@FXML
	public void signup(ActionEvent event) throws IOException{
		
		
		System.out.println(username.getText() + " " + password.getText() + " " + passwordc.getText());
		if(!model.checkSignUp(username.getText(), password.getText(), passwordc.getText())) {
			message.setText("Sign up failed. Please try again.");
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
			message.setText("Connected");
		} else {
			message.setText("Not Connected");
		}
		
	}
	

	@FXML
	public void goBack(ActionEvent event) throws IOException {
		// add sign out logic
		
		Parent root = FXMLLoader.load(getClass().getResource("/hockey/fxml/Menu.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/hockey/css/Menu.css").toExternalForm());
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();	
	}

	public static void setMessage(String string) {
		message.setText(string);
		
	}
	
}
