package me.mrdaniel.adventuremmo.io.tops;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.util.Tuple;

import com.google.common.collect.Maps;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class HoconTopDatabase extends MMOObject implements TopDatabase {

	private final ConfigurationLoader<CommentedConfigurationNode> loader;
	private final ConfigurationNode node;

	public HoconTopDatabase(@Nonnull final AdventureMMO mmo, @Nonnull final Path path) {
		super(mmo);

		this.loader = HoconConfigurationLoader.builder().setPath(path).build();

		if (!Files.exists(path)) {
			try { Files.createFile(path); }
			catch (final IOException exc) { super.getLogger().error("Failed to create playerdata file: {}", exc); }
		}
		this.node = this.load();
	}

	private ConfigurationNode load() {
		try { return this.loader.load(); }
		catch (final IOException exc) { super.getLogger().error("Failed to load playerdata file: {}", exc); return this.loader.createEmptyNode(); }
	}

	private void save() {
		try { this.loader.save(this.node); }
		catch (final IOException exc) { super.getLogger().error("Failed to save playerdata file: {}", exc); }
	}

	@Override
	@Nonnull
	public Map<Integer, Tuple<String, Integer>> getTop(@Nullable final SkillType skill) {
		ConfigurationNode node = this.node.getNode(skill == null ? "total" : skill.getId());
		Map<Integer, Tuple<String, Integer>> top = Maps.newHashMap();

		for (int i = 1; i <= 10; i++) {
			top.put(i, new Tuple<String, Integer>(node.getNode(String.valueOf(i), "name").getString(""), node.getNode(String.valueOf(i), "level").getInt(0)));
		}
		return top;
	}

	@Override
	public void update(@Nonnull final String player, @Nullable final SkillType skill, final int level) {
		this.update(this.node.getNode(skill == null ? "total" : skill.getId()), player, level);
		this.save();
	}

	private void update(@Nonnull final ConfigurationNode node, @Nonnull final String name, final int level) {
		for (int i = 1; i <= 10; i++) {
			if (name.equalsIgnoreCase(node.getNode(String.valueOf(i), "name").getString())) {
				node.getNode(String.valueOf(i), "level").setValue(level);
				for (int j = i; j > 1; j--) {
					if (level > node.getNode(String.valueOf(j-1), "level").getInt()) {
						node.getNode(String.valueOf(j), "name").setValue(node.getNode(String.valueOf(j-1), "name")).getString();
						node.getNode(String.valueOf(j), "level").setValue(node.getNode(String.valueOf(j-1), "level")).getInt();
						node.getNode(String.valueOf(j-1), "name").setValue(name);
						node.getNode(String.valueOf(j-1), "level").setValue(level);
					}
				}
				return;
			}
		}
		for (int i = 1; i <= 10; i++) {
			if (level >= node.getNode(String.valueOf(i), "level").getInt()) {
				for (int j = 9; j > i; j--) {
					node.getNode(String.valueOf(j+1), "name").setValue(node.getNode(String.valueOf(j), "name")).getString();
					node.getNode(String.valueOf(j+1), "level").setValue(node.getNode(String.valueOf(j), "level")).getInt();
				}
				node.getNode(String.valueOf(i), "name").setValue(name);
				node.getNode(String.valueOf(i), "level").setValue(level);
				return;
			}
		}
	}
}