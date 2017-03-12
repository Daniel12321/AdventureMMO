package me.mrdaniel.adventuremmo.listeners.skills;

import javax.annotation.Nonnull;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.util.Tristate;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.ActiveAbility;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;
import me.mrdaniel.adventuremmo.event.AbilityEvent;

public abstract class ActiveAbilityListener extends MMOObject {

	protected final ActiveAbility ability;
	protected final SkillType skill;
	protected final ToolType tool;

	protected final Tristate onblock;

	public ActiveAbilityListener(@Nonnull final AdventureMMO mmo, @Nonnull final ActiveAbility ability, @Nonnull final SkillType skill, @Nonnull final ToolType tool, final Tristate onblock) {
		super(mmo);

		this.ability = ability;
		this.skill = skill;
		this.tool = tool;

		this.onblock = onblock;
	}

	@Listener
	public void onAbility(final AbilityEvent.Pre e, @First final ToolType tool) {
		if (tool != this.tool) { return; }
		if (super.getGame().getEventManager().post(new AbilityEvent.Activate(super.getMMO(), e.getTargetEntity(), this.tool, this.skill, this.ability))) { return; }
		if (this.onblock == Tristate.TRUE && !e.isOnBlock())  { return; }
		if (this.onblock == Tristate.FALSE && e.isOnBlock()) { return; }

		final int level = super.getMMO().getPlayerDatabase().get(e.getTargetEntity().getUniqueId()).getLevel(this.skill);

		this.ability.activate(super.getMMO(), e.getTargetEntity(), level);
	}
}