package hockey.java;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import javafx.util.Pair;
import sun.net.www.content.text.plain;

public class HockeyServer extends Listener{ //SERVER
	
	static Game game;
	static Server server;
	static int tcpPort = 27960;
//	static PacketPlayerData data;
	static Player p1;
	static Player p2;
	static boolean p1Connected = false;
	static boolean p2Connected = false;
	
	
	public HockeyServer() {
		
	}
	
	public static void main(String[] args) {			
		String ngrok_url = "https://d69be386.ngrok.io";
		game = new Game();
		
		try {
			System.out.println("Creating hockey server");
			server = new Server();
			server.getKryo().register(PacketPlayerData.class);
			
			server.bind(tcpPort);
			server.start();
			
			server.addListener(new HockeyServer());
			System.out.println("Server is ready");
			
		}catch (Exception e) {
			System.out.println("e: "+e.getMessage());
		}   
	}
	
	public void connected(Connection conn) {
		System.out.println("Received a connection from "+conn.getRemoteAddressTCP());
		
		
		Player player = new Player("username");
		
		PacketPlayerData packet = new PacketPlayerData();
		packet.message = "Connection from" + conn.getRemoteAddressTCP();
		
		conn.sendTCP(packet);
		
//		System.out.println("current striker 1 vel: "+data.s1.getVelocity().x +" "+data.s1.getVelocity().y);
//		System.out.println("current striker 1 loc: "+data.s1.getLocation().x +" "+data.s1.getLocation().y);
//		System.out.println("current striker 2 vel: "+data.s2.getVelocity().x +" "+data.s2.getVelocity().y);
//		System.out.println("current striker 2 loc: "+data.s2.getLocation().x +" "+data.s2.getLocation().y);
//		System.out.println("current puck vel: "+data.p.getVelocity().x +" "+data.p.getVelocity().y);
//		System.out.println("current puck loc: "+data.p.getLocation().x +" "+data.p.getLocation().y);
		conn.sendTCP(packet);
		System.out.println("Connection received");
		
		if(!p1Connected) { 
			p1Connected = true;
			p1 = player;
		}
		else if (!p2Connected) {
			p2Connected = true;
			p2 = player;
		}
		else { //p1 and p2 already connected, the rest connections will wait
			System.out.print("Sorry. The game has started. Please wait for the next game.");
			return;
		}
					
	}
	
	

}
