package hockey.java;

public class Player {
	
	private String username;
	private PVector mouse;
	private int score;
	
	public Player(String username) {
		this.username = username;
		mouse = new PVector(0,0,0);
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
