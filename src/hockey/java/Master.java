package hockey.java;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import hockey.java.database.SQLModel;
import hockey.java.front.CenterCircle;
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
	private static Map<Integer, User> users = Collections.synchronizedMap(new HashMap<>()); 
	private static Queue<Integer> queuedPlayers;
	private static List<Integer> players;
	private static SQLModel model = new SQLModel();
	private static Puck puck;
	private static Player p1;
	private static Player p2;
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
		p1 = new Player("p1", 1);
		p2 = new Player("p2", 2);
		s1 = new Striker(p1);
		//s2 = new Striker();
		//u1 = new User();
		//u2 = new User();
		//u1.initStriker();
		//u2.initStriker();

		puck = new Puck();

		//goal1 = new Goal(1, puck, u1.getStriker().getPlayer());
		//goal2 = new Goal(2, puck, u2.getStriker().getPlayer());
		g1 = new Goal(1, puck, p1);
		g2 = new Goal(2, puck, p2);
		wall1 = new Walls(1);
		wall2 = new Walls(2);

		mid = new Midline();
		friction = .988;

		pu = new PowerUp();
		puckPU = new PowerUpPuckSize();
		time = 0;
		ran = new Random();
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
				initBoard();

				break;
			case Constants.PLAYGUESTATTEMPT: //6 = play guest
				// model.guestSignUp();
				// test how many players
				// c.sendTCP

				break;
			}


		} else if (o instanceof Striker){
			int player = ((Striker) o).getPlayer().getPlayerID();
			if (player == 1) {
				s1 = (Striker)o;
			}
			else {
				s2 = (Striker)o;
			}
			s1.step(p1.getMouse(), mid);
			//s2.step(p1.getMouse());
			s1.checkBoundaries(puck);
			//s2.checkBoundaries(puck);
			puck.collision(s1);
			//puck.collision(s2);
			puck.checkBoundaries();
			puck.step(friction);
			puck.collision(mid, pu);
			puck.collision(puckPU);
			if (g1.goalDetection(1)) {
				p1.score();
				s1.reset(1);
				mid.reset();
				puck.resetSize();
				//s2.reset(2);
			}
			if (g2.goalDetection(2)) {
				p2.score();
				s1.reset(1);
				mid.reset();
				puck.resetSize();
				//s2.reset(2);
			}
			if (p1.getScore() == 7) {
				//send win/loss message
			}
			if (p2.getScore() == 7) {
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

	public void disconnected(Connection c) {
		System.out.println("Lost connection from client.");
	}

}
