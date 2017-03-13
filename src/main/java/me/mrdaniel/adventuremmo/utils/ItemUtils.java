package me.mrdaniel.adventuremmo.utils;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.SkullType;
import org.spongepowered.api.data.type.SkullTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class ItemUtils {

	@Nonnull
	public static ItemStack build(@Nonnull final ItemType type, final int amount, final int unsafe) {
		return ItemStack.builder().fromContainer(new MemoryDataContainer().set(DataQuery.of("ItemType"), type).set(DataQuery.of("Count"), amount).set(DataQuery.of("UnsafeDamage"), unsafe)).build();
	}

	@Nonnull
	public static Entity drop(@Nonnull final Location<World> loc, @Nonnull final ItemStackSnapshot item) {
		Entity e = loc.createEntity(EntityTypes.ITEM);
		e.offer(Keys.REPRESENTED_ITEM, item);
		loc.getExtent().spawnEntity(e, ServerUtils.getSpawnCause(e));
		return e;
	}

	@Nonnull
	public static Optional<ItemStack> getHead(@Nonnull final EntityType type) {
		ItemStack item = ItemStack.builder().itemType(ItemTypes.SKULL).quantity(1).build();

		SkullType skull;
		if (type == EntityTypes.ZOMBIE) { skull = SkullTypes.ZOMBIE; }
		else if (type == EntityTypes.SKELETON) { skull = SkullTypes.SKELETON; }
		else if (type == EntityTypes.CREEPER) { skull = SkullTypes.CREEPER; }
		else if (type == EntityTypes.WITHER_SKELETON) { skull = SkullTypes.WITHER_SKELETON; }
		else if (type == EntityTypes.ENDER_DRAGON) { skull = SkullTypes.ENDER_DRAGON; }
		else return Optional.empty();

		item.offer(Keys.SKULL_TYPE, skull);
		return Optional.of(item);
	}

	@Nonnull
	public static ItemStack getPlayerHead(@Nonnull final Player p) {
		ItemStack item = ItemStack.builder().itemType(ItemTypes.SKULL).quantity(1).build();
		item.offer(Keys.SKULL_TYPE, SkullTypes.PLAYER);
		item.offer(Keys.REPRESENTED_PLAYER, p.getProfile());
		return item;
	}
}