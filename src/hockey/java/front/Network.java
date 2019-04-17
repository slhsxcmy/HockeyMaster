package hockey.java.front;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import hockey.java.Master;
import hockey.java.packet.PacketPuck;
import hockey.java.packet.PacketStatus;
import hockey.java.packet.PacketStriker;

public class Network extends Listener{

	private Connection c = null;

	public static void main(String[] args) {
		
		String ip = Master.ngrok_url;
		int port = Master.tcpPort;
		Client client = new Client();
		
		// register packet
		Master.registerClasses(client.getKryo());

		// start client BEFORE connecting
		client.start();
		
		try {
			// connect to server with timeout (ms)
			client.connect(5000, ip, port);
		} catch (IOException e) {
			System.out.println("Cannot connect to server.");
		} 

		// add listener for connected/received/disconnected methods
		client.addListener(new Network());
		System.out.println("Client waiting for a packet...\n");		
		
		while(true) {
			// send self data
			// (Connection) c.sendTCP(pm); 
		}
		
	}

	// runs upon connection 
	public void connected(Connection c) {
		System.out.println("Connected to server at " + c.getRemoteAddressTCP().getHostString());
		
		// c.sendTCP(pm); 
		
	}
	
	// runs upon packet received
	public void received(Connection c, Object o) {
		
		if (o instanceof PacketStatus){
			switch(((PacketStatus) o).status) {
			case 1: break;
			case 2: break;
			case 3: break;
			case 4: break;
			case 5: break;
			case 6: break;
			case 7: break;
			case 8: break;
	
			}
		} else if (o instanceof PacketStriker){
			PVector location = ((PacketStriker) o).location;
			PVector velocity = ((PacketStriker) o).velocity;
			
		} else if (o instanceof PacketPuck){
			PVector location = ((PacketPuck) o).location;
			PVector velocity = ((PacketPuck) o).velocity;
			
		} 
		

	}
	
	public void disconnected(Connection c) {
		System.out.println("Disconnected from server at " + c.getRemoteAddressTCP().getHostString());
	}
	
}
