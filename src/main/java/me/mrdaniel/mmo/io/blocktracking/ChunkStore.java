package me.mrdaniel.mmo.io.blocktracking;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.world.World;

public class ChunkStore {
	
	private List<Coord> store;
	public String world;
	public int chunkX;
	public int chunkZ;
	private String fileStr;
	public long lastUse;
	
	public ChunkStore(World world, int chunkX, int chunkZ) {
		this.store = new ArrayList<Coord>();
		this.world = world.getName();
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.fileStr = ChunkManager.getInstance().getPath().resolve(this.world + "." + chunkX + "." + chunkZ + ".dat").toString();
		this.lastUse = System.currentTimeMillis();
	}
	public void add(int x, int y, int z) { lastUse = System.currentTimeMillis(); store.add(new Coord(x,y,z)); }
	public void remove(int x, int y, int z) { lastUse = System.currentTimeMillis(); for (int i = 0; i < store.size(); i++) { Coord coord = store.get(i); if (coord.X() == x && coord.Y() == y && coord.Z() == z) { store.remove(coord); } } }
	public boolean isBlocked(int x, int y, int z) {  lastUse = System.currentTimeMillis(); for (int i = 0; i < store.size(); i++) { Coord coord = store.get(i); if (coord.X() == x && coord.Y() == y && coord.Z() == z) { return true; } } return false;  }
	public void save() { ChunkManager.getInstance().write(store, new File(fileStr)); }
	public void load() { store = ChunkManager.getInstance().read(new File(fileStr)); }
}