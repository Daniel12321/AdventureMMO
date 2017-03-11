package me.mrdaniel.adventuremmo.catalogtypes.abilities.abilities;

import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.adventuremmo.catalogtypes.abilities.ActiveAbility;

public class MadMiner extends ActiveAbility {

	@Override
	public String getName() {
		return "Mad Miner";
	}

	@Override
	public String getId() {
		return "madminer";
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
	public void activate(Player p, int level) {

	}
}