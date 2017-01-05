package me.mrdaniel.mmo.listeners;

import org.spongepowered.api.block.tileentity.Piston;
import org.spongepowered.api.entity.FallingBlock;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.entity.ConstructEntityEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.DropItemEvent;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.data.MMOData;

public class WorldListener {
	
	@Listener(order = Order.LAST)
	public void grow(final ChangeBlockEvent.Grow e) {
		if (e.isCancelled()) { return; }
		e.getTransactions().forEach(trans -> {
			trans.getOriginal().getLocation().ifPresent(loc -> { Main.getInstance().getChunkManager().remove(loc);});
		});
	}

	@Listener(order = Order.LAST)
	public void grow(final ChangeBlockEvent.Decay e) {
		if (e.isCancelled()) { return; }
		e.getTransactions().forEach(trans -> {
			trans.getOriginal().getLocation().ifPresent(loc -> { Main.getInstance().getChunkManager().remove(loc);});
		});
	}

	@Listener(order = Order.LAST)
	public void grow(final ChangeBlockEvent.Modify e) {
		if (e.isCancelled()) { return; }
		e.getCause().first(Piston.class).ifPresent(piston -> {
			e.getTransactions().forEach(trans -> {
				trans.getOriginal().getLocation().ifPresent(loc -> {
					Main.getInstance().getChunkManager().add(loc);
				});
			});
		});
	}

	@Listener(order = Order.LAST)
	public void onBlockFall(final DestructEntityEvent e, @First final Player p) {
		if (e.getTargetEntity() instanceof FallingBlock) {
			Main.getInstance().getChunkManager().add(e.getTargetEntity().getLocation());
		}
	}

	@Listener(order = Order.LAST)
	public void onBlockFall(final ConstructEntityEvent.Post e, @First final Player p) {
		if (e.getTargetEntity() instanceof FallingBlock) {
			Main.getInstance().getChunkManager().remove(e.getTargetEntity().getLocation());
		}
	}

	@Listener
	public void onItemDrop(final DropItemEvent.Dispense e) {
		e.getEntities().forEach(entity -> {
			if (entity instanceof Item) { ((Item)entity).item().get().createStack().get(MMOData.class).ifPresent(data -> e.setCancelled(true)); }
		});
	}
}