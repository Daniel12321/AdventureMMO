package me.mrdaniel.mmo.listeners;

import java.util.Optional;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.tileentity.Piston;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.mmo.io.blocktracking.WatchList;

public class WorldListener {
	
	@Listener
	public void grow(ChangeBlockEvent.Grow e) {
		Optional<Location<World>> loc = e.getTransactions().get(0).getFinal().getLocation();
		if (loc.isPresent()) {
			WatchList.remove(loc.get());
		}
	}
	@Listener
	public void grow(ChangeBlockEvent.Modify e) {
		if (e.getCause().first(Piston.class).isPresent()) {
			for (Transaction<BlockSnapshot> t : e.getTransactions()) {
				Optional<Location<World>> loc = t.getFinal().getLocation();
				if (loc.isPresent()) {
					WatchList.add(loc.get());
				}
			}
		}
	}
}