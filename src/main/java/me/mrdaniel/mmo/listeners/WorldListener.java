package me.mrdaniel.mmo.listeners;

import java.util.Optional;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.tileentity.Piston;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.FallingBlock;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.entity.ConstructEntityEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.data.MMOData;
import me.mrdaniel.mmo.io.blocktracking.WatchList;

public class WorldListener {
	
	@Listener(order = Order.LAST)
	public void grow(ChangeBlockEvent.Grow e) {
		if (e.isCancelled()) { return; }
		Optional<Location<World>> loc = e.getTransactions().get(0).getFinal().getLocation();
		if (loc.isPresent()) {
			WatchList.remove(loc.get());
		}
	}

	@Listener(order = Order.LAST)
	public void grow(ChangeBlockEvent.Decay e) {
		if (e.isCancelled()) { return; }
		Optional<Location<World>> loc = e.getTransactions().get(0).getFinal().getLocation();
		if (loc.isPresent()) {
			WatchList.remove(loc.get());
		}
	}

	@Listener(order = Order.LAST)
	public void grow(ChangeBlockEvent.Modify e) {
		if (e.isCancelled()) { return; }
		if (e.getCause().first(Piston.class).isPresent()) {
			for (Transaction<BlockSnapshot> t : e.getTransactions()) {
				Optional<Location<World>> loc = t.getFinal().getLocation();
				if (loc.isPresent()) {
					WatchList.add(loc.get());
				}
			}
		}
	}

	@Listener(order = Order.LAST)
	public void onBlockFall(DestructEntityEvent e, @First Player p) {
		if (e.getTargetEntity() instanceof FallingBlock) {
			WatchList.add(e.getTargetEntity().getLocation());
		}
	}

	@Listener(order = Order.LAST)
	public void onBlockFall(ConstructEntityEvent.Post e, @First Player p) {
		if (e.getTargetEntity() instanceof FallingBlock) {
			WatchList.remove(e.getTargetEntity().getLocation());
		}
	}

	@Listener(order = Order.LAST)
	public void onEntitySpawn(ConstructEntityEvent.Post e) {
		//TODO find a better way to do this
		if (e.getTargetEntity() instanceof Item) {
			final Item itemEntity = (Item) e.getTargetEntity();
			
			Main.getInstance().getGame().getScheduler().createTaskBuilder()
			.delayTicks(1)
			.execute(()-> {
				ItemStack item = itemEntity.getItemData().item().get().createStack();
				Optional<MMOData> loreOpt = item.get(MMOData.class);
				if (!(loreOpt.isPresent())) { return; }
				if (loreOpt.get().getEnabled()) { itemEntity.remove(); }
			}).submit(Main.getInstance());
		}
	}
}