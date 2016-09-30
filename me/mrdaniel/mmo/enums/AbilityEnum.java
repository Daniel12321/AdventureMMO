package me.mrdaniel.mmo.enums;

public enum AbilityEnum {
	
	SUPER_BREAKER(SkillType.MINING, "Super Breaker", ToolType.PICKAXE, "Mine at the speed of lightning!"),
	TREE_VELLER(SkillType.WOODCUTTING, "Tree Feller", ToolType.AXE, "Cuts down trees in 1 hit!"),
	GIGA_DRILL_BREAKER(SkillType.EXCAVATION, "Giga Drill Breaker", ToolType.SHOVEL, "Dig at the speed of lightning!"),
	GREEN_TERRA(SkillType.FARMING, "Green Terra", ToolType.HOE, "Change the world");
	
	public SkillType skillType;
	public String name;
	public ToolType tool;
	public String desc;
	
	AbilityEnum(SkillType skillType, String name, ToolType tool, String desc) {
		this.skillType = skillType;
		this.name = name;
		this.tool = tool;
		this.desc = desc;
	}
	
	public static AbilityEnum match(ToolType type) {
		if (type == ToolType.AXE) { return AbilityEnum.TREE_VELLER; }
		else if (type == ToolType.PICKAXE) { return AbilityEnum.SUPER_BREAKER; }
		else if (type == ToolType.SHOVEL) { return AbilityEnum.GIGA_DRILL_BREAKER; }
		else if (type == ToolType.HOE) { return AbilityEnum.GREEN_TERRA; }
		return null;
	}
}