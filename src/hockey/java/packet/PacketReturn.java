package hockey.java.packet;

public class PacketReturn {
	public int status;
	public String username;
	public int id;
	/*
	  1 = success
	  2 = failure
	  12 = signup
	  34 = login
	  56 = signout
	  78 = play logged or guest
	*/
}
