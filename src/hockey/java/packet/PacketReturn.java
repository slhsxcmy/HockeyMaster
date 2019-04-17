package hockey.java.packet;

public class PacketReturn {
	public int status;
	
	/*
	  odd = success
	  even = failure
	  12 = signup
	  34 = login
	  56 = signout
	  78 = play (logged or guest)
	  */
	

	public int id;
	public String username;
	
}
