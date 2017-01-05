package me.mrdaniel.mmo.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.ToolType;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Singleton
public class ModdedTools {

	@Nonnull public final Path path;
	@Nonnull public final ConfigurationLoader<CommentedConfigurationNode> manager;
	@Nonnull public CommentedConfigurationNode config;

	@Nonnull private final ArrayList<ModdedTool> tools;

	public ModdedTools(@Nonnull final Path path) {
		Main.getInstance().getLogger().info("Loading Modded Tools File...");

		this.path = path;
		this.manager = HoconConfigurationLoader.builder().setPath(path).build();
		this.config = this.manager.createEmptyNode(ConfigurationOptions.defaults());

		this.tools = new ArrayList<ModdedTool>();

		if (!Files.exists(path)) {
			try { Files.createFile(path); }
			catch (final IOException exc) { Main.getInstance().getLogger().error("Failed to create moddedtools file: {}", exc); }

			this.config.getNode("pixelmon:RubyPickaxe", "type").setValue("pickaxe");
			this.config.getNode("pixelmon:RubyAxe", "type").setValue("axe");
			this.config.getNode("pixelmon:RubyShovel", "type").setValue("shovel");
			this.config.getNode("pixelmon:RubyHoe", "type").setValue("hoe");
				
			this.config.getNode("pixelmon:SapphirePickaxe", "type").setValue("pickaxe");
			this.config.getNode("pixelmon:SapphireAxe", "type").setValue("axe");
			this.config.getNode("pixelmon:SapphireShovel", "type").setValue("shovel");
			this.config.getNode("pixelmon:SapphireHoe", "type").setValue("hoe");
				
			this.config.getNode("pixelmon:AmethystPickaxe", "type").setValue("pickaxe");
			this.config.getNode("pixelmon:AmethystAxe", "type").setValue("axe");
			this.config.getNode("pixelmon:AmethystShovel", "type").setValue("shovel");
			this.config.getNode("pixelmon:AmethystHoe", "type").setValue("hoe");
				
			this.config.getNode("pixelmon:CrystalPickaxe", "type").setValue("pickaxe");
			this.config.getNode("pixelmon:CrystalAxe", "type").setValue("axe");
			this.config.getNode("pixelmon:CrystalShovel", "type").setValue("shovel");
			this.config.getNode("pixelmon:CrystalHoe", "type").setValue("hoe");
				
			this.config.getNode("pixelmon:FirestonePickaxe", "type").setValue("pickaxe");
			this.config.getNode("pixelmon:FirestoneAxe", "type").setValue("axe");
			this.config.getNode("pixelmon:FirestoneShovel", "type").setValue("shovel");
			this.config.getNode("pixelmon:FirestoneHoe", "type").setValue("hoe");
				
			this.config.getNode("pixelmon:WaterstonePickaxe", "type").setValue("pickaxe");
			this.config.getNode("pixelmon:WaterstoneAxe", "type").setValue("axe");
			this.config.getNode("pixelmon:WaterstoneShovel", "type").setValue("shovel");
			this.config.getNode("pixelmon:WaterstoneHoe", "type").setValue("hoe");
				
			this.config.getNode("pixelmon:LeafstonePickaxe", "type").setValue("pickaxe");
			this.config.getNode("pixelmon:LeafstoneAxe", "type").setValue("axe");
			this.config.getNode("pixelmon:LeafstoneShovel", "type").setValue("shovel");
			this.config.getNode("pixelmon:LeafstoneHoe", "type").setValue("hoe");
				
			this.config.getNode("pixelmon:ThunderstonePickaxe", "type").setValue("pickaxe");
			this.config.getNode("pixelmon:ThunderstoneAxe", "type").setValue("axe");
			this.config.getNode("pixelmon:ThunderstoneShovel", "type").setValue("shovel");
			this.config.getNode("pixelmon:ThunderstoneHoe", "type").setValue("hoe");
				
			this.config.getNode("pixelmon:SunstonePickaxe", "type").setValue("pickaxe");
			this.config.getNode("pixelmon:SunstoneAxe", "type").setValue("axe");
			this.config.getNode("pixelmon:SunstoneShovel", "type").setValue("shovel");
			this.config.getNode("pixelmon:SunstoneHoe", "type").setValue("hoe");
				
			this.config.getNode("pixelmon:MoonstonePickaxe", "type").setValue("pickaxe");
			this.config.getNode("pixelmon:MoonstoneAxe", "type").setValue("axe");
			this.config.getNode("pixelmon:MoonstoneShovel", "type").setValue("shovel");
			this.config.getNode("pixelmon:MoonstoneHoe", "type").setValue("hoe");
				
			this.config.getNode("pixelmon:DawnstonePickaxe", "type").setValue("pickaxe");
			this.config.getNode("pixelmon:DawnstoneAxe", "type").setValue("axe");
			this.config.getNode("pixelmon:DawnstoneShovel", "type").setValue("shovel");
			this.config.getNode("pixelmon:DawnstoneHoe", "type").setValue("hoe");
				
			this.config.getNode("pixelmon:DuskstonePickaxe", "type").setValue("pickaxe");
			this.config.getNode("pixelmon:DuskstoneAxe", "type").setValue("axe");
			this.config.getNode("pixelmon:DuskstoneShovel", "type").setValue("shovel");
			this.config.getNode("pixelmon:DuskstoneHoe", "type").setValue("hoe");
				
			this.save();
		}
		else {
			try { this.config = this.manager.load(); }
			catch (final IOException exc) { Main.getInstance().getLogger().error("Failed to load moddedtools config: {}", exc); }
		}

		this.config.getChildrenMap().forEach((id, value) -> ToolType.of(value.getNode("type").getString()).ifPresent(type -> this.tools.add(new ModdedTool((String) id, type))));

		Main.getInstance().getLogger().info("Loaded {} Modded Tools!", this.tools.size());
	}

	@Nonnull public Optional<ModdedTool> getToolType(@Nonnull final String id) {
		for (ModdedTool mTool : this.tools) { if (mTool.id.equalsIgnoreCase(id)) { return Optional.of(mTool); } }
		return Optional.empty();
	}

	private void save() {
		try { this.manager.save(this.config); }
		catch (final IOException exc) { Main.getInstance().getLogger().error("Failed to save moddedtools file: {}", exc); }
	}
}