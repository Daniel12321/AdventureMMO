package me.mrdaniel.adventuremmo.io.items;

import java.util.Optional;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

public interface ItemDatabase {

	Optional<BlockData> getData(BlockType type);
	Optional<ToolData> getData(ItemType type);
	Optional<ToolData> getData(ItemStack item);
}