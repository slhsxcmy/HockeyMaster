package hockey.java.network;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import hockey.java.Hockey;
import hockey.java.controller.GameController;
import hockey.java.front.User;
import hockey.java.packet.Constants;
import hockey.java.packet.PacketPuck;
import hockey.java.packet.PacketReturn;
import hockey.java.packet.PacketStats;
import hockey.java.packet.PacketStriker;
import javafx.application.Platform;

public class Network extends Listener{

	//private static Hockey hockey;
	private static Client client = new Client();;
	private static String ip = NetworkHelper.client_ngrok_url;
	private static int port = NetworkHelper.client_tcpPort;
	
	public Network(/*Hockey hockey*/) {
	
		System.out.println("Starting Network constructor");

		//System.out.println("Before init Kryo client");
//		client = new Client();
		//System.out.println("After init Kryo client");
		
		// register packet
		System.out.println("Before client registering Kryo classes");
		Kryo k = client.getKryo();
		System.out.println("Got client Kryo");
		NetworkHelper.registerClasses(k);
		System.out.println("After client registering Kryo classes");

		client.start();
		
		System.out.println("Client started");

		try {
			System.out.println("Client trying to connect to " + ip + ":" + port);
			client.connect(5000, ip, port); //blocks for 5 seconds
		} catch (IOException e) {
			System.out.println("Cannot connect to server. Exiting Client.");
			System.exit(2);
		} 

		// add listener for connected/received/disconnected methods
		client.addListener(this);
		
		System.out.println("Client waiting for a packet...\n");		
			
	}
	
	// runs upon packet received
	public void received(Connection c, Object o) {
		
		if (o instanceof PacketReturn){
			System.out.println("Client received PacketReturn of type " + ((PacketReturn) o).status);

			int playerID = ((PacketReturn) o).playerNum;
			int id = ((PacketReturn) o).dbid;
			String username = ((PacketReturn) o).username;
			String message = ((PacketReturn) o).message;
			
			switch(((PacketReturn) o).status) {
			
			case Constants.SIGNUPSUCCESS:
			case Constants.LOGINSUCCESS: 

				System.out.println("user dbid = " + id + " username = " + username);
				Hockey.setUser(new User(id,username));
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
			case Constants.PLAYSUCCESS: 
				System.out.println("You are No. "+ ((PacketReturn) o).playerNum + " player in player list");
				System.out.println("Going to game scene");
				Platform.runLater(() -> {
					Hockey.getPrimaryStage().setScene(Hockey.getGameScene());
					Hockey.getGameController().init(playerID);
					Hockey.getGameController().gameLoop();
                });
				break;
			case Constants.PLAYFAILURE: 
				// TODO show error
				break;
			
			case Constants.GAMEOVER:
				break;
			}
		} else if (o instanceof PacketStriker){
			System.out.println("Client received PacketStriker of id = " + ((PacketStriker) o).id);
			GameController.getOtherStriker().setPosition(((PacketStriker) o).locX, ((PacketStriker) o).locY);
			GameController.getOtherStriker().setVelocity(((PacketStriker) o).velX, ((PacketStriker) o).velY);

			
		} else if (o instanceof PacketPuck){
			System.out.println("Client received PacketPuck!");
			
			GameController.getPuck().setPosition(((PacketPuck) o).locX, ((PacketPuck) o).locY);
			GameController.getPuck().setVelocity(((PacketPuck) o).velX, ((PacketPuck) o).velY);

			
		} else if (o instanceof PacketStats){
			System.out.println("Client received PacketStats!");
			Hockey.getStatsController().setStats(((PacketStats) o).matchesWon, ((PacketStats) o).matchesLost, ((PacketStats) o).goalsFor, ((PacketStats) o).goalsAgainst);
			Platform.runLater(() -> {
				Hockey.getPrimaryStage().setScene(Hockey.getStatsScene());
            });
		} 
		

	}
	
	
	public void disconnected(Connection c) {
		System.out.println("Disconnected from server. ");
	}
	

	public Client getClient() { return client; }
	
}
