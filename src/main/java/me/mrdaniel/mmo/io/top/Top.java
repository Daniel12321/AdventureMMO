package me.mrdaniel.mmo.io.top;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TreeMap;

import javax.annotation.Nonnull;

import org.spongepowered.api.util.Tuple;

import com.google.common.collect.Maps;

import me.mrdaniel.mmo.Main;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class Top {

	@Nonnull private final Path path;
	@Nonnull private final ConfigurationLoader<?> manager;
	@Nonnull private ConfigurationNode config;

	public Top(@Nonnull final Path path) {
		this.path = path;
		this.manager = HoconConfigurationLoader.builder().setPath(path).build();
		this.config = this.manager.createEmptyNode(ConfigurationOptions.defaults());

		if (!Files.exists(this.path)) {
			try { Files.createFile(this.path); }
			catch (final IOException exc) { Main.getInstance().getLogger().error("Failed to create Top file: {}", exc); }

			this.config.getNode("1", "name").setValue("x");
			this.config.getNode("1", "level").setValue(0);

			this.config.getNode("2", "name").setValue("x");
			this.config.getNode("2", "level").setValue(0);

			this.config.getNode("3", "name").setValue("x");
			this.config.getNode("3", "level").setValue(0);

			this.config.getNode("4", "name").setValue("x");
			this.config.getNode("4", "level").setValue(0);

			this.config.getNode("5", "name").setValue("x");
			this.config.getNode("5", "level").setValue(0);

			this.config.getNode("6", "name").setValue("x");
			this.config.getNode("6", "level").setValue(0);

			this.config.getNode("7", "name").setValue("x");
			this.config.getNode("7", "level").setValue(0);

			this.config.getNode("8", "name").setValue("x");
			this.config.getNode("8", "level").setValue(0);

			this.config.getNode("9", "name").setValue("x");
			this.config.getNode("9", "level").setValue(0);

			this.config.getNode("10", "name").setValue("x");
			this.config.getNode("10", "level").setValue(0);

		    this.save();
		}
		else {
			try { this.config = this.manager.load(); }
			catch (final IOException exc) { Main.getInstance().getLogger().error("Failed to load Top file: {}", exc); }
		}
	}

	public void update(@Nonnull final String name, final int level) {
		for (int i = 1; i <= 10; i++) {
			if (this.config.getNode(i, "name").getString().equalsIgnoreCase(name)) {
				if (level < this.config.getNode(i, "level").getInt()) {
					this.config.getNode(i, "level").setValue(level-1);
					for (int j = i; j <= 9; j++) { switchPlaces(j, j+1); }
					update(name, level);
					return;
				}
				this.config.getNode(i, "level").setValue(level);
				jLoop: for (int j = i; j >= 2; j--) {
					if (j == 1) { break jLoop; }
					if (level >= this.config.getNode(j-1, "level").getInt()) { switchPlaces(j, j-1); }
				}
				this.save();
				return;
			}
		}

		if (level < this.config.getNode("10", "level").getInt()) { return; }
		this.config.getNode("10", "name").setValue(name);
		this.config.getNode("10", "level").setValue(level);
		for (int i = 10; i >= 2; i--) {
			if (level >= this.config.getNode(i-1, "level").getInt()) {
				switchPlaces(i, i-1);
			}
			else { break; }
		}
		this.save();
	}

	private void switchPlaces(final int first, final int second) {
		Tuple<String, Integer> temp = new Tuple<String, Integer>(this.config.getNode(first, "name").getString(), this.config.getNode(first, "level").getInt());

		this.config.getNode(first, "name").setValue(this.config.getNode(second, "name").getString());
		this.config.getNode(first, "level").setValue(this.config.getNode(second, "level").getInt());

		this.config.getNode(second, "name").setValue(temp.getFirst());
		this.config.getNode(second, "level").setValue(temp.getSecond());
	}

	public TreeMap<Integer, Tuple<String, Integer>> getTop() {
		TreeMap<Integer, Tuple<String, Integer>> top = Maps.newTreeMap();

		this.config.getChildrenMap().forEach((key, node) -> top.put((int)key, new Tuple<String, Integer>(node.getNode("name").getString(), node.getNode("level").getInt())));

		return top;
	}

	private void save() {
		try { this.manager.save(this.config); }
		catch (final IOException exc) { Main.getInstance().getLogger().error("Failed to save Top: {}", exc); }
	}
}