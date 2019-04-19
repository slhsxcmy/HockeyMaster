package hockey.java.front;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import hockey.java.Hockey;
import hockey.java.Master;
import hockey.java.controller.GameController;
import hockey.java.controller.LoggedController;
import hockey.java.controller.LoginController;
import hockey.java.controller.StatsController;
import hockey.java.packet.Constants;
import hockey.java.packet.PacketPuck;
import hockey.java.packet.PacketReturn;
import hockey.java.packet.PacketStats;
import hockey.java.packet.PacketStriker;
import javafx.application.Platform;

public class Network extends Listener{

	//private static Hockey hockey;
	private static Client client;
	private static String ip = Master.ngrok_url;
	private static int port = Master.tcpPort;
	
	public Network(/*Hockey hockey*/) {
	
		System.out.println("Starting Network constructor");
		
		client = new Client();
		
		// register packet
		Master.registerClasses(client.getKryo());

		client.start();
		
		
		try {
			client.connect(5000, ip, port); //blocks for 5 seconds
		} catch (IOException e) {
			System.out.println("Cannot connect to server.");
		} 

		// add listener for connected/received/disconnected methods
		client.addListener(this);
		
		System.out.println("Client waiting for a packet...\n");		
			
	}
	
	// runs upon packet received
	public void received(Connection c, Object o) {
		
		if (o instanceof PacketReturn){
			System.out.println("Client received PacketReturn of type " + ((PacketReturn) o).status);
			
			int id = ((PacketReturn) o).id;
			String username = ((PacketReturn) o).username;
			String message = ((PacketReturn) o).message;
			
			switch(((PacketReturn) o).status) {
			
			case Constants.SIGNUPSUCCESS:
//				id = ((PacketReturn) o).id;
//				username = ((PacketReturn) o).username;
//				System.out.println("user id = " + id + " username = " + username);
//				System.out.println("setting scene to logged");
//				Platform.runLater(() -> {
//					Hockey.getPrimaryStage().setScene(Hockey.getLoggedScene());
//                });
//				System.out.println("set scene complete");
				break;
			case Constants.LOGINSUCCESS: 

				System.out.println("user id = " + id + " username = " + username);
				System.out.println("setting scene to logged");
				Platform.runLater(() -> {
					Hockey.getPrimaryStage().setScene(Hockey.getLoggedScene());
                });
				System.out.println("set scene complete");
				break;
			case Constants.SIGNUPFAILURE: 
				Platform.runLater(() -> {
					Hockey.getSignupController().setMessage(message);
                });

				break;
			case Constants.LOGINFAILURE: 

				Platform.runLater(() -> {
				
					Hockey.getLoginController().setMessage(message);

                });
				break;
			case Constants.SIGNOUTSUCCESS: 
				Platform.runLater(() -> {
					Hockey.getPrimaryStage().setScene(Hockey.getMenuScene());;
                });
				break;
			case Constants.SIGNOUTFAILURE: 
				Platform.runLater(() -> {
					Hockey.getLoggedController().setMessage(message);
                });
				break;
			case Constants.PLAYLOGGEDSUCCESS: 
				
				System.out.println("Going to game scene");
				Platform.runLater(() -> {
					Hockey.getPrimaryStage().setScene(Hockey.getGameScene());
					Hockey.getGameController().init(id);
					Hockey.getGameController().gameLoop(id);
                });
				break;
			case Constants.PLAYLOGGEDFAILURE: 
				
				break;
			case Constants.PLAYGUESTSUCCESS: 
				//Game game = new Game();
				break;
			case Constants.PLAYGUESTFAILURE: 
				

				break;
			case Constants.GAMEOVER:
				
			}
		} else if (o instanceof Striker){
			System.out.println("Client received PacketStriker!");
			GameController.setOtherStriker((Striker)o);
			
		} else if (o instanceof Puck){
			System.out.println("Client received PacketPuck!");
			GameController.setPuck((Puck)o);
			
		} else if (o instanceof PacketStats){
			System.out.println("Client received PacketStats!");
			Hockey.getStatsController().setStats(((PacketStats) o).matchesWon, ((PacketStats) o).matchesLost, ((PacketStats) o).goalsFor, ((PacketStats) o).goalsAgainst);
			Platform.runLater(() -> {
				Hockey.getPrimaryStage().setScene(Hockey.getStatsScene());;
            });
		} 
		

	}
	
	
	public void disconnected(Connection c) {
		System.out.println("Disconnected from server. ");
	}
	

	public Client getClient() { return client; }
	
}
