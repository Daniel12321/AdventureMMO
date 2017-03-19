package me.mrdaniel.adventuremmo.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.mrdaniel.adventuremmo.AdventureMMO;

public class HoconPlayerDatabase implements PlayerDatabase {

	private final Logger logger;
	private final Path path;
	private final Map<UUID, HoconPlayerData> data;

	public HoconPlayerDatabase(@Nonnull final AdventureMMO mmo, @Nonnull final Path path) {
		this.logger = LoggerFactory.getLogger("AdventureMMO PlayerDatabase");
		this.path = path;
		this.data = new ConcurrentHashMap<UUID, HoconPlayerData>();

		if (!Files.exists(path)) {
			try { Files.createDirectory(path); }
			catch (final IOException exc) { this.logger.error("Failed to create main directory: {}", exc); }
		}

		mmo.getGame().getServer().getOnlinePlayers().forEach(p -> this.load(p.getUniqueId()));
	}

	@Override
	public synchronized HoconPlayerData get(@Nonnull final UUID uuid) {
		return this.data.get(uuid);
	}

	@Override
	public Optional<PlayerData> getOffline(@Nonnull final UUID uuid) {
		Path path = this.path.resolve(uuid.toString() + ".conf");
		if (!Files.exists(path)) { return Optional.empty(); }
		return Optional.of(new HoconPlayerData(path));
	}

	@Override
	public synchronized void load(@Nonnull final UUID uuid) {
		this.data.put(uuid, new HoconPlayerData(this.path.resolve(uuid.toString() + ".conf")));
	}

	@Override
	public synchronized void unload(@Nonnull final UUID uuid) {
		this.data.remove(uuid);
	}
}