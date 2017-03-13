package me.mrdaniel.adventuremmo.event;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;
import me.mrdaniel.adventuremmo.data.BlockData;

public class BreakBlockEvent extends AbstractEvent {

	private final Player player;
	private final Location<World> block;
	private final Cause cause;

	public BreakBlockEvent(@Nonnull final AdventureMMO mmo, @Nonnull final Player player, @Nonnull final Location<World> block_location, @Nonnull final BlockData block, @Nullable final ToolType tool) {
		Cause.Builder b = Cause.source(mmo).named("block", block);
		Optional.ofNullable(tool).ifPresent(data -> b.named("tool", tool));

		this.player = player;
		this.block = block_location;
		this.cause = b.build();
	}

	@Nonnull
	public Player getPlayer() {
		return this.player;
	}

	@Nonnull
	public Location<World> getBlock() {
		return this.block;
	}

	@Override
	public Cause getCause() {
		return this.cause;
	}
}