package me.mrdaniel.adventuremmo.managers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class TopManager extends MMOObject {

	private final ConfigurationLoader<CommentedConfigurationNode> loader;
	private final CommentedConfigurationNode node;

	private final Top total;
	private final Map<SkillType, Top> skills;

	public TopManager(@Nonnull final AdventureMMO mmo, @Nonnull final Path path) {
		super(mmo);

		this.loader = HoconConfigurationLoader.builder().setPath(path).build();

		if (!Files.exists(path)) {
			try { Files.createFile(path); }
			catch (final IOException exc) { super.getLogger().error("Failed to create playerdata file: {}", exc); }
		}
		this.node = this.load();

		this.total = new Top(this.node.getNode("total"));
		this.skills = Maps.newHashMap();
		SkillTypes.getAll().forEach(type -> this.skills.put(type, new Top(this.node.getNode(type.getId()))));
	}

	private CommentedConfigurationNode load() {
		try { return this.loader.load(); }
		catch (final IOException exc) { super.getLogger().error("Failed to load playerdata file: {}", exc); return this.loader.createEmptyNode(); }
	}

	private void save() {
		try { this.loader.save(this.node); }
		catch (final IOException exc) { super.getLogger().error("Failed to save playerdata file: {}", exc); }
	}

	public void update(@Nullable final SkillType type, @Nonnull final String name, final int level) {
		if (type == null) { this.total.update(name, level); }
		else this.skills.get(type).update(name, level);

		this.save();
	}

	public Top getTop(@Nullable final SkillType type) {
		if (type == null) { return this.total; }
		else return this.skills.get(type);
	}
}