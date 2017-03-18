package me.mrdaniel.adventuremmo.listeners;

import javax.annotation.Nonnull;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;

public class ClientListener extends MMOObject {

	public ClientListener(@Nonnull final AdventureMMO mmo) {
		super(mmo);
	}

	@Listener
	public void onJoin(final ClientConnectionEvent.Join e) {
		super.getMMO().getPlayerDatabase().load(e.getTargetEntity().getUniqueId());
	}

	@Listener
	public void onQuit(final ClientConnectionEvent.Disconnect e) {
		super.getMMO().getPlayerDatabase().unload(e.getTargetEntity().getUniqueId());
		super.getMMO().getMenus().getScoreboardManager().unload(e.getTargetEntity());
		super.getMMO().getSuperTools().undo(e.getTargetEntity());
	}
}