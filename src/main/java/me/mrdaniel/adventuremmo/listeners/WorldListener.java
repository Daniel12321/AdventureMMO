package me.mrdaniel.adventuremmo.listeners;

import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.tileentity.Piston;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.type.Include;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.event.world.LoadWorldEvent;
import org.spongepowered.api.event.world.UnloadWorldEvent;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.transaction.SlotTransaction;
import org.spongepowered.api.util.Tristate;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.data.manipulators.ImmutableSuperToolData;

public class WorldListener extends MMOObject {

	private final UUID uuid;

	public WorldListener(@Nonnull final AdventureMMO mmo) {
		super(mmo);

		this.uuid = UUID.fromString("af191b27-3180-4021-bf4a-1d0484069300");
	}

	@Include(value = {ChangeBlockEvent.Grow.class, ChangeBlockEvent.Break.class})
	@Listener(order = Order.LATE)
	@IsCancelled(value = Tristate.FALSE)
	public void onGrow(final ChangeBlockEvent e) {
		e.getTransactions().forEach(trans -> trans.getOriginal().getLocation().ifPresent(loc -> loc.getExtent().setCreator(loc.getBlockPosition(), null)));
	}

	@Listener(order = Order.LATE)
	@IsCancelled(value = Tristate.FALSE)
	public void onBlockPlace(final ChangeBlockEvent.Place e) {
		if (e.getCause().first(Piston.class).isPresent() || e.getCause().first(Player.class).isPresent()) {
			e.getTransactions().forEach(trans -> trans.getOriginal().getLocation().ifPresent(loc -> loc.getExtent().setCreator(loc.getBlockPosition(), e.getCause().first(Player.class).map(p -> p.getUniqueId()).orElse(this.uuid))));
		}
	}

	@Listener(order = Order.EARLY)
	@IsCancelled(value = Tristate.FALSE)
	public void onItemClick(final ClickInventoryEvent e, @First final Player p) {
		for (SlotTransaction trans : e.getTransactions()) {
			if (trans.getOriginal().get(ImmutableSuperToolData.class).isPresent()) { e.setCancelled(true); return; }
		}
	}

	@Listener(order = Order.EARLY)
	@IsCancelled(value = Tristate.FALSE)
	public void onItemDrop(final DropItemEvent.Pre e) {
		for (ItemStackSnapshot item : e.getOriginalDroppedItems()) {
			if (item.get(ImmutableSuperToolData.class).isPresent()) { e.setCancelled(true); return; }
		}
	}

	@Listener
	@IsCancelled(value = Tristate.FALSE)
	public void onWorldLoad(final LoadWorldEvent e) {
		super.getMMO().getDoubleDrops().loadWorld(e.getTargetWorld());
	}

	@Listener
	@IsCancelled(value = Tristate.FALSE)
	public void onWorldUnload(final UnloadWorldEvent e) {
		this.getMMO().getDoubleDrops().unloadWorld(e.getTargetWorld());
	}
}