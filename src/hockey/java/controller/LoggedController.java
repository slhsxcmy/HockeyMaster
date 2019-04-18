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
	private Label message;
	
	@FXML
	private Button playLogged;
	
	@FXML
	private Button stats;
	
	@FXML
	private Button signout;
	
	public void playLogged(ActionEvent event) throws IOException{
		PacketAttempt p = new PacketAttempt(Constants.PLAYLOGGEDATTEMPT, Hockey.getUser().getId());
		Hockey.getNetwork().getClient().sendTCP(p);
		System.out.println("sent playLogged Attempt");
		
	}
	
	public void goStats(ActionEvent event) throws IOException{
		// send attempt to get stats
	}
	
	public void signout(ActionEvent event) throws IOException{	
		PacketAttempt p = new PacketAttempt(Constants.SIGNOUTATTEMPT, Hockey.getUser().getId());		
		Hockey.getNetwork().getClient().sendTCP(p);
		System.out.println("sent signout attempt");
	}

	public void setMessage(String string) {
		message.setText(string);
		
	}
	
}
