package me.mrdaniel.mmo.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import org.spongepowered.api.text.Text;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.Ability;
import me.mrdaniel.mmo.enums.Setting;
import me.mrdaniel.mmo.utils.TextUtils;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Singleton
public class Config {
	
	@Nonnull public final Path path;
	@Nonnull public final ConfigurationLoader<CommentedConfigurationNode> manager;
	@Nonnull public CommentedConfigurationNode config;

	@Nonnull public Text PREFIX;
	public long RECHARGE_MILLIS;
	public int SCOREBOARD_ACTIVE_SECONDS;
	public boolean ECONENABLED;
	public double START;
	public double INCREMENT;
	@Nonnull public final ArrayList<Ability> BLOCKEDABILITYIES;
	@Nonnull public final HashMap<Setting, Boolean> FORCEDSETTINGS;

	public Config(@Nonnull final Path path) {
		Main.getInstance().getLogger().info("Loading Config File...");

		this.path = path;
		this.manager = HoconConfigurationLoader.builder().setPath(path).build();
		this.config = this.manager.createEmptyNode(ConfigurationOptions.defaults());

		if (!Files.exists(this.path)) {
			try { Files.createFile(this.path); }
			catch (final IOException exc) { Main.getInstance().getLogger().error("Failed to create config file: {}", exc); }

			this.config.getNode("prefix").setValue("&7[&9MMO&7]");
			this.config.getNode("recharge_millis").setValue(250000);
			this.config.getNode("scoreboard_active_seconds").setValue(12);

			this.config.getNode("economy", "enabled").setValue(false);
			this.config.getNode("economy", "startAmount").setComment("The money a player gets for gaining 1 level is the level * incrementAmount + startAmount");
			this.config.getNode("economy", "startAmount").setValue(15.0);
			this.config.getNode("economy", "incrementAmount").setValue(0.5);

			this.config.getNode("personal_settings").setComment("Here you can force all players to have certain personal settings");
			this.config.getNode("personal_settings", "sounds", "force").setValue(false);
			this.config.getNode("personal_settings", "sounds", "forced_value").setValue(true);
			this.config.getNode("personal_settings", "effects", "force").setValue(false);
			this.config.getNode("personal_settings", "effects", "forced_value").setValue(true);
			this.config.getNode("personal_settings", "scoreboard", "force").setValue(false);
			this.config.getNode("personal_settings", "scoreboard", "forced_value").setValue(false);
			this.config.getNode("personal_settings", "scoreboardpermanent", "force").setValue(false);
			this.config.getNode("personal_settings", "scoreboardpermanent", "forced_value").setValue(false);

			this.config.getNode("commands", "/mining").setValue(true);
			this.config.getNode("commands", "/excavation").setValue(true);
			this.config.getNode("commands", "/woodcutting").setValue(true);
			this.config.getNode("commands", "/fishing").setValue(true);
			this.config.getNode("commands", "/farming").setValue(true);
			this.config.getNode("commands", "/acrobatics").setValue(true);
			this.config.getNode("commands", "/taming").setValue(true);
			this.config.getNode("commands", "/salvage").setValue(true);
			this.config.getNode("commands", "/repair").setValue(true);
			this.config.getNode("commands", "/swords").setValue(true);
			this.config.getNode("commands", "/axes").setValue(true);
			this.config.getNode("commands", "/unarmed").setValue(true);
			this.config.getNode("commands", "/archery").setValue(true);

			this.save();
		}
		else {
			try { this.config = this.manager.load(); }
			catch (final IOException exc) { Main.getInstance().getLogger().error("Failed to load config file: {}", exc); }
		}

	    this.PREFIX = TextUtils.toText(this.config.getNode("prefix").getString() + " ");
	    this.RECHARGE_MILLIS = this.config.getNode("recharge_millis").getLong();
	    this.SCOREBOARD_ACTIVE_SECONDS = this.config.getNode("scoreboard_active_seconds").getInt();
	    this.ECONENABLED = this.config.getNode("economy", "enabled").getBoolean();
	    this.START = this.config.getNode("economy", "startAmount").getDouble();
	    this.INCREMENT = this.config.getNode("economy", "incrementAmount").getDouble();
	    this.BLOCKEDABILITYIES = new ArrayList<Ability>();
	    this.FORCEDSETTINGS = new HashMap<Setting, Boolean>();

	    this.config.getNode("personal_settings").getChildrenMap().forEach((id, value) -> {
	    	if (value.getNode("force").getBoolean()) {
	    		this.FORCEDSETTINGS.put(Setting.match((String) id), value.getNode("forced_value").getBoolean());
	    	}
	    });

	    Main.getInstance().getLogger().info("Done Loading Config File!");
	}

	public void disableEcon() {
		this.config.getNode("economy", "enabled").setValue(false);
		this.save();
		this.ECONENABLED = false;
	}

	private void save() {
		try { this.manager.save(this.config); }
		catch (final IOException exc) { Main.getInstance().getLogger().error("Failed to save config file: {}", exc); }
	}
}