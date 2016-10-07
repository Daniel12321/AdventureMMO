package me.mrdaniel.mmo.io;

import java.io.File;
import java.io.IOException;

import org.spongepowered.api.text.Text;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.utils.TextUtils;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class Config {
	
	private static Config instance = null;
	public static Config getInstance() {
		if (instance == null) { instance = new Config(); }
		return instance;
	}
	
	public File file;
	public ConfigurationLoader<CommentedConfigurationNode> manager;
	public CommentedConfigurationNode config;
	
	private Config() {
		file = Main.getInstance().getPath().resolve("config.conf").toFile();
		manager = HoconConfigurationLoader.builder().setFile(file).build();
		config = manager.createEmptyNode(ConfigurationOptions.defaults());
	}
	
	public void setup() {
		Main.getInstance().getLogger().info("Loading Config File");
		try {
			if (!file.exists()) {
				file.createNewFile();
				
				config.getNode("prefix").setValue("&7[&9MMO&7]");
				config.getNode("recharge_millis").setValue(200000);
				config.getNode("scoreboard_active_seconds").setValue(12);
				
				config.getNode("economy", "enabled").setValue(false);
				config.getNode("economy", "startAmount").setComment("The money a player gets for gaining 1 level is the level * incrementAmount + startAmount");
				config.getNode("economy", "startAmount").setValue(15.0);
				config.getNode("economy", "incrementAmount").setValue(0.5);
				
				config.getNode("commands", "/mining").setValue(true);
				config.getNode("commands", "/excavation").setValue(true);
				config.getNode("commands", "/woodcutting").setValue(true);
				config.getNode("commands", "/fishing").setValue(true);
				config.getNode("commands", "/farming").setValue(true);
				config.getNode("commands", "/acrobatics").setValue(true);
				config.getNode("commands", "/taming").setValue(true);
				config.getNode("commands", "/salvage").setValue(true);
				config.getNode("commands", "/repair").setValue(true);
				
		        manager.save(config);
			}
	        config = manager.load();
	        
	        if (!config.getChildrenMap().values().contains(config.getNode("commands"))) {
	        	file.delete();
	        	instance = new Config();
	        	instance.setup();
	        	return;
	        }
	        if (!config.getChildrenMap().values().contains(config.getNode("scoreboard_active_seconds"))) {
	        	try {
	        		config.getNode("scoreboard_active_seconds").setValue(12);
	        		manager.save(config);
	        	}
	        	catch (IOException exc) {
	    			Main.getInstance().getLogger().error("Error while saving Config file");
	    			exc.printStackTrace();
	    			return;
	    		}
	        }
	        PREFIX = TextUtils.color(config.getNode("prefix").getString() + " ");
	        RECHARGE_MILLIS = config.getNode("recharge_millis").getLong();
	        SCOREBOARD_ACTIVE_SECONDS = config.getNode("scoreboard_active_seconds").getInt();
	        ECONENABLED = config.getNode("economy", "enabled").getBoolean();
	        START = config.getNode("economy", "startAmount").getDouble();
	        INCREMENT = config.getNode("economy", "incrementAmount").getDouble();
		}
		catch (IOException exc) {
			Main.getInstance().getLogger().error("Error while loading Config file");
			exc.printStackTrace();
			return;
		}
		Main.getInstance().getLogger().info("Done Loading Config File");
	}
	public void disableEcon() {
		try {
			config.getNode("economy", "enabled").setValue(false);
			manager.save(config);
			ECONENABLED = false;
		}
		catch (IOException exc) {
			Main.getInstance().getLogger().error("Error while saving Config file");
			exc.printStackTrace();
			return;
		}
	}
	public Text PREFIX;
	public long RECHARGE_MILLIS;
	public int SCOREBOARD_ACTIVE_SECONDS;
	public boolean ECONENABLED;
	public double START;
	public double INCREMENT;
}