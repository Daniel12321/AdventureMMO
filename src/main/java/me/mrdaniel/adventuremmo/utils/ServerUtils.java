package me.mrdaniel.adventuremmo.utils;

import java.util.Iterator;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.Server;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.collect.Lists;

public class ServerUtils {

	@Nonnull
	public static Cause getSpawnCause(@Nonnull final Entity e, @Nonnull final NamedCause... causes) {
		return getCause(EntitySpawnCause.builder().entity(e).type(SpawnTypes.PLUGIN).build(), causes);
	}

	@Nonnull
	public static Cause getCause(@Nonnull final Object root, @Nonnull final NamedCause... causes) {
		return Cause.source(root).addAll(Lists.newArrayList(causes)).build();
	}

	public static void broadcast(@Nonnull final Server server, @Nonnull final Text message) {
		server.getOnlinePlayers().forEach(p -> p.sendMessage(message));
	}

	@Nonnull
	public static Optional<Location<World>> getFirstBlock(@Nonnull final Player p) {
		Iterator<BlockRayHit<World>> iter = BlockRay.from(p).distanceLimit(50).iterator();
		while (iter.hasNext()) {
			Location<World> loc = iter.next().getLocation();
			if (loc.getBlockType() != BlockTypes.AIR) { return Optional.of(loc); }
		}
		return Optional.empty();
	}
}