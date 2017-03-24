package me.mrdaniel.adventuremmo.service;

import javax.annotation.Nonnull;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.io.playerdata.PlayerDatabase;

public class AdventureMMOService {

	private final AdventureMMO mmo;

	public AdventureMMOService(@Nonnull final AdventureMMO mmo) {
		this.mmo = mmo;
	}

	@Nonnull
	public AdventureMMO getPlugin() {
		return this.mmo;
	}

	@Nonnull
	public PlayerDatabase getPlayerDatabase() {
		return this.mmo.getPlayerDatabase();
	}
}