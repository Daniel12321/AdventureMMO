package me.mrdaniel.mmo.enums;

import java.util.HashMap;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

import com.google.common.collect.ImmutableMap;

import me.mrdaniel.mmo.utils.ItemWrapper;

public class RepairStore {
	
	private static RepairStore instance = null;
	public static RepairStore getInstance() { if (instance == null) { instance = new RepairStore(); } return instance; }
	
	public ImmutableMap<ItemType, ItemWrapper> items;
	
	private RepairStore() {
		HashMap<ItemType, ItemWrapper> t = new HashMap<ItemType, ItemWrapper>();
		
		t.put(ItemTypes.BOW, new ItemWrapper(ItemTypes.STRING, 385, 3, 100));
		t.put(ItemTypes.FISHING_ROD, new ItemWrapper(ItemTypes.STRING, 65, 2, 100));
		
		t.put(ItemTypes.WOODEN_SWORD, new ItemWrapper(ItemTypes.PLANKS, 60, 2, 100));
		t.put(ItemTypes.WOODEN_PICKAXE, new ItemWrapper(ItemTypes.PLANKS, 60, 3, 100));
		t.put(ItemTypes.WOODEN_AXE, new ItemWrapper(ItemTypes.PLANKS, 60, 3, 100));
		t.put(ItemTypes.WOODEN_SHOVEL, new ItemWrapper(ItemTypes.PLANKS, 60, 1, 100));
		t.put(ItemTypes.WOODEN_HOE, new ItemWrapper(ItemTypes.PLANKS, 60, 2, 100));
		t.put(ItemTypes.LEATHER_HELMET, new ItemWrapper(ItemTypes.LEATHER, 56, 5, 100));
		t.put(ItemTypes.LEATHER_CHESTPLATE, new ItemWrapper(ItemTypes.LEATHER, 81, 8, 100));
		t.put(ItemTypes.LEATHER_LEGGINGS, new ItemWrapper(ItemTypes.LEATHER, 76, 7, 100));
		t.put(ItemTypes.LEATHER_BOOTS, new ItemWrapper(ItemTypes.LEATHER, 66, 4, 100));
		
		t.put(ItemTypes.STONE_SWORD, new ItemWrapper(ItemTypes.COBBLESTONE, 132, 2, 200));
		t.put(ItemTypes.STONE_PICKAXE, new ItemWrapper(ItemTypes.COBBLESTONE, 132, 3, 200));
		t.put(ItemTypes.STONE_AXE, new ItemWrapper(ItemTypes.COBBLESTONE, 132, 3, 200));
		t.put(ItemTypes.STONE_SHOVEL, new ItemWrapper(ItemTypes.COBBLESTONE, 132, 1, 200));
		t.put(ItemTypes.STONE_HOE, new ItemWrapper(ItemTypes.COBBLESTONE, 132, 2, 200));
		t.put(ItemTypes.CHAINMAIL_HELMET, new ItemWrapper(ItemTypes.IRON_BARS, 166, 5, 200));
		t.put(ItemTypes.CHAINMAIL_CHESTPLATE, new ItemWrapper(ItemTypes.IRON_BARS, 241, 8, 200));
		t.put(ItemTypes.CHAINMAIL_LEGGINGS, new ItemWrapper(ItemTypes.IRON_BARS, 226, 7, 200));
		t.put(ItemTypes.CHAINMAIL_BOOTS, new ItemWrapper(ItemTypes.IRON_BARS, 196, 4, 200));
		
		t.put(ItemTypes.IRON_SWORD, new ItemWrapper(ItemTypes.IRON_INGOT, 251, 2, 300));
		t.put(ItemTypes.IRON_PICKAXE, new ItemWrapper(ItemTypes.IRON_INGOT, 251, 3, 300));
		t.put(ItemTypes.IRON_AXE, new ItemWrapper(ItemTypes.IRON_INGOT, 251, 3, 300));
		t.put(ItemTypes.IRON_SHOVEL, new ItemWrapper(ItemTypes.IRON_INGOT, 251, 1, 300));
		t.put(ItemTypes.IRON_HOE, new ItemWrapper(ItemTypes.IRON_INGOT, 251, 2, 300));
		t.put(ItemTypes.IRON_HELMET, new ItemWrapper(ItemTypes.IRON_INGOT, 166, 5, 300));
		t.put(ItemTypes.IRON_CHESTPLATE, new ItemWrapper(ItemTypes.IRON_INGOT, 241, 8, 300));
		t.put(ItemTypes.IRON_LEGGINGS, new ItemWrapper(ItemTypes.IRON_INGOT, 226, 7, 300));
		t.put(ItemTypes.IRON_BOOTS, new ItemWrapper(ItemTypes.IRON_INGOT, 196, 4, 300));
		
		t.put(ItemTypes.GOLDEN_SWORD, new ItemWrapper(ItemTypes.GOLD_INGOT, 33, 2, 400));
		t.put(ItemTypes.GOLDEN_PICKAXE, new ItemWrapper(ItemTypes.GOLD_INGOT, 33, 3, 400));
		t.put(ItemTypes.GOLDEN_AXE, new ItemWrapper(ItemTypes.GOLD_INGOT, 33, 3, 400));
		t.put(ItemTypes.GOLDEN_SHOVEL, new ItemWrapper(ItemTypes.GOLD_INGOT, 33, 1, 400));
		t.put(ItemTypes.GOLDEN_HOE, new ItemWrapper(ItemTypes.GOLD_INGOT, 33, 2, 400));
		t.put(ItemTypes.GOLDEN_HELMET, new ItemWrapper(ItemTypes.GOLD_INGOT, 78, 5, 400));
		t.put(ItemTypes.GOLDEN_CHESTPLATE, new ItemWrapper(ItemTypes.GOLD_INGOT, 113, 8, 400));
		t.put(ItemTypes.GOLDEN_LEGGINGS, new ItemWrapper(ItemTypes.GOLD_INGOT, 106, 7, 400));
		t.put(ItemTypes.GOLDEN_BOOTS, new ItemWrapper(ItemTypes.GOLD_INGOT, 92, 4, 400));
		
		t.put(ItemTypes.DIAMOND_SWORD, new ItemWrapper(ItemTypes.DIAMOND, 1562, 2, 500));
		t.put(ItemTypes.DIAMOND_PICKAXE, new ItemWrapper(ItemTypes.DIAMOND, 1562, 3, 500));
		t.put(ItemTypes.DIAMOND_AXE, new ItemWrapper(ItemTypes.DIAMOND, 1562, 3, 500));
		t.put(ItemTypes.DIAMOND_SHOVEL, new ItemWrapper(ItemTypes.DIAMOND, 1562, 1, 500));
		t.put(ItemTypes.DIAMOND_HOE, new ItemWrapper(ItemTypes.DIAMOND, 1562, 2, 500));
		t.put(ItemTypes.DIAMOND_HELMET, new ItemWrapper(ItemTypes.DIAMOND, 364, 5, 500));
		t.put(ItemTypes.DIAMOND_CHESTPLATE, new ItemWrapper(ItemTypes.DIAMOND, 529, 8, 500));
		t.put(ItemTypes.DIAMOND_LEGGINGS, new ItemWrapper(ItemTypes.DIAMOND, 496, 7, 500));
		t.put(ItemTypes.DIAMOND_BOOTS, new ItemWrapper(ItemTypes.DIAMOND, 430, 4, 500));
		
		items = ImmutableMap.copyOf(t);
		t.clear();
	}
}