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
		t.put(ItemTypes.WOODEN_SWORD, new ItemWrapper(ItemTypes.PLANKS, 2, 100));
		t.put(ItemTypes.WOODEN_PICKAXE, new ItemWrapper(ItemTypes.PLANKS, 3, 100));
		t.put(ItemTypes.WOODEN_AXE, new ItemWrapper(ItemTypes.PLANKS, 3, 100));
		t.put(ItemTypes.WOODEN_SHOVEL, new ItemWrapper(ItemTypes.PLANKS, 1, 100));
		t.put(ItemTypes.WOODEN_HOE, new ItemWrapper(ItemTypes.PLANKS, 2, 100));
		t.put(ItemTypes.LEATHER_HELMET, new ItemWrapper(ItemTypes.LEATHER, 5, 100));
		t.put(ItemTypes.LEATHER_CHESTPLATE, new ItemWrapper(ItemTypes.LEATHER, 8, 100));
		t.put(ItemTypes.LEATHER_LEGGINGS, new ItemWrapper(ItemTypes.LEATHER, 7, 100));
		t.put(ItemTypes.LEATHER_BOOTS, new ItemWrapper(ItemTypes.LEATHER, 4, 100));
		
		t.put(ItemTypes.STONE_SWORD, new ItemWrapper(ItemTypes.COBBLESTONE, 2, 200));
		t.put(ItemTypes.STONE_PICKAXE, new ItemWrapper(ItemTypes.COBBLESTONE, 3, 200));
		t.put(ItemTypes.STONE_AXE, new ItemWrapper(ItemTypes.COBBLESTONE, 3, 200));
		t.put(ItemTypes.STONE_SHOVEL, new ItemWrapper(ItemTypes.COBBLESTONE, 1, 200));
		t.put(ItemTypes.STONE_HOE, new ItemWrapper(ItemTypes.COBBLESTONE, 2, 200));
		t.put(ItemTypes.CHAINMAIL_HELMET, new ItemWrapper(ItemTypes.IRON_BARS, 5, 200));
		t.put(ItemTypes.CHAINMAIL_CHESTPLATE, new ItemWrapper(ItemTypes.IRON_BARS, 8, 200));
		t.put(ItemTypes.CHAINMAIL_LEGGINGS, new ItemWrapper(ItemTypes.IRON_BARS, 7, 200));
		t.put(ItemTypes.CHAINMAIL_BOOTS, new ItemWrapper(ItemTypes.IRON_BARS, 4, 200));
		
		t.put(ItemTypes.IRON_SWORD, new ItemWrapper(ItemTypes.IRON_INGOT, 2, 300));
		t.put(ItemTypes.IRON_PICKAXE, new ItemWrapper(ItemTypes.IRON_INGOT, 3, 300));
		t.put(ItemTypes.IRON_AXE, new ItemWrapper(ItemTypes.IRON_INGOT, 3, 300));
		t.put(ItemTypes.IRON_SHOVEL, new ItemWrapper(ItemTypes.IRON_INGOT, 1, 300));
		t.put(ItemTypes.IRON_HOE, new ItemWrapper(ItemTypes.IRON_INGOT, 2, 300));
		t.put(ItemTypes.IRON_HELMET, new ItemWrapper(ItemTypes.IRON_INGOT, 5, 300));
		t.put(ItemTypes.IRON_CHESTPLATE, new ItemWrapper(ItemTypes.IRON_INGOT, 8, 300));
		t.put(ItemTypes.IRON_LEGGINGS, new ItemWrapper(ItemTypes.IRON_INGOT, 7, 300));
		t.put(ItemTypes.IRON_BOOTS, new ItemWrapper(ItemTypes.IRON_INGOT, 4, 300));
		
		t.put(ItemTypes.GOLDEN_SWORD, new ItemWrapper(ItemTypes.GOLD_INGOT, 2, 400));
		t.put(ItemTypes.GOLDEN_PICKAXE, new ItemWrapper(ItemTypes.GOLD_INGOT, 3, 400));
		t.put(ItemTypes.GOLDEN_AXE, new ItemWrapper(ItemTypes.GOLD_INGOT, 3, 400));
		t.put(ItemTypes.GOLDEN_SHOVEL, new ItemWrapper(ItemTypes.GOLD_INGOT, 1, 400));
		t.put(ItemTypes.GOLDEN_HOE, new ItemWrapper(ItemTypes.GOLD_INGOT, 2, 400));
		t.put(ItemTypes.GOLDEN_HELMET, new ItemWrapper(ItemTypes.GOLD_INGOT, 5, 400));
		t.put(ItemTypes.GOLDEN_CHESTPLATE, new ItemWrapper(ItemTypes.GOLD_INGOT, 8, 400));
		t.put(ItemTypes.GOLDEN_LEGGINGS, new ItemWrapper(ItemTypes.GOLD_INGOT, 7, 400));
		t.put(ItemTypes.GOLDEN_BOOTS, new ItemWrapper(ItemTypes.GOLD_INGOT, 4, 400));
		
		t.put(ItemTypes.DIAMOND_SWORD, new ItemWrapper(ItemTypes.DIAMOND, 2, 500));
		t.put(ItemTypes.DIAMOND_PICKAXE, new ItemWrapper(ItemTypes.DIAMOND, 3, 500));
		t.put(ItemTypes.DIAMOND_AXE, new ItemWrapper(ItemTypes.DIAMOND, 3, 500));
		t.put(ItemTypes.DIAMOND_SHOVEL, new ItemWrapper(ItemTypes.DIAMOND, 1, 500));
		t.put(ItemTypes.DIAMOND_HOE, new ItemWrapper(ItemTypes.DIAMOND, 2, 500));
		t.put(ItemTypes.DIAMOND_HELMET, new ItemWrapper(ItemTypes.DIAMOND, 5, 500));
		t.put(ItemTypes.DIAMOND_CHESTPLATE, new ItemWrapper(ItemTypes.DIAMOND, 8, 500));
		t.put(ItemTypes.DIAMOND_LEGGINGS, new ItemWrapper(ItemTypes.DIAMOND, 7, 500));
		t.put(ItemTypes.DIAMOND_BOOTS, new ItemWrapper(ItemTypes.DIAMOND, 4, 500));
		
		items = ImmutableMap.copyOf(t);
		t.clear();
	}
}