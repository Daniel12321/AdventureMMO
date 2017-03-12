package me.mrdaniel.adventuremmo.event;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.entity.living.humanoid.player.TargetPlayerEvent;
import org.spongepowered.api.event.impl.AbstractEvent;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.data.BlockData;
import me.mrdaniel.adventuremmo.data.ToolData;

public class BreakBlockEvent extends AbstractEvent implements TargetPlayerEvent {

	private final Player player;
	private final Cause cause;

	public BreakBlockEvent(@Nonnull final AdventureMMO mmo, @Nonnull final Player player, @Nonnull final BlockData bdata, @Nullable final ToolData tool) {
		Cause.Builder b = Cause.source(mmo).named("block", bdata);
		Optional.ofNullable(tool).ifPresent(data -> b.named("tool", data.getType()));

		this.player = player;
		this.cause = b.build();
	}

	@Override
	public Player getTargetEntity() {
		return this.player;
	}

	@Override
	public Cause getCause() {
		return this.cause;
	}
}