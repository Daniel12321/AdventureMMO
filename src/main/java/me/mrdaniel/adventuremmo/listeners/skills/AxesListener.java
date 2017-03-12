package me.mrdaniel.adventuremmo.listeners.skills;

import javax.annotation.Nonnull;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.util.Tristate;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Abilities;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolTypes;
import me.mrdaniel.adventuremmo.event.PlayerTargetEntityEvent;

public class AxesListener extends ActiveAbilityListener  {

	private final int damage_exp;
	private final int kill_exp;

	public AxesListener(@Nonnull final AdventureMMO mmo, final int damage_exp, final int kill_exp) {
		super(mmo, Abilities.SLAUGHTER, SkillTypes.AXES, ToolTypes.AXE, Tristate.FALSE);

		this.damage_exp = damage_exp;
		this.kill_exp = kill_exp;
	}

	@Listener
	public void onTarget(final PlayerTargetEntityEvent e, @First final ToolType tool) {
		if (tool == super.tool) {
			super.getMMO().getPlayerDatabase().get(e.getPlayer().getUniqueId()).addExp(e.getPlayer(), super.skill, e instanceof PlayerTargetEntityEvent.Damage ? this.damage_exp : this.kill_exp);
		}
	}
}