package me.mrdaniel.adventuremmo.catalogtypes.abilities;

import org.spongepowered.api.entity.living.player.Player;

public interface ActiveAbilityActions {

	void activate(Player p);
	void deactivate(Player p);
}