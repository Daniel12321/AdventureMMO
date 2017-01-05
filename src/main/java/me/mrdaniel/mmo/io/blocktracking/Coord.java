package me.mrdaniel.mmo.io.blocktracking;

public class Coord {

	private int x;
	private int y;
	private int z;
	
	public Coord(int x, int y, int z) { this.x = x; this.y = y; this.z = z; }
	
	public int X() { return x; }
	public int Y() { return y; }
	public int Z() { return z; }
}
