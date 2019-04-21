package hockey.java.controller;

import java.io.IOException;

import hockey.java.Hockey;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GameOverController {
	@FXML
	private Label message;
	
	@FXML
	private Button back; 

	@FXML
	public void goBack(ActionEvent event) throws IOException {
		
		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
		primaryStage.setScene(Hockey.getLoggedScene());
		//primaryStage.show();
	}
	
	@FXML
    protected void initialize() {
        // populate listView https://stackoverflow.com/questions/47509117/javafx-populate-a-simple-listview?rq=1
		
    }
	
	public void setMessage(String m) {
		message.setText(m);
	}
}
