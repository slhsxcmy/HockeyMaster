package hockey.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import hockey.java.database.SQLModel;
import hockey.java.front.Goal;
import hockey.java.front.Midline;
import hockey.java.front.PVector;
import hockey.java.front.Player;
import hockey.java.front.PowerUp;
import hockey.java.front.PowerUpPuckSize;
import hockey.java.front.Puck;
import hockey.java.front.Striker;
import hockey.java.front.User;
import hockey.java.front.Walls;
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
	private static Map<Integer, Connection> connections = Collections.synchronizedMap(new HashMap<>()); 
	private static Queue<Integer> waitList = new LinkedList<Integer>();
	private static List<Integer> players = new ArrayList<Integer>(); //store id in database
	private static SQLModel model = new SQLModel();

	private static Puck puck;

	private static Striker s1;
	private static Striker s2;
	private static Goal g1;
	private static Goal g2;
	private static Walls wall1, wall2;
	private static Midline mid;
	private static double friction;
	private static PowerUp pu;
	private static PowerUpPuckSize puckPU;
	private static int time;
	private static Random ran;

	public static void initBoard() {
		
		s1 = new Striker(new Player(1));
		s2 = new Striker(new Player(2));
		//u1 = new User();
		//u2 = new User();
		//u1.initStriker();
		//u2.initStriker();

		puck = new Puck();

		//goal1 = new Goal(1, puck, u1.getStriker().getPlayer());
		//goal2 = new Goal(2, puck, u2.getStriker().getPlayer());
		g1 = new Goal(1, puck, s1.getPlayer());
		g2 = new Goal(2, puck, s2.getPlayer());
		wall1 = new Walls(1);
		wall2 = new Walls(2);

		mid = new Midline();
		friction = .988;

		pu = new PowerUp();
		puckPU = new PowerUpPuckSize();
		time = 0;
		ran = new Random();
	}

	

	public static Map<Integer, User> getUsers(){
		return onlineUsers;
	}

	public static Map<Integer, Connection> getConnections(){
		return connections;
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
		k.register(Striker.class);
		k.register(Player.class);
		k.register(Puck.class);
		k.register(PVector.class);
		k.register(Constants.class);
		k.register(com.sun.javafx.geom.RectBounds.class);
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
			PacketReturn p;

			switch(((PacketAttempt) o).attempt) {

		
			case Constants.SIGNUPATTEMPT: 
				System.out.println("received sign up attempt, begin to send return packet");

				c.sendTCP(model.checkSignUp(username, pw, confirm, c));
				
				debug();
				
				break;
			case Constants.LOGINATTEMPT: 
				c.sendTCP(model.checkLogin(username, pw, c));
				
				debug();
				
				break;

			case Constants.SIGNOUTATTEMPT: //3 = signout
				onlineUsers.remove(id);
				c.sendTCP(new PacketReturn(Constants.SIGNOUTSUCCESS));
				break;

			case Constants.GETSTATSATTEMPT: //4 = get stats
				c.sendTCP(model.getStats(id));
				break;


			
			case Constants.PLAYLOGGEDATTEMPT:
				p = model.loggedPlay(username, c);
				if(p.status == Constants.PLAYSUCCESS) {
					activateGame(); // game board on server					
				}
				
				debug();

				break;
			case Constants.PLAYGUESTATTEMPT: 

				p = model.signAsGuest(c);
				if(p.status == Constants.PLAYSUCCESS) {
					activateGame(); // game board on server				
				}

				debug();				
				break;	

			}
		} else if (o instanceof PacketStriker){
			int id = ((PacketStriker) o).id;
			
			
			
			if (id == 1) {
				s1.setPosition(((PacketStriker) o).locX, ((PacketStriker) o).locY);
				s1.setVelocity(((PacketStriker) o).velX, ((PacketStriker) o).velY);
				
				
			} else {
				s2.setPosition(((PacketStriker) o).locX, ((PacketStriker) o).locY);
				s2.setVelocity(((PacketStriker) o).velX, ((PacketStriker) o).velY);
				
			}
			
			System.out.println("Server forwarding PacketStriker to id = " + (3 - id));
			connections.get(players.get(2-id)).sendTCP(o);
			
			//s1.step(s1.getPlayer().getMouse(), mid);
			//s2.step(p1.getMouse());
			s1.checkBoundaries(puck); // s1 cannot push puck into wall
			puck.collision(s1); // resolve collision
			
			s2.checkBoundaries(puck); // s2 cannot push puck into wall
			puck.collision(s2); // resolve collision
			
			puck.checkBoundaries(); // puck and wall
			puck.step(friction); 
			
			puck.collision(mid, pu); // powerup move midline
			puck.collision(puckPU); // powerup minimize puck

			
			System.out.println("Server sending PacketPuck " );
			PacketPuck pp = new PacketPuck(puck.getLocation().x,puck.getLocation().y,puck.getVelocity().x,puck.getVelocity().y);
			connections.get(players.get(0)).sendTCP(pp);
			connections.get(players.get(1)).sendTCP(pp);
			
			if (g1.goalDetection(1)) {
				//TODO update score
				s1.getPlayer().score();
				s1.reset(1);
				s2.reset(2);
				
				mid.reset();
				puck.resetSize();
				
			}
			if (g2.goalDetection(2)) {
				s2.getPlayer().score();
				s1.reset(1);
				s2.reset(2);
				
				mid.reset();
				puck.resetSize();
			}
			if (s1.getPlayer().getScore() == 7) {
				//send win/loss message
				// c.sendTCP(new PacketReturn(Constants.GAMEOVER, winnerUsername));
				// TODO update SQL
				
				
			}
			if (s2.getPlayer().getScore() == 7) {
				//send win/loss message
			}
			if (time == (int)(ran.nextDouble() * 2500)) {
				time = 0;
				int choose = new Random().nextInt() % 2;
				if (choose == 0) {
					if (pu.hidden() && mid.inMiddle()) {
						pu.reset();
					}
				}
				else {
					if (puckPU.hidden() && puck.width == 30) {
						puckPU.reset(puck);
					}
				}
			}
			time++;
			
		} 

	}

	public void activateGame() {	
		connections.get(players.get(0)).sendTCP(new PacketReturn(Constants.PLAYSUCCESS, players.get(0), onlineUsers.get(players.get(0)).getUsername(), 1));
		connections.get(players.get(1)).sendTCP(new PacketReturn(Constants.PLAYSUCCESS, players.get(1), onlineUsers.get(players.get(1)).getUsername(), 2));
		initBoard();
	}
	
	public void disconnected(Connection c) {
		System.out.println("Lost connection from client.");
	}
	
	public void debug() {
		// DEBUG
		System.out.println("onlineUsers now contains------------------------");
		for (Map.Entry<Integer,User> entry : onlineUsers.entrySet()) {  
            System.out.println("id = " + entry.getKey() + 
                             ", user = " + entry.getValue().getUsername()); 
		}
		System.out.println("------------------------------------------------");
		
		System.out.println("Waitlist now contains---------------------------");
		for(int i=0; i<waitList.size(); i++) {
			System.out.println("ids in the waitlist- "+waitList);
		}
		System.out.println("------------------------------------------------");
		
		System.out.println("Playerlist now contains-------------------------");
		for(int i=0; i<players.size(); i++) {
			System.out.println(players.get(i));
		}
		System.out.println("------------------------------------------------");
		// DEBUG END
		
	}

}
