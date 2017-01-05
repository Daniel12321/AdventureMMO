package me.mrdaniel.mmo.scoreboard;

import org.spongepowered.api.entity.living.player.Player;

@FunctionalInterface
public interface PlayerRunnable {

	public abstract void run(Player p);
}