package me.mrdaniel.mmo.io.players;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.skills.SkillSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MMOPlayerDatabase {

    private static MMOPlayerDatabase instance = new MMOPlayerDatabase();;

    public static MMOPlayerDatabase getInstance() {
        return instance;
    }

    private ConcurrentHashMap<UUID, MMOPlayer> players = new ConcurrentHashMap<>();
    private Path playersPath;

    private MMOPlayerDatabase() {
    }

    /**
     * @param playersPath Folder where the players are all saved.
     */
    public void setPlayersPath(Path playersPath) {
        this.playersPath = playersPath;
    }

    public MMOPlayer getOrCreatePlayer(String uuid) {
        return getOrCreatePlayer(UUID.fromString(uuid));
    }

    /**
     * Get the player's information. If the player does not exist, it will create it.
     * @param uuid The UUID of the player
     * @return
     */
    public synchronized MMOPlayer getOrCreatePlayer(UUID uuid){
        if(players.containsKey(uuid))
            return players.get(uuid);

        MMOPlayer player = new MMOPlayer(uuid.toString(), load(uuid));

        players.put(uuid, player);

        return player;
    }

    public void saveAll() {
        for (MMOPlayer mmop : players.values())
            save(mmop);
    }

    public void unloadAll(){
        for (MMOPlayer mmop : players.values())
            unload(UUID.fromString(mmop.getUUID()));
    }

    /**
     * Unload the player from the map.
     * @param playerUUID The UUID of the player to unload.
     */
    public synchronized void unload(UUID playerUUID) {
        players.remove(playerUUID);
    }

    /**
     * Save the player.
     * @param mmop The player to save.
     */
    public synchronized void save(MMOPlayer mmop) {
        try {
            File file = getPlayerFile(UUID.fromString(mmop.getUUID()));

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(mmop.getSkills().serialize());
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

        if (!folder.exists())
            folder.mkdir();

        File file = playersPath.resolve(playerUUID +".dat").toFile();

        if(!file.exists())
            file.createNewFile();

        return file;
    }

}