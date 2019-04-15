package hockey.java;

import java.io.IOException;
import java.util.Date;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientThread extends Listener{// NETWORK

	private Connection c = null;
	public static Client client;
	String ip = Master.ngrok_url;
	int port = Master.tcpPort;
	
	
	public ClientThread() {
		client = new Client();
		client.start();
	}
//	public static void main(String[] args) {
//		
//		client = new Client();
//		
//		// register packet
//		client.getKryo().register(Player.class);
//		client.getKryo().register(Puck.class);
//		client.getKryo().register(Striker.class);
//
//		// start client BEFORE connecting
//		client.start();
//		
//		try {
//			// connect to server with timeout (ms)
//			client.connect(5000, ip, port);
//		} catch (IOException e) {
//			System.out.println("Cannot connect to server.");
//		} 
//
//		// add listener for connected/received/disconnected methods
//		client.addListener(new ClientThread());
//		System.out.println("Client waiting for a packet...\n");		
//		
//		while(true) {
//			// send self data
//			// (Connection) c.sendTCP(pm); 
//		}
//		
//	}

	// runs upon connection 
	public void connect() {
		System.out.println("Connected to server at " + c.getRemoteAddressTCP().getHostString());
		client = new Client();
		client.getKryo().register(Puck.class);
		client.getKryo().register(Striker.class);
		client.addListener(this);

		client.start();
		
		try {
			client.connect(5000, ip, port); //blocks for 5 seconds
		} catch (IOException e) {
			e.printStackTrace();
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
