package me.mrdaniel.mmo.utils;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause.Builder;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.mmo.Main;

public class ItemUtils {
	
	public static ItemStack build(ItemType type, int quantity, int dura) {
		MemoryDataContainer container = (MemoryDataContainer) new MemoryDataContainer().set(DataQuery.of("ItemType"), type).set(DataQuery.of("Count"), quantity).set(DataQuery.of("UnsafeDamage"), dura);
		ItemStack stack = Main.getInstance().getGame().getRegistry().createBuilder(ItemStack.Builder.class).fromContainer(container).build();
		return stack;
	}
	
	public static Item drop(ItemStack item, Location<World> loc) {
		Entity e = loc.getExtent().createEntity(EntityTypes.ITEM, loc.getPosition());
		Item i = (Item) e;
		i.offer(Keys.REPRESENTED_ITEM, item.createSnapshot());
		loc.getExtent().spawnEntity(i, Cause.source(Sponge.getRegistry().createBuilder(Builder.class).entity(i).type(SpawnTypes.PLUGIN).build()).build());  
		return i;
	}
}