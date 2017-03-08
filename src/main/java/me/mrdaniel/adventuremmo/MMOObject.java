package me.mrdaniel.adventuremmo;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.plugin.PluginContainer;

public abstract class MMOObject {

	private final AdventureMMO mmo;

	public MMOObject(@Nonnull final AdventureMMO mmo) {
		this.mmo = mmo;
	}

	@Nonnull public AdventureMMO getMMO() { return this.mmo; }
	@Nonnull public PluginContainer getContainer() { return this.mmo.getContainer(); }
	@Nonnull public Logger getLogger() { return this.mmo.getLogger(); }
	@Nonnull public Game getGame() { return this.mmo.getGame(); }
	@Nonnull public Server getServer() { return this.mmo.getGame().getServer(); }
}