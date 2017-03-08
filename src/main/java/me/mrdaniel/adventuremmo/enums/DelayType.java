package me.mrdaniel.adventuremmo.enums;

import java.util.Optional;

import javax.annotation.Nonnull;

public enum DelayType {

	SUPER_BREAKER("super_breaker"),
	TREE_VELLER("tree_veller"),
	GIGA_DRILL_BREAKER("giga_drill_breaker"),
	GREEN_TERRA("green_terra"),
	SLAUGHTER("slaughter"),
	BLOODSHED("bloodshed"),
	SAITAMA_PUNCH("saitama_punch"),
	SUMMON_WOLF("summon_wolf"),
	SUMMON_OCELOT("summon_ocelot"),
	SUMMON_HORSE("summon_horse"),

	RECHARGE_MESSAGE("recharge_message"),
	CLICK("click");

	private String name;

	DelayType(String name) {
		this.name = name;
	}

	@Nonnull public String getName() { return this.name; }

	@Nonnull public static Optional<DelayType> of(@Nonnull final String name) {
		for (DelayType t : values()) { if (t.name.equalsIgnoreCase(name)) return Optional.of(t); }
		return Optional.empty();
	}
}