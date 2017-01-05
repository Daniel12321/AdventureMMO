package me.mrdaniel.mmo.io.players;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.skills.SkillSet;

@Singleton
public class MMOPlayerDatabase {

	@Nonnull private final ConcurrentHashMap<UUID, MMOPlayer> players;
	@Nonnull private Path dirPath;

	/**
	 * Will still work, but {@code Main.getInstance().getMMOPlayerDatabase()} is advised
	 */
	@Deprecated @Nonnull public static MMOPlayerDatabase getInstance() { return Main.getInstance().getMMOPlayerDatabase(); }

	public MMOPlayerDatabase(@Nonnull final Path directory) {
		if (!Files.exists(directory)) {
			try { Files.createDirectory(directory); }
			catch (final IOException exc) { Main.getInstance().getLogger().error("Failed to create MMOPlayerDatabase root directory: {}", exc); }
		}
		this.players = new ConcurrentHashMap<UUID, MMOPlayer>();
	}

	/**
	 * Get the player's information. If the player does not exist, it will create it.
	 * 
	 * @param {@link UUID} of the player.
	 * @return {@link MMOPlayer}
	 */
	public synchronized MMOPlayer getOrCreatePlayer(@Nonnull final UUID uuid) {
		if (this.players.containsKey(uuid)) return this.players.get(uuid);

		MMOPlayer mmoplayer = load(uuid);

		this.players.put(uuid, mmoplayer);
		return mmoplayer;
	}

	/**
	 * Saves all {@link MMOPlayer}'s.
	 */
	public void saveAll() {
		this.players.values().forEach(this::save);
	}

	/**
	 * Unloads all {@link MMOPlayer}'s.
	 */
	public void unloadAll() {
		for (MMOPlayer mmoplayer : this.players.values()) unload(UUID.fromString(mmoplayer.getUUID()));
	}

	/**
	 * Saves the player's information and unloads it from the map.
	 * 
	 * @param {@link UUID} of the player to unload.
	 */
	public synchronized void unload(@Nonnull final UUID playerUUID) {
		save(this.players.get(playerUUID));
		this.players.remove(playerUUID);
	}

	/**
	 * Saves the player's information.
	 * 
	 * @param {@link MMOPlayer} to save.
	 */
	public synchronized void save(@Nonnull final MMOPlayer mmoplayer) {
		try {
			File file = this.dirPath.resolve(mmoplayer.getUUID() + ".dat").toFile();
			if (!file.exists()) { file.createNewFile(); }

			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			MMOPlayerData data = new MMOPlayerData(mmoplayer.getSkills().serialize(), mmoplayer.getSettings().serialize());

			oos.writeObject(data);
			oos.flush();
			oos.close();
			fos.close();
		}
		catch (Exception exc) {
			Main.getInstance().getLogger().error("Error while saving player file: {}", exc);
		}
	}

	/**
	 * Loads the player's information
	 * 
	 * @param {@link UUID} of the player 
	 * @return the player's {@link MMOPlayer}
	 */
	private MMOPlayer load(@Nonnull final UUID playerUUID) {
		try {
			File file = this.dirPath.resolve(playerUUID.toString() + ".dat").toFile();
			if (!file.exists()) { return new MMOPlayer(playerUUID.toString(), new SkillSet(), new Settings()); }
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);

			Object raw = ois.readObject();

			ois.close();
			fis.close();

			if (raw instanceof int[][]) { return new MMOPlayer(playerUUID.toString(), SkillSet.deserialize((int[][])raw), new Settings()); }
			MMOPlayerData data = (MMOPlayerData) raw;
			return new MMOPlayer(playerUUID.toString(), SkillSet.deserialize(data.skills), new Settings(data.settings));
		}
		catch (Exception exc) {
			Main.getInstance().getLogger().error("Error while loading player file: {}", exc);
			return new MMOPlayer(playerUUID.toString(), new SkillSet(), new Settings());
		}
	}
}