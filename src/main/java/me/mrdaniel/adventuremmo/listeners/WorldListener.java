package me.mrdaniel.adventuremmo.listeners;

import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.tileentity.Piston;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.First;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;

public class WorldListener extends MMOObject {

	private final UUID uuid;

	public WorldListener(@Nonnull final AdventureMMO mmo) {
		super(mmo);

		this.uuid = UUID.fromString("af191b27-3180-4021-bf4a-1d0484069300");
	}

	@Listener(order = Order.LATE)
	public void onGrow(final ChangeBlockEvent.Grow e) {
		if (e.isCancelled()) { return; }

		e.getTransactions().forEach(trans -> {
			trans.setCustom(BlockSnapshot.builder().from(trans.getFinal()).creator(null).build());
		});
	}

	@Listener(order = Order.LATE)
	public void onPiston(final ChangeBlockEvent.Post e, @First final Piston piston) {
		e.getTransactions().forEach(trans -> trans.setCustom(BlockSnapshot.builder().from(trans.getFinal()).creator(e.getCause().first(Player.class).map(p -> p.getUniqueId()).orElse(this.uuid)).build()));
	}

//	@Listener(order = Order.LATE)
//	public void onBlockPlace(final ChangeBlockEvent.Post e, @First final Player p) {
//		e.getTransactions().forEach(trans -> trans.getOriginal().getLocation().ifPresent(loc -> loc.setBlock(BlockSnapshot.builder().from(trans.getFinal()).creator(p.getUniqueId()).build().getState(), ServerUtils.getCause(super.getContainer()))));
//	}

	@Listener(order = Order.LATE)
	public void onBlockPlace(final ChangeBlockEvent.Place e) {
		if (e.getCause().first(Piston.class).isPresent() || e.getCause().first(Player.class).isPresent()) {
			for (Transaction<BlockSnapshot> trans : e.getTransactions()) {
				BlockSnapshot newblock = BlockSnapshot.builder().from(trans.getFinal()).creator(trans.getFinal().getCreator().orElse(this.uuid)).notifier(trans.getFinal().getCreator().orElse(this.uuid)).build();
				trans.setCustom(newblock);
			}
		}
	}
}