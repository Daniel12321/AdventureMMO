package me.mrdaniel.mmo.enums;

import java.util.Optional;

import javax.annotation.Nonnull;

public enum SkillType {
	
	MINING("Mining", 0, true),
	WOODCUTTING("Woodcutting", 1, true),
	EXCAVATION("Excavation", 2, true),
	FISHING("Fishing", 3, false),
	FARMING("Farming", 4, true),
	ACROBATICS("Acrobatics", 5, false),
	TAMING("Taming", 6, false),
	SALVAGE("Salvage", 7, false),
	REPAIR("Repair", 8, false),
	SWORDS("Swords", 9, false),
	AXES("Axes", 10, false),
	UNARMED("Unarmed", 11, false),
	ARCHERY("Archery", 12, false);
	
	private String name;
	private int id;
	private boolean doubleDrop;
	
	SkillType(String name, int id, boolean doubleDrop) {
		this.name = name;
		this.id = id;
		this.doubleDrop = doubleDrop;
	}

	@Nonnull public String getName() { return this.name; }
	public int getId() { return this.id; }
	public boolean hasDoubleDrop() { return this.doubleDrop; }

	public static Optional<SkillType> of(@Nonnull final String name) {
		for (SkillType type : SkillType.values()) if (type.name.equalsIgnoreCase(name)) return Optional.of(type);
		return Optional.empty();
	}

	public static SkillType of(final int id) {
		for (SkillType t : SkillType.values()) if (t.id == id) return t;
		return MINING;
	}
}