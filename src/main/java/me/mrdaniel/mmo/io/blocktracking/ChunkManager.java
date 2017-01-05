package me.mrdaniel.mmo.io.blocktracking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.collect.Lists;

import me.mrdaniel.mmo.Main;

@Singleton
public class ChunkManager {

	@Nonnull private final List<ChunkStore> chunks;
	@Nonnull private final Path path;
	@Nonnull public Path getPath() { return path; }

	public ChunkManager(@Nonnull final Path path) {
		this.chunks = Lists.newArrayList();
		this.path = path;

		if (!Files.exists(path)) {
			try { Files.createDirectory(path); }
			catch (IOException exc) { Main.getInstance().getLogger().error("Failed to create main chunkstore directory: {}", exc); }
		}

		Task.builder().delayTicks(100).intervalTicks(100).execute(this::heartbeat).submit(Main.getInstance());
	}

	public void add(@Nonnull final Location<World> loc) {
		ChunkStore store = get(loc);

		int x = Math.abs(loc.getBlockX()) % 32;
		int y = loc.getBlockY();
		int z = Math.abs(loc.getBlockZ()) % 32;

		store.add(x,y,z);
	}

	public void remove(@Nonnull final Location<World> loc) {
		ChunkStore store = get(loc);

		int x = Math.abs(loc.getBlockX()) % 32;
		int y = loc.getBlockY();
		int z = Math.abs(loc.getBlockZ()) % 32;

		store.remove(x,y,z);
	}

	public boolean isBlocked(@Nonnull final Location<World> loc) {
		ChunkStore store = get(loc);

		int x = Math.abs(loc.getBlockX()) % 32;
		int y = loc.getBlockY();
		int z = Math.abs(loc.getBlockZ()) % 32;

		if (store.isBlocked(x, y, z)) { return true; }
		return false;
	}

	public ChunkStore get(@Nonnull final Location<World> loc) {
		int chunkX = loc.getBlockX()/32;
		int chunkZ = loc.getBlockZ()/32;
		for (ChunkStore store : this.chunks) { if (store.chunkX == chunkX && store.chunkZ == chunkZ && store.world == loc.getExtent().getName()) { return store; } }
		ChunkStore store = new ChunkStore(loc.getExtent(), chunkX, chunkZ);
		store.load();
		this.chunks.add(store);
		return store;
	}

	public void unload(@Nonnull final ChunkStore store) { store.save(); this.chunks.remove(store); }
	public void heartbeat() {
		for (int i = 0; i < this.chunks.size(); i++) {
			ChunkStore store = this.chunks.get(i);
			if (store.lastUse < System.currentTimeMillis() - 30000) {
				unload(store);
			}
		}
	}
	public void writeAll() { for (ChunkStore store : chunks) { store.save(); } }
	public void write(@Nonnull final List<Coord> store, @Nonnull final File file) {
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
		catch (final Exception exc) { Main.getInstance().getLogger().error("Error while saving chunk file: {}", exc); }
	}

	public List<Coord> read(@Nonnull final File file) {
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
		catch (final Exception exc) {
			Main.getInstance().getLogger().error("Error while loading chunk file: {}", exc);
			return Lists.newArrayList();
		}
	}

	public boolean shouldWatch(@Nonnull final BlockType type) {
		return (Main.getInstance().getValueStore().getBlockValue(type).isPresent());
	}
}