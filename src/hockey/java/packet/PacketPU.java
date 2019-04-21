package hockey.java.packet;

public class PacketPU {
	public int puid;
	public double x;
	public double y;

	
	public PacketPU() {
		this(-1,-1,-1);
	}
	
	public PacketPU(int i) {
		this(i,-1,-1);
	}
	
	public PacketPU(int i, double a, double b) {
		puid = i;
		x = a;
		y = b;
	}
}
