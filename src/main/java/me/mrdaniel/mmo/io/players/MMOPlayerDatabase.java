package me.mrdaniel.mmo.io.players;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.skills.SkillSet;

public class MMOPlayerDatabase {
	
	private static MMOPlayerDatabase instance = null;
	
	private ConcurrentHashMap<UUID, MMOPlayer> players;
	private Path playersPath;
	
	public static MMOPlayerDatabase getInstance() {
		if (instance == null) instance = new MMOPlayerDatabase();
		return instance;
	}
	
	private MMOPlayerDatabase() {
		players = new ConcurrentHashMap<>();
	}
	
	/**
	 * Set the path location where the players are stored.
	 * 
	 * @param playersPath
	 * Folder where the players are all saved.
	 */
	public void setPlayersPath(Path playersPath) {
		this.playersPath = playersPath;
	}
	
	/**
	 * Get the player's information. If the player does not exist, it will create it.
	 * 
	 * @param UUID String
	 * The UUID of the player in String format
	 * @return MMOPlayer
	 */
	public MMOPlayer getOrCreatePlayer(String uuid) {
		return getOrCreatePlayer(UUID.fromString(uuid));
	}
	
	/**
	 * Get the player's information. If the player does not exist, it will create it.
	 * 
	 * @param UUID
	 * The UUID of the player.
	 * @return MMOPlayer
	 */
	public synchronized MMOPlayer getOrCreatePlayer(UUID uuid) {
		if (players.containsKey(uuid)) return players.get(uuid);
		
		MMOPlayer mmoplayer = new MMOPlayer(uuid.toString(), load(uuid));
		
		players.put(uuid, mmoplayer);
		return mmoplayer;
	}
	
	/**
	 * Saves all MMOPlayer's.
	 */
	public void saveAll() {
		for (MMOPlayer mmoplayer : players.values()) save(mmoplayer);
	}
	
	/**
	 * Unloads all MMOPlayer's.
	 */
	public void unloadAll() {
		for (MMOPlayer mmoplayer : players.values()) unload(UUID.fromString(mmoplayer.getUUID()));
	}
	
	/**
	 * Saves the player and unloads the player from the map.
	 * 
	 * @param UUID
	 * The UUID of the player to unload.
	 */
	public synchronized void unload(UUID playerUUID) {
		save(players.get(playerUUID));
		players.remove(playerUUID);
	}
	
	/**
	 * Saves the player and unloads the player from the map.
	 * 
	 * @param UUID String
	 * The UUID of the player to unload in String format.
	 */
	public synchronized void unload(String playerUUID) {
		unload(UUID.fromString(playerUUID));
	}
	
	/**
	 * Save the player.
	 * 
	 * @param MMOPlayer
	 * The player to save.
	 */
	public synchronized void save(MMOPlayer mmoplayer) {
		try {
			File file = getPlayerFile(UUID.fromString(mmoplayer.getUUID()));
			
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(mmoplayer.getSkills().serialize());
			oos.flush();
			oos.close();
			fos.close();
		}
		catch (Exception exc) {
			Main.getInstance().getLogger().error("Error while saving player file");
			exc.printStackTrace();
		}
	}
	private SkillSet load(UUID playerUUID) {
		try {
			File file = getPlayerFile(playerUUID);
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			int[][] sRaw = (int[][]) ois.readObject();

			ois.close();
			fis.close();
			
			return SkillSet.deserialize(sRaw);
		}
		catch (Exception exc) {
			Main.getInstance().getLogger().error("Error while loading player file");
			return SkillSet.getEmpty();
		}
	}
	private File getPlayerFile(UUID playerUUID) throws IOException {
		File folder = playersPath.toFile();
		
		if (!folder.exists()) folder.mkdir();
		
		File file = playersPath.resolve(playerUUID + ".dat").toFile();
		
		if (!file.exists()) file.createNewFile();
		return file;
	}
}