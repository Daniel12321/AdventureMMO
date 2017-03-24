package me.mrdaniel.adventuremmo.io.playerdata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class HoconPlayerData implements PlayerData {

	private static final Logger LOGGER = LoggerFactory.getLogger("AdventureMMO Playerdata");

	private final ConfigurationLoader<CommentedConfigurationNode> loader;
	private final CommentedConfigurationNode node;

	public HoconPlayerData(@Nonnull final Path path) {
		this.loader = HoconConfigurationLoader.builder().setPath(path).build();

		if (!Files.exists(path)) {
			try { Files.createFile(path); }
			catch (final IOException exc) { LOGGER.error("Failed to create playerdata file: {}", exc); }
		}
		this.node = this.load();
	}

	private CommentedConfigurationNode load() {
		try { return this.loader.load(); }
		catch (final IOException exc) { LOGGER.error("Failed to load playerdata file: {}", exc); return this.loader.createEmptyNode(); }
	}

	private void save() {
		try { this.loader.save(this.node); }
		catch (final IOException exc) { LOGGER.error("Failed to save playerdata file: {}", exc); }
	}

	public int getLevel(@Nonnull final SkillType skill) { return this.node.getNode(skill.getId(), "level").getInt(); }
	public void setLevel(@Nonnull final SkillType skill, final int level) { node.getNode(skill.getId(), "level").setValue(level); this.save(); }
	public int getExp(@Nonnull final SkillType skill) { return this.node.getNode(skill.getId(), "exp").getInt(); }
	public void setExp(@Nonnull final SkillType skill, final int exp) { this.node.getNode(skill.getId(), "exp").setValue(exp); this.save(); }
}