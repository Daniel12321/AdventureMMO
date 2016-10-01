package me.mrdaniel.mmo.utils;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause.Builder;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.mmo.Main;

public class ServerUtils {
		
	public static Cause getCause() {
		NamedCause cause = NamedCause.source(Main.getInstance().getGame().getPluginManager().getPlugin("adventuremmo").get());
		return Cause.of(cause);
	}
	public static Entity spawn(Location<World> loc, EntityType type) {
		Entity e = loc.getExtent().createEntity(type, loc.getPosition());
		loc.getExtent().spawnEntity(e, Cause.source(Main.getInstance().getGame().getRegistry().createBuilder(Builder.class).entity(e).type(SpawnTypes.PLUGIN).build()).build());
		return e;
	}
}