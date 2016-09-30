package me.mrdaniel.mmo.utils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import me.mrdaniel.mmo.Main;

public class ServerUtils {
		
	public static Player getPlayer(String player) {
		
		for (Player p : Main.getInstance().getGame().getServer().getOnlinePlayers()) {
			if (p.getName().equalsIgnoreCase(player)) { return p; }
		}
		
		Player found = null;
		for (Player p : Main.getInstance().getGame().getServer().getOnlinePlayers()) {
			if (!p.getName().toLowerCase().contains(player.toLowerCase())) continue;
			if (found != null) return null; found = p;
		}
		return found;
	}
	public static Player getPlayerFromUUID(String uuid) {
		for (Player p : Main.getInstance().getGame().getServer().getOnlinePlayers()) {
			if (p.getUniqueId().toString().equalsIgnoreCase(uuid)) { return p; }
		}
		Player found = null;
		for (Player p : Main.getInstance().getGame().getServer().getOnlinePlayers()) {
			if (!p.getUniqueId().toString().toLowerCase().contains(uuid.toLowerCase())) continue;
			if (found != null) return null; found = p;
		}
		return found;
	}
	public static void broadcast(String permission, Text text) {
		for (Player player : Main.getInstance().getGame().getServer().getOnlinePlayers()) {
			if (player.hasPermission(permission)) {
				player.sendMessage(text);
			}
		}
	}
}