package me.mrdaniel.adventuremmo.io.playerdata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;

import me.mrdaniel.adventuremmo.AdventureMMO;

public class HoconPlayerDatabase implements PlayerDatabase {

	private final Path path;
	private final Map<UUID, HoconPlayerData> players;

	public HoconPlayerDatabase(@Nonnull final AdventureMMO mmo, @Nonnull final Path path) {
		this.path = path;
		this.players = new ConcurrentHashMap<UUID, HoconPlayerData>();

		if (!Files.exists(path)) {
			try { Files.createDirectory(path); }
			catch (final IOException exc) { mmo.getLogger().error("Failed to create main playerdata directory: {}", exc); }
		}

		mmo.getGame().getServer().getOnlinePlayers().forEach(p -> this.load(p.getUniqueId()));
	}

	public synchronized void load(@Nonnull final UUID uuid) {
		this.players.put(uuid, new HoconPlayerData(this.path.resolve(uuid.toString() + ".conf")));
	}

	public synchronized void unload(@Nonnull final UUID uuid) {
		this.players.remove(uuid);
	}

	public synchronized PlayerData get(@Nonnull final UUID uuid) {
		return this.players.get(uuid);
	}

	public Optional<PlayerData> getOffline(@Nonnull final UUID uuid) {
		Path path = this.path.resolve(uuid.toString() + ".conf");
		if (!Files.exists(path)) { return Optional.empty(); }
		return Optional.of(new HoconPlayerData(path));
	}
}