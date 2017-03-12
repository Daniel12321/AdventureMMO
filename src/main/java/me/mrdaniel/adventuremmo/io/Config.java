package me.mrdaniel.adventuremmo.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.Nonnull;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class Config extends MMOObject {

	private final int recharge_seconds;

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

		this.recharge_seconds = this.node.getNode("abilities", "recharge_seconds").getInt();
	}

	private CommentedConfigurationNode load() {
		try { return this.loader.load(); }
		catch (final IOException exc) { super.getLogger().error("Failed to load config file: {}", exc); return this.loader.createEmptyNode(); }
	}

	@Nonnull public ConfigurationNode getMessagesNode() { return this.node.getNode("messages"); }

	public int getAbilityRechargeSeconds() { return this.recharge_seconds; }
	public boolean isSkillEnabled(@Nonnull final SkillType type) { return this.node.getNode("skills", type.getId(), "enabled").getBoolean(); }

	public int getAcrobaticsExp() { return this.node.getNode("skills", "acrobatics", "exp_multiplier").getInt(); }
	public int getFishExp() { return this.node.getNode("skills", "fishing", "fish_exp").getInt(); }
	public int getSwordsKillExp() { return this.node.getNode("skills", "swords", "kill_exp").getInt(); }
	public int getSwordsDamageExp() { return this.node.getNode("skills", "swords", "damage_exp").getInt(); }
	public int getAxesKillExp() { return this.node.getNode("skills", "axes", "kill_exp").getInt(); }
	public int getAxesDamageExp() { return this.node.getNode("skills", "axes", "damage_exp").getInt(); }
	public int getUnarmedKillExp() { return this.node.getNode("skills", "unarmed", "kill_exp").getInt(); }
	public int getUnarmedDamageExp() { return this.node.getNode("skills", "unarmed", "damage_exp").getInt(); }
	public int getBowKillExp() { return this.node.getNode("skills", "archery", "kill_exp").getInt(); }
	public int getBowDamageExp() { return this.node.getNode("skills", "archery", "damage_exp").getInt(); }
}