package me.mrdaniel.adventuremmo.listeners;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.tileentity.Piston;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;

public class WorldListener {

	private final UUID uuid;
	private final Logger logger;

	public WorldListener() {
		this.uuid = UUID.fromString("af191b27-3180-4021-bf4a-1d0484069300");
		this.logger = LoggerFactory.getLogger("WorldListener");
	}

	@Listener(order = Order.LATE)
	public void onGrow(final ChangeBlockEvent.Grow e) {
		if (e.isCancelled()) { return; }

		this.logger.info("Found Block Growth!");

		e.getTransactions().forEach(trans -> {
			trans.setCustom(BlockSnapshot.builder().from(trans.getFinal()).creator(null).build());
		});
	}

	@Listener(order = Order.LATE)
	public void onBlockPlace(final ChangeBlockEvent.Place e) {
		if (e.isCancelled()) { return; }

		if (e.getCause().first(Piston.class).isPresent() || e.getCause().first(Player.class).isPresent()) {
			for (Transaction<BlockSnapshot> trans : e.getTransactions()) {
				this.logger.info("Found Block Transaction!");

				BlockSnapshot newblock = BlockSnapshot.builder().from(trans.getFinal()).creator(trans.getFinal().getCreator().orElse(this.uuid)).notifier(trans.getFinal().getCreator().orElse(this.uuid)).build();
				trans.setCustom(newblock);
			}
			//e.getTransactions().forEach(trans -> trans.setCustom(BlockSnapshot.builder().from(trans.getFinal()).creator(trans.getFinal().getCreator().orElse(this.uuid)).build()));
		}
	}
}