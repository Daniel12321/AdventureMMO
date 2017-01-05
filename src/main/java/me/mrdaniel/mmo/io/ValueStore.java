package me.mrdaniel.mmo.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.util.Tuple;

import com.google.common.collect.Maps;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.Ability;
import me.mrdaniel.mmo.utils.Formula;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Singleton
public class ValueStore {

	@Nonnull private final Path path;
	@Nonnull private final ConfigurationLoader<CommentedConfigurationNode> manager;
	@Nonnull public CommentedConfigurationNode config;

	@Nonnull private final Map<BlockType, Double> blocks;
	@Nonnull private final Map<Ability, Tuple<Boolean, Formula>> abilities;

	public ValueStore(@Nonnull final Path path) {
		this.blocks = Maps.newHashMap();
		this.abilities = Maps.newHashMap();

		this.path = path;
		this.manager = HoconConfigurationLoader.builder().setPath(path).build();
		this.config = this.manager.createEmptyNode(ConfigurationOptions.defaults());

		if (!Files.exists(path)) {
			try { Files.createFile(path); }
			catch (final IOException exc) { Main.getInstance().getLogger().error("Failed to create values file: {}", exc); }

			CommentedConfigurationNode blocks = this.config.getNode("blocks");

			blocks.setComment("Here you can set the EXP each block gives a player");
			blocks.getNode(BlockTypes.POTATOES.getId()).setValue(50.0);
			blocks.getNode(BlockTypes.WHEAT.getId()).setValue(50.0);
			blocks.getNode(BlockTypes.CARROTS.getId()).setValue(50.0);
			blocks.getNode(BlockTypes.NETHER_WART.getId()).setValue(50.0);
			blocks.getNode(BlockTypes.CACTUS.getId()).setValue(50.0);
			blocks.getNode(BlockTypes.REEDS.getId()).setValue(50.00);
			blocks.getNode(BlockTypes.MELON_BLOCK.getId()).setValue(75.0);
			blocks.getNode(BlockTypes.PUMPKIN.getId()).setValue(75.0);
			blocks.getNode(BlockTypes.WATERLILY.getId()).setValue(35.0);
			blocks.getNode(BlockTypes.RED_MUSHROOM.getId()).setValue(35.0);
			blocks.getNode(BlockTypes.BROWN_MUSHROOM.getId()).setValue(35.0);
			blocks.getNode(BlockTypes.RED_MUSHROOM_BLOCK.getId()).setValue(15.0);
			blocks.getNode(BlockTypes.BROWN_MUSHROOM_BLOCK.getId()).setValue(15.0);

			blocks.getNode(BlockTypes.LOG.getId()).setValue(50.0);
			blocks.getNode(BlockTypes.LEAVES.getId()).setValue(10.0);

			blocks.getNode(BlockTypes.OBSIDIAN.getId()).setValue(600.0);
			blocks.getNode(BlockTypes.DIAMOND_ORE.getId()).setValue(500.0);
			blocks.getNode(BlockTypes.EMERALD_ORE.getId()).setValue(550.0);
			blocks.getNode(BlockTypes.GOLD_ORE.getId()).setValue(250.0);
			blocks.getNode(BlockTypes.IRON_ORE.getId()).setValue(150.0);
			blocks.getNode(BlockTypes.COAL_ORE.getId()).setValue(30.0);
			blocks.getNode(BlockTypes.REDSTONE_ORE.getId()).setValue(50.0);
			blocks.getNode(BlockTypes.LAPIS_ORE.getId()).setValue(50.0);
			blocks.getNode(BlockTypes.QUARTZ_ORE.getId()).setValue(30.0);
			blocks.getNode(BlockTypes.MOSSY_COBBLESTONE.getId()).setValue(30.0);

			blocks.getNode(BlockTypes.SAND.getId()).setValue(15.0);
			blocks.getNode(BlockTypes.DIRT.getId()).setValue(15.0);
			blocks.getNode(BlockTypes.GRAVEL.getId()).setValue(20.0);
			blocks.getNode(BlockTypes.GRASS.getId()).setValue(30.0);
			blocks.getNode(BlockTypes.SOUL_SAND.getId()).setValue(40.0);
			blocks.getNode(BlockTypes.MYCELIUM.getId()).setValue(40.0);
			blocks.getNode(BlockTypes.CLAY.getId()).setValue(40.0);

			CommentedConfigurationNode abilities = this.config.getNode("abilities");

			for (Ability a : Ability.values()) {
				abilities.getNode(a.getName().replaceAll(" ", "").toLowerCase(), "disabled").setValue(false);
			}

			abilities.getNode("superbreaker", "beginvalue").setValue(5.0);
			abilities.getNode("superbreaker", "increment").setValue(0.08);
			abilities.getNode("superbreaker", "maximum").setValue(45.0);
			abilities.getNode("treeveller", "beginvalue").setValue(5.0);
			abilities.getNode("treeveller", "increment").setValue(0.08);
			abilities.getNode("treeveller", "maximum").setValue(45.0);
			abilities.getNode("gigadrillbreaker", "beginvalue").setValue(5.0);
			abilities.getNode("gigadrillbreaker", "increment").setValue(0.08);
			abilities.getNode("gigadrillbreaker", "maximum").setValue(45.0);
			abilities.getNode("greenterra", "beginvalue").setValue(5.0);
			abilities.getNode("greenterra", "increment").setValue(0.08);
			abilities.getNode("greenterra", "maximum").setValue(45.0);
			abilities.getNode("slaughter", "beginvalue").setValue(5.0);
			abilities.getNode("slaughter", "increment").setValue(0.08);
			abilities.getNode("slaughter", "maximum").setValue(45.0);
			abilities.getNode("bloodshed", "beginvalue").setValue(5.0);
			abilities.getNode("bloodshed", "increment").setValue(0.08);
			abilities.getNode("bloodshed", "maximum").setValue(45.0);
			abilities.getNode("saitamapunch", "beginvalue").setValue(5.0);
			abilities.getNode("saitamapunch", "increment").setValue(0.08);
			abilities.getNode("saitamapunch", "maximum").setValue(45.0);

			abilities.getNode("miningdoubledrop", "beginvalue").setValue(0.0);
			abilities.getNode("miningdoubledrop", "increment").setValue(0.2);
			abilities.getNode("miningdoubledrop", "maximum").setValue(85.0);
			abilities.getNode("farmingdoubledrop", "beginvalue").setValue(0.0);
			abilities.getNode("farmingdoubledrop", "increment").setValue(0.2);
			abilities.getNode("farmingdoubledrop", "maximum").setValue(85.0);
			abilities.getNode("excavationdoubledrop", "beginvalue").setValue(0);
			abilities.getNode("excavationdoubledrop", "increment").setValue(0.2);
			abilities.getNode("excavationdoubledrop", "maximum").setValue(85.0);
			abilities.getNode("woodcuttingdoubledrop", "beginvalue").setValue(0.0);
			abilities.getNode("woodcuttingdoubledrop", "increment").setValue(0.2);
			abilities.getNode("woodcuttingdoubledrop", "maximum").setValue(85.0);
			abilities.getNode("roll", "beginvalue").setValue(0.0);
			abilities.getNode("roll", "increment").setValue(0.16);
			abilities.getNode("roll", "maximum").setValue(50.0);
			abilities.getNode("dodge", "beginvalue").setValue(0.0);
			abilities.getNode("dodge", "increment").setValue(0.08);
			abilities.getNode("dodge", "maximum").setValue(30.0);
			abilities.getNode("decapitation", "beginvalue").setValue(0.0);
			abilities.getNode("decapitation", "increment").setValue(0.03);
			abilities.getNode("decapitation", "maximum").setValue(30.0);
			abilities.getNode("arrowrain", "beginvalue").setValue(0.0);
			abilities.getNode("arrowrain", "increment").setValue(0.05);
			abilities.getNode("arrowrain", "maximum").setValue(50.0);
			abilities.getNode("disarm", "beginvalue").setValue(0.0);
			abilities.getNode("disarm", "increment").setValue(0.03);
			abilities.getNode("disarm", "maximum").setValue(15.0);

			abilities.getNode("salvage", "beginvalue").setValue(20.0);
			abilities.getNode("salvage", "increment").setValue(0.2);
			abilities.getNode("salvage", "maximum").setValue(100.0);
			abilities.getNode("repair", "beginvalue").setValue(10.0);
			abilities.getNode("repair", "increment").setValue(0.12);
			abilities.getNode("repair", "maximum").setValue(100.0);
			abilities.getNode("treasurehunt", "beginvalue").setValue(2.0);
			abilities.getNode("treasurehunt", "increment").setValue(0.04);
			abilities.getNode("treasurehunt", "maximum").setValue(50.0);
			abilities.getNode("watertreasure", "beginvalue").setValue(8.0);
			abilities.getNode("watertreasure", "increment").setValue(0.2);
			abilities.getNode("watertreasure", "maximum").setValue(80.0);
			abilities.getNode("summonwolf", "beginvalue").setValue(1000.0);
			abilities.getNode("summonwolf", "increment").setComment("summonwolf increment must have a negative value");
			abilities.getNode("summonwolf", "increment").setValue(-2.0);
			abilities.getNode("summonwolf", "maximum").setValue(10000.0);
			abilities.getNode("summonocelot", "beginvalue").setValue(1500.0);
			abilities.getNode("summonocelot", "increment").setComment("summonocelot increment must have a negative value");
			abilities.getNode("summonocelot", "increment").setValue(-2.0);
			abilities.getNode("summonocelot", "maximum").setValue(10000.0);
			abilities.getNode("summonhorse", "beginvalue").setValue(2000.0);
			abilities.getNode("summonhorse", "increment").setComment("summonhorse increment must have a negative value");
			abilities.getNode("summonhorse", "increment").setValue(-2.0);
			abilities.getNode("summonhorse", "maximum").setValue(10000.0);

			CommentedConfigurationNode skills = this.config.getNode("skills");

			skills.getNode("acrobatics").setComment("Here you can set the EXP you gain for each half-heart of fall damage");
			skills.getNode("acrobatics").setValue(4.0);

			skills.getNode("fishing").setComment("Here you can set the EXP you gain for fishin one fish");
			skills.getNode("fishing").setValue(450.0);

			skills.getNode("taming").setComment("Here you can set the EXP you gain for taming one animal");
			skills.getNode("taming").setValue(750.0);

			skills.getNode("swords", "kill").setComment("Here you can set the EXP you gain when killing a mob with a sword");
			skills.getNode("swords", "kill").setValue(60);
			skills.getNode("swords", "hurt").setComment("Here you can set the EXP you gain when you hurt a mob with a sword");
			skills.getNode("swords", "hurt").setValue(10);

			skills.getNode("axes", "kill").setComment("Here you can set the EXP you gain when killing a mob with an axe");
			skills.getNode("axes", "kill").setValue(75);
			skills.getNode("axes", "hurt").setComment("Here you can set the EXP you gain when you hurt a mob with an axe");
			skills.getNode("axes", "hurt").setValue(10);

			skills.getNode("archery", "kill").setComment("Here you can set the EXP you gain when killing a mob with a bow");
			skills.getNode("archery", "kill").setValue(150);
			skills.getNode("archery", "hurt").setComment("Here you can set the EXP you gain when you hurt a mob with a bow");
			skills.getNode("archery", "hurt").setValue(30);

			skills.getNode("unarmed", "kill").setComment("Here you can set the EXP you gain when killing a mob with your hand");
			skills.getNode("unarmed", "kill").setValue(100);
			skills.getNode("unarmed", "hurt").setComment("Here you can set the EXP you gain when you hurt a mob with your hand");
			skills.getNode("unarmed", "hurt").setValue(5);

			this.save();
		}
		else {
			try { this.config = this.manager.load(); }
			catch (final IOException exc) { Main.getInstance().getLogger().error("Failed to load values config: {}", exc); }
		}

		this.config.getNode("blocks").getChildrenMap().forEach((id, node) -> Main.getInstance().getGame().getRegistry().getType(BlockType.class, (String) id).ifPresent(type -> this.blocks.put(type, node.getDouble())));
		this.config.getNode("abilities").getChildrenMap().forEach((id, node) -> Ability.of((String) id).ifPresent(a -> this.abilities.put(a, new Tuple<Boolean, Formula>(node.getNode("disabled").getBoolean(), new Formula(node.getNode("beginvalue").getDouble(), node.getNode("increment").getDouble(), node.getNode("maximum").getDouble())))));
	}

	private void save() {
		try { this.manager.save(this.config); }
		catch (final IOException exc) { Main.getInstance().getLogger().error("Failed to save values file: {}", exc); }
	}

	public Optional<Double> getBlockValue(@Nonnull final BlockType type) {
		return (this.blocks.containsKey(type)) ? Optional.of(this.blocks.get(type)) : Optional.empty();
	}

	@Nonnull public Tuple<Boolean, Formula> getAbility(@Nonnull final Ability a) {
		return this.abilities.containsKey(a) ? this.abilities.get(a) : new Tuple<Boolean, Formula>(false, new Formula(0, 0, 0));
	}

	public double getAcrobatics() { return this.config.getNode("skills", "acrobatics").getDouble(); }
	public double getFishing() { return this.config.getNode("skills", "fishing").getDouble(); }
	public double getTaming() { return this.config.getNode("skills", "taming").getDouble(); }
	public double getSwordsKill() { return this.config.getNode("skills", "swords", "kill").getDouble(); }
	public double getSwordsHurt() { return this.config.getNode("skills", "swords", "hurt").getDouble(); }
	public double getAxesKill() { return this.config.getNode("skills", "axes", "kill").getDouble(); }
	public double getAxesHurt() { return this.config.getNode("skills", "axes", "hurt").getDouble(); }
	public double getArcheryKill() { return this.config.getNode("skills", "archery", "kill").getDouble(); }
	public double getArcheryHurt() { return this.config.getNode("skills", "archery", "hurt").getDouble(); }
	public double getUnarmedKill() { return this.config.getNode("skills", "unarmed", "kill").getDouble(); }
	public double getUnarmedHurt() { return this.config.getNode("skills", "unarmed", "hurt").getDouble(); }
}