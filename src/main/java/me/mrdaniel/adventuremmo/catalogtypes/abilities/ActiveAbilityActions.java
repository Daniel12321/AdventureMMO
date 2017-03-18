package me.mrdaniel.adventuremmo.catalogtypes.abilities;

import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.adventuremmo.AdventureMMO;

public interface ActiveAbilityActions {

	public static ActiveAbilityActions EMPTY = new ActiveAbilityActions() {
		@Override public void activate(AdventureMMO mmo, Player p) {}
		@Override public void deactivate(AdventureMMO mmo, Player p) {}
	};

	void activate(AdventureMMO mmo, Player p);
	void deactivate(AdventureMMO mmo, Player p);
}