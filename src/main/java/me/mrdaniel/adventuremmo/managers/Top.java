package me.mrdaniel.adventuremmo.managers;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.util.Tuple;

import com.google.common.collect.Maps;

import ninja.leaping.configurate.ConfigurationNode;

public class Top {

	private final ConfigurationNode node;

	public Top(@Nonnull final ConfigurationNode node) {
		this.node = node;
	}

	public void update(@Nonnull final String name, final int level) {
		for (int i = 1; i <= 10; i++) {
			if (name.equalsIgnoreCase(this.node.getNode(String.valueOf(i), "name").getString())) {
				this.node.getNode(String.valueOf(i), "level").setValue(level);
				for (int j = i; j > 1; j--) {
					if (level > this.node.getNode(String.valueOf(j-1), "level").getInt()) {
						this.node.getNode(String.valueOf(j), "name").setValue(this.node.getNode(String.valueOf(j-1), "name")).getString();
						this.node.getNode(String.valueOf(j), "level").setValue(this.node.getNode(String.valueOf(j-1), "level")).getInt();
						this.node.getNode(String.valueOf(j-1), "name").setValue(name);
						this.node.getNode(String.valueOf(j-1), "level").setValue(level);
					}
				}
				return;
			}
		}
		for (int i = 1; i <= 10; i++) {
			if (level >= this.node.getNode(String.valueOf(i), "level").getInt()) {
				for (int j = 9; j > i; j--) {
					this.node.getNode(String.valueOf(j+1), "name").setValue(this.node.getNode(String.valueOf(j), "name")).getString();
					this.node.getNode(String.valueOf(j+1), "level").setValue(this.node.getNode(String.valueOf(j), "level")).getInt();
				}
				this.node.getNode(String.valueOf(i), "name").setValue(name);
				this.node.getNode(String.valueOf(i), "level").setValue(level);
				return;
			}
		}
	}

	@Nonnull
	public Map<Integer, Tuple<String, Integer>> getTop() {
		Map<Integer, Tuple<String, Integer>> top = Maps.newHashMap();

		for (int i = 1; i <= 10; i++) {
			top.put(i, new Tuple<String, Integer>(Optional.ofNullable(this.node.getNode(String.valueOf(i), "name").getString()).orElse(""), this.node.getNode(String.valueOf(i), "level").getInt()));
		}
		return top;
	}
}