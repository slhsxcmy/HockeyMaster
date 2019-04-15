//package hockey.java;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import com.esotericsoftware.kryonet.Connection;
//import com.esotericsoftware.kryonet.Listener;
//import com.esotericsoftware.kryonet.Server;
//
//public class HockeyServer extends Listener{ //SERVER
//	
//	static Game game;
//	static Server server;
//	static int tcpPort = 27960;
////	static PacketPlayerData data;
////	static Player p1;
////	static Player p2;
//	public static Map<Integer, Striker> strikers = new HashMap<Integer, Striker>(); //strikers contain players
//	public static boolean p1Connected = false;
//	public static boolean p2Connected = false;
//	public Puck puck;
//	
//	public HockeyServer() {
//		
//	}
//	
//	public static void main(String[] args) {			
//		String ngrok_url = "https://d69be386.ngrok.io";
//		game = new Game();
//		
//		try {
//			System.out.println("Creating hockey server");
//			server = new Server();
//			server.getKryo().register(PacketPlayerData.class);
//			
//			server.bind(tcpPort);
//			server.start();
//			
//			server.addListener(new HockeyServer());
//			System.out.println("Server is ready");
//			
//		}catch (Exception e) {
//			System.out.println("e: "+e.getMessage());
//		}   
//	}
//	
//	public void connected(Connection conn) {
//		System.out.println("Received a connection from "+conn.getRemoteAddressTCP());
//		
//		
//		Striker s = new Striker();
//		
//		PacketPlayerData packet = new PacketPlayerData();
//		packet.message = "Connection from" + conn.getRemoteAddressTCP();
//		
//		conn.sendTCP(packet);
//		
////		System.out.println("current striker 1 vel: "+data.s1.getVelocity().x +" "+data.s1.getVelocity().y);
////		System.out.println("current striker 1 loc: "+data.s1.getLocation().x +" "+data.s1.getLocation().y);
////		System.out.println("current striker 2 vel: "+data.s2.getVelocity().x +" "+data.s2.getVelocity().y);
////		System.out.println("current striker 2 loc: "+data.s2.getLocation().x +" "+data.s2.getLocation().y);
////		System.out.println("current puck vel: "+data.p.getVelocity().x +" "+data.p.getVelocity().y);
////		System.out.println("current puck loc: "+data.p.getLocation().x +" "+data.p.getLocation().y);
//		//conn.sendTCP(packet);
//		System.out.println("Connection received");
//		
//		if(!p1Connected) { 
//			p1Connected = true;
//			strikers.put(conn.getID(), s);
//		}
//		else if (!p2Connected) {
//			p2Connected = true;
//			strikers.put(conn.getID(), s);
//			
//			//TODO 2 players connected. Start Game Here.
//		}
//		else { //p1 and p2 already connected, the rest connections will wait
//			System.out.print("Sorry. The game has started. Please wait for the next game.");
//			return;
//		}
//					
//	}
//	
//	public void received(Connection conn, Object obj) {
//		if(obj instanceof Striker) { //if packet is striker info
//			Striker s = (Striker) obj;
//			strikers.get(conn.getID()).location = s.location;
//			strikers.get(conn.getID()).velocity = s.velocity;
//			s.connID = conn.getID();
//			server.sendToAllExceptTCP(conn.getID(), s);
//			System.out.println("received and sent an update X packet");
//			
//		}
//		else if(obj instanceof Puck) { //totally unsure about this part
//			Puck p = (Puck) obj;
//			puck.location = p.location;
//			puck.velocity = p.velocity;
//			server.sendToTCP(tcpPort, p);
//
//		}
//	}
//	
//	public void disconnected(Connection conn) {
//		strikers.clear();
//		server.sendToAllExceptTCP(conn.getID(), "A player has disconnected. End game.");
//		//TODO How to exit game?
//		
//	}
//
//}
