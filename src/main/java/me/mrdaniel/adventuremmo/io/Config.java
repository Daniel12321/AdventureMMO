package me.mrdaniel.adventuremmo.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import me.mrdaniel.adventuremmo.AdventureMMO;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class Config {

	private final ConfigurationLoader<CommentedConfigurationNode> loader;
	private final CommentedConfigurationNode node;

	public Config(@Nonnull final AdventureMMO mmo, @Nonnull final Path path) {
		this.loader = HoconConfigurationLoader.builder().setPath(path).build();

		if (!Files.exists(path)) {
			try { mmo.getContainer().getAsset("config.conf").get().copyToFile(path); }
			catch (final IOException exc) { mmo.getLogger().error("Failed to save config asset: {}", exc); }
		}
		this.node = this.load(mmo.getLogger());
	}

	private CommentedConfigurationNode load(@Nonnull final Logger logger) {
		try { return this.loader.load(); }
		catch (final IOException exc) { logger.error("Failed to load config file: {}", exc); return this.loader.createEmptyNode(); }
	}

	@Nonnull
	public CommentedConfigurationNode getNode(@Nonnull final Object... keys) {
		return this.node.getNode(keys);
	}
}