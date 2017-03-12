package me.mrdaniel.adventuremmo.catalogtypes.abilities.abilities;

import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.adventuremmo.catalogtypes.abilities.ActiveAbility;

public class GreenThumbs extends ActiveAbility {

	public GreenThumbs() {
		super("Green Thumbs", "greenthumbs", 5.0, 0.08);
	}

	@Override
	protected void activate(final Player p) {
		
	}

	@Override
	protected void deactivate(final Player p) {
		
	}
}