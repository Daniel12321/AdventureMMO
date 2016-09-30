package me.mrdaniel.mmo.enums;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

public enum RepairEnum {
	
	WOOD_SWORD(2, ItemTypes.LEATHER),
	WOOD_PICKAXE(3, ItemTypes.LEATHER),
	WOOD_AXE(3, ItemTypes.LEATHER),
	WOOD_SHOVEL(1, ItemTypes.LEATHER),
	WOOD_HOE(2, ItemTypes.LEATHER),
	LEATHER_HELMET(5, ItemTypes.LEATHER),
	LEATHER_CHESTPLATE(8, ItemTypes.LEATHER),
	LEATHER_LEGGINGS(7, ItemTypes.LEATHER),
	LEATHER_BOOTS(4, ItemTypes.LEATHER),
	
	STONE_SWORD(2, ItemTypes.COBBLESTONE),
	STONE_PICKAXE(3, ItemTypes.COBBLESTONE),
	STONE_AXE(3, ItemTypes.COBBLESTONE),
	STONE_SHOVEL(1, ItemTypes.COBBLESTONE),
	STONE_HOE(2, ItemTypes.COBBLESTONE),
	CHAINMAIL_HELMET(5, ItemTypes.IRON_BARS),
	CHAINMAIL_CHESTPLATE(8, ItemTypes.IRON_BARS),
	CHAINMAIL_LEGGINGS(7, ItemTypes.IRON_BARS),
	CHAINMAIL_BOOTS(4, ItemTypes.IRON_BARS),
	
	IRON_SWORD(2, ItemTypes.IRON_INGOT),
	IRON_PICKAXE(3, ItemTypes.IRON_INGOT),
	IRON_AXE(3, ItemTypes.IRON_INGOT),
	IRON_SHOVEL(1, ItemTypes.IRON_INGOT),
	IRON_HOE(2, ItemTypes.IRON_INGOT),
	IRON_HELMET(5, ItemTypes.IRON_INGOT),
	IRON_CHESTPLATE(8, ItemTypes.IRON_INGOT),
	IRON_LEGGINGS(7, ItemTypes.IRON_INGOT),
	IRON_BOOTS(4, ItemTypes.IRON_INGOT),
	
	GOLD_SWORD(2, ItemTypes.GOLD_INGOT),
	GOLD_PICKAXE(3, ItemTypes.GOLD_INGOT),
	GOLD_AXE(3, ItemTypes.GOLD_INGOT),
	GOLD_SHOVEL(1, ItemTypes.GOLD_INGOT),
	GOLD_HOE(2, ItemTypes.GOLD_INGOT),
	GOLD_HELMET(5, ItemTypes.GOLD_INGOT),
	GOLD_CHESTPLATE(8, ItemTypes.GOLD_INGOT),
	GOLD_LEGGINGS(7, ItemTypes.GOLD_INGOT),
	GOLD_BOOTS(4, ItemTypes.GOLD_INGOT),
	
	DIAMOND_SWORD(2, ItemTypes.DIAMOND),
	DIAMOND_PICKAXE(3, ItemTypes.DIAMOND),
	DIAMOND_AXE(3, ItemTypes.DIAMOND),
	DIAMOND_SHOVEL(1, ItemTypes.DIAMOND),
	DIAMOND_HOE(2, ItemTypes.DIAMOND),
	DIAMOND_HELMET(5, ItemTypes.DIAMOND),
	DIAMOND_CHESTPLATE(8, ItemTypes.DIAMOND),
	DIAMOND_LEGGINGS(7, ItemTypes.DIAMOND),
	DIAMOND_BOOTS(4, ItemTypes.DIAMOND);
	
	private int maxItems;
	private ItemType type;
	
	RepairEnum(int maxItems, ItemType type) {
		this.maxItems = maxItems;
		this.type = type;
	}
	public int getMaxItems() { return maxItems; }
	public ItemType getType() { return type; }
	
	public static RepairEnum match(String id) {
		
		if (id.equals("minecraft:wooden_sword")) { return RepairEnum.WOOD_SWORD; }
		else if (id.equals("minecraft:wooden_pickaxe")) { return RepairEnum.WOOD_PICKAXE; }
		else if (id.equals("minecraft:wooden_axe")) { return RepairEnum.WOOD_AXE; }
		else if (id.equals("minecraft:wooden_shovel")) { return RepairEnum.WOOD_SHOVEL; }
		else if (id.equals("minecraft:wooden_hoe")) { return RepairEnum.WOOD_HOE; }
		else if (id.equals("minecraft:leather_helmet")) { return RepairEnum.LEATHER_HELMET; }
		else if (id.equals("minecraft:leather_chestplate")) { return RepairEnum.LEATHER_CHESTPLATE; }
		else if (id.equals("minecraft:leather_leggings")) { return RepairEnum.LEATHER_LEGGINGS; }
		else if (id.equals("minecraft:leather_boots")) { return RepairEnum.LEATHER_BOOTS; }
		
		else if (id.equals("minecraft:stone_sword")) { return RepairEnum.STONE_SWORD; }
		else if (id.equals("minecraft:stone_pickaxe")) { return RepairEnum.STONE_PICKAXE; }
		else if (id.equals("minecraft:stone_axe")) { return RepairEnum.STONE_AXE; }
		else if (id.equals("minecraft:stone_shovel")) { return RepairEnum.STONE_SHOVEL; }
		else if (id.equals("minecraft:stone_hoe")) { return RepairEnum.STONE_HOE; }
		else if (id.equals("minecraft:chainmail_helmet")) { return RepairEnum.CHAINMAIL_HELMET; }
		else if (id.equals("minecraft:chainmail_chestplate")) { return RepairEnum.CHAINMAIL_CHESTPLATE; }
		else if (id.equals("minecraft:chainmail_leggings")) { return RepairEnum.CHAINMAIL_LEGGINGS; }
		else if (id.equals("minecraft:chainmail_boots")) { return RepairEnum.CHAINMAIL_BOOTS; }
		
		else if (id.equals("minecraft:iron_sword")) { return RepairEnum.IRON_SWORD; }
		else if (id.equals("minecraft:iron_pickaxe")) { return RepairEnum.IRON_PICKAXE; }
		else if (id.equals("minecraft:iron_axe")) { return RepairEnum.IRON_AXE; }
		else if (id.equals("minecraft:iron_shovel")) { return RepairEnum.IRON_SHOVEL; }
		else if (id.equals("minecraft:iron_hoe")) { return RepairEnum.IRON_HOE; }
		else if (id.equals("minecraft:iron_helmet")) { return RepairEnum.IRON_HELMET; }
		else if (id.equals("minecraft:iron_chestplate")) { return RepairEnum.IRON_CHESTPLATE; }
		else if (id.equals("minecraft:iron_leggings")) { return RepairEnum.IRON_LEGGINGS; }
		else if (id.equals("minecraft:iron_boots")) { return RepairEnum.IRON_BOOTS; }
		
		else if (id.equals("minecraft:golden_sword")) { return RepairEnum.GOLD_SWORD; }
		else if (id.equals("minecraft:golden_pickaxe")) { return RepairEnum.GOLD_PICKAXE; }
		else if (id.equals("minecraft:golden_axe")) { return RepairEnum.GOLD_AXE; }
		else if (id.equals("minecraft:golden_shovel")) { return RepairEnum.GOLD_SHOVEL; }
		else if (id.equals("minecraft:golden_hoe")) { return RepairEnum.GOLD_HOE; }
		else if (id.equals("minecraft:golden_helmet")) { return RepairEnum.GOLD_HELMET; }
		else if (id.equals("minecraft:golden_chestplate")) { return RepairEnum.GOLD_CHESTPLATE; }
		else if (id.equals("minecraft:golden_leggings")) { return RepairEnum.GOLD_LEGGINGS; }
		else if (id.equals("minecraft:golden_boots")) { return RepairEnum.GOLD_BOOTS; }
		
		else if (id.equals("minecraft:diamond_sword")) { return RepairEnum.DIAMOND_SWORD; }
		else if (id.equals("minecraft:diamond_pickaxe")) { return RepairEnum.DIAMOND_PICKAXE; }
		else if (id.equals("minecraft:diamond_axe")) { return RepairEnum.DIAMOND_AXE; }
		else if (id.equals("minecraft:diamond_shovel")) { return RepairEnum.DIAMOND_SHOVEL; }
		else if (id.equals("minecraft:diamond_hoe")) { return RepairEnum.DIAMOND_HOE; }
		else if (id.equals("minecraft:diamond_helmet")) { return RepairEnum.DIAMOND_HELMET; }
		else if (id.equals("minecraft:diamond_chestplate")) { return RepairEnum.DIAMOND_CHESTPLATE; }
		else if (id.equals("minecraft:diamond_leggings")) { return RepairEnum.DIAMOND_LEGGINGS; }
		else if (id.equals("minecraft:diamond_boots")) { return RepairEnum.DIAMOND_BOOTS; }
		return null;
	}
}