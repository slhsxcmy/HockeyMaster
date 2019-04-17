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
	private Button signup;
	
	@FXML
	private Button playAsGuest;

	// scenes
	private Scene signupScene; // goBack
	private Scene loginScene; // signup
	
	
	public void join(ActionEvent event) throws IOException{
		
	}
	
	public void goLogin(ActionEvent event) throws IOException{
		System.out.println("Switching scene to Login");
		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
		primaryStage.setScene(loginScene);
		
	}
	
	public void goSignup(ActionEvent event) throws IOException{
		System.out.println("Switching scene to Signup");
		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
		primaryStage.setScene(signupScene);
		
			
	}

	public Scene getSignupScene() {
		return signupScene;
	}

	public void setSignupScene(Scene signupScene) {
		this.signupScene = signupScene;
	}

	public Scene getLoginScene() {
		return loginScene;
	}

	public void setLoginScene(Scene loginScene) {
		this.loginScene = loginScene;
	}
	
}
