package me.mrdaniel.adventuremmo.event;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.impl.AbstractEvent;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;

public class LevelUpEvent extends AbstractEvent implements Cancellable {

	private final Player player;
	private final SkillType skill;
	private final int old_level;
	private final int new_level;

	private final Cause cause;
	private boolean cancelled;

	public LevelUpEvent(@Nonnull final AdventureMMO mmo, @Nonnull final Player player, @Nonnull final SkillType skill,
			final int old_level, final int new_level) {
		this.player = player;
		this.skill = skill;
		this.old_level = old_level;
		this.new_level = new_level;

		this.cause = Cause.builder().append(mmo.getContainer()).build(EventContext.empty());
		this.cancelled = false;
	}

	@Nonnull
	public Player getPlayer() {
		return this.player;
	}

	@Nonnull
	public SkillType getSkill() {
		return this.skill;
	}

	public int getOldLevel() {
		return this.old_level;
	}

	public int getNewLevel() {
		return this.new_level;
	}

	@Override
	public Cause getCause() {
		return this.cause;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(final boolean cancelled) {
		this.cancelled = cancelled;
	}
}