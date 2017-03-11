package me.mrdaniel.adventuremmo.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.Nonnull;

import org.spongepowered.api.text.Text;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.utils.TextUtils;
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
	@Nonnull private String getPrefix() { return this.node.getNode("messages", "prefix").getString(); }

	@Nonnull public Text getLevelUpText(@Nonnull final String skill, @Nonnull final int level) { return TextUtils.toText(this.getPrefix() + " " + this.node.getNode("messages", "levelup").getString().replace("%skill%", skill).replace("%level%", String.valueOf(level))); }
	@Nonnull public Text getDodgeText() { return TextUtils.toText(this.getPrefix() + " " + this.node.getNode("messages", "dodge").getString()); }
	@Nonnull public Text getRollText() { return TextUtils.toText(this.getPrefix() + " " + this.node.getNode("messages", "roll").getString()); }
	@Nonnull public Text getAbilityRechargingText(final int seconds) { return TextUtils.toText(this.getPrefix() + " " + this.node.getNode("messages", "ability_recharge").getString().replace("%seconds%", String.valueOf(seconds))); }
	@Nonnull public Text getAbilityActivateText(@Nonnull final String ability) { return TextUtils.toText(this.getPrefix() + " " + this.node.getNode("messages", "ability_activate").getString().replace("%ability%", ability)); }
	@Nonnull public Text getAbilityEndText(@Nonnull final String ability) { return TextUtils.toText(this.getPrefix() + " " + this.node.getNode("messages", "ability_end").getString().replace("%ability%", ability)); }

	public int getAbilityRechargeSeconds() { return this.node.getNode("abilities", "recharge_seconds").getInt(); }
}