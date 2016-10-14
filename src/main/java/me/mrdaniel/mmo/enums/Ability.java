package me.mrdaniel.mmo.enums;

import me.mrdaniel.mmo.io.AdvancedConfig;

public enum Ability {
	
	SUPER_BREAKER(SkillType.MINING, ShowState.DURATION, "Super Breaker", "Mine at the speed of lightning!"),
	TREE_VELLER(SkillType.WOODCUTTING, ShowState.DURATION, "Tree Feller", "Cuts down trees in 1 hit!"),
	GIGA_DRILL_BREAKER(SkillType.EXCAVATION, ShowState.DURATION, "Giga Drill Breaker", "Dig at the speed of lightning!"),
	GREEN_TERRA(SkillType.FARMING, ShowState.DURATION, "Green Terra", "Change the world"),
	
	SLAUGHTER(SkillType.AXES, ShowState.DURATION, "Slaughter", "Hurt multiple enemies at once"),
	BLOODSHED(SkillType.SWORDS, ShowState.DURATION, "Bloodshed", "Make your enemies bleed"),
	SAITAMA_PUNCH(SkillType.UNARMED, ShowState.DURATION, "Saitama Punch", "Punch your enemies far away"),
	
	MINING_DOUBLEDROP(SkillType.MINING, ShowState.CHANCE, "Mining Double Drop", "Get double the items"),
	FARMING_DOUBLEDROP(SkillType.FARMING, ShowState.CHANCE, "Farming Double Drop", "Get double the items"),
	EXCAVATION_DOUBLEDROP(SkillType.EXCAVATION, ShowState.CHANCE, "Excavation Double Drop", "Get double the items"),
	WOODCUTTING_DOUBLEDROP(SkillType.WOODCUTTING, ShowState.CHANCE, "Woodcutting Double Drop", "Get double the items"),
	
	ROLL(SkillType.ACROBATICS, ShowState.CHANCE, "Roll", "Roll to avoid fall damage"),
	DODGE(SkillType.ACROBATICS, ShowState.CHANCE, "Dodge", "Dodge to avoid damage"),
	SALVAGE(SkillType.SALVAGE, ShowState.RETRIEVE, "Salvage", "Salvage items on a gold block"),
	REPAIR(SkillType.REPAIR, ShowState.REPAIR, "Repair", "Repair items on an iron block"),
	
	DECAPITATION(SkillType.SWORDS, ShowState.CHANCE, "Decapitation", "Decapitate your enemies"),
	ARROW_RAIN(SkillType.ARCHERY, ShowState.CHANCE, "Arrow Rain", "Make arrows rain upon your enemies"),
	DISARM(SkillType.UNARMED, ShowState.CHANCE, "Disarm", "Take away your enemies weapon"),
	
	TREASURE_HUNT(SkillType.EXCAVATION, ShowState.CHANCE, "Treasure Hunt", "Find nice items hiding in the ground"),
	WATER_TREASURE(SkillType.FISHING, ShowState.CHANCE, "Water Treasure", "Find nice items hiding in the water"),
	
	SUMMON_WOLF(SkillType.TAMING, ShowState.DELAY, "Wolf Summoning", "Summon a wolf"),
	SUMMON_OCELOT(SkillType.TAMING, ShowState.DELAY, "Ocelot Summoning", "Summon an ocelot"),
	SUMMON_HORSE(SkillType.TAMING, ShowState.DELAY, "Horse Summoning", " Summon a horse");
	
	public SkillType skillType;
	public ShowState showState;
	public String name;
	public String desc;
	public double increase;
	public double start;
	public double max;
	
	Ability(SkillType skillType, ShowState showState, String name, String desc) {
		this.skillType = skillType;
		this.showState = showState;
		this.name = name;
		this.desc = desc;
		this.increase = AdvancedConfig.getInstance().abilities.get(name)[1];
		this.start = AdvancedConfig.getInstance().abilities.get(name)[0];
		this.max = AdvancedConfig.getInstance().abilities.get(name)[2];
	}
	public double getValue(double level) {
		double pre = start + (level * increase);
		return (pre > max) ? max : pre;
	}
}