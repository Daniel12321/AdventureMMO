package me.mrdaniel.adventuremmo.catalogtypes.abilities.abilities;

import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.adventuremmo.catalogtypes.abilities.ActiveAbility;
import me.mrdaniel.adventuremmo.data.manipulators.MMOData;

public class TreeVeller extends ActiveAbility {

	@Override
	public String getName() {
		return "Tree Veller";
	}

	@Override
	public String getId() {
		return "treeveller";
	}

	@Override
	public double getIncrement() {
		return 0.08;
	}

	@Override
	public double getInitial() {
		return 5.0;
	}

	@Override
	public void activate(final Player p, final int level) {
		p.get(MMOData.class).orElse(new MMOData()).setAbility(this.getId(), System.currentTimeMillis() + super.getSeconds(level));
	}
}