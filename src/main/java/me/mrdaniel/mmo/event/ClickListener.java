package me.mrdaniel.mmo.event;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.scheduler.Task;

import com.google.common.collect.Maps;

import me.mrdaniel.mmo.Main;

public class ClickListener {

	private final HashMap<UUID, Boolean> delays;

	public ClickListener() {
		this.delays = Maps.newHashMap();
	}

	@Listener
	public void onClick(final InteractItemEvent.Secondary e, @First final Player p) {		
		if (this.delays.containsKey(p.getUniqueId())) { e.setCancelled(this.delays.get(p.getUniqueId())); return; }

		Optional<BlockState> block = (e.getInteractionPoint().isPresent()) ? Optional.of(p.getWorld().getBlock(e.getInteractionPoint().get().toInt())) : Optional.empty();
		ClickEvent event = new ClickEvent(p, e.getCause(), ClickType.RIGHT, block);
		Main.getInstance().getGame().getEventManager().post(event);

		e.setCancelled(event.isCancelled());
		this.delays.put(p.getUniqueId(), event.isCancelled());

		Task.builder().delayTicks(1).execute(() -> this.delays.remove(p.getUniqueId())).submit(Main.getInstance());
	}

	@Listener
	public void onClick(final InteractBlockEvent.Primary e, @First final Player p) {
		ClickEvent event = new ClickEvent(p, e.getCause(), ClickType.LEFT, Optional.of(e.getTargetBlock().getState()));
		Main.getInstance().getGame().getEventManager().post(event);
		e.setCancelled(event.isCancelled());
	}

	@Listener
	public void onClick(final InteractEntityEvent.Primary e, @First final Player p) {
		ClickEvent event = new ClickEvent(p, e.getCause(), ClickType.LEFT, Optional.empty());
		Main.getInstance().getGame().getEventManager().post(event);
		e.setCancelled(event.isCancelled());
	}
}