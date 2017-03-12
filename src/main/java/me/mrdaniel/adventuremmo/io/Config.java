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

//	@Nonnull public CommentedConfigurationNode getMessagesNode() { return this.node.getNode("messages"); }
//	@Nonnull public CommentedConfigurationNode getAbilitiesNode() { return this.node.getNode("abilities"); }
//	@Nonnull public ConfigurationNode getSkillsNode() { return this.node.getNode("skills"); }

//	public int getAbilityRechargeSeconds() { return this.recharge_seconds; }
//	public boolean isSkillEnabled(@Nonnull final SkillType type) { return this.node.getNode("skills", type.getId(), "enabled").getBoolean(); }
//
//	public double getAcrobaticsExp() { return this.node.getNode("skills", "acrobatics", "exp_multiplier").getDouble(); }
//	public int getFishExp() { return this.node.getNode("skills", "fishing", "fish_exp").getInt(); }
//	public int getSwordsKillExp() { return this.node.getNode("skills", "swords", "kill_exp").getInt(); }
//	public int getSwordsDamageExp() { return this.node.getNode("skills", "swords", "damage_exp").getInt(); }
//	public int getAxesKillExp() { return this.node.getNode("skills", "axes", "kill_exp").getInt(); }
//	public int getAxesDamageExp() { return this.node.getNode("skills", "axes", "damage_exp").getInt(); }
//	public int getUnarmedKillExp() { return this.node.getNode("skills", "unarmed", "kill_exp").getInt(); }
//	public int getUnarmedDamageExp() { return this.node.getNode("skills", "unarmed", "damage_exp").getInt(); }
//	public int getArcheryKillExp() { return this.node.getNode("skills", "archery", "kill_exp").getInt(); }
//	public int getArcheryDamageExp() { return this.node.getNode("skills", "archery", "damage_exp").getInt(); }
}