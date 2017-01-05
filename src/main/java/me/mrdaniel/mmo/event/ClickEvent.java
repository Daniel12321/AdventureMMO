package me.mrdaniel.mmo.event;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

public class ClickEvent extends AbstractEvent implements Cancellable {

	private Player p;
	private Cause cause;
	private ClickType type;
	private Optional<BlockState> block;
	private boolean cancelled;

	public ClickEvent(@Nonnull final Player p, @Nonnull final Cause cause, @Nonnull final ClickType type, @Nonnull final Optional<BlockState> block) {
		this.p = p;
		this.cause = cause;
		this.type = type;
		this.block = block;
	}

	@Override
	@Nonnull
	public Cause getCause() {
		return this.cause;
	}

	@Nonnull
	public Player getPlayer() {
		return this.p;
	}

	@Nonnull
	public Optional<BlockState> getBlock() {
		return this.block;
	}

	@Nonnull
	public ClickType getType() {
		return this.type;
	}

	@Override
	@Nonnull
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	@Nonnull
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}
}