package me.mrdaniel.adventuremmo.event;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.entity.TargetEntityEvent;
import org.spongepowered.api.event.impl.AbstractEvent;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;

public abstract class PlayerTargetEntityEvent extends AbstractEvent implements TargetEntityEvent {

	private final Player player;
	private final Entity entity;
	private final Cause cause;

	public PlayerTargetEntityEvent(@Nonnull final AdventureMMO mmo, @Nonnull final Player player, @Nonnull final Entity entity, @Nonnull final ToolType tool) {
		this.player = player;
		this.entity = entity;
		this.cause = Cause.source(mmo).named("tool", tool).build();
	}

	@Nonnull
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public Entity getTargetEntity() {
		return this.entity;
	}

	@Override
	public Cause getCause() {
		return this.cause;
	}

	public static class Damage extends PlayerTargetEntityEvent {

		public Damage(@Nonnull final AdventureMMO mmo, @Nonnull final Player player, @Nonnull final Entity entity, @Nonnull final ToolType tool) {
			super(mmo, player, entity, tool);
		}
	}

	public static class Kill extends PlayerTargetEntityEvent {

		public Kill(@Nonnull final AdventureMMO mmo, @Nonnull final Player player, @Nonnull final Entity entity, @Nonnull final ToolType tool) {
			super(mmo, player, entity, tool);
		}
	}
}