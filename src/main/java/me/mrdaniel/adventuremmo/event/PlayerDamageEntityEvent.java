package me.mrdaniel.adventuremmo.event;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.impl.AbstractEvent;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;

public class PlayerDamageEntityEvent extends AbstractEvent {

	private final Player player;
	private final DamageEntityEvent original_event;
	private final Cause cause;

	public PlayerDamageEntityEvent(@Nonnull final AdventureMMO mmo, @Nonnull final Player player, @Nonnull final DamageEntityEvent original_event, @Nonnull final ToolType tool) {
		this.player = player;
		this.original_event = original_event;
		this.cause = Cause.source(mmo).named("tool", tool).build();
	}

	@Nonnull
	public Player getPlayer() {
		return this.player;
	}

	@Nonnull
	public DamageEntityEvent getOriginalEvent() {
		return this.original_event;
	}

	@Override
	public Cause getCause() {
		return this.cause;
	}
}