package me.mrdaniel.mmo.io.blocktracking;

public class Coord implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private volatile transient int x;
	private volatile transient int y;
	private volatile transient int z;
	
	public Coord(int x, int y, int z) { this.x = x; this.y = y; this.z = z; }
	
	public int X() { return x; }
	public int Y() { return y; }
	public int Z() { return z; }
}
