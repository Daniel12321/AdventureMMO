package me.mrdaniel.adventuremmo.enums;

import java.util.Optional;

import javax.annotation.Nonnull;

public enum SkillType {

	MINING("Mining", "mining"),
	WOODCUTTING("Woodcutting", "woodcutting"),
	EXCAVATION("Excavation", "excavation"),
	FISHING("Fishing", "fishing"),
	FARMING("Farming", "farming"),
	ACROBATICS("Acrobatics", "acrobatics"),
	TAMING("Taming", "taming"),
	SALVAGE("Salvage", "salvage"),
	REPAIR("Repair", "repair"),
	SWORDS("Swords", "swords"),
	AXES("Axes", "axes"),
	UNARMED("Unarmed", "unarmed"),
	ARCHERY("Archery", "archery");

	private final String name;
	private final String id;

	SkillType(String name, String id) {
		this.name = name;
		this.id = id;
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
	public static Optional<SkillType> of(@Nonnull final String id) {
		for (SkillType type : values()) { if (type.id.equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}