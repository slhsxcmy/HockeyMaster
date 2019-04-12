package hockey.java;

import java.io.IOException;
import java.util.Date;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class Master extends Listener { // SERVER

	private static Server server = null;
	public static final String ngrok_url = "https://d69be386.ngrok.io";
	public static final int tcpPort = 27960;
	
	public static void main(String[] args) {
		System.out.println("Creating server...");
		
		// create server
		server = new Server();
		
		// register packet. ONLY objects registered as packets can be sent
		server.getKryo().register(Player.class);
		server.getKryo().register(Puck.class);
		server.getKryo().register(Striker.class);
		
		
		try {
			// bind to ports
			server.bind(tcpPort);
		} catch (IOException e) {
			System.out.println("Failed to bind to port " + tcpPort);
		}
		
		// add listener for connected/received/disconnected methods
		server.addListener(new Master());
		
		// start server
		server.start();
		System.out.println("Server is ready!");
		
	}
	
	// runs when connection 
	public void connected(Connection c) {
		System.out.println("Received connection from " + c.getRemoteAddressTCP().getHostString());
		// create message packet
		
		// send message
		// c.sendTCP(object); 
		
	}

	// runs when packet received
	public void received(Connection c, Object p) {
		// forward packet to other player
	}
	
	public void disconnected(Connection c) {
		System.out.println("Lost connection from " + c.getRemoteAddressTCP().getHostString());
	}

}
