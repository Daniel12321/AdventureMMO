package me.mrdaniel.adventuremmo.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.Nonnull;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class Config extends MMOObject {

	private final ConfigurationLoader<CommentedConfigurationNode> loader;
	private final CommentedConfigurationNode node;

	public Config(@Nonnull final AdventureMMO mmo, @Nonnull final Path path) {
		super(mmo);

		this.loader = HoconConfigurationLoader.builder().setPath(path).build();

		if (!Files.exists(path)) {
			try { super.getContainer().getAsset("config.conf").get().copyToFile(path); }
			catch (final IOException exc) { super.getLogger().error("Failed to save config asset: {}", exc); }
		}
		this.node = this.load();
	}

	private CommentedConfigurationNode load() {
		try { return this.loader.load(); }
		catch (final IOException exc) { super.getLogger().error("Failed to load config file: {}", exc); return this.loader.createEmptyNode(); }
	}

	@Nonnull
	public CommentedConfigurationNode getNode(@Nonnull final Object... keys) {
		return this.node.getNode(keys);
	}
}