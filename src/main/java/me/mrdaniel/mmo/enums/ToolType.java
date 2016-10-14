package me.mrdaniel.mmo.enums;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

import me.mrdaniel.mmo.io.ModdedTool;
import me.mrdaniel.mmo.io.ModdedTools;

public enum ToolType {
	
	PICKAXE("Pickaxe", true, false),
	AXE("Axe", true, false),
	SHOVEL("Shovel", true, false),
	HOE("Hoe", true, false),
	BONE("Bone", true, true),
	APPLE("Apple", true, true),
	FISH("Fish", true, true),
	SWORD("Sword", true, false),
	ROD("Fishingrod", false, false),
	BOOTS("Boots", false, false),
	LEGGINGS("Leggings", false, false),
	CHESTPLATE("Chestplate", false, false),
	HELMET("Helmet", false, false),
	BOW("Bow", false, false),
	HAND("Hand", true, false);
	
	public String name;
	public boolean activeAbility;
	public boolean requiresSneaking;
	
	ToolType(String name, boolean activeAbility, boolean requiresSneaking) {
		this.name = name; this.activeAbility = activeAbility; this.requiresSneaking = requiresSneaking;
	}
	
	public static ToolType matchName(String name) {
		for (ToolType type : values()) {
			if (type.name.equalsIgnoreCase(name)) {
				return type;
			}
		}
		return null;
	}
	public Ability getAbility(BlockType bType) {
		if (!activeAbility) { return null; }
		if (name.equals("Pickaxe")) { return Ability.SUPER_BREAKER; }
		else if (name.equals("Shovel")) { return Ability.GIGA_DRILL_BREAKER; }
		else if (name.equals("Hoe")) { return Ability.GREEN_TERRA; }
		else if (name.equals("Bone")) { return Ability.SUMMON_WOLF; }
		else if (name.equals("Apple")) { return Ability.SUMMON_HORSE; }
		else if (name.equals("Fish")) { return Ability.SUMMON_OCELOT; }
		else if (name.equals("Sword")) { return Ability.BLOODSHED; }
		else if (name.equals("Hand")) { return Ability.SAITAMA_PUNCH; }
		else if (name.equals("Axe")) {
			if (bType == BlockTypes.LOG || bType == BlockTypes.LOG2) { return Ability.TREE_VELLER; }
			else { return Ability.SLAUGHTER; }
		}
		return null;
	}
	public static ToolType matchID(ItemType type) {
		if (type == ItemTypes.WOODEN_SWORD
				|| type == ItemTypes.STONE_SWORD
				|| type == ItemTypes.IRON_SWORD
				|| type == ItemTypes.GOLDEN_SWORD
				|| type == ItemTypes.DIAMOND_SWORD) {
			return ToolType.SWORD;
		}
		else if (type == ItemTypes.WOODEN_AXE
				|| type == ItemTypes.STONE_AXE
				|| type == ItemTypes.IRON_AXE
				|| type == ItemTypes.GOLDEN_AXE
				|| type == ItemTypes.DIAMOND_AXE) {
			return ToolType.AXE;
		}
		else if (type == ItemTypes.WOODEN_PICKAXE
				|| type == ItemTypes.STONE_PICKAXE
				|| type == ItemTypes.IRON_PICKAXE
				|| type == ItemTypes.GOLDEN_PICKAXE
				|| type == ItemTypes.DIAMOND_PICKAXE) {
			return ToolType.PICKAXE;
		}
		else if (type == ItemTypes.WOODEN_SHOVEL
				|| type == ItemTypes.STONE_SHOVEL
				|| type == ItemTypes.IRON_SHOVEL
				|| type == ItemTypes.GOLDEN_SHOVEL
				|| type == ItemTypes.DIAMOND_SHOVEL) {
			return ToolType.SHOVEL;
		}
		else if (type == ItemTypes.WOODEN_HOE
				|| type == ItemTypes.STONE_HOE
				|| type == ItemTypes.IRON_HOE
				|| type == ItemTypes.GOLDEN_HOE
				|| type == ItemTypes.DIAMOND_HOE) {
			return ToolType.HOE;
		}
		else if (type == ItemTypes.LEATHER_BOOTS
				|| type == ItemTypes.CHAINMAIL_BOOTS
				|| type == ItemTypes.IRON_BOOTS
				|| type == ItemTypes.GOLDEN_BOOTS
				|| type == ItemTypes.DIAMOND_BOOTS) {
			return ToolType.BOOTS;
		}
		else if (type == ItemTypes.LEATHER_LEGGINGS
				|| type == ItemTypes.CHAINMAIL_LEGGINGS
				|| type == ItemTypes.IRON_LEGGINGS
				|| type == ItemTypes.GOLDEN_LEGGINGS
				|| type == ItemTypes.DIAMOND_LEGGINGS) {
			return ToolType.LEGGINGS;
		}
		else if (type == ItemTypes.LEATHER_CHESTPLATE
				|| type == ItemTypes.CHAINMAIL_CHESTPLATE
				|| type == ItemTypes.IRON_CHESTPLATE
				|| type == ItemTypes.GOLDEN_CHESTPLATE
				|| type == ItemTypes.DIAMOND_CHESTPLATE) {
			return ToolType.CHESTPLATE;
		}
		else if (type == ItemTypes.LEATHER_HELMET
				|| type == ItemTypes.CHAINMAIL_HELMET
				|| type == ItemTypes.IRON_HELMET
				|| type == ItemTypes.GOLDEN_HELMET
				|| type == ItemTypes.DIAMOND_HELMET) {
			return ToolType.HELMET;
		}
		else if (type == ItemTypes.BONE) {
			return ToolType.BONE;
		}
		else if (type == ItemTypes.APPLE) {
			return ToolType.APPLE;
		}
		else if (type == ItemTypes.FISH
				|| type == ItemTypes.COOKED_FISH) {
			return ToolType.FISH;
		}
		else if (type == ItemTypes.BOW) {
			return ToolType.BOW;
		}
		else if (type == ItemTypes.FISHING_ROD) {
			return ToolType.ROD;
		}
		else if (type == ItemTypes.NONE) {
			return ToolType.HAND;
		}
		else {
			ModdedTool tool = ModdedTools.getInstance().getToolType(type.getId());
			if (tool == null) return null;
			return tool.type;
		}
	}
}