package me.mrdaniel.adventuremmo.enums;

import java.util.Optional;

import javax.annotation.Nonnull;

public enum ToolType {

	PICKAXE("Pickaxe", "pickaxe", SkillType.MINING),
	AXE("Axe", "axe", SkillType.WOODCUTTING, SkillType.AXES),
	SHOVEL("Shovel", "shovel", SkillType.EXCAVATION),
	HOE("Hoe", "hoe", SkillType.FARMING),
	ROD("Fishing Rod", "rod", SkillType.FISHING),
	SWORD("Sword", "sword", SkillType.SWORDS),
	HAND("Hand", "hand", SkillType.UNARMED);

	private final String name;
	private final String id;
	private final SkillType[] skills;

	ToolType(String name, String id, SkillType... skills) {
		this.name = name;
		this.id = id;
		this.skills = skills;
	}

	@Nonnull
	public String getName() {
		return this.name;
	}

	@Nonnull
	public String getID() {
		return this.id;
	}

	@Nonnull
	public SkillType[] getSkills() {
		return this.skills;
	}

	@Nonnull
	public static Optional<ToolType> of(@Nonnull final String id) {
		for (ToolType type : values()) { if (type.id.equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}