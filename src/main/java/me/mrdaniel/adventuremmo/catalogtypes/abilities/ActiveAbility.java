package me.mrdaniel.adventuremmo.catalogtypes.abilities;

import org.spongepowered.api.entity.living.player.Player;

public abstract class ActiveAbility extends Ability {

	public int getSeconds(final int level) {
		return (int) (this.getInitial() + (this.getIncrement()*level));
	}

	public abstract void activate(Player p, int level);
}