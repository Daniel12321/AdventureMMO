package me.mrdaniel.mmo.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import me.mrdaniel.mmo.Main;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class BlackList {
	
	public static File file = new File("config/mmo/blacklist.conf");
	public static ConfigurationLoader<CommentedConfigurationNode> manager = HoconConfigurationLoader.builder().setFile(file).build();
	public static CommentedConfigurationNode config = manager.createEmptyNode(ConfigurationOptions.defaults());
	
	public static ArrayList<String> blacklist = new ArrayList<String>();
	
	public static void setup() {
		blacklist.clear();
		Main.getInstance().getLogger().info("Loading Blacklist File");
		try {
			if (!file.exists()) {
				file.createNewFile();
				
				config.getNode("blocked", "minecraft:wooden_axe").setValue(true);
				config.getNode("blocked", "pixelmon:ThunderstoneAxe").setValue(true);
				
				manager.save(config);
			}
			config = manager.load();
			
			for (CommentedConfigurationNode idNode : config.getNode("blocked").getChildrenMap().values()) {
				String id = idNode.getKey().toString().toLowerCase();
				if (idNode.getBoolean()) blacklist.add(id);
			}
			Main.getInstance().getLogger().info("Loaded " + blacklist.size() + " blocked items");
		}
		catch (IOException e) {
			Main.getInstance().getLogger().error("Error while loading Blacklist file");
			e.printStackTrace();
			return;
		}
	}
}