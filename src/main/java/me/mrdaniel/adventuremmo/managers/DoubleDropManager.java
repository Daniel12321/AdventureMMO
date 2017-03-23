package me.mrdaniel.adventuremmo.managers;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.cause.entity.spawn.BlockSpawnCause;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.event.world.LoadWorldEvent;
import org.spongepowered.api.event.world.UnloadWorldEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Tristate;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;
import com.google.common.collect.Maps;

import me.mrdaniel.adventuremmo.AdventureMMO;

public class DoubleDropManager {

	private final Map<World, Map<Vector3i, Integer>> blocks;

	public DoubleDropManager(@Nonnull final AdventureMMO mmo) {
		this.blocks = Maps.newHashMap();

		Task.builder().delayTicks(10).intervalTicks(1).execute(() -> this.blocks.values().forEach(m -> m.clear())).submit(mmo);
		mmo.getGame().getServer().getWorlds().forEach(w -> this.blocks.put(w, Maps.newHashMap()));
	}

	public void addDouble(@Nonnull final World world, @Nonnull final Vector3i pos) {
		this.blocks.get(world).put(pos, 2);
	}

	public void addTriple(@Nonnull final World world, @Nonnull final Vector3i pos) {
		this.blocks.get(world).put(pos, 3);
	}

	@Listener
	@IsCancelled(value = Tristate.FALSE)
	public void onWorldLoad(final LoadWorldEvent e) {
		this.blocks.put(e.getTargetWorld(), Maps.newHashMap());
	}

	@Listener
	@IsCancelled(value = Tristate.FALSE)
	public void onWorldUnload(final UnloadWorldEvent e) {
		this.blocks.remove(e.getTargetWorld());
	}

	@Listener(order = Order.LATE)
	@IsCancelled(value = Tristate.FALSE)
	public void onItemDrop(final DropItemEvent.Destruct e, @First final BlockSpawnCause block) {
		e.getEntities().stream().filter(ent -> ent instanceof Item).map(ent -> (Item)ent).forEach(item -> {
			Optional.ofNullable(this.blocks.get(item.getWorld()).get(item.getLocation().getBlockPosition())).ifPresent(times -> {
				ItemStack is = item.item().get().createStack();
				is.setQuantity(is.getQuantity()*times);
				item.offer(Keys.REPRESENTED_ITEM, is.createSnapshot());
			});
		});
	}
}

//public class DoubleDropManager {
//
//	private final Map<World, List<Vector3i>> blocks;
//
//	public DoubleDropManager(@Nonnull final AdventureMMO mmo) {
//		this.blocks = Maps.newHashMap();
//
//		Task.builder().delayTicks(10).intervalTicks(2).execute(() -> this.blocks.values().forEach(list -> list.clear())).submit(mmo);
//		mmo.getGame().getServer().getWorlds().forEach(w -> this.blocks.put(w, Lists.newArrayList()));
//	}
//
//	public void addDouble(@Nonnull final World world, @Nonnull final Vector3i pos) {
//		this.blocks.get(world).add(pos);
//	}
//
//	public void addTriple(@Nonnull final World world, @Nonnull final Vector3i pos) {
//		this.blocks.get(world).add(pos);
//	}
//
//	public void loadWorld(@Nonnull final World world) {
//		this.blocks.put(world, Lists.newArrayList());
//	}
//
//	public void unloadWorld(@Nonnull final World world) {
//		this.blocks.remove(world);
//	}
//
//	@Listener(order = Order.LATE)
//	public void onItemDrop(final DropItemEvent.Destruct e, @First final BlockSpawnCause block, @First final Player p) {
//		e.getEntities().stream().filter(ent -> ent instanceof Item).map(ent -> (Item)ent).filter(item -> this.blocks.get(item.getWorld()).contains(item.getLocation().getBlockPosition())).forEach(item -> ItemUtils.drop(item.getLocation(), item.item().get()));
//	}
//}