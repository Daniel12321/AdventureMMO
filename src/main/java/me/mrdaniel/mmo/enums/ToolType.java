package me.mrdaniel.mmo.enums;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.io.ModdedTool;

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
	
	private String name;
	private boolean activeAbility;
	private boolean requiresSneaking;
	
	ToolType(String name, boolean activeAbility, boolean requiresSneaking) {
		this.name = name; this.activeAbility = activeAbility; this.requiresSneaking = requiresSneaking;
	}

	@Nonnull public String getName() { return this.name; }
	public boolean isActivaAbility() { return this.activeAbility; }
	public boolean requiresSneaking() { return this.requiresSneaking; }
	
	@Nonnull
	public static Optional<ToolType> of(@Nonnull final String name) {
		for (ToolType type : values()) { if (type.name.equalsIgnoreCase(name)) { return Optional.of(type); } }
		return Optional.empty();
	}

	@Nonnull
	public Optional<Ability> getAbility(@Nonnull final BlockType bType) {
		if (!this.activeAbility) { return Optional.empty(); }
		if (this == PICKAXE) { return Optional.of(Ability.SUPER_BREAKER); }
		else if (this == SHOVEL) { return Optional.of(Ability.GIGA_DRILL_BREAKER); }
		else if (this == HOE) { return Optional.of(Ability.GREEN_TERRA); }
		else if (this == BONE) { return Optional.of(Ability.SUMMON_WOLF); }
		else if (this == APPLE) { return Optional.of(Ability.SUMMON_HORSE); }
		else if (this == FISH) { return Optional.of(Ability.SUMMON_OCELOT); }
		else if (this == SWORD) { return Optional.of(Ability.BLOODSHED); }
		else if (this == HAND) { return Optional.of(Ability.SAITAMA_PUNCH); }
		else if (this == AXE) {
			if (bType == BlockTypes.AIR) { return Optional.of(Ability.SLAUGHTER); }
			else { return Optional.of(Ability.TREE_VELLER); }
		}
		return Optional.empty();
	}

	public static Optional<ToolType> of(@Nonnull final ItemType type) {
		if (type == ItemTypes.WOODEN_SWORD
				|| type == ItemTypes.STONE_SWORD
				|| type == ItemTypes.IRON_SWORD
				|| type == ItemTypes.GOLDEN_SWORD
				|| type == ItemTypes.DIAMOND_SWORD) {
			return Optional.of(SWORD);
		}
		else if (type == ItemTypes.WOODEN_AXE
				|| type == ItemTypes.STONE_AXE
				|| type == ItemTypes.IRON_AXE
				|| type == ItemTypes.GOLDEN_AXE
				|| type == ItemTypes.DIAMOND_AXE) {
			return Optional.of(AXE);
		}
		else if (type == ItemTypes.WOODEN_PICKAXE
				|| type == ItemTypes.STONE_PICKAXE
				|| type == ItemTypes.IRON_PICKAXE
				|| type == ItemTypes.GOLDEN_PICKAXE
				|| type == ItemTypes.DIAMOND_PICKAXE) {
			return Optional.of(PICKAXE);
		}
		else if (type == ItemTypes.WOODEN_SHOVEL
				|| type == ItemTypes.STONE_SHOVEL
				|| type == ItemTypes.IRON_SHOVEL
				|| type == ItemTypes.GOLDEN_SHOVEL
				|| type == ItemTypes.DIAMOND_SHOVEL) {
			return Optional.of(SHOVEL);
		}
		else if (type == ItemTypes.WOODEN_HOE
				|| type == ItemTypes.STONE_HOE
				|| type == ItemTypes.IRON_HOE
				|| type == ItemTypes.GOLDEN_HOE
				|| type == ItemTypes.DIAMOND_HOE) {
			return Optional.of(HOE);
		}
		else if (type == ItemTypes.LEATHER_BOOTS
				|| type == ItemTypes.CHAINMAIL_BOOTS
				|| type == ItemTypes.IRON_BOOTS
				|| type == ItemTypes.GOLDEN_BOOTS
				|| type == ItemTypes.DIAMOND_BOOTS) {
			return Optional.of(BOOTS);
		}
		else if (type == ItemTypes.LEATHER_LEGGINGS
				|| type == ItemTypes.CHAINMAIL_LEGGINGS
				|| type == ItemTypes.IRON_LEGGINGS
				|| type == ItemTypes.GOLDEN_LEGGINGS
				|| type == ItemTypes.DIAMOND_LEGGINGS) {
			return Optional.of(LEGGINGS);
		}
		else if (type == ItemTypes.LEATHER_CHESTPLATE
				|| type == ItemTypes.CHAINMAIL_CHESTPLATE
				|| type == ItemTypes.IRON_CHESTPLATE
				|| type == ItemTypes.GOLDEN_CHESTPLATE
				|| type == ItemTypes.DIAMOND_CHESTPLATE) {
			return Optional.of(CHESTPLATE);
		}
		else if (type == ItemTypes.LEATHER_HELMET
				|| type == ItemTypes.CHAINMAIL_HELMET
				|| type == ItemTypes.IRON_HELMET
				|| type == ItemTypes.GOLDEN_HELMET
				|| type == ItemTypes.DIAMOND_HELMET) {
			return Optional.of(HELMET);
		}
		else if (type == ItemTypes.BONE) {
			return Optional.of(BONE);
		}
		else if (type == ItemTypes.APPLE) {
			return Optional.of(APPLE);
		}
		else if (type == ItemTypes.FISH
				|| type == ItemTypes.COOKED_FISH) {
			return Optional.of(FISH);
		}
		else if (type == ItemTypes.BOW) {
			return Optional.of(BOW);
		}
		else if (type == ItemTypes.FISHING_ROD) {
			return Optional.of(ROD);
		}
		else if (type == ItemTypes.NONE) {
			return Optional.of(HAND);
		}
		else {
			Optional<ModdedTool> tool = Main.getInstance().getModdedTools().getToolType(type.getId());
			if (!tool.isPresent()) return Optional.empty();
			return Optional.of(tool.get().type);
		}
	}
}