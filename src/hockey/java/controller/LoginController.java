package hockey.java.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hockey.java.Hockey;
import hockey.java.database.SQLModel;
import hockey.java.packet.PacketAttempt;
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
	private Button login;
	
	@FXML
	private Button back;
	
	public void login(ActionEvent event) throws IOException{
		System.out.println(username.getText() + " " + password.getText());
		
		//sending login packet
		PacketAttempt p = new PacketAttempt();
		p.attempt = 2;
		p.username = username.getText();
		p.password = password.getText();		
		Hockey.getNetwork().getConnection().sendTCP(p);
		//sent
		
		
//		if(!model.checkLogin(username.getText(), password.getText())) {
//			errorMessage.setText("Username doesn't match with password. Please try again.");
//		}		
//		else {
//			Parent root = FXMLLoader.load(getClass().getResource("/hockey/fxml/Logged.fxml"));
//			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("/hockey/css/Logged.css").toExternalForm());
//			
//			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
//			window.setScene(scene);
//			window.show();			
//		}
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
		
		Parent root = FXMLLoader.load(getClass().getResource("/hockey/fxml/Menu.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/hockey/css/Menu.css").toExternalForm());
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();	
	}

	public static void setMessage(String string) {
		message.setText("Username doesn't match with password. Please try again.");
		
	}
	
}
