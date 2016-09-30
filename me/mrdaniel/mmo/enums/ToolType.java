package me.mrdaniel.mmo.enums;

import me.mrdaniel.mmo.io.ModdedTool;
import me.mrdaniel.mmo.io.ModdedTools;

public enum ToolType {
	
	PICKAXE("Pickaxe"),
	AXE("Axe"),
	SHOVEL("Shovel"),
	HOE("Hoe"), SWORD("Sword"),
	ROD("Fishingrod"),
	BOOTS("Boots"),
	LEGGINGS("Leggings"),
	CHESTPLATE("Chestplate"),
	HELMET("Helmet"),
	BOW("Bow");
	
	private String name;
	
	ToolType(String name) { this.name = name; }
	
	public static ToolType matchName(String name) {
		for (ToolType type : ToolType.values()) {
			if (type.name.equalsIgnoreCase(name)) {
				return type;
			}
		}
		return null;
	}
	
	public static ToolType matchID(String id) {
		if (id.equals("minecraft:wooden_sword")
				|| id.equals("minecraft:stone_sword")
				|| id.equals("minecraft:iron_sword")
				|| id.equals("minecraft:golden_sword")
				|| id.equals("minecraft:diamond_sword")) { 
			return ToolType.SWORD;
		}
		else if (id.equals("minecraft:wooden_axe")
				|| id.equals("minecraft:stone_axe")
				|| id.equals("minecraft:iron_axe")
				|| id.equals("minecraft:golden_axe")
				|| id.equals("minecraft:diamond_axe")) { 
			return ToolType.AXE;
		}
		else if (id.equals("minecraft:wooden_pickaxe")
				|| id.equals("minecraft:stone_pickaxe")
				|| id.equals("minecraft:iron_pickaxe")
				|| id.equals("minecraft:golden_pickaxe")
				|| id.equals("minecraft:diamond_pickaxe")) { 
			return ToolType.PICKAXE;
		}
		else if (id.equals("minecraft:wooden_shovel")
				|| id.equals("minecraft:stone_shovel")
				|| id.equals("minecraft:iron_shovel")
				|| id.equals("minecraft:golden_shovel")
				|| id.equals("minecraft:diamond_shovel")) { 
			return ToolType.SHOVEL;
		}
		else if (id.equals("minecraft:wooden_hoe")
				|| id.equals("minecraft:stone_hoe")
				|| id.equals("minecraft:iron_hoe")
				|| id.equals("minecraft:golden_hoe")
				|| id.equals("minecraft:diamond_hoe")) { 
			return ToolType.HOE;
		}
		else if (id.equals("minecraft:leather_boots")
				|| id.equals("minecraft:chainmail_boots")
				|| id.equals("minecraft:iron_boots")
				|| id.equals("minecraft:golden_boots")
				|| id.equals("minecraft:diamond_boots")) { 
			return ToolType.BOOTS;
		}
		else if (id.equals("minecraft:leather_leggings")
				|| id.equals("minecraft:chainmail_leggings")
				|| id.equals("minecraft:iron_leggings")
				|| id.equals("minecraft:golden_leggings")
				|| id.equals("minecraft:diamond_leggings")) { 
			return ToolType.LEGGINGS;
		}
		else if (id.equals("minecraft:leather_chestplate")
				|| id.equals("minecraft:chainmail_chestplate")
				|| id.equals("minecraft:iron_chestplate")
				|| id.equals("minecraft:golden_chestplate")
				|| id.equals("minecraft:diamond_chestplate")) { 
			return ToolType.CHESTPLATE;
		}
		else if (id.equals("minecraft:leather_helmet")
				|| id.equals("minecraft:chainmail_helmet")
				|| id.equals("minecraft:iron_helmet")
				|| id.equals("minecraft:golden_helmet")
				|| id.equals("minecraft:diamond_helmet")) { 
			return ToolType.HELMET;
		}
		else if (id.equals("minecraft:bow")) {
			return ToolType.BOW;
		}
		else if (id.equals("minecraft:fishing_rod")) {
			return ToolType.ROD;
		}
		else {
			ModdedTool tool = ModdedTools.getToolType(id);
			if (tool == null) return null;
			return tool.type;
		}
	}
}