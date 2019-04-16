package hockey.java;

import java.io.IOException;
import java.util.Date;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

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

		// start client BEFORE connecting
		client.start();
		
		try {
			// connect to server with timeout (ms)
			client.connect(5000, ip, port);
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
		
	}

	// runs upon connection 
	public void connected(Connection c) {
		System.out.println("Connected to server at " + c.getRemoteAddressTCP().getHostString());
		
		// c.sendTCP(pm); 
		
	}
	
	// runs upon packet received
	public void received(Connection c, Object p) {
		
//		if(p instanceof Puck) {
//		} else if(p instanceof Striker) {
//		}

	}
	
	public void disconnected(Connection c) {
		System.out.println("Disconnected from server at " + c.getRemoteAddressTCP().getHostString());
	}
	
}
