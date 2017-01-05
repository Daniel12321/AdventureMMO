package me.mrdaniel.mmo.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import me.mrdaniel.mmo.Main;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Singleton
public class BlackList {

	@Nonnull public final Path path;
	@Nonnull public final ConfigurationLoader<CommentedConfigurationNode> manager;
	@Nonnull public CommentedConfigurationNode config;

	@Nonnull public final ArrayList<String> blacklist = new ArrayList<String>();

	public BlackList(@Nonnull final Path path) {
		Main.getInstance().getLogger().info("Loading Blacklist File...");

		this.path = path;
		this.manager = HoconConfigurationLoader.builder().setPath(path).build();
		this.config = this.manager.createEmptyNode(ConfigurationOptions.defaults());

		if (!Files.exists(path)) {
			try { Files.createFile(path); }
			catch (final IOException exc) { Main.getInstance().getLogger().error("Failed to create blacklist file: {}", exc); }
				
			this.config.getNode("blocked", "minecraft:wooden_axe").setValue(true);
			this.config.getNode("blocked", "pixelmon:ThunderstoneAxe").setValue(false);
				
			this.save();
		}
		else {
			try { this.config = this.manager.load(); }
			catch (final IOException exc) { Main.getInstance().getLogger().error("Failed to load blacklist config: {}", exc); }
		}

		this.config.getNode("blocked").getChildrenMap().forEach((id, value) -> { if (value.getBoolean()) { this.blacklist.add((String) id); } });

		Main.getInstance().getLogger().info("Loaded " + this.blacklist.size() + " blocked items");
	}

	private void save() {
		try { this.manager.save(this.config); }
		catch (final IOException exc) { Main.getInstance().getLogger().error("Failed to save blacklist file: {}", exc); }
	}

	public boolean contains(@Nonnull final String id) {
		return (this.blacklist.contains(id));
	}
}