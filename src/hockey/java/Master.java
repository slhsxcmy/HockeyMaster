package hockey.java;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import hockey.java.database.SQLModel;
import hockey.java.front.PVector;
import hockey.java.front.Player;
import hockey.java.front.Puck;
import hockey.java.front.Striker;
import hockey.java.front.User;
import hockey.java.packet.Constants;
import hockey.java.packet.PacketAttempt;
import hockey.java.packet.PacketPuck;
import hockey.java.packet.PacketReturn;
import hockey.java.packet.PacketStats;
import hockey.java.packet.PacketStriker;

public class Master extends Listener { // SERVER

	private static Server server;
	public static final String ngrok_url = "localhost";//"https://d69be386.ngrok.io";
	public static final int tcpPort = 27960;
	private static Map<Integer, User> users = Collections.synchronizedMap(new HashMap<>()); 
	private static Queue<Integer> queuedPlayers;
	private static List<Integer> players;
	private static SQLModel model = new SQLModel();
	
	public static void registerClasses(Kryo k) {

		// register packet. ONLY objects registered as packets can be sent
		k.register(PacketAttempt.class);
		k.register(PacketReturn.class);
		k.register(PacketStats.class);
		k.register(PacketStriker.class);
		k.register(PacketPuck.class);
		k.register(Striker.class);
		k.register(Player.class);
		k.register(Puck.class);
		k.register(PVector.class);
		k.register(Constants.class);
		// k.register(com.sun.javafx.geom.RectBounds.class);
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
			System.out.println("Failed to bind to port " + tcpPort + ". Exiting Server.");
			return;
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
		
	}

	// runs when packet received
	public void received(Connection c, Object o) {
		if (o instanceof PacketAttempt){
			System.out.println("Server received PacketAttempt of type " + ((PacketAttempt) o).attempt);
			int id = ((PacketAttempt) o).id;
			String username = ((PacketAttempt) o).username;
			String pw = ((PacketAttempt) o).password;
			String confirm = ((PacketAttempt) o).confirm;
			
			switch(((PacketAttempt) o).attempt) {
			
			/* RETURN
			  1 = sign up success
			  2 = sign up failure
			  3 = login success
			  4 = login failure
			  5 = signout
			  7 = play (logged or guest)
			  8 = stats
			  */
			case Constants.SIGNUPATTEMPT: //1 = signup
				c.sendTCP(model.checkSignUp(username, pw, confirm));
				break;
			case Constants.LOGINATTEMPT: //2 = login
				c.sendTCP(model.checkLogin(username, pw));
				break;
			case Constants.SIGNOUTATTEMPT: //3 = signout
				users.remove(id);
				c.sendTCP(new PacketReturn(Constants.SIGNOUTSUCCESS));
				break;

			case Constants.GETSTATSATTEMPT: //4 = get stats

				c.sendTCP(model.getStats(id));
				break;
				
			case Constants.PLAYLOGGEDATTEMPT: //5 = play logged
				c.sendTCP(new PacketReturn(Constants.PLAYLOGGEDSUCCESS));

				
				break;
			case Constants.PLAYGUESTATTEMPT: //6 = play guest
				// model.guestSignUp();
				// test how many players
				// c.sendTCP
				
				break;
	
			}
			
			
		} else if (o instanceof PacketStriker){
			System.out.println("received PacketStriker");
			
			PVector location = ((PacketStriker) o).location;
			PVector velocity = ((PacketStriker) o).velocity;
			// TODO detect collision
			
			// TODO calculate puck movement
			
			// TODO detect hit powerup
			
			// TODO detect goal
			
			// TODO send puck and other striker to client
		} 
		
	}
	
	public void disconnected(Connection c) {
		System.out.println("Lost connection from client.");
		
	}

}
