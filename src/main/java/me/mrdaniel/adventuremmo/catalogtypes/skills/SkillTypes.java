package me.mrdaniel.adventuremmo.catalogtypes.skills;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import me.mrdaniel.adventuremmo.catalogtypes.abilities.Abilities;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolTypes;

public final class SkillTypes {

	private SkillTypes(){}

	public static final SkillType MINING = new SkillType("Mining", "mining", Abilities.MAD_MINER, ToolTypes.PICKAXE);
	public static final SkillType WOODCUTTING = new SkillType("Woodcutting", "woodcutting", Abilities.TREE_VELLER, ToolTypes.AXE);
	public static final SkillType EXCAVATION = new SkillType("Excavation", "excavation", Abilities.GIGA_DRILL, ToolTypes.SHOVEL);
	public static final SkillType FISHING = new SkillType("Fishing", "fishing", Abilities.FISH_CRAZE, ToolTypes.ROD);
	public static final SkillType FARMING = new SkillType("Farming", "farming", Abilities.GREEN_THUMBS, ToolTypes.HOE);
	public static final SkillType ACROBATICS = new SkillType("Acrobatics", "acrobatics", null, null);
	public static final SkillType SWORDS = new SkillType("Swords", "swords", Abilities.BLOODSHED, ToolTypes.SWORD);
	public static final SkillType AXES = new SkillType("Axes", "axes", Abilities.SLAUGHTER, ToolTypes.AXE);
	public static final SkillType UNARMED = new SkillType("Unarmed", "unarmed", Abilities.SAITAMA_PUNCH, ToolTypes.HAND);
	public static final SkillType ARCHERY = new SkillType("Archery", "archery", null, ToolTypes.BOW);
//	public static final SkillType TAMING = new SkillType(null, "Taming", "taming");
//	public static final SkillType SALVAGE = new SkillType(null, "Salvage", "salvage");
//	public static final SkillType REPAIR = new SkillType(null, "Repair", "repair");

	@Nonnull
	public static List<SkillType> getAll() {
		return Lists.newArrayList(MINING, WOODCUTTING, EXCAVATION, FISHING, FARMING, ACROBATICS, SWORDS, AXES, UNARMED, ARCHERY);
	}

	@Nonnull
	public static Optional<SkillType> of(@Nonnull final String id) {
		for (SkillType type : getAll()) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}

	@Nonnull
	public static Optional<SkillType> of(@Nonnull final ToolType tool, final boolean onblock) {
		if (tool == ToolTypes.AXE) { return Optional.of(onblock ? SkillTypes.WOODCUTTING : SkillTypes.AXES); }
		for (SkillType type : getAll()) { if (type.getTool().isPresent() && type.getTool().get() == tool) { return Optional.of(type); } }
		return Optional.empty();
	}
}