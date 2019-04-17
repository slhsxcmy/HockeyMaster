package hockey.java.front;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import hockey.java.Hockey;
import hockey.java.Master;
import hockey.java.controller.LoggedController;
import hockey.java.controller.LoginController;
import hockey.java.controller.SignupController;
import hockey.java.packet.PacketPuck;
import hockey.java.packet.PacketReturn;
import hockey.java.packet.PacketStriker;

public class Network extends Listener{

	private Connection c = null;

	public static void main(String[] args) {
		
		String ip = Master.ngrok_url;
		int port = Master.tcpPort;
		Client client = new Client();
		
		// register packet
		Master.registerClasses(client.getKryo());

		client.start();
		
		client.addListener(new Network());
		
		try {
			client.connect(5000, ip, port); //blocks for 5 seconds
		} catch (IOException e) {
			System.out.println("Cannot connect to server.");
		} 

		// add listener for connected/received/disconnected methods
		client.addListener(new Network());
		System.out.println("Client waiting for a packet...\n");		
		
		while(true) {
			// send self data
			// (Connection) c.sendTCP(pm); 
		}
		// c.sendTCP(pm); 
		
	}
	
	// runs upon packet received
	public void received(Connection c, Object o) {
		
		if (o instanceof PacketReturn){
			/*
			  odd = success
			  even = failure
			  12 = signup
			  34 = login
			  56 = signout
			  78 = play (logged or guest)
			*/
			int id;
			String username;
			switch(((PacketReturn) o).status) {
			
			case 1: 
			case 3: 
				id = ((PacketReturn) o).id;
				username = ((PacketReturn) o).username;
				Hockey.setSelf(new User(id,username));
				break;
			case 2: 
				SignupController.setMessage("Signup failed. Please try again.");
				break;
			case 4: 
				LoginController.setMessage("Login failed. Please try again.");
				break;
			case 5: 
				Hockey.setSelf(null);
				break;
			case 6: 
				LoggedController.setMessage("Login failed. Please try again.");
				break;
			case 7: 
				Game game = new Game();
				break;
			case 8: 
				LoggedController.setMessage("Join game failed. Please wait.");
				break;
			}
		} else if (o instanceof PacketStriker){
			PVector location = ((PacketStriker) o).location;
			PVector velocity = ((PacketStriker) o).velocity;
			
		} else if (o instanceof PacketPuck){
			PVector location = ((PacketPuck) o).location;
			PVector velocity = ((PacketPuck) o).velocity;
			
		} 
		

	}
	
	public Connection getConnection() {
		return c;
	}
//	public void disconnected(Connection c) {
//		System.out.println("Disconnected from server at " + c.getRemoteAddressTCP().getHostString());
//	}
	
}
