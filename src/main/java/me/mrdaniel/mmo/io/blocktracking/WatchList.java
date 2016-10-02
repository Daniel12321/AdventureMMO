package me.mrdaniel.mmo.io.blocktracking;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.mmo.io.ModdedBlocks;

public class WatchList {
	
	public static boolean shouldWatch(BlockType type, String id) {
		if (type == BlockTypes.OBSIDIAN
			|| type == BlockTypes.DIAMOND_ORE
			|| type == BlockTypes.EMERALD_ORE
			|| type == BlockTypes.GOLD_ORE
			|| type == BlockTypes.IRON_ORE
			|| type == BlockTypes.COAL_ORE
			|| type == BlockTypes.REDSTONE_ORE
			|| type == BlockTypes.LIT_REDSTONE_ORE
			|| type == BlockTypes.LAPIS_ORE
			|| type == BlockTypes.QUARTZ_ORE
			|| type == BlockTypes.MOSSY_COBBLESTONE
			|| type == BlockTypes.LOG
			|| type == BlockTypes.LOG2
			|| type == BlockTypes.SAND
			|| type == BlockTypes.DIRT
			|| type == BlockTypes.GRAVEL
			|| type == BlockTypes.SOUL_SAND
			|| type == BlockTypes.GRASS
			|| type == BlockTypes.MYCELIUM
			|| type == BlockTypes.CLAY
			|| type == BlockTypes.POTATOES
			|| type == BlockTypes.CARROTS
			|| type == BlockTypes.CACTUS
			|| type == BlockTypes.REEDS
			|| type == BlockTypes.WATERLILY
			|| type == BlockTypes.WHEAT
			|| type == BlockTypes.MELON_BLOCK
			|| type == BlockTypes.PUMPKIN
			|| type == BlockTypes.NETHER_WART
			|| type == BlockTypes.BROWN_MUSHROOM
			|| type == BlockTypes.BROWN_MUSHROOM_BLOCK
			|| type == BlockTypes.RED_MUSHROOM
			|| type == BlockTypes.RED_MUSHROOM_BLOCK
			|| type == BlockTypes.LEAVES
			|| type == BlockTypes.LEAVES2
			|| ModdedBlocks.getInstance().getModdedBlock(id) != null) { return true; }
		return false;
	}
	public static void add(Location<World> loc) { ChunkManager.getInstance().add(loc); }
	public static void remove(Location<World> loc) { ChunkManager.getInstance().remove(loc); }
	public static boolean isBlocked(Location<World> loc) { return ChunkManager.getInstance().isBlocked(loc); }
}