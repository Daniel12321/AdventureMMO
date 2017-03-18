package me.mrdaniel.adventuremmo.managers;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.cause.entity.spawn.BlockSpawnCause;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.utils.ItemUtils;

public class DoubleDropManager {

	private final HashMap<World, List<Vector3i>> blocks;

	public DoubleDropManager(@Nonnull final AdventureMMO mmo) {
		this.blocks = Maps.newHashMap();

		Task.builder().delayTicks(10).intervalTicks(1).execute(() -> this.blocks.values().forEach(list -> list.clear())).submit(mmo);
		mmo.getGame().getServer().getWorlds().forEach(w -> this.blocks.put(w, Lists.newArrayList()));
	}

	public void add(@Nonnull final World world, @Nonnull final Vector3i pos) {
		this.blocks.get(world).add(pos);
	}

	public void loadWorld(@Nonnull final World world) {
		this.blocks.put(world, Lists.newArrayList());
	}

	public void unloadWorld(@Nonnull final World world) {
		this.blocks.remove(world);
	}

	@Listener(order = Order.LATE)
	public void onItemDrop(final DropItemEvent.Destruct e, @First final BlockSpawnCause block, @First final Player p) {
		e.getEntities().stream().filter(ent -> ent instanceof Item).map(ent -> (Item)ent).filter(item -> this.blocks.get(item.getWorld()).contains(item.getLocation().getBlockPosition())).forEach(item -> ItemUtils.drop(item.getLocation(), item.item().get()));
	}
}