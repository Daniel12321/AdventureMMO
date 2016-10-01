package me.mrdaniel.mmo.io.blocktracking;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.mmo.io.ModdedBlocks;

public class WatchList {
	
	public static boolean shouldWatch(BlockType type, String id) {
		if (type.equals(BlockTypes.OBSIDIAN)
			|| type.equals(BlockTypes.DIAMOND_ORE)
			|| type.equals(BlockTypes.EMERALD_ORE)
			|| type.equals(BlockTypes.GOLD_ORE)
			|| type.equals(BlockTypes.IRON_ORE)
			|| type.equals(BlockTypes.COAL_ORE)
			|| type.equals(BlockTypes.REDSTONE_ORE)
			|| type.equals(BlockTypes.LIT_REDSTONE_ORE)
			|| type.equals(BlockTypes.LAPIS_ORE)
			|| type.equals(BlockTypes.QUARTZ_ORE)
			|| type.equals(BlockTypes.MOSSY_COBBLESTONE)
			|| type.equals(BlockTypes.LOG)
			|| type.equals(BlockTypes.LOG2)
			|| type.equals(BlockTypes.SAND)
			|| type.equals(BlockTypes.DIRT)
			|| type.equals(BlockTypes.GRAVEL)
			|| type.equals(BlockTypes.SOUL_SAND)
			|| type.equals(BlockTypes.GRASS)
			|| type.equals(BlockTypes.MYCELIUM)
			|| type.equals(BlockTypes.CLAY)
			|| type.equals(BlockTypes.POTATOES)
			|| type.equals(BlockTypes.CARROTS)
			|| type.equals(BlockTypes.CACTUS)
			|| type.equals(BlockTypes.REEDS)
			|| type.equals(BlockTypes.WATERLILY)
			|| type.equals(BlockTypes.WHEAT)
			|| type.equals(BlockTypes.MELON_BLOCK)
			|| type.equals(BlockTypes.PUMPKIN)
			|| type.equals(BlockTypes.NETHER_WART)
			|| type.equals(BlockTypes.BROWN_MUSHROOM)
			|| type.equals(BlockTypes.BROWN_MUSHROOM_BLOCK)
			|| type.equals(BlockTypes.RED_MUSHROOM)
			|| type.equals(BlockTypes.RED_MUSHROOM_BLOCK)
			|| type.equals(BlockTypes.LEAVES)
			|| type.equals(BlockTypes.LEAVES2)
			|| ModdedBlocks.getModdedBlock(id) != null) { return true; }
		return false;
	}
	public static void add(Location<World> loc) { ChunkManager.getInstance().add(loc); }
	public static void remove(Location<World> loc) { ChunkManager.getInstance().remove(loc); }
	public static boolean isBlocked(Location<World> loc) { return ChunkManager.getInstance().isBlocked(loc); }
}