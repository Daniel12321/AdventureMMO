package me.mrdaniel.adventuremmo.listeners;

import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.tileentity.Piston;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.type.Include;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;

public class WorldListener extends MMOObject {

	private final UUID uuid;

	public WorldListener(@Nonnull final AdventureMMO mmo) {
		super(mmo);

		this.uuid = UUID.fromString("af191b27-3180-4021-bf4a-1d0484069300");
	}

	@Include(value = {ChangeBlockEvent.Grow.class, ChangeBlockEvent.Break.class})
	@Listener(order = Order.LATE)
	public void onGrow(final ChangeBlockEvent e) {
		if (e.isCancelled()) { return; }

		e.getTransactions().forEach(trans -> trans.getOriginal().getLocation().ifPresent(loc -> loc.getExtent().setCreator(loc.getBlockPosition(), null)));
	}

	@Listener(order = Order.LATE)
	public void onBlockPlace(final ChangeBlockEvent.Place e) {
		if (e.getCause().first(Piston.class).isPresent() || e.getCause().first(Player.class).isPresent()) {
			e.getTransactions().forEach(trans -> trans.getOriginal().getLocation().ifPresent(loc -> loc.getExtent().setCreator(loc.getBlockPosition(), e.getCause().first(Player.class).map(p -> p.getUniqueId()).orElse(this.uuid))));
		}
	}
}