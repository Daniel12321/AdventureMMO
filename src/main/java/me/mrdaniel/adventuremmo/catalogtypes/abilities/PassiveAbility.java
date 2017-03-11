package me.mrdaniel.adventuremmo.catalogtypes.abilities;

import javax.annotation.Nonnull;

public class PassiveAbility extends Ability {

	private final String name;
	private final String id;
	private final double initial;
	private final double increment;

	public PassiveAbility(@Nonnull final String name, @Nonnull final String id, final double initial, final double increment) {
		this.name = name;
		this.id = id;
		this.initial = initial;
		this.increment = increment;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public double getInitial() {
		return this.initial;
	}

	@Override
	public double getIncrement() {
		return this.increment;
	}

	public boolean getChance(final int level) {
		return this.getInitial() + (this.getIncrement()*level) > Math.random()*100;
	}
}