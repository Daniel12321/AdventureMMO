package me.mrdaniel.mmo.enums;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

public enum Ability {

	SUPER_BREAKER(SkillType.MINING, ShowState.DURATION, DelayType.SUPER_BREAKER, "Super Breaker", "Mine at the speed of lightning!"),
	TREE_VELLER(SkillType.WOODCUTTING, ShowState.DURATION, DelayType.TREE_VELLER, "Tree Feller", "Cuts down trees in 1 hit!"),
	GIGA_DRILL_BREAKER(SkillType.EXCAVATION, ShowState.DURATION, DelayType.GIGA_DRILL_BREAKER, "Giga Drill Breaker", "Dig at the speed of lightning!"),
	GREEN_TERRA(SkillType.FARMING, ShowState.DURATION, DelayType.GREEN_TERRA, "Green Terra", "Change the world"),

	SLAUGHTER(SkillType.AXES, ShowState.DURATION, DelayType.SLAUGHTER, "Slaughter", "Hurt multiple enemies at once"),
	BLOODSHED(SkillType.SWORDS, ShowState.DURATION, DelayType.BLOODSHED, "Bloodshed", "Make your enemies bleed"),
	SAITAMA_PUNCH(SkillType.UNARMED, ShowState.DURATION, DelayType.SAITAMA_PUNCH, "Saitama Punch", "Punch your enemies far away"),

	MINING_DOUBLEDROP(SkillType.MINING, ShowState.CHANCE, null, "Mining Double Drop", "Get double the items"),
	FARMING_DOUBLEDROP(SkillType.FARMING, ShowState.CHANCE, null, "Farming Double Drop", "Get double the items"),
	EXCAVATION_DOUBLEDROP(SkillType.EXCAVATION, ShowState.CHANCE, null, "Excavation Double Drop", "Get double the items"),
	WOODCUTTING_DOUBLEDROP(SkillType.WOODCUTTING, ShowState.CHANCE, null, "Woodcutting Double Drop", "Get double the items"),

	ROLL(SkillType.ACROBATICS, ShowState.CHANCE, null, "Roll", "Roll to avoid fall damage"),
	DODGE(SkillType.ACROBATICS, ShowState.CHANCE, null, "Dodge", "Dodge to avoid damage"),
	SALVAGE(SkillType.SALVAGE, ShowState.RETRIEVE, null, "Salvage", "Salvage items on a gold block"),
	REPAIR(SkillType.REPAIR, ShowState.REPAIR, null, "Repair", "Repair items on an iron block"),

	DECAPITATION(SkillType.SWORDS, ShowState.CHANCE, null, "Decapitation", "Decapitate your enemies"),
	ARROW_RAIN(SkillType.ARCHERY, ShowState.CHANCE, null, "Arrow Rain", "Make arrows rain upon your enemies"),
	DISARM(SkillType.UNARMED, ShowState.CHANCE, null, "Disarm", "Take away your enemies weapon"),

	TREASURE_HUNT(SkillType.EXCAVATION, ShowState.CHANCE, null, "Treasure Hunt", "Find nice items hiding in the ground"),
	WATER_TREASURE(SkillType.FISHING, ShowState.CHANCE, null, "Water Treasure", "Find nice items hiding in the water"),

	SUMMON_WOLF(SkillType.TAMING, ShowState.DELAY, DelayType.SUMMON_WOLF, "Wolf Summoning", "Summon a wolf"),
	SUMMON_OCELOT(SkillType.TAMING, ShowState.DELAY, DelayType.SUMMON_OCELOT, "Ocelot Summoning", "Summon an ocelot"),
	SUMMON_HORSE(SkillType.TAMING, ShowState.DELAY, DelayType.SUMMON_HORSE, "Horse Summoning", " Summon a horse");

	private SkillType skillType;
	private ShowState showState;
	private DelayType delay;
	private String name;
	private String desc;

	Ability(SkillType skillType, ShowState showState, DelayType delay, String name, String desc) {
		this.skillType = skillType;
		this.showState = showState;
		this.delay = delay;
		this.name = name;
		this.desc = desc;
	}

	@Nonnull public SkillType getSkillType() { return this.skillType; }
	@Nonnull public ShowState getShowState() { return this.showState; }
	@Nonnull public DelayType getDelay() { return this.delay; }
	@Nonnull public String getName() { return this.name; }
	@Nonnull public String getDesc() { return this.desc; }

	public static List<Ability> getAll(@Nonnull final SkillType type) {
		List<Ability> l = Lists.newArrayList();
		for (Ability a : values()) { if (a.skillType == type) { l.add(a); } }
		return l;
	}

	public static Optional<Ability> of(@Nonnull final String name) {
		String s = name.replaceAll(" ", "");
		for (Ability a : values()) { if (a.name.replaceAll(" ", "").equalsIgnoreCase(s)) { return Optional.of(a); } }
		return Optional.empty();
	}
}