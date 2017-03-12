package me.mrdaniel.adventuremmo.catalogtypes.abilities;

import javax.annotation.Nonnull;

public class PassiveAbility extends Ability {

	public PassiveAbility(@Nonnull final String name, @Nonnull final String id, final double initial, final double increment) {
		super(name, id, initial, increment);
	}

	public boolean getChance(final int level) {
		return this.getInitial() + (this.getIncrement()*level) > Math.random()*100;
	}
}