package me.mrdaniel.adventuremmo.listeners.skills;

import javax.annotation.Nonnull;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.util.Tristate;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Abilities;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolTypes;
import me.mrdaniel.adventuremmo.data.BlockData;
import me.mrdaniel.adventuremmo.event.BreakBlockEvent;

public class FarmingListener extends ActiveAbilityListener {

	public FarmingListener(@Nonnull final AdventureMMO mmo) {
		super(mmo, Abilities.GREEN_THUMBS, SkillTypes.FARMING, ToolTypes.HOE, Tristate.UNDEFINED);
	}

	@Listener
	public void onBlockBreak(final BreakBlockEvent e, @First final BlockData block) {
		if (block.getSkill() == super.skill) {
			super.getMMO().getPlayerDatabase().get(e.getPlayer().getUniqueId()).addExp(e.getPlayer(), super.skill, block.getExp());
		}
	}
}