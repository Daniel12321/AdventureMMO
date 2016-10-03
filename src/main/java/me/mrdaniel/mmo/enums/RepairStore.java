package me.mrdaniel.mmo.enums;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

import com.google.common.collect.ImmutableMap;

import me.mrdaniel.mmo.utils.ItemInfo;

public class RepairStore {
	
	private static RepairStore instance = null;
	public static RepairStore getInstance() { if (instance == null) { instance = new RepairStore(); } return instance; }
	
	public ImmutableMap<ItemType, ItemInfo> items;
	
	private RepairStore() {
		ImmutableMap.Builder<ItemType, ItemInfo> t = ImmutableMap.builder();
		
		items = t.put(ItemTypes.BOW, new ItemInfo(ItemTypes.STRING, 385, 3, 100))
		.put(ItemTypes.FISHING_ROD, new ItemInfo(ItemTypes.STRING, 65, 2, 100))
		
		.put(ItemTypes.WOODEN_SWORD, new ItemInfo(ItemTypes.PLANKS, 60, 2, 100))
		.put(ItemTypes.WOODEN_PICKAXE, new ItemInfo(ItemTypes.PLANKS, 60, 3, 100))
		.put(ItemTypes.WOODEN_AXE, new ItemInfo(ItemTypes.PLANKS, 60, 3, 100))
		.put(ItemTypes.WOODEN_SHOVEL, new ItemInfo(ItemTypes.PLANKS, 60, 1, 100))
		.put(ItemTypes.WOODEN_HOE, new ItemInfo(ItemTypes.PLANKS, 60, 2, 100))
		.put(ItemTypes.LEATHER_HELMET, new ItemInfo(ItemTypes.LEATHER, 56, 5, 100))
		.put(ItemTypes.LEATHER_CHESTPLATE, new ItemInfo(ItemTypes.LEATHER, 81, 8, 100))
		.put(ItemTypes.LEATHER_LEGGINGS, new ItemInfo(ItemTypes.LEATHER, 76, 7, 100))
		.put(ItemTypes.LEATHER_BOOTS, new ItemInfo(ItemTypes.LEATHER, 66, 4, 100))
		
		.put(ItemTypes.STONE_SWORD, new ItemInfo(ItemTypes.COBBLESTONE, 132, 2, 200))
		.put(ItemTypes.STONE_PICKAXE, new ItemInfo(ItemTypes.COBBLESTONE, 132, 3, 200))
		.put(ItemTypes.STONE_AXE, new ItemInfo(ItemTypes.COBBLESTONE, 132, 3, 200))
		.put(ItemTypes.STONE_SHOVEL, new ItemInfo(ItemTypes.COBBLESTONE, 132, 1, 200))
		.put(ItemTypes.STONE_HOE, new ItemInfo(ItemTypes.COBBLESTONE, 132, 2, 200))
		.put(ItemTypes.CHAINMAIL_HELMET, new ItemInfo(ItemTypes.IRON_BARS, 166, 5, 200))
		.put(ItemTypes.CHAINMAIL_CHESTPLATE, new ItemInfo(ItemTypes.IRON_BARS, 241, 8, 200))
		.put(ItemTypes.CHAINMAIL_LEGGINGS, new ItemInfo(ItemTypes.IRON_BARS, 226, 7, 200))
		.put(ItemTypes.CHAINMAIL_BOOTS, new ItemInfo(ItemTypes.IRON_BARS, 196, 4, 200))
		
		.put(ItemTypes.IRON_SWORD, new ItemInfo(ItemTypes.IRON_INGOT, 251, 2, 300))
		.put(ItemTypes.IRON_PICKAXE, new ItemInfo(ItemTypes.IRON_INGOT, 251, 3, 300))
		.put(ItemTypes.IRON_AXE, new ItemInfo(ItemTypes.IRON_INGOT, 251, 3, 300))
		.put(ItemTypes.IRON_SHOVEL, new ItemInfo(ItemTypes.IRON_INGOT, 251, 1, 300))
		.put(ItemTypes.IRON_HOE, new ItemInfo(ItemTypes.IRON_INGOT, 251, 2, 300))
		.put(ItemTypes.IRON_HELMET, new ItemInfo(ItemTypes.IRON_INGOT, 166, 5, 300))
		.put(ItemTypes.IRON_CHESTPLATE, new ItemInfo(ItemTypes.IRON_INGOT, 241, 8, 300))
		.put(ItemTypes.IRON_LEGGINGS, new ItemInfo(ItemTypes.IRON_INGOT, 226, 7, 300))
		.put(ItemTypes.IRON_BOOTS, new ItemInfo(ItemTypes.IRON_INGOT, 196, 4, 300))
		
		.put(ItemTypes.GOLDEN_SWORD, new ItemInfo(ItemTypes.GOLD_INGOT, 33, 2, 400))
		.put(ItemTypes.GOLDEN_PICKAXE, new ItemInfo(ItemTypes.GOLD_INGOT, 33, 3, 400))
		.put(ItemTypes.GOLDEN_AXE, new ItemInfo(ItemTypes.GOLD_INGOT, 33, 3, 400))
		.put(ItemTypes.GOLDEN_SHOVEL, new ItemInfo(ItemTypes.GOLD_INGOT, 33, 1, 400))
		.put(ItemTypes.GOLDEN_HOE, new ItemInfo(ItemTypes.GOLD_INGOT, 33, 2, 400))
		.put(ItemTypes.GOLDEN_HELMET, new ItemInfo(ItemTypes.GOLD_INGOT, 78, 5, 400))
		.put(ItemTypes.GOLDEN_CHESTPLATE, new ItemInfo(ItemTypes.GOLD_INGOT, 113, 8, 400))
		.put(ItemTypes.GOLDEN_LEGGINGS, new ItemInfo(ItemTypes.GOLD_INGOT, 106, 7, 400))
		.put(ItemTypes.GOLDEN_BOOTS, new ItemInfo(ItemTypes.GOLD_INGOT, 92, 4, 400))
		
		.put(ItemTypes.DIAMOND_SWORD, new ItemInfo(ItemTypes.DIAMOND, 1562, 2, 500))
		.put(ItemTypes.DIAMOND_PICKAXE, new ItemInfo(ItemTypes.DIAMOND, 1562, 3, 500))
		.put(ItemTypes.DIAMOND_AXE, new ItemInfo(ItemTypes.DIAMOND, 1562, 3, 500))
		.put(ItemTypes.DIAMOND_SHOVEL, new ItemInfo(ItemTypes.DIAMOND, 1562, 1, 500))
		.put(ItemTypes.DIAMOND_HOE, new ItemInfo(ItemTypes.DIAMOND, 1562, 2, 500))
		.put(ItemTypes.DIAMOND_HELMET, new ItemInfo(ItemTypes.DIAMOND, 364, 5, 500))
		.put(ItemTypes.DIAMOND_CHESTPLATE, new ItemInfo(ItemTypes.DIAMOND, 529, 8, 500))
		.put(ItemTypes.DIAMOND_LEGGINGS, new ItemInfo(ItemTypes.DIAMOND, 496, 7, 500))
		.put(ItemTypes.DIAMOND_BOOTS, new ItemInfo(ItemTypes.DIAMOND, 430, 4, 500))
		.build();
	}
}