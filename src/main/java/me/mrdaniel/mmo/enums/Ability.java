package me.mrdaniel.mmo.enums;

public enum Ability {
	
	SUPER_BREAKER(SkillType.MINING, ShowState.DURATION, "Super Breaker", "Mine at the speed of lightning!", 0.08, 5, true, false),
	TREE_VELLER(SkillType.WOODCUTTING, ShowState.DURATION, "Tree Feller", "Cuts down trees in 1 hit!", 0.08, 5, true, false),
	GIGA_DRILL_BREAKER(SkillType.EXCAVATION, ShowState.DURATION, "Giga Drill Breaker", "Dig at the speed of lightning!", 0.08, 5, true, false),
	GREEN_TERRA(SkillType.FARMING, ShowState.DURATION, "Green Terra", "Change the world", 0.08, 5, true, false),
	
	MINING_DOUBLEDROP(SkillType.MINING, ShowState.CHANCE, "Double Drop", "Get double the items", 0.2, 0, false, false),
	FARMING_DOUBLEDROP(SkillType.FARMING, ShowState.CHANCE, "Double Drop", "Get double the items", 0.2, 0, false, false),
	EXCAVATION_DOUBLEDROP(SkillType.EXCAVATION, ShowState.CHANCE, "Double Drop", "Get double the items", 0.2, 0, false, false),
	WOODCUTTING_DOUBLEDROP(SkillType.WOODCUTTING, ShowState.CHANCE, "Double Drop", "Get double the items", 0.2, 0, false, false),
	
	ROLL(SkillType.ACROBATICS, ShowState.CHANCE, "Roll", "Roll on the ground to avoid fall damage", 0.2, 0, false, false),
	DODGE(SkillType.ACROBATICS, ShowState.CHANCE, "Dodge", "Use a skillful dodge to avoid damage", 0.1, 0, false, false),
	SALVAGE(SkillType.SALVAGE, ShowState.RETRIEVE, "Salvage", "Break items on a gold block re retrieve items", 0.2, 20, false, false),
	TREASURE_HUNT(SkillType.EXCAVATION, ShowState.CHANCE, "Treasure Hunt", "You can find some nice items hiding in the ground", 0.04, 2, false, false),
	WATER_TREASURE(SkillType.FISHING, ShowState.CHANCE, "Water Treasure", "You can find some nice items hiding in the water", 0.2, 8, false, false),
	
	SUMMON_WOLF(SkillType.TAMING, ShowState.DELAY, "Wolf Summoning", "Summon a wolf", -2.0, 1000.0, false, true),
	SUMMON_OCELOT(SkillType.TAMING, ShowState.DELAY, "Ocelot Summoning", "Summon an ocelot", -2.0, 1500.0, false, true),
	SUMMON_HORSE(SkillType.TAMING, ShowState.DELAY, "Horse Summoning", " Summon a horse", -2.0, 2000.0, false, true);
	
	public SkillType skillType;
	public ShowState showState;
	public String name;
	public String desc;
	public double increase;
	public double start;
	public boolean millis;
	public boolean requiresSneaking;
	
	Ability(SkillType skillType, ShowState showState, String name, String desc, double increase, double start, boolean millis, boolean requiresSneaking) {
		this.skillType = skillType;
		this.showState = showState;
		this.name = name;
		this.desc = desc;
		this.increase = increase;
		this.start = start;
		this.millis = millis;
		this.requiresSneaking = requiresSneaking;
	}
	public double getValue(double level) { return start + (level * increase); }
}