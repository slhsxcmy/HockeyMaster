package hockey.java.packet;

public class PacketStats {
	
	public PacketStats(int mw, int ml, int gf, int ga) {
		matchesWon = mw;
		matchesLost = ml;
		goalsFor = gf;
		goalsAgainst = ga;
	}

	public int matchesWon;
	public int matchesLost;
	public int goalsFor;
	public int goalsAgainst;
	
}
