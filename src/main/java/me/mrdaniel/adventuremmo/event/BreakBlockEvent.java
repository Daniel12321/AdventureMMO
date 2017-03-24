package me.mrdaniel.adventuremmo.event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;
import me.mrdaniel.adventuremmo.io.items.BlockData;

public class BreakBlockEvent extends AbstractEvent {

	private final Player player;
	private final Location<World> location;
	private final BlockData block;
	private final ToolType tool;

	private final Cause cause;

	public BreakBlockEvent(@Nonnull final AdventureMMO mmo, @Nonnull final Player player, @Nonnull final Location<World> location, @Nonnull final BlockData block, @Nullable final ToolType tool) {
		this.player = player;
		this.location = location;
		this.block = block;
		this.tool = tool;

		this.cause = Cause.source(mmo.getContainer()).build();
	}

	@Nonnull
	public Player getPlayer() {
		return this.player;
	}

	@Nonnull
	public Location<World> getLocation() {
		return this.location;
	}

	@Nonnull
	public BlockData getBlock() {
		return this.block;
	}

	@Nullable
	public ToolType getTool() {
		return this.tool;
	}

	@Override
	public Cause getCause() {
		return this.cause;
	}
}