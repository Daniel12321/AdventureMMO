package me.mrdaniel.adventuremmo.catalogtypes.abilities;

import javax.annotation.Nonnull;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ActiveAbility extends Ability {

	private final ActiveAbilityActions actions;

	public ActiveAbility(@Nonnull final String name, @Nonnull final String id, @Nonnull final ActiveAbilityActions actions) {
		super(name, id);

		this.actions = actions;
	}

	@Nonnull
	public ActiveAbilityActions getActions() {
		return this.actions;
	}

	public double getSeconds(final int level) {
		return super.getValue(level);
	}

	@Override
	public Text getValueLine(final int level) {
		return Text.of(TextColors.YELLOW, "Duration: ", String.format("%.2f", this.getValue(level)), "s");
	}
}