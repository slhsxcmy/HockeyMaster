package hockey.java.packet;

public class PacketAttempt {
	public int attempt;	
	/*
	  1 = signup
	  2 = login
	  3 = signout
	  4 = get stats
	  5 = play logged
	  6 = play guest
	*/
	public String username = null;

	public String password = null;
	public String confirm = null;
	
	public PacketAttempt() {} // default
	public PacketAttempt(int attempt, String username) { // signout, get stats, play
		this(attempt,username,null,null);
	}
	public PacketAttempt(int attempt, String username, String password) { // login
		this(attempt,username,password,null);
	}				
	public PacketAttempt(int attempt, String username, String password, String confirm) { // signup
		this.attempt = attempt;
		this.username = username;
		this.password = password;
		this.confirm = confirm;
	}
}
