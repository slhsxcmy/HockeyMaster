package hockey.java.controller;

import java.io.IOException;

import hockey.java.Hockey;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MenuController {
	@FXML
	private Label appTitle;

	@FXML
	private Label pageTitle;
	
	@FXML
	private Button login;
	
	@FXML
	private Button register;
	
	@FXML
	private Button playAsGuest;
	
	public void join(ActionEvent event) throws IOException{
		
	}
	
	public void goLogin(ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("/hockey/fxml/Login.fxml"));
		Scene scene = new Scene(root);
		

		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	public void goRegister(ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("/hockey/fxml/Signup.fxml"));
		Scene scene = new Scene(root);
		

		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();	
	}
	
}
