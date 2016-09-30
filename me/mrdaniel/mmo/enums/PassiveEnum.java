package me.mrdaniel.mmo.enums;

public enum PassiveEnum {
	
	MINING_DOUBLEDROP(SkillType.MINING, "Double Drop", "Get double the items", 0.2, 0),
	FARMING_DOUBLEDROP(SkillType.FARMING, "Double Drop", "Get double the items", 0.2, 0),
	EXCAVATION_DOUBLEDROP(SkillType.EXCAVATION, "Double Drop", "Get double the items", 0.2, 0),
	WOODCUTTING_DOUBLEDROP(SkillType.WOODCUTTING, "Double Drop", "Get double the items", 0.2, 0),
	
	ROLL(SkillType.ACROBATICS, "Roll", "Roll on the ground to avoid fall damage", 0.2, 0),
	DODGE(SkillType.ACROBATICS, "Dodge", "Use a skillful dodge to avoid damage", 0.1, 0),
	TREASURE_HUNT(SkillType.EXCAVATION, "Treasure Hunt", "You can find some nice items hiding in the ground", 0.04, 3),
	WATER_TREASURE(SkillType.FISHING, "Water Treasure", "You can find some nice items hiding in the water", 0.2, 8);
	
	public SkillType type;
	public String name;
	public String desc;
	public double increase;
	public double start;
	
	PassiveEnum(SkillType type, String name, String desc, double increase, double start) {
		this.type = type;
		this.name = name;
		this.desc = desc;
		this.increase = increase;
		this.start = start;
	}
}