package hockey.java;

import com.esotericsoftware.kryonet.Connection;

public class Player {
	
	private Connection conn;
	private String username;
	private PVector mouse;
	private int score;
	
	
	public Player(String username) {
		this.username = username;
		mouse = new PVector(0, 0);
		score = 0;
	}
	
	public void score() {
		score++;
	}
	
	public String getUsername() {
		return username;
	}
	
	public PVector getMouse() {
		return mouse;
	}
	
	public int getScore() {
		return score;
	}

}
