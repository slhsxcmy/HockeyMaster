package hockey.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import hockey.java.controller.GameController;
import hockey.java.database.SQLModel;
import hockey.java.front.Goal;
import hockey.java.front.Midline;
import hockey.java.front.PVector;
import hockey.java.front.Player;
import hockey.java.front.PowerUp;
import hockey.java.front.PowerUpGoalSize;
import hockey.java.front.PowerUpMidline;
import hockey.java.front.PowerUpPuckSize;
import hockey.java.front.Puck;
import hockey.java.front.Striker;
import hockey.java.front.User;
import hockey.java.front.Walls;
import hockey.java.network.NetworkHelper;
import hockey.java.packet.Constants;
import hockey.java.packet.PacketAttempt;
import hockey.java.packet.PacketMouse;
import hockey.java.packet.PacketPU;
import hockey.java.packet.PacketPuck;
import hockey.java.packet.PacketReturn;
import hockey.java.packet.PacketStriker;
import javafx.scene.paint.Color;

public class Master extends Listener { // SERVER

	private static Server server;

	private static final int GOALSTOWIN = 100;
	private static final int PUMT = 300; // power up mean time
	public static final String server_ngrok_url = NetworkHelper.server_ngrok_url;
	public static final int server_tcpPort = NetworkHelper.server_tcpPort;

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
	private static Set<PowerUp> powerups = Collections.synchronizedSet(new HashSet<>());
	private static PowerUpMidline puMidline;
	private static PowerUpPuckSize puPuck;
	private static PowerUpGoalSize puGoal;
	private static int time;
	private static Random rnd = new Random();
	private static int rt = rnd.nextInt(2 * PUMT);
	
	public static PowerUp getRandomPowerUp() {
		int index = rnd.nextInt(powerups.size());
		Iterator<PowerUp> iter = powerups.iterator();
		for (int i = 0; i < index; i++) {
		    iter.next();
		}
		return iter.next();	
	}

	public static int getGoalsFor(int dbid) {
		if(dbid == players.get(0)) {
			return s1.getPlayer().getScore();
		}
		else {
			return s2.getPlayer().getScore();
		}	
	}

	public static int getGoalsAgainst(int dbid) {
		if(dbid == players.get(0)) {
			return s2.getPlayer().getScore();
		}
		else {
			return s1.getPlayer().getScore();
		}	
	}

	public static void initBoard() {


		puMidline = new PowerUpMidline();
		powerups.add(puMidline);
		System.out.println("midline : " + ((Color)puMidline.getCircle().getFill()).getRed() + "," + ((Color)puMidline.getCircle().getFill()).getGreen() + "," + ((Color)puMidline.getCircle().getFill()).getBlue() + ",");
		
		puPuck = new PowerUpPuckSize();
		powerups.add(puPuck);
		System.out.println("pucksize: " + ((Color)puPuck.getCircle().getFill()).getRed() + "," + ((Color)puPuck.getCircle().getFill()).getGreen() + "," + ((Color)puPuck.getCircle().getFill()).getBlue() + ",");
		
//		puGoal = new PowerUpGoalSize();
//		powerups.add(puGoal);
		
		s1 = new Striker(new Player(1));
		s2 = new Striker(new Player(2));

		puck = new Puck();

		g1 = new Goal(1, puck, s1.getPlayer());
		g2 = new Goal(2, puck, s2.getPlayer());
		wall1 = new Walls(1);
		wall2 = new Walls(2);

		mid = new Midline();
		friction = .988;

		time = 0;

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

	public static void main(String[] args) {
		System.out.println("Creating server...");

		// create server
		server = new Server();

		NetworkHelper.registerClasses(server.getKryo());

		try {
			// bind to ports
			server.bind(NetworkHelper.server_tcpPort);
		} catch (IOException e) {
			System.out.println("Failed to bind to port " + NetworkHelper.server_tcpPort + ". Exiting Server.");
			return;
		}

		// add listener for connected/received/disconnected methods
		server.addListener(new Master());

		// start server
		server.start();
		System.out.println("Server is ready!");

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
				} else {
					c.sendTCP(p);
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
		} else if (o instanceof PacketMouse){

			PVector mouse = new PVector(((PacketMouse)o).x,((PacketMouse)o).y);
			int id = ((PacketMouse) o).id;
			PacketStriker ps;

			// id     = 1~2 
			// id - 1 = 1~2 
			// 2 - id = 1~0
			// 3 - id = 2~1
			if (id == 1) {
				s1.step(mouse, mid);
	           	s1.checkStrikerWallsMidline(); 
	           	ps = new PacketStriker(id,s1.getLocation().x,s1.getLocation().y,s1.getVelocity().x,s1.getVelocity().y);
			} else {
				s2.step(mouse, mid);
	           	s2.checkStrikerWallsMidline();
	           	ps = new PacketStriker(id,s2.getLocation().x,s2.getLocation().y,s2.getVelocity().x,s2.getVelocity().y);
			}

			connections.get(players.get(2-id)).sendTCP(ps);
			connections.get(players.get(id-1)).sendTCP(ps);// added to send to self as well

			if(puck.collision(s1)) {
				System.out.println("collision with 1");
				puck.recalculate(s1); // resolve collision
			}

			if(puck.collision(s2)) {
				System.out.println("collision with 2");
				puck.recalculate(s2); // resolve collision
			}
			
			puck.step(friction); 

			puck.checkPuckWalls();

			
			int checkMidline = puck.collision(mid, puMidline);
			int checkPuck = puck.collision(puPuck);
			if(checkMidline > 0) { // powerup move midline activate
				PacketPU ppu = new PacketPU(Constants.PUMIDLINEACT, checkMidline);
				Striker s = (checkMidline == 1) ? s1 : s2;
				puMidline.activate(mid, s);
				connections.get(players.get(0)).sendTCP(ppu);
				connections.get(players.get(1)).sendTCP(ppu);
				System.out.println("s1 hit Midline Power Up");
			} 
			
			if(checkPuck > 0) {// powerup minimize puck activate
				PacketPU ppu = new PacketPU(Constants.PUPUCKSIZEACT);
				puPuck.activate(puck);
				connections.get(players.get(0)).sendTCP(ppu);
				connections.get(players.get(1)).sendTCP(ppu);
				System.out.println("HIT Puck Size Power Up");
			}
			
				
			//puck.collision(goal1, puGoal); // powerup change goal size
			//puck.collision(goal2, puGoal);
			
			
			PacketPuck pp = new PacketPuck(puck.getLocation().x,puck.getLocation().y,puck.getVelocity().x,puck.getVelocity().y);
			connections.get(players.get(0)).sendTCP(pp);
			connections.get(players.get(1)).sendTCP(pp);
			
			
			
			if (g1.goalDetection(1)) {
				
				s1.getPlayer().score();
				s1.reset(1);
				s2.reset(2);
				
				mid.reset();
				puck.resetSize();
				
				// send goal message
				connections.get(players.get(0)).sendTCP(new PacketReturn(Constants.GOAL, 1, onlineUsers.get(players.get(0)).getUsername()+" GOAL!"));
				connections.get(players.get(1)).sendTCP(new PacketReturn(Constants.GOAL, 1, onlineUsers.get(players.get(0)).getUsername()+" GOAL!"));
				
			}
			if (g2.goalDetection(2)) {
				s2.getPlayer().score();
				s1.reset(1);
				s2.reset(2);
				mid.reset();
				puck.resetSize();
				
				connections.get(players.get(0)).sendTCP(new PacketReturn(Constants.GOAL, 2, onlineUsers.get(players.get(1)).getUsername()+" GOAL!"));
				connections.get(players.get(1)).sendTCP(new PacketReturn(Constants.GOAL, 2, onlineUsers.get(players.get(1)).getUsername()+" GOAL!"));

				
			}
			if (s1.getPlayer().getScore() == GOALSTOWIN) { //GAME OVER HERE
				//Update SQL inside updateStats
				connections.get(players.get(0)).sendTCP(model.updateStats(players.get(0), "You won!"));
				connections.get(players.get(1)).sendTCP(model.updateStats(players.get(1), "You lost..."));				
				
			}
			if (s2.getPlayer().getScore() == GOALSTOWIN) { //GAME OVER HERE
				connections.get(players.get(1)).sendTCP(model.updateStats(players.get(1), "You won!"));
				connections.get(players.get(0)).sendTCP(model.updateStats(players.get(0), "You lost..."));

				
			}
			
			// Power up control
			
			time %= 2 * PUMT;
			if (time == rt) {
				rt = rnd.nextInt(2 * PUMT);
				
				PowerUp p = getRandomPowerUp();
				PacketPU ppu;
				if (p == puMidline) {
					if (puMidline.hidden() && mid.inMiddle()) {
						PVector v = puMidline.reset();
						ppu = new PacketPU(Constants.PUMIDLINESHOW, v.x, v.y);
						connections.get(players.get(0)).sendTCP(ppu);
						connections.get(players.get(1)).sendTCP(ppu);
						System.out.println("Showing Midline Power Up");
					}
				}
				else if (p == puPuck){ // pucksize
					if (puPuck.hidden() && puck.width == 30) {
						PVector v = puPuck.reset();
						ppu = new PacketPU(Constants.PUPUCKSIZESHOW, v.x, v.y);
						connections.get(players.get(0)).sendTCP(ppu);
						connections.get(players.get(1)).sendTCP(ppu);
						System.out.println("Showing Puck Size Power Up");
					}
				} else if (p instanceof PowerUpGoalSize) { // goal size
					// TODO 
				}
			}
			time++;
			System.out.println(time + " < " + rt);
		} 

	}

	public void activateGame() {	
		connections.get(players.get(0)).sendTCP(new PacketReturn(Constants.PLAYSUCCESS, players.get(0), onlineUsers.get(players.get(0)).getUsername(), 1));
		connections.get(players.get(1)).sendTCP(new PacketReturn(Constants.PLAYSUCCESS, players.get(1), onlineUsers.get(players.get(1)).getUsername(), 2));
		initBoard();
	}

	// runs when connection 
	public void connected(Connection c) {
		System.out.println("Received connection from " + c.getRemoteAddressTCP().getHostString());
	}

	public void disconnected(Connection c) {
		System.out.println("Lost connection from client. ");
	}

	public void debug() {
		// DEBUG
		System.out.println("onlineUsers now contains------------------------");
		for (Map.Entry<Integer,User> entry : onlineUsers.entrySet()) {  
            System.out.println("id = " + entry.getKey() + ", user = " + entry.getValue().getUsername()); 
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
