package me.mrdaniel.mmo.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.spongepowered.api.text.Text;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.Ability;
import me.mrdaniel.mmo.enums.Setting;
import me.mrdaniel.mmo.utils.TextUtils;
import ninja.leaping.configurate.ConfigurationNode;
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
				config.getNode("recharge_millis").setValue(250000);
				config.getNode("scoreboard_active_seconds").setValue(12);

				config.getNode("economy", "enabled").setValue(false);
				config.getNode("economy", "startAmount").setComment("The money a player gets for gaining 1 level is the level * incrementAmount + startAmount");
				config.getNode("economy", "startAmount").setValue(15.0);
				config.getNode("economy", "incrementAmount").setValue(0.5);

				config.getNode("personal_settings").setComment("Here you can force all players to have certain personal settings");
				config.getNode("personal_settings", "sounds", "force").setValue(false);
				config.getNode("personal_settings", "sounds", "forced_value").setValue(true);
				config.getNode("personal_settings", "effects", "force").setValue(false);
				config.getNode("personal_settings", "effects", "forced_value").setValue(true);
				config.getNode("personal_settings", "scoreboard", "force").setValue(false);
				config.getNode("personal_settings", "scoreboard", "forced_value").setValue(false);
				config.getNode("personal_settings", "scoreboardpermanent", "force").setValue(false);
				config.getNode("personal_settings", "scoreboardpermanent", "forced_value").setValue(false);

				config.getNode("commands", "/mining").setValue(true);
				config.getNode("commands", "/excavation").setValue(true);
				config.getNode("commands", "/woodcutting").setValue(true);
				config.getNode("commands", "/fishing").setValue(true);
				config.getNode("commands", "/farming").setValue(true);
				config.getNode("commands", "/acrobatics").setValue(true);
				config.getNode("commands", "/taming").setValue(true);
				config.getNode("commands", "/salvage").setValue(true);
				config.getNode("commands", "/repair").setValue(true);
				config.getNode("commands", "/swords").setValue(true);
				config.getNode("commands", "/axes").setValue(true);
				config.getNode("commands", "/unarmed").setValue(true);
				config.getNode("commands", "/archery").setValue(true);

				config.getNode("abilities", "superbreaker", "blocked").setValue(false);
				config.getNode("abilities", "treeveller", "blocked").setValue(false);
				config.getNode("abilities", "gigadrillbreaker", "blocked").setValue(false);
				config.getNode("abilities", "greenterra", "blocked").setValue(false);
				config.getNode("abilities", "miningdoubledrop", "blocked").setValue(false);
				config.getNode("abilities", "farmingdoubledrop", "blocked").setValue(false);
				config.getNode("abilities", "excavationdoubledrop", "blocked").setValue(false);
				config.getNode("abilities", "woodcuttingdoubledrop", "blocked").setValue(false);
				config.getNode("abilities", "roll", "blocked").setValue(false);
				config.getNode("abilities", "dodge", "blocked").setValue(false);
				config.getNode("abilities", "salvage", "blocked").setValue(false);
				config.getNode("abilities", "repair", "blocked").setValue(false);
				config.getNode("abilities", "treasurehunt", "blocked").setValue(false);
				config.getNode("abilities", "watertreasure", "blocked").setValue(false);
				config.getNode("abilities", "summonwolf", "blocked").setValue(false);
				config.getNode("abilities", "summonocelot", "blocked").setValue(false);
				config.getNode("abilities", "summonhorse", "blocked").setValue(false);

				config.getNode("abilities", "slaughter", "blocked").setValue(false);
				config.getNode("abilities", "bloodshed", "blocked").setValue(false);
				config.getNode("abilities", "saitama_punch", "blocked").setValue(false);
				config.getNode("abilities", "decapitation", "blocked").setValue(false);
				config.getNode("abilities", "arrowrain", "blocked").setValue(false);
				config.getNode("abilities", "disarm", "blocked").setValue(false);

		        manager.save(config);
			}
	        config = manager.load();

	        if (!config.getNode("abilities").getChildrenMap().values().contains(config.getNode("abilities", "disarm"))) {
	        	file.delete();
	        	instance = new Config();
	        	instance.setup();
	        	return;
	        }

	        PREFIX = TextUtils.color(config.getNode("prefix").getString() + " ");
	        RECHARGE_MILLIS = config.getNode("recharge_millis").getLong();
	        SCOREBOARD_ACTIVE_SECONDS = config.getNode("scoreboard_active_seconds").getInt();
	        ECONENABLED = config.getNode("economy", "enabled").getBoolean();
	        START = config.getNode("economy", "startAmount").getDouble();
	        INCREMENT = config.getNode("economy", "incrementAmount").getDouble();
	        BLOCKEDABILITYIES = new ArrayList<Ability>();
	        FORCEDSETTINGS = new HashMap<Setting, Boolean>();

	        for (ConfigurationNode abilityNode : config.getNode("abilities").getChildrenMap().values()) {
	        	if (abilityNode.getNode("blocked").getBoolean()) {
	        		abilityLoop: for (Ability ab : Ability.values()) {
	        			if (ab.name.replaceAll(" ", "").equalsIgnoreCase(abilityNode.getKey().toString())) {
	        				BLOCKEDABILITYIES.add(ab); break abilityLoop;
	        			}
	        		}
	        	}
	        }
	        for (ConfigurationNode settingNode : config.getNode("personal_settings").getChildrenMap().values()) {
	        	if (settingNode.getNode("force").getBoolean()) {
	        		Setting s = Setting.match(settingNode.getKey().toString());
	        		boolean value = settingNode.getNode("forced_value").getBoolean();
	        		FORCEDSETTINGS.put(s, value);
	        	}
	        }
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
	public ArrayList<Ability> BLOCKEDABILITYIES;
	public HashMap<Setting, Boolean> FORCEDSETTINGS;
}