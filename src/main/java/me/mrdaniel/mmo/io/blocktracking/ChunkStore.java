package me.mrdaniel.mmo.io.blocktracking;

import java.io.File;
import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.world.World;

import com.google.common.collect.Lists;

import me.mrdaniel.mmo.Main;

public class ChunkStore {

	@Nonnull private final String fileStr;
	@Nonnull private List<Coord> store;
	@Nonnull public final String world;
	public final int chunkX;
	public final int chunkZ;
	public long lastUse;

	public ChunkStore(@Nonnull final World world, final int chunkX, final int chunkZ) {
		this.store = Lists.newArrayList();
		this.world = world.getName();
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.fileStr = Main.getInstance().getChunkManager().getPath().resolve(this.world + "." + chunkX + "." + chunkZ + ".dat").toString();
		this.lastUse = System.currentTimeMillis();
	}
	public void add(final int x, final int y, final int z) { this.lastUse = System.currentTimeMillis(); this.store.add(new Coord(x,y,z)); }
	public void remove(final int x, final int y, final int z) { this.lastUse = System.currentTimeMillis(); for (int i = 0; i < this.store.size(); i++) { Coord coord = this.store.get(i); if (coord.X() == x && coord.Y() == y && coord.Z() == z) { this.store.remove(coord); } } }
	public boolean isBlocked(final int x, final int y, final int z) { this.lastUse = System.currentTimeMillis(); for (int i = 0; i < this.store.size(); i++) { Coord coord = this.store.get(i); if (coord.X() == x && coord.Y() == y && coord.Z() == z) { return true; } } return false;  }
	public void save() { Main.getInstance().getChunkManager().write(this.store, new File(this.fileStr)); }
	public void load() { this.store = Main.getInstance().getChunkManager().read(new File(this.fileStr)); }
}