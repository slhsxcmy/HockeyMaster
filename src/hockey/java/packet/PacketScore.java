package hockey.java.packet;

public class PacketScore {
	public int playerID1;
	public int playerID2;
	public int score1;
	public int score2;
	
	public PacketScore() {} // for KryoNet
	
	public PacketScore(int playerID1, int playerID2, int score1, int score2) {
		this.playerID1 = playerID1;
		this.playerID2 = playerID2;
		this.score1 = score1;
		this.score2 = score2;
	}
	
	
	
}
