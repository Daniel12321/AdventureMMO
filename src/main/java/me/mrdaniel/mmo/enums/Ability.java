package me.mrdaniel.mmo.enums;

import me.mrdaniel.mmo.io.AdvancedConfig;

public enum Ability {
	
	SUPER_BREAKER(SkillType.MINING, ShowState.DURATION, "Super Breaker", "Mine at the speed of lightning!", true, false),
	TREE_VELLER(SkillType.WOODCUTTING, ShowState.DURATION, "Tree Feller", "Cuts down trees in 1 hit!", true, false),
	GIGA_DRILL_BREAKER(SkillType.EXCAVATION, ShowState.DURATION, "Giga Drill Breaker", "Dig at the speed of lightning!", true, false),
	GREEN_TERRA(SkillType.FARMING, ShowState.DURATION, "Green Terra", "Change the world", true, false),
	
	MINING_DOUBLEDROP(SkillType.MINING, ShowState.CHANCE, "Mining Double Drop", "Get double the items", false, false),
	FARMING_DOUBLEDROP(SkillType.FARMING, ShowState.CHANCE, "Farming Double Drop", "Get double the items", false, false),
	EXCAVATION_DOUBLEDROP(SkillType.EXCAVATION, ShowState.CHANCE, "Excavation Double Drop", "Get double the items", false, false),
	WOODCUTTING_DOUBLEDROP(SkillType.WOODCUTTING, ShowState.CHANCE, "Woodcutting Double Drop", "Get double the items", false, false),
	
	ROLL(SkillType.ACROBATICS, ShowState.CHANCE, "Roll", "Roll on the ground to avoid fall damage", false, false),
	DODGE(SkillType.ACROBATICS, ShowState.CHANCE, "Dodge", "Use a skillful dodge to avoid damage", false, false),
	SALVAGE(SkillType.SALVAGE, ShowState.RETRIEVE, "Salvage", "Break items on a gold block to retrieve items", false, false),
	REPAIR(SkillType.REPAIR, ShowState.REPAIR, "Repair", "Repair items on an iron block using raw materials", false, false),
	
	TREASURE_HUNT(SkillType.EXCAVATION, ShowState.CHANCE, "Treasure Hunt", "You can find some nice items hiding in the ground", false, false),
	WATER_TREASURE(SkillType.FISHING, ShowState.CHANCE, "Water Treasure", "You can find some nice items hiding in the water", false, false),
	
	SUMMON_WOLF(SkillType.TAMING, ShowState.DELAY, "Wolf Summoning", "Summon a wolf", false, true),
	SUMMON_OCELOT(SkillType.TAMING, ShowState.DELAY, "Ocelot Summoning", "Summon an ocelot", false, true),
	SUMMON_HORSE(SkillType.TAMING, ShowState.DELAY, "Horse Summoning", " Summon a horse", false, true);
	
	public SkillType skillType;
	public ShowState showState;
	public String name;
	public String desc;
	public double increase;
	public double start;
	public boolean millis;
	public boolean requiresSneaking;
	
	Ability(SkillType skillType, ShowState showState, String name, String desc, boolean millis, boolean requiresSneaking) {
		this.skillType = skillType;
		this.showState = showState;
		this.name = name;
		this.desc = desc;
		this.increase = AdvancedConfig.getInstance().abilities.get(name)[1];
		this.start = AdvancedConfig.getInstance().abilities.get(name)[0];
		this.millis = millis;
		this.requiresSneaking = requiresSneaking;
	}
	public double getValue(double level) { return start + (level * increase); }
}