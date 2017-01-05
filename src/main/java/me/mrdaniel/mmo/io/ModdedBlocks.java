package me.mrdaniel.mmo.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.SkillType;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Singleton
public class ModdedBlocks {

	@Nonnull public final Path path;
	@Nonnull public final ConfigurationLoader<CommentedConfigurationNode> manager;
	@Nonnull public CommentedConfigurationNode config;

	@Nonnull private final ArrayList<ModdedBlock> blocks;

	public ModdedBlocks(@Nonnull final Path path) {
		Main.getInstance().getLogger().info("Loading Modded Blocks File...");

		this.path = path;
		this.manager = HoconConfigurationLoader.builder().setPath(path).build();
		this.config = this.manager.createEmptyNode(ConfigurationOptions.defaults());

		this.blocks = new ArrayList<ModdedBlock>();

		if (!Files.exists(path)) {
			try { Files.createFile(path); }
			catch (final IOException exc) { Main.getInstance().getLogger().error("Failed to create moddedblocks file: {}", exc); }

			config.getNode("pixelmon:Bauxite", "type").setValue("mining");
			config.getNode("pixelmon:Bauxite", "exp").setValue(250);
				
			config.getNode("pixelmon:CrystalOre", "type").setValue("mining");
			config.getNode("pixelmon:CrystalOre", "exp").setValue(250);
				
			config.getNode("pixelmon:SiliconOre", "type").setValue("mining");
			config.getNode("pixelmon:SiliconOre", "exp").setValue(200);
				
			config.getNode("pixelmon:AmethystOre", "type").setValue("mining");
			config.getNode("pixelmon:AmethystOre", "exp").setValue(250);
				
			config.getNode("pixelmon:SapphireOre", "type").setValue("mining");
			config.getNode("pixelmon:SapphireOre", "exp").setValue(250);
				
			config.getNode("pixelmon:RubyOre", "type").setValue("mining");
			config.getNode("pixelmon:RubyOre", "exp").setValue(250);
				
			config.getNode("pixelmon:Sun_Stone_Ore", "type").setValue("mining");
			config.getNode("pixelmon:Sun_Stone_Ore", "exp").setValue(250);
				
			config.getNode("pixelmon:DawnDuskstone_Ore", "type").setValue("mining");
			config.getNode("pixelmon:DawnDuskstone_Ore", "exp").setValue(250);
				
			config.getNode("pixelmon:Firestone_Ore", "type").setValue("mining");
			config.getNode("pixelmon:Firestone_Ore", "exp").setValue(250);
				
			config.getNode("pixelmon:Waterstone_Ore", "type").setValue("mining");
			config.getNode("pixelmon:Waterstone_Ore", "exp").setValue(250);
				
			config.getNode("pixelmon:Leafstone_Ore", "type").setValue("mining");
			config.getNode("pixelmon:Leafstone_Ore", "exp").setValue(250);
				
			config.getNode("pixelmon:Thunderstone_Ore", "type").setValue("mining");
			config.getNode("pixelmon:Thunderstone_Ore", "exp").setValue(250);
				
		    this.save();
		}
		else {
			try { this.config = this.manager.load(); }
			catch (final IOException exc) { Main.getInstance().getLogger().error("Failed to load moddedblocks config: {}", exc); }
		}

	    this.config.getChildrenMap().forEach((id, n) -> SkillType.of(n.getNode("type").getString()).ifPresent(type -> this.blocks.add(new ModdedBlock((String) id, type, n.getNode("exp").getInt()))));

		Main.getInstance().getLogger().info("Loaded {} Modded Blocks", this.blocks.size());
	}

	@Nonnull
	public Optional<ModdedBlock> get(@Nonnull final String id) {
		for (ModdedBlock mBlock : this.blocks) { if (mBlock.id.equalsIgnoreCase(id)) { return Optional.of(mBlock); } }
		return Optional.empty();
	}

	private void save() {
		try { this.manager.save(this.config); }
		catch (final IOException exc) { Main.getInstance().getLogger().error("Failed to save moddedtools file: {}", exc); }
	}
}