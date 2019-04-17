package hockey.java.front;

import java.io.IOException;
import java.util.Date;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import hockey.java.Master;

public class Network extends Listener{

	private Connection c = null;

	public static void main(String[] args) {
		
		String ip = Master.ngrok_url;
		int port = Master.tcpPort;
		Client client = new Client();
		
		// register packet
		client.getKryo().register(Player.class);
		client.getKryo().register(Puck.class);
		client.getKryo().register(Striker.class);
		client.addListener(this);

		client.start();
		
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
	public void received(Connection conn, Object obj) {
		
		if(obj instanceof Striker) {
			Striker s = new Striker();
		} 
		else if(obj instanceof Striker) {
			
		}

	}
	
//	public void disconnected(Connection c) {
//		System.out.println("Disconnected from server at " + c.getRemoteAddressTCP().getHostString());
//	}
	
}
