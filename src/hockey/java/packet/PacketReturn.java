package hockey.java.packet;

public class PacketReturn {
	public int status;
	
	/*
	  1 = sign up success
	  2 = sign up failure
	  3 = login success
	  4 = login failure
	  5 = signout
	  7 = play (logged or guest)
	  8 = stats
	  */
	

	public int id;
	public String username;
	
}
