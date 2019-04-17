package hockey.java.packet;

public class PacketReturn {
	public int status;
	public String username;
	public int id;
	/*
	  1 = signup success
	  2 = signup failure
	  3 = login success
	  4 = login failure
	  5 = signout
	  6 = play logged or guest
	  7 = stats
	*/
}
