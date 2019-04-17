package hockey.java;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import hockey.java.front.PVector;
import hockey.java.front.Player;
import hockey.java.front.Puck;
import hockey.java.front.Striker;
import hockey.java.front.User;
import hockey.java.packet.PacketAttempt;
import hockey.java.packet.PacketStriker;

public class Master extends Listener { // SERVER

	private static Server server = null;
	public static final String ngrok_url = "https://d69be386.ngrok.io";
	public static final int tcpPort = 27960;
	public static Map<Integer, User> users = Collections.synchronizedMap(new HashMap<>()); 
	public static boolean p1Ready = false;
	public static boolean p2Ready = false;
	
	
	public static void registerClasses(Kryo k) {

		// register packet. ONLY objects registered as packets can be sent
		k.register(Player.class);
		k.register(Puck.class);
		k.register(Striker.class);
		k.register(User.class);
	}
	
	public static void main(String[] args) {

	
		System.out.println("Creating server...");
		
		// create server
		server = new Server();
		
		Master.registerClasses(server.getKryo());
		
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
	public void received(Connection c, Object o) {
		if (o instanceof PacketAttempt){
			switch(((PacketAttempt) o).attempt) {
			case 1: break;
			case 2: break;
			case 3: break;
			case 4: break;
			case 5: break;
			case 6: break;
	
			}
		} else if (o instanceof PacketStriker){
			PVector location = ((PacketStriker) o).location;
			PVector velocity = ((PacketStriker) o).velocity;
			
		} 
		
	}
	
	public void disconnected(Connection c) {
		System.out.println("Lost connection from " + c.getRemoteAddressTCP().getHostString());
	}

}
