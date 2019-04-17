package hockey.java.front;

public class User {
	private Striker striker;
	
	// get from DB
	private int id;
	private String username;
	private int matchesWon;
	private int matchesLost;
	private int goalsFor;
	private int goalsAgainst;
	
	public User(int id,String username) {
		striker = null;
		this.id = id;
		this.username = username;
		matchesWon = 0;
		matchesLost = 0;
		goalsFor = 0;
		goalsAgainst = 0;
	}
	
	public void initStriker() {
		striker = new Striker(new Player(username, id));
	}
	public Striker getStriker() {
		return striker;
	}
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getMatchesWon() {
		return matchesWon;
	}
	public void setMatchesWon(int matchesWon) {
		this.matchesWon = matchesWon;
	}
	public int getMatchesLost() {
		return matchesLost;
	}
	public void setMatchesLost(int matchesLost) {
		this.matchesLost = matchesLost;
	}
	public int getGoalsFor() {
		return goalsFor;
	}
	public void setGoalsFor(int goalsFor) {
		this.goalsFor = goalsFor;
	}
	public int getGoalsAgainst() {
		return goalsAgainst;
	}
	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}
	public void setStriker(Striker striker) {
		this.striker = striker;
	}
	
}
