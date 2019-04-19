package hockey.java.packet;

public class PacketReturn {
	public int status;
	public int id;
	public String username;
	public String message;

	public int playerID;
	
	public PacketReturn(int status, int id) {
		this.status = status;
		this.id = id;
	}
	
	public PacketReturn() {} // for KryoNet; otherwise cannot be deserialized
	public PacketReturn(int status) {
		this.status = status;
	}
	public PacketReturn(int status, int id, String username) {
		this.status = status;
		this.id = id;
		this.username = username;
	}
	public PacketReturn(int status, String message) {
		this.status = status;
		this.message = message;
	}
	
	
}
