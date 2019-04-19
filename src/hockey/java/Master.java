package hockey.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import hockey.java.database.SQLModel;
import hockey.java.front.PVector;
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
	private static Map<Integer, User> onlineUsers = Collections.synchronizedMap(new HashMap<>()); 
	private static Queue<Integer> waitList = new LinkedList<Integer>();
	private static List<Integer> players = new ArrayList<Integer>(); //store id in database
	private static SQLModel model = new SQLModel();
	
	
	public static Map<Integer, User> getMap(){
		return onlineUsers;
	}
	
	public static Queue<Integer> getWaitlist(){
		return waitList;
	}
	
	public static List<Integer> getPlayerlist(){
		return players;
	}
	
	public static void registerClasses(Kryo k) {

		// register packet. ONLY objects registered as packets can be sent
		k.register(PacketAttempt.class);
		k.register(PacketReturn.class);
		k.register(PacketStats.class);
		k.register(PacketStriker.class);
		k.register(PacketPuck.class);
		k.register(Constants.class);
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
		
		// DEBUG
		//model.signAsGuest();
	}
	
	// runs when connection 
	public void connected(Connection c) {
		System.out.println("Received connection from " + c.getRemoteAddressTCP().getHostString());
		
	}

	// runs when packet received
	public void received(Connection c, Object o) {
		if (o instanceof PacketAttempt){
			System.out.println("Server received PacketAttempt of type " + ((PacketAttempt) o).attempt);
			
			String username = ((PacketAttempt) o).username;
			String pw = ((PacketAttempt) o).password;
			String confirm = ((PacketAttempt) o).confirm;
			PacketReturn p = null;
			
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
			case Constants.SIGNUPATTEMPT: 
				System.out.println("received sign up attempt, begin to send return packet");
				c.sendTCP(model.checkSignUp(username, pw, confirm));
				//DEGUG
				System.out.println("Map now contains: ");
				for (Map.Entry<Integer,User> entry : onlineUsers.entrySet()) {  
		            System.out.println("id = " + entry.getKey() + 
		                             ", user = " + entry.getValue().getUsername()); 
				}
				
				System.out.println("Waitlist now contains: ");
				for(int i=0; i<waitList.size(); i++) {
					System.out.println("ids in the waitlist- "+waitList);
				}
				
				System.out.println("Playerlist now contains: ");
				for(int i=0; i<players.size(); i++) {
					System.out.println(players.get(i));
				}
			//DEBUG FINISH 
				break;
			case Constants.LOGINATTEMPT: 
				c.sendTCP(model.checkLogin(username, pw));
				//DEGUG
				System.out.println("Map now contains: ");
				for (Map.Entry<Integer,User> entry : onlineUsers.entrySet()) {  
		            System.out.println("id = " + entry.getKey() + 
		                             ", user = " + entry.getValue().getUsername()); 
				}
				
				System.out.println("Waitlist now contains: ");
				for(int i=0; i<waitList.size(); i++) {
					System.out.println("ids in the waitlist- "+waitList);
				}
				
				System.out.println("Playerlist now contains: ");
				for(int i=0; i<players.size(); i++) {
					System.out.println(players.get(i));
				}
			//DEBUG FINISH 
				break;
			case Constants.SIGNOUTATTEMPT: 
				
				break;
			case Constants.GETSTATSATTEMPT: 
				
				break;
			case Constants.PLAYLOGGEDATTEMPT:				
				c.sendTCP(model.loggedPlay(username));
				//DEGUG
				System.out.println("Map now contains: ");
				for (Map.Entry<Integer,User> entry : onlineUsers.entrySet()) {  
		            System.out.println("id = " + entry.getKey() + 
		                             ", user = " + entry.getValue().getUsername()); 
				}
				
				System.out.println("Waitlist now contains: ");
				for(int i=0; i<waitList.size(); i++) {
					System.out.println("ids in the waitlist- "+waitList);
				}
				
				System.out.println("Playerlist now contains: ");
				for(int i=0; i<players.size(); i++) {
					System.out.println(players.get(i));
				}
			//DEBUG FINISH 
				break;
			case Constants.PLAYGUESTATTEMPT: 
				c.sendTCP(model.signAsGuest());
				
				//DEGUG
					System.out.println("Map now contains: ");
					for (Map.Entry<Integer,User> entry : onlineUsers.entrySet()) {  
			            System.out.println("id = " + entry.getKey() + 
			                             ", user = " + entry.getValue().getUsername()); 
					}
					
					System.out.println("Waitlist now contains: ");
					for(int i=0; i<waitList.size(); i++) {
						System.out.println("ids in the waitlist- "+waitList);
					}
					
					System.out.println("Playerlist now contains: ");
					for(int i=0; i<players.size(); i++) {
						System.out.println(players.get(i));
					}
				//DEBUG FINISH 
					
				break;	
			}
			
			if(p != null) {
				System.out.println("Sending to client PacketReturn of type " + p.status);
				c.sendTCP(p);
			}
			
		} else if (o instanceof PacketStriker){
			PVector location = ((PacketStriker) o).location;
			PVector velocity = ((PacketStriker) o).velocity;
			
		}
		
	}
	
	public void disconnected(Connection c) {
		System.out.println("Lost connection from client.");
		
	}

}
