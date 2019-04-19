package hockey.java.packet;

import hockey.java.front.PVector;

public class PacketStriker {
	
	public PacketStriker() {
		this(-1,new PVector(0,0),new PVector(0,0));
	}
	
	public PacketStriker(int id, PVector location, PVector velocity) {
		this.id = id;
		this.location = location;
		this.velocity = velocity;
	}
	
	public int id;
	public PVector location;
	public PVector velocity;
	public void print() {
		System.out.print("id ");
		if(location == null) System.out.print("null ");
		else System.out.print(location.x + "," + location.y + " ");
		if(velocity == null) System.out.print("null");
		else System.out.println(velocity.x + "," + velocity.y);
	}
}
