package hockey.java;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import hockey.java.front.Player;
import hockey.java.front.Puck;
import hockey.java.front.Striker;
import hockey.java.front.User;

public class Master extends Listener { // SERVER

	private static Server server = null;
	public static final String ngrok_url = "https://d69be386.ngrok.io";
	public static final int tcpPort = 27960;
	public static Map<Integer, User> users = Collections.synchronizedMap(new HashMap<>()); 
	public static boolean p1Ready = false;
	public static boolean p2Ready = false;
	
	
	
	public static void main(String[] args) {

	
		System.out.println("Creating server...");
		
		// create server
		server = new Server();
		
		// register packet. ONLY objects registered as packets can be sent
		server.getKryo().register(Player.class);
		server.getKryo().register(Puck.class);
		server.getKryo().register(Striker.class);
		server.getKryo().register(User.class);
		
		
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
		User u = new User(); //create a striker for each player		
		//users.put(u.getId(), u.)
		/*if(!p1Connected) { 
			p1Connected = true;
			strikers.put(c.getID(), s);
		}
		else if (!p2Connected) {
			p2Connected = true;
			
			//TODO Start game here
		}
		else { //p1 and p2 already connected, the rest connections will wait
			System.out.print("Sorry. The game has started. Please wait for the next game.");
			return;
		}
		*/

		
	}

	// runs when packet received
	public void received(Connection c, Object p) {
		// forward packet to other player
	}
	
	public void disconnected(Connection c) {
		System.out.println("Lost connection from " + c.getRemoteAddressTCP().getHostString());
	}

}
