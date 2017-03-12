package me.mrdaniel.adventuremmo.listeners.skills;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.action.FishingEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.util.Tristate;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Abilities;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolTypes;

public class FishingListener extends ActiveAbilityListener {

	private final int fish_exp;

	public FishingListener(@Nonnull final AdventureMMO mmo, final int fish_exp) {
		super(mmo, Abilities.FISH_CRAZE, SkillTypes.FISHING, ToolTypes.ROD, Tristate.UNDEFINED);

		this.fish_exp = fish_exp;
	}

	@Listener
	public void onFish(final FishingEvent.Stop e) {
		if (e.getTargetEntity() instanceof Player) {
			Player p = (Player) e.getTargetEntity();
			e.getItemStackTransaction().forEach(trans -> {
				ItemType item = trans.getOriginal().getType();
				int exp = this.fish_exp;
				if (item == ItemTypes.SADDLE || item == ItemTypes.ENCHANTED_BOOK || item == ItemTypes.BOW || item == ItemTypes.FISHING_ROD || item == ItemTypes.NAME_TAG) { exp *= 3; }
				super.getMMO().getPlayerDatabase().get(p.getUniqueId()).addExp(p, super.skill, exp);
			});
		}
	}
}