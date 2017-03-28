package me.mrdaniel.adventuremmo.listeners.skills;

import javax.annotation.Nonnull;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.filter.IsCancelled;
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

	@Listener(order = Order.EARLY)
	@IsCancelled(value = Tristate.FALSE)
	public void onAbility(final AbilityEvent e) {
		if (e.getTool() != this.tool || !this.ability.isEnabled() || (this.onblock == Tristate.TRUE && !e.isOnBlock()) || (this.onblock == Tristate.FALSE && e.isOnBlock())) { return; }

		e.setAbility(this.ability);
		e.setSkill(this.skill);
	}
}