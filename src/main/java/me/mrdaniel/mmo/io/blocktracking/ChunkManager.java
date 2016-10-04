package me.mrdaniel.mmo.io.blocktracking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.mmo.Main;

public class ChunkManager {
	
	private static ChunkManager instance = null;
	public static ChunkManager getInstance() { if (instance == null) { instance = new ChunkManager(); } return instance; }
	
	private ChunkManager() {
		chunks = new ArrayList<ChunkStore>();
		path = Main.getInstance().getFile().toPath().resolve("store");
	}
	
	private List<ChunkStore> chunks;
	private Path path;
	public Path getPath() { return path; }
	
	public void setup() {
		
		File folder = path.toFile();
		if (!folder.exists()) folder.mkdir();
		
		Main.getInstance().getGame().getScheduler().createTaskBuilder()
		.delay(5, TimeUnit.SECONDS)
		.interval(5, TimeUnit.SECONDS)
		.execute(()->heartbeat())
		.submit(Main.getInstance());
	}
	public void add(Location<World> loc) {
		ChunkStore store = get(loc);
		
		int x = Math.abs(loc.getBlockX()) % 32;
		int y = loc.getBlockY();
		int z = Math.abs(loc.getBlockZ()) % 32;
		
		store.add(x,y,z);
	}
	public void remove(Location<World> loc) {
		ChunkStore store = get(loc);
		
		int x = Math.abs(loc.getBlockX()) % 32;
		int y = loc.getBlockY();
		int z = Math.abs(loc.getBlockZ()) % 32;
		
		store.remove(x,y,z);
	}
	public boolean isBlocked(Location<World> loc) {
		ChunkStore store = get(loc);
		
		int x = Math.abs(loc.getBlockX()) % 32;
		int y = loc.getBlockY();
		int z = Math.abs(loc.getBlockZ()) % 32;
		
		if (store.isBlocked(x, y, z)) { return true; }
		return false;
	}
	public ChunkStore get(Location<World> loc) {
		int chunkX = loc.getBlockX()/32;
		int chunkZ = loc.getBlockZ()/32;
		for (ChunkStore store : chunks) { if (store.chunkX == chunkX && store.chunkZ == chunkZ && store.world == loc.getExtent().getName()) { return store; } }
		ChunkStore store = new ChunkStore(loc.getExtent(), chunkX, chunkZ);
		store.load();
		chunks.add(store);
		return store;
	}
	public void unload(ChunkStore store) { store.save(); chunks.remove(store); }
	public void heartbeat() {
		for (int i = 0; i < chunks.size(); i++) {
			ChunkStore store = chunks.get(i);
			if (store.lastUse < System.currentTimeMillis() - 30000) {
				unload(store);
			}
		}
	}
	public void writeAll() { for (ChunkStore store : chunks) { store.save(); } }
	public void write(List<Coord> store, File file) {
		try {
			if (store.isEmpty()) { if (file.exists()) { file.delete(); } return; }
			if (!file.exists()) { file.createNewFile(); }
			
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			int[][] storeRaw = new int[store.size()][3];
			for (int i = 0; i < store.size(); i++) {
				Coord coord = store.get(i);
				storeRaw[i][0] = coord.X(); storeRaw[i][1] = coord.Y(); storeRaw[i][2] = coord.Z();
			}
			oos.writeObject(storeRaw);
			oos.flush();
			oos.close();
			fos.close();
		}
		catch (Exception exc) {
			Main.getInstance().getLogger().error("Error while saving chunk file");
			exc.printStackTrace();
		}
	}
	public List<Coord> read(File file) {
		if (!file.exists()) { return new ArrayList<Coord>(); }
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			int[][] storeRaw = (int[][]) ois.readObject();
			
			ois.close();
			fis.close();
			
			List<Coord> store = new ArrayList<Coord>();
			for (int i = 0; i < storeRaw.length; i++) {
				store.add(new Coord(storeRaw[i][0], storeRaw[i][1], storeRaw[i][2]));
			}
			return store;
		}
		catch (Exception exc) {
			Main.getInstance().getLogger().error("Error while loading chunk file");
			return new ArrayList<Coord>();
		}
	}
}