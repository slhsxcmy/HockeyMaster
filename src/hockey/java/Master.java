package hockey.java;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class Master extends Listener { // SERVER

	static Server server;
	public static final String ngrok_url = "https://d69be386.ngrok.io";
	public static final int tcpPort = 27014;
	public static Map<Integer, Striker> strikers = new HashMap<Integer, Striker>(); //connection ID and striker
	public static boolean p1Connected = false;
	public static boolean p2Connected = false;
	
	
	public static void main(String[] args) {		 
		System.out.println("Creating server...");
		
		// create server
		server = new Server();
		
		
		
		// register packet. ONLY objects registered as packets can be sent
		//server.getKryo().register(Player.class); striker contains player
//		server.getKryo().register(Puck.class);
//		server.getKryo().register(Striker.class);
		
		
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
		//Striker s = new Striker(); //create a striker for each player		
//		Packet packet = new Packet();
//		packet.message = "Connection from" + c.getRemoteAddressTCP();
		
		// send message
		//c.sendTCP(packet);
		System.out.println("Connection received");
		
		if(!p1Connected) { 
			p1Connected = true;
			//strikers.put(c.getID(), s);
		}
		else if (!p2Connected) {
			p2Connected = true;
			
			//TODO Start game here
		}
		else { //p1 and p2 already connected, the rest connections will wait
			System.out.print("Sorry. The game has started. Please wait for the next game.");
			return;
		}
		
	}

	// runs when packet received
	public void received(Connection conn, Object obj) {
		// forward packet to other player
		if(obj instanceof Striker) { //if packet is striker info
			//cast
			Striker s = (Striker) obj; 
			//update loc and vel
			strikers.get(conn.getID()).location = s.location;
			strikers.get(conn.getID()).velocity = s.velocity;
			s.connID = conn.getID();
			//send package
			server.sendToAllExceptTCP(conn.getID(), s);
			System.out.println("received and sent an update striker packet");
			
		}
		else if(obj instanceof Puck) { //totally unsure about this part
			//cast
			Puck p = (Puck) obj; 
			//update loc and vel
//			puck.location = p.location;
//			puck.velocity = p.velocity;
			//send package
			server.sendToAllTCP(p);
			System.out.println("received and sent an update puck packet");

		}
	}
	
	public void disconnected(Connection c) {
		System.out.println("Lost connection from " + c.getRemoteAddressTCP().getHostString());
		try {
			Game.game.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
