package me.mrdaniel.adventuremmo.event;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.impl.AbstractEvent;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;

public class PlayerDamageEntityEvent extends AbstractEvent {

	private final Player player;
	private final Entity entity;
	private final ToolType tool;
	private final double damage;
	private final boolean death;
	private final Cause cause;

	public PlayerDamageEntityEvent(@Nonnull final AdventureMMO mmo, @Nonnull final Player player,
			@Nonnull final Entity entity, @Nonnull final ToolType tool, final double damage, final boolean death) {
		this.player = player;
		this.entity = entity;
		this.tool = tool;
		this.damage = damage;
		this.death = death;

		this.cause = Cause.builder().append(mmo.getContainer()).build(EventContext.empty());
	}

	@Nonnull
	public Player getPlayer() {
		return this.player;
	}

	@Nonnull
	public Entity getEntity() {
		return this.entity;
	}

	@Nonnull
	public ToolType getTool() {
		return this.tool;
	}

	public double getDamage() {
		return this.damage;
	}

	public boolean isDeath() {
		return this.death;
	}

	@Override
	public Cause getCause() {
		return this.cause;
	}
}