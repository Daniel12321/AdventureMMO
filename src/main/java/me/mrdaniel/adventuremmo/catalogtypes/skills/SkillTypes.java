package me.mrdaniel.adventuremmo.catalogtypes.skills;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

public final class SkillTypes {

	private SkillTypes(){}

	public static final SkillType MINING = new SkillType("Mining", "mining");
	public static final SkillType WOODCUTTING = new SkillType("Woodcutting", "woodcutting");
	public static final SkillType EXCAVATION = new SkillType("Excavation", "excavation");
	public static final SkillType FISHING = new SkillType("Fishing", "fishing");
	public static final SkillType FARMING = new SkillType("Farming", "farming");
	public static final SkillType ACROBATICS = new SkillType("Acrobatics", "acrobatics");
	public static final SkillType SWORDS = new SkillType("Swords", "swords");
	public static final SkillType AXES = new SkillType("Axes", "axes");
	public static final SkillType UNARMED = new SkillType("Unarmed", "unarmed");
	public static final SkillType ARCHERY = new SkillType("Archery", "archery");
//	public static final SkillType TAMING = new SkillType("Taming", "taming");
//	public static final SkillType SALVAGE = new SkillType("Salvage", "salvage");
//	public static final SkillType REPAIR = new SkillType("Repair", "repair");

	public static final List<SkillType> VALUES = Lists.newArrayList(MINING, WOODCUTTING, EXCAVATION, FISHING, FARMING, ACROBATICS, SWORDS, AXES, UNARMED, ARCHERY);

	@Nonnull
	public static Optional<SkillType> of(@Nonnull final String id) {
		for (SkillType type : VALUES) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}