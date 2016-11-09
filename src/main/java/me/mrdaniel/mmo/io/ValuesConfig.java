package me.mrdaniel.mmo.io;

import java.io.File;
import java.io.IOException;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;

import com.google.common.collect.ImmutableMap;

import me.mrdaniel.mmo.Main;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ValuesConfig {
	
	private static ValuesConfig instance = null;
	public static ValuesConfig getInstance() {
		if (instance == null) { instance = new ValuesConfig(); }
		return instance;
	}
	
	public File file;
	public ConfigurationLoader<CommentedConfigurationNode> manager;
	public CommentedConfigurationNode config;
	
	public ImmutableMap<BlockType, Integer> blockExps;
	
	private ValuesConfig() {
		file = Main.getInstance().getPath().resolve("values.conf").toFile();
		manager = HoconConfigurationLoader.builder().setFile(file).build();
		config = manager.createEmptyNode(ConfigurationOptions.defaults());
	}
	
	public void setup() {
		Main.getInstance().getLogger().info("Loading Values Config File");
		try {
			if (!file.exists()) {
				file.createNewFile();
				
				config.getNode("blocks").setComment("Here you can set the EXP each block gives");
				config.getNode("blocks", "POTATOES").setValue(50);
				config.getNode("blocks", "WHEAT").setValue(50);
				config.getNode("blocks", "CARROTS").setValue(50);
				config.getNode("blocks", "NETHER_WART").setValue(50);
				config.getNode("blocks", "CACTUS").setValue(50);
				config.getNode("blocks", "REEDS").setValue(50);
				config.getNode("blocks", "MELON_BLOCK").setValue(75);
				config.getNode("blocks", "PUMPKIN").setValue(75);
				config.getNode("blocks", "WATERLILY").setValue(35);
				config.getNode("blocks", "RED_MUSHROOM").setValue(35);
				config.getNode("blocks", "BROWN_MUSHROOM").setValue(35);
				config.getNode("blocks", "RED_MUSHROOM_BLOCK").setValue(15);
				config.getNode("blocks", "BROWN_MUSHROOM_BLOCK").setValue(15);
				
				config.getNode("blocks", "LOG").setValue(50);
				config.getNode("blocks", "LEAVES").setValue(15);
				
				config.getNode("blocks", "OBSIDIAN").setValue(600);
				config.getNode("blocks", "DIAMOND_ORE").setValue(500);
				config.getNode("blocks", "EMERALD_ORE").setValue(550);
				config.getNode("blocks", "GOLD_ORE").setValue(250);
				config.getNode("blocks", "IRON_ORE").setValue(150);
				config.getNode("blocks", "COAL_ORE").setValue(30);
				config.getNode("blocks", "REDSTONE_ORE").setValue(50);
				config.getNode("blocks", "LAPIS_ORE").setValue(50);
				config.getNode("blocks", "QUARTZ_ORE").setValue(30);
				config.getNode("blocks", "MOSSY_COBBLESTONE").setValue(30);
				
				config.getNode("blocks", "SAND").setValue(15);
				config.getNode("blocks", "DIRT").setValue(15);
				config.getNode("blocks", "GRAVEL").setValue(20);
				config.getNode("blocks", "GRASS").setValue(30);
				config.getNode("blocks", "SOUL_SAND").setValue(40);
				config.getNode("blocks", "MYCELIUM").setValue(40);
				config.getNode("blocks", "CLAY").setValue(40);

		        manager.save(config);
			}
	        config = manager.load();

	        ImmutableMap.Builder<BlockType, Integer> b = ImmutableMap.builder();
	        blockExps = b.put(BlockTypes.POTATOES, config.getNode("blocks", "POTATOES").getInt())
	        .put(BlockTypes.WHEAT, config.getNode("blocks", "WHEAT").getInt())
	        .put(BlockTypes.CARROTS, config.getNode("blocks", "CARROTS").getInt())
	        .put(BlockTypes.NETHER_WART, config.getNode("blocks", "NETHER_WART").getInt())
	        .put(BlockTypes.CACTUS, config.getNode("blocks", "CACTUS").getInt())
	        .put(BlockTypes.REEDS, config.getNode("blocks", "REEDS").getInt())
	        .put(BlockTypes.MELON_BLOCK, config.getNode("blocks", "MELON_BLOCK").getInt())
	        .put(BlockTypes.PUMPKIN, config.getNode("blocks", "PUMPKIN").getInt())
	        .put(BlockTypes.WATERLILY, config.getNode("blocks", "WATERLILY").getInt())
	        .put(BlockTypes.RED_MUSHROOM, config.getNode("blocks", "RED_MUSHROOM").getInt())
	        .put(BlockTypes.BROWN_MUSHROOM, config.getNode("blocks", "BROWN_MUSHROOM").getInt())
	        .put(BlockTypes.RED_MUSHROOM_BLOCK, config.getNode("blocks", "RED_MUSHROOM_BLOCK").getInt())
	        .put(BlockTypes.BROWN_MUSHROOM_BLOCK, config.getNode("blocks", "BROWN_MUSHROOM_BLOCK").getInt())

	        .put(BlockTypes.LOG, config.getNode("blocks", "LOG").getInt())
	        .put(BlockTypes.LOG2, config.getNode("blocks", "LOG").getInt())
	        .put(BlockTypes.LEAVES, config.getNode("blocks", "LEAVES").getInt())
	        .put(BlockTypes.LEAVES2, config.getNode("blocks", "LEAVES").getInt())

	        .put(BlockTypes.OBSIDIAN, config.getNode("blocks", "OBSIDIAN").getInt())
	        .put(BlockTypes.DIAMOND_ORE, config.getNode("blocks", "DIAMOND_ORE").getInt())
	        .put(BlockTypes.EMERALD_ORE, config.getNode("blocks", "EMERALD_ORE").getInt())
	        .put(BlockTypes.GOLD_ORE, config.getNode("blocks", "GOLD_ORE").getInt())
	        .put(BlockTypes.IRON_ORE, config.getNode("blocks", "IRON_ORE").getInt())
	        .put(BlockTypes.COAL_ORE, config.getNode("blocks", "COAL_ORE").getInt())
	        .put(BlockTypes.REDSTONE_ORE, config.getNode("blocks", "REDSTONE_ORE").getInt())
	        .put(BlockTypes.LIT_REDSTONE_ORE, config.getNode("blocks", "REDSTONE_ORE").getInt())
	        .put(BlockTypes.LAPIS_ORE, config.getNode("blocks", "LAPIS_ORE").getInt())
	        .put(BlockTypes.QUARTZ_ORE, config.getNode("blocks", "QUARTZ_ORE").getInt())
	        .put(BlockTypes.MOSSY_COBBLESTONE, config.getNode("blocks", "MOSSY_COBBLESTONE").getInt())

	        .put(BlockTypes.SAND, config.getNode("blocks", "SAND").getInt())
	        .put(BlockTypes.DIRT, config.getNode("blocks", "DIRT").getInt())
	        .put(BlockTypes.GRAVEL, config.getNode("blocks", "GRAVEL").getInt())
	        .put(BlockTypes.GRASS, config.getNode("blocks", "GRASS").getInt())
	        .put(BlockTypes.SOUL_SAND, config.getNode("blocks", "SOUL_SAND").getInt())
	        .put(BlockTypes.MYCELIUM, config.getNode("blocks", "MYCELIUM").getInt())
	        .put(BlockTypes.CLAY, config.getNode("blocks", "CLAY").getInt())
	        .build();
		}
		catch (IOException e) {
			Main.getInstance().getLogger().error("Error while loading Values Config file");
			e.printStackTrace();
			return;
		}
		Main.getInstance().getLogger().info("Done Loading Values Config File");
	}
}