package me.mrdaniel.adventuremmo.catalogtypes.abilities;

import javax.annotation.Nonnull;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class PassiveAbility extends Ability {

	public PassiveAbility(@Nonnull final String name, @Nonnull final String id) {
		super(name, id);
	}

	public boolean getChance(final int level) {
		return this.getInitial() + (this.getIncrement()*level) > Math.random()*100;
	}

	@Override
	public Text getValueLine(int level) {
		return Text.of(TextColors.YELLOW, "Chance: ", this.getChance(level), "%");
	}
}