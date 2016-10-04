package me.mrdaniel.mmo.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.SkillType;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ModdedBlocks {
	
	private static ModdedBlocks instance = null;
	public static ModdedBlocks getInstance() {
		if (instance == null) { instance = new ModdedBlocks(); }
		return instance;
	}
	
	public File file;
	public ConfigurationLoader<CommentedConfigurationNode> manager;
	public CommentedConfigurationNode config;
	
	private ArrayList<ModdedBlock> blocks;
	
	private ModdedBlocks() {
		file = Main.getInstance().getFile().toPath().resolve("moddedblocks.conf").toFile();
		manager = HoconConfigurationLoader.builder().setFile(file).build();
		config = manager.createEmptyNode(ConfigurationOptions.defaults());
		blocks = new ArrayList<ModdedBlock>();
	}
	
	public void setup() {
		blocks.clear();
		Main.getInstance().getLogger().info("Loading Modded Blocks File");
		try {
			if (!file.exists()) {
				file.createNewFile();
				
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
				
		        manager.save(config);
			}
	        config = manager.load();
	        
	        for (CommentedConfigurationNode idNode : config.getChildrenMap().values()) {
	        	SkillType type = SkillType.match(idNode.getNode("type").getString());
	        	if (type == null) {
	        		Main.getInstance().getLogger().error("Error finding type for block " + idNode.getKey().toString());
	        		continue;
	        	}
	        	int exp = 0;
	        	try { exp = Integer.valueOf(idNode.getNode("exp").getInt()); }
	        	catch (Exception exc) {
	        		Main.getInstance().getLogger().error("Error finding exp level for block " + idNode.getKey().toString());
	        		continue;
	        	}
	        	
	        	blocks.add(new ModdedBlock(idNode.getKey().toString(), type, exp));
	        }
		}
		catch (IOException e) {
			Main.getInstance().getLogger().error("Error while loading Modded Blocks file");
			e.printStackTrace();
			return;
		}
		Main.getInstance().getLogger().info("Loading " + blocks.size() + " Modded Blocks");
	}
	public ModdedBlock getModdedBlock(String id) {
		for (ModdedBlock mBlock : blocks) {
			if (mBlock.id.equalsIgnoreCase(id)) {
				return mBlock;
			}
		}
		return null;
	}
}