package hockey.java.controller;

import java.io.IOException;

import hockey.java.Hockey;
import hockey.java.packet.Constants;
import hockey.java.packet.PacketAttempt;
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
	private static Label message;
	
	@FXML
	private Button join;
	
	@FXML
	private Button stats;
	
	@FXML
	private Button signout;
	
	public void join(ActionEvent event) throws IOException{
		PacketAttempt p = new PacketAttempt(Constants.PLAYLOGGEDATTEMPT, Hockey.getUser().getUsername());
		Hockey.getNetwork().getClient().sendTCP(p);
		
	}
	
	public void goStats(ActionEvent event) throws IOException{

		Parent root = FXMLLoader.load(getClass().getResource("/hockey/fxml/Stats.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/hockey/css/Stats.css").toExternalForm());
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();	

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

	public static void setMessage(String string) {
		message.setText(string);
		
	}
	
}
