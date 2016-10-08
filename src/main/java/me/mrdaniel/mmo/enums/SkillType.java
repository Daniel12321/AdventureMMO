package me.mrdaniel.mmo.enums;

public enum SkillType {
	
	MINING("Mining", 0, true),
	WOODCUTTING("Woodcutting", 1, true),
	EXCAVATION("Excavation", 2, true),
	FISHING("Fishing", 3, false),
	FARMING("Farming", 4, true),
	ACROBATICS("Acrobatics", 5, false),
	TAMING("Taming", 6, false),
	SALVAGE("Salvage", 7, false),
	REPAIR("Repair", 8, false);
	
	public String name;
	public int id;
	public boolean doubleDrop;
	
	SkillType(String name, int id, boolean doubleDrop) {
		this.name = name;
		this.id = id;
		this.doubleDrop = doubleDrop;
	}
	
	public static SkillType match(String name) {
		for (SkillType type : SkillType.values()) { if (type.name.equalsIgnoreCase(name)) { return type; } }
		return null;
	}
}