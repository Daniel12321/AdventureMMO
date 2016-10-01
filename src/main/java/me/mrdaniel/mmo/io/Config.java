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
	public static File file = new File("config/mmo/config.conf");
	public static ConfigurationLoader<CommentedConfigurationNode> manager = HoconConfigurationLoader.builder().setFile(file).build();
	public static CommentedConfigurationNode config = manager.createEmptyNode(ConfigurationOptions.defaults());
	
	public static void setup() {
		Main.getInstance().getLogger().info("Loading Config File");
		try {
			if (!file.exists()) {
				file.createNewFile();
				
				config.getNode("prefix").setValue("&7[&9MMO&7]");
				config.getNode("recharge_millis").setValue(200000);
				
		        manager.save(config);
			}
	        config = manager.load();
		}
		catch (IOException e) {
			Main.getInstance().getLogger().error("Error while loading Config file");
			e.printStackTrace();
			return;
		}
		Main.getInstance().getLogger().info("Done Loading Config File");
	}
	public static Text PREFIX() { return TextUtils.color(config.getNode("prefix").getString() + " "); }
	public static long RECHARGE_MILLIS() { return config.getNode("recharge_millis").getLong(); }
}