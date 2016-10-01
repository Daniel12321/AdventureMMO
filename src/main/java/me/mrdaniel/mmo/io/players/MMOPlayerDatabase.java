package me.mrdaniel.mmo.io.players;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.skills.SkillSet;

public class MMOPlayerDatabase {
	
	private static MMOPlayerDatabase instance = null;
	public static MMOPlayerDatabase getInstance() { if (instance == null) { instance = new MMOPlayerDatabase(); } return instance; }
	
	private MMOPlayerDatabase() {}
	
	public ArrayList<MMOPlayer> players = new ArrayList<MMOPlayer>();
	
	public void setup() {
		
		File folder = new File("config/mmo/players");
		if (!folder.exists()) folder.mkdir();
	}
	public MMOPlayer getOrCreate(String uuid) {
		for (MMOPlayer mmop : players) {
			if (mmop.getUUID().equalsIgnoreCase(uuid)) { return mmop; }
		}
		MMOPlayer mmop = new MMOPlayer(uuid, read(new File("config/mmo/players/" + uuid + ".dat")));
		players.add(mmop);
		return mmop;
	}
	public void writeAll() {
		for (MMOPlayer mmop : players) { store(mmop); }
	}
	public void unload(MMOPlayer mmop) {
		store(mmop);
		players.remove(mmop);
	}
	public void store(MMOPlayer mmop) {
		try {
			File file = new File("config/mmo/players/" + mmop.getUUID() + ".dat");
			if (!file.exists()) { file.createNewFile(); }
			
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
	private SkillSet read(File file) {
		if (!file.exists()) { return SkillSet.getEmpty(); }
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			int[][] sRaw = (int[][]) ois.readObject();
			
			ois.close();
			fis.close();
			
			return SkillSet.deserialize(sRaw);
		}
		catch (Exception exc) {
			Main.getInstance().getLogger().error("Error while loading player file");
			exc.printStackTrace();
			return SkillSet.getEmpty();
		}
	}
}