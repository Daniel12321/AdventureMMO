package me.mrdaniel.adventuremmo.event;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.entity.living.humanoid.player.TargetPlayerEvent;
import org.spongepowered.api.plugin.PluginContainer;

import me.mrdaniel.adventuremmo.enums.SkillType;
import me.mrdaniel.adventuremmo.utils.ServerUtils;

public class LevelUpEvent implements TargetPlayerEvent, Cancellable {

	private final Player player;
	private final Cause cause;

	private boolean cancelled;

	public LevelUpEvent(@Nonnull final Player player, @Nonnull final PluginContainer container, @Nonnull final SkillType skill, final int old_level, final int new_level) {
		this.player = player;
		this.cause = ServerUtils.getCause(container, NamedCause.of("skill", skill), NamedCause.of("old_level", old_level), NamedCause.of("new_level", new_level));
	}

	@Override
	public Cause getCause() {
		return this.cause;
	}

	@Override
	public Player getTargetEntity() {
		return this.player;
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