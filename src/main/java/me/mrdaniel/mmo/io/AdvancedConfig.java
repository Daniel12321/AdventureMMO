package me.mrdaniel.mmo.io;

import java.io.File;
import java.io.IOException;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;

import com.google.common.collect.ImmutableMap;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.SkillType;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class AdvancedConfig {
	
	private static AdvancedConfig instance = null;
	public static AdvancedConfig getInstance() {
		if (instance == null) { instance = new AdvancedConfig(); }
		return instance;
	}
	
	public File file;
	public ConfigurationLoader<CommentedConfigurationNode> manager;
	public CommentedConfigurationNode config;
	
	public ImmutableMap<BlockType, Integer> blockExps;
	public ImmutableMap<SkillType, double[]> skillExps;
	public ImmutableMap<String, double[]> abilities;
	
	private AdvancedConfig() {
		file = Main.getInstance().getPath().resolve("advancedconfig.conf").toFile();
		manager = HoconConfigurationLoader.builder().setFile(file).build();
		config = manager.createEmptyNode(ConfigurationOptions.defaults());
	}
	
	public void setup() {
		Main.getInstance().getLogger().info("Loading Advanced Config File");
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
				
				config.getNode("acrobatics").setComment("Here you can set the EXP you gain for each half-heart of fall damage");
				config.getNode("acrobatics").setValue(4.0);
				config.getNode("fishing").setComment("Here you can set the EXP you gain for fishin one fish");
				config.getNode("fishing").setValue(450.0);
				config.getNode("taming").setComment("Here you can set the EXP you gain for taming one animal");
				config.getNode("taming").setValue(750.0);
				
				config.getNode("swords", "kill").setComment("Here you can set the EXP you gain when killing a mob with a sword");
				config.getNode("swords", "kill").setValue(60);
				config.getNode("swords", "hurt").setComment("Here you can set the EXP you gain when you hurt a mob with a sword");
				config.getNode("swords", "hurt").setValue(10);
				
				config.getNode("axes", "kill").setComment("Here you can set the EXP you gain when killing a mob with an axe");
				config.getNode("axes", "kill").setValue(75);
				config.getNode("axes", "hurt").setComment("Here you can set the EXP you gain when you hurt a mob with an axe");
				config.getNode("axes", "hurt").setValue(10);
				
				config.getNode("archery", "kill").setComment("Here you can set the EXP you gain when killing a mob with a bow");
				config.getNode("archery", "kill").setValue(150);
				config.getNode("archery", "hurt").setComment("Here you can set the EXP you gain when you hurt a mob with a bow");
				config.getNode("archery", "hurt").setValue(30);
				
				config.getNode("unarmed", "kill").setComment("Here you can set the EXP you gain when killing a mob with your hand");
				config.getNode("unarmed", "kill").setValue(100);
				config.getNode("unarmed", "hurt").setComment("Here you can set the EXP you gain when you hurt a mob with your hand");
				config.getNode("unarmed", "hurt").setValue(5);
				
				config.getNode("abilities", "SUPER_BREAKER", "beginvalue").setValue(5.0);
				config.getNode("abilities", "SUPER_BREAKER", "increment").setValue(0.08);
				config.getNode("abilities", "SUPER_BREAKER", "maximum").setValue(45.0);
				config.getNode("abilities", "TREE_VELLER", "beginvalue").setValue(5.0);
				config.getNode("abilities", "TREE_VELLER", "increment").setValue(0.08);
				config.getNode("abilities", "TREE_VELLER", "maximum").setValue(45.0);
				config.getNode("abilities", "GIGA_DRILL_BREAKER", "beginvalue").setValue(5.0);
				config.getNode("abilities", "GIGA_DRILL_BREAKER", "increment").setValue(0.08);
				config.getNode("abilities", "GIGA_DRILL_BREAKER", "maximum").setValue(45.0);
				config.getNode("abilities", "GREEN_TERRA", "beginvalue").setValue(5.0);
				config.getNode("abilities", "GREEN_TERRA", "increment").setValue(0.08);
				config.getNode("abilities", "GREEN_TERRA", "maximum").setValue(45.0);
				config.getNode("abilities", "SLAUGHTER", "beginvalue").setValue(5.0);
				config.getNode("abilities", "SLAUGHTER", "increment").setValue(0.08);
				config.getNode("abilities", "SLAUGHTER", "maximum").setValue(45.0);
				config.getNode("abilities", "BLOODSHED", "beginvalue").setValue(5.0);
				config.getNode("abilities", "BLOODSHED", "increment").setValue(0.08);
				config.getNode("abilities", "BLOODSHED", "maximum").setValue(45.0);
				config.getNode("abilities", "SAITAMA_PUNCH", "beginvalue").setValue(5.0);
				config.getNode("abilities", "SAITAMA_PUNCH", "increment").setValue(0.08);
				config.getNode("abilities", "SAITAMA_PUNCH", "maximum").setValue(45.0);
				
				config.getNode("abilities", "MINING_DOUBLEDROP", "beginvalue").setValue(0.0);
				config.getNode("abilities", "MINING_DOUBLEDROP", "increment").setValue(0.2);
				config.getNode("abilities", "MINING_DOUBLEDROP", "maximum").setValue(85.0);
				config.getNode("abilities", "FARMING_DOUBLEDROP", "beginvalue").setValue(0.0);
				config.getNode("abilities", "FARMING_DOUBLEDROP", "increment").setValue(0.2);
				config.getNode("abilities", "FARMING_DOUBLEDROP", "maximum").setValue(85.0);
				config.getNode("abilities", "EXCAVATION_DOUBLEDROP", "beginvalue").setValue(0);
				config.getNode("abilities", "EXCAVATION_DOUBLEDROP", "increment").setValue(0.2);
				config.getNode("abilities", "EXCAVATION_DOUBLEDROP", "maximum").setValue(85.0);
				config.getNode("abilities", "WOODCUTTING_DOUBLEDROP", "beginvalue").setValue(0.0);
				config.getNode("abilities", "WOODCUTTING_DOUBLEDROP", "increment").setValue(0.2);
				config.getNode("abilities", "WOODCUTTING_DOUBLEDROP", "maximum").setValue(85.0);
				config.getNode("abilities", "ROLL", "beginvalue").setValue(0.0);
				config.getNode("abilities", "ROLL", "increment").setValue(0.16);
				config.getNode("abilities", "ROLL", "maximum").setValue(50.0);
				config.getNode("abilities", "DODGE", "beginvalue").setValue(0.0);
				config.getNode("abilities", "DODGE", "increment").setValue(0.08);
				config.getNode("abilities", "DODGE", "maximum").setValue(30.0);
				config.getNode("abilities", "DECAPITATION", "beginvalue").setValue(0.0);
				config.getNode("abilities", "DECAPITATION", "increment").setValue(0.03);
				config.getNode("abilities", "DECAPITATION", "maximum").setValue(30.0);
				config.getNode("abilities", "ARROW_RAIN", "beginvalue").setValue(0.0);
				config.getNode("abilities", "ARROW_RAIN", "increment").setValue(0.05);
				config.getNode("abilities", "ARROW_RAIN", "maximum").setValue(50.0);
				config.getNode("abilities", "DISARM", "beginvalue").setValue(0.0);
				config.getNode("abilities", "DISARM", "increment").setValue(0.03);
				config.getNode("abilities", "DISARM", "maximum").setValue(15.0);
				
				config.getNode("abilities", "SALVAGE", "beginvalue").setValue(20.0);
				config.getNode("abilities", "SALVAGE", "increment").setValue(0.2);
				config.getNode("abilities", "SALVAGE", "maximum").setValue(100.0);
				config.getNode("abilities", "REPAIR", "beginvalue").setValue(10.0);
				config.getNode("abilities", "REPAIR", "increment").setValue(0.12);
				config.getNode("abilities", "REPAIR", "maximum").setValue(100.0);
				config.getNode("abilities", "TREASURE_HUNT", "beginvalue").setValue(2.0);
				config.getNode("abilities", "TREASURE_HUNT", "increment").setValue(0.04);
				config.getNode("abilities", "TREASURE_HUNT", "maximum").setValue(50.0);
				config.getNode("abilities", "WATER_TREASURE", "beginvalue").setValue(8.0);
				config.getNode("abilities", "WATER_TREASURE", "increment").setValue(0.2);
				config.getNode("abilities", "WATER_TREASURE", "maximum").setValue(80.0);
				config.getNode("abilities", "SUMMON_WOLF", "beginvalue").setValue(1000.0);
				config.getNode("abilities", "SUMMON_WOLF", "increment").setComment("SUMMON_WOLF increment must have a negative value");
				config.getNode("abilities", "SUMMON_WOLF", "increment").setValue(-2.0);
				config.getNode("abilities", "SUMMON_WOLF", "maximum").setValue(10000.0);
				config.getNode("abilities", "SUMMON_OCELOT", "beginvalue").setValue(1500.0);
				config.getNode("abilities", "SUMMON_OCELOT", "increment").setComment("SUMMON_OCELOT increment must have a negative value");
				config.getNode("abilities", "SUMMON_OCELOT", "increment").setValue(-2.0);
				config.getNode("abilities", "SUMMON_OCELOT", "maximum").setValue(10000.0);
				config.getNode("abilities", "SUMMON_HORSE", "beginvalue").setValue(2000.0);
				config.getNode("abilities", "SUMMON_HORSE", "increment").setComment("SUMMON_HORSE increment must have a negative value");
				config.getNode("abilities", "SUMMON_HORSE", "increment").setValue(-2.0);
				config.getNode("abilities", "SUMMON_HORSE", "maximum").setValue(10000.0);
				
		        manager.save(config);
			}
	        config = manager.load();
	        
	        if (!config.getChildrenMap().values().contains(config.getNode("unarmed"))) {
	        	file.delete();
	        	instance = new AdvancedConfig();
	        	instance.setup();
	        }
	        
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
	        
	        ImmutableMap.Builder<SkillType, double[]> b2 = ImmutableMap.builder();
	        skillExps = b2.put(SkillType.ACROBATICS, new double[]{config.getNode("acrobatics").getDouble()})
	        .put(SkillType.FISHING, new double[]{config.getNode("fishing").getDouble()})
	        .put(SkillType.TAMING, new double[]{config.getNode("taming").getDouble()})
	        
	        .put(SkillType.SWORDS, new double[]{config.getNode("swords", "kill").getDouble(), config.getNode("swords", "hurt").getDouble()})
	        .put(SkillType.AXES, new double[]{config.getNode("axes", "kill").getDouble(), config.getNode("axes", "hurt").getDouble()})
	        .put(SkillType.ARCHERY, new double[]{config.getNode("archery", "kill").getDouble(), config.getNode("archery", "hurt").getDouble()})
	        .put(SkillType.UNARMED, new double[]{config.getNode("unarmed", "kill").getDouble(), config.getNode("unarmed", "hurt").getDouble()})
	        .build();
	        
	        ImmutableMap.Builder<String, double[]> b3 = ImmutableMap.builder();
	        abilities = b3.put("Super Breaker", new double[]{config.getNode("abilities", "SUPER_BREAKER", "beginvalue").getDouble(), config.getNode("abilities", "SUPER_BREAKER", "increment").getDouble(), config.getNode("abilities", "SUPER_BREAKER", "maximum").getDouble()})
	        .put("Tree Feller", new double[]{config.getNode("abilities", "TREE_VELLER", "beginvalue").getDouble(), config.getNode("abilities", "TREE_VELLER", "increment").getDouble(), config.getNode("abilities", "TREE_VELLER", "maximum").getDouble()})
	        .put("Giga Drill Breaker", new double[]{config.getNode("abilities", "GIGA_DRILL_BREAKER", "beginvalue").getDouble(), config.getNode("abilities", "GIGA_DRILL_BREAKER", "increment").getDouble(), config.getNode("abilities", "GIGA_DRILL_BREAKER", "maximum").getDouble()})
	        .put("Green Terra", new double[]{config.getNode("abilities", "GREEN_TERRA", "beginvalue").getDouble(), config.getNode("abilities", "GREEN_TERRA", "increment").getDouble(), config.getNode("abilities", "GREEN_TERRA", "maximum").getDouble()})
	        .put("Mining Double Drop", new double[]{config.getNode("abilities", "MINING_DOUBLEDROP", "beginvalue").getDouble(), config.getNode("abilities", "MINING_DOUBLEDROP", "increment").getDouble(), config.getNode("abilities", "MINING_DOUBLEDROP", "maximum").getDouble()})
	        .put("Farming Double Drop", new double[]{config.getNode("abilities", "FARMING_DOUBLEDROP", "beginvalue").getDouble(), config.getNode("abilities", "FARMING_DOUBLEDROP", "increment").getDouble(), config.getNode("abilities", "FARMING_DOUBLEDROP", "maximum").getDouble()})
	        .put("Excavation Double Drop", new double[]{config.getNode("abilities", "EXCAVATION_DOUBLEDROP", "beginvalue").getDouble(), config.getNode("abilities", "EXCAVATION_DOUBLEDROP", "increment").getDouble(), config.getNode("abilities", "EXCAVATION_DOUBLEDROP", "maximum").getDouble()})
	        .put("Woodcutting Double Drop", new double[]{config.getNode("abilities", "WOODCUTTING_DOUBLEDROP", "beginvalue").getDouble(), config.getNode("abilities", "WOODCUTTING_DOUBLEDROP", "increment").getDouble(), config.getNode("abilities", "WOODCUTTING_DOUBLEDROP", "maximum").getDouble()})
	        .put("Roll", new double[]{config.getNode("abilities", "ROLL", "beginvalue").getDouble(), config.getNode("abilities", "ROLL", "increment").getDouble(), config.getNode("abilities", "ROLL", "maximum").getDouble()})
	        .put("Dodge", new double[]{config.getNode("abilities", "DODGE", "beginvalue").getDouble(), config.getNode("abilities", "DODGE", "increment").getDouble(), config.getNode("abilities", "DODGE", "maximum").getDouble()})
	        .put("Salvage", new double[]{config.getNode("abilities", "SALVAGE", "beginvalue").getDouble(), config.getNode("abilities", "SALVAGE", "increment").getDouble(), config.getNode("abilities", "SALVAGE", "maximum").getDouble()})
	        .put("Repair", new double[]{config.getNode("abilities", "REPAIR", "beginvalue").getDouble(), config.getNode("abilities", "REPAIR", "increment").getDouble(), config.getNode("abilities", "REPAIR", "maximum").getDouble()})
	        .put("Treasure Hunt", new double[]{config.getNode("abilities", "TREASURE_HUNT", "beginvalue").getDouble(), config.getNode("abilities", "TREASURE_HUNT", "increment").getDouble(), config.getNode("abilities", "TREASURE_HUNT", "maximum").getDouble()})
	        .put("Water Treasure", new double[]{config.getNode("abilities", "WATER_TREASURE", "beginvalue").getDouble(), config.getNode("abilities", "WATER_TREASURE", "increment").getDouble(), config.getNode("abilities", "WATER_TREASURE", "maximum").getDouble()})
	        .put("Wolf Summoning", new double[]{config.getNode("abilities", "SUMMON_WOLF", "beginvalue").getDouble(), config.getNode("abilities", "SUMMON_WOLF", "increment").getDouble(), config.getNode("abilities", "SUMMON_WOLF", "maximum").getDouble()})
	        .put("Ocelot Summoning", new double[]{config.getNode("abilities", "SUMMON_OCELOT", "beginvalue").getDouble(), config.getNode("abilities", "SUMMON_OCELOT", "increment").getDouble(), config.getNode("abilities", "SUMMON_OCELOT", "maximum").getDouble()})
	        .put("Horse Summoning", new double[]{config.getNode("abilities", "SUMMON_HORSE", "beginvalue").getDouble(), config.getNode("abilities", "SUMMON_HORSE", "increment").getDouble(), config.getNode("abilities", "SUMMON_HORSE", "maximum").getDouble()})
	        		
	        .put("Slaughter", new double[]{config.getNode("abilities", "SLAUGHTER", "beginvalue").getDouble(), config.getNode("abilities", "SLAUGHTER", "increment").getDouble(), config.getNode("abilities", "SLAUGHTER", "maximum").getDouble()})
	        .put("Bloodshed", new double[]{config.getNode("abilities", "BLOODSHED", "beginvalue").getDouble(), config.getNode("abilities", "BLOODSHED", "increment").getDouble(), config.getNode("abilities", "BLOODSHED", "maximum").getDouble()})
	        .put("Saitama Punch", new double[]{config.getNode("abilities", "SAITAMA_PUNCH", "beginvalue").getDouble(), config.getNode("abilities", "SAITAMA_PUNCH", "increment").getDouble(), config.getNode("abilities", "SAITAMA_PUNCH", "maximum").getDouble()})
	        .put("Decapitation", new double[]{config.getNode("abilities", "DECAPITATION", "beginvalue").getDouble(), config.getNode("abilities", "DECAPITATION", "increment").getDouble(), config.getNode("abilities", "DECAPITATION", "maximum").getDouble()})
	        .put("Arrow Rain", new double[]{config.getNode("abilities", "ARROW_RAIN", "beginvalue").getDouble(), config.getNode("abilities", "ARROW_RAIN", "increment").getDouble(), config.getNode("abilities", "ARROW_RAIN", "maximum").getDouble()})
	        .put("Disarm", new double[]{config.getNode("abilities", "DISARM", "beginvalue").getDouble(), config.getNode("abilities", "DISARM", "increment").getDouble(), config.getNode("abilities", "DISARM", "maximum").getDouble()})
	        .build();
		}
		catch (IOException e) {
			Main.getInstance().getLogger().error("Error while loading Advanced Config file");
			e.printStackTrace();
			return;
		}
		Main.getInstance().getLogger().info("Done Loading Advanced Config File");
	}
}