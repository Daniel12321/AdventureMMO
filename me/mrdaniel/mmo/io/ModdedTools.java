package me.mrdaniel.mmo.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.ToolType;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ModdedTools {
	
	public static File file = new File("config/mmo/moddedtools.conf");
	public static ConfigurationLoader<CommentedConfigurationNode> manager = HoconConfigurationLoader.builder().setFile(file).build();
	public static CommentedConfigurationNode config = manager.createEmptyNode(ConfigurationOptions.defaults());
	
	private static ArrayList<ModdedTool> tools = new ArrayList<ModdedTool>();
	
	public static void setup() {
		Main.getInstance().getGame().getServer().getConsole().sendMessage(Text.of(TextColors.GREEN, "[SpongeMMO]: Loading Modded Tools file"));
		try {
			if (!file.exists()) {
				file.createNewFile();
				
				config.getNode("pixelmon:RubyPickaxe", "type").setValue("pickaxe");
				config.getNode("pixelmon:RubyAxe", "type").setValue("axe");
				config.getNode("pixelmon:RubyShovel", "type").setValue("shovel");
				config.getNode("pixelmon:RubyHoe", "type").setValue("hoe");
				
				config.getNode("pixelmon:SapphirePickaxe", "type").setValue("pickaxe");
				config.getNode("pixelmon:SapphireAxe", "type").setValue("axe");
				config.getNode("pixelmon:SapphireShovel", "type").setValue("shovel");
				config.getNode("pixelmon:SapphireHoe", "type").setValue("hoe");
				
				config.getNode("pixelmon:AmethystPickaxe", "type").setValue("pickaxe");
				config.getNode("pixelmon:AmethystAxe", "type").setValue("axe");
				config.getNode("pixelmon:AmethystShovel", "type").setValue("shovel");
				config.getNode("pixelmon:AmethystHoe", "type").setValue("hoe");
				
				config.getNode("pixelmon:CrystalPickaxe", "type").setValue("pickaxe");
				config.getNode("pixelmon:CrystalAxe", "type").setValue("axe");
				config.getNode("pixelmon:CrystalShovel", "type").setValue("shovel");
				config.getNode("pixelmon:CrystalHoe", "type").setValue("hoe");
				
				config.getNode("pixelmon:FirestonePickaxe", "type").setValue("pickaxe");
				config.getNode("pixelmon:FirestoneAxe", "type").setValue("axe");
				config.getNode("pixelmon:FirestoneShovel", "type").setValue("shovel");
				config.getNode("pixelmon:FirestoneHoe", "type").setValue("hoe");
				
				config.getNode("pixelmon:WaterstonePickaxe", "type").setValue("pickaxe");
				config.getNode("pixelmon:WaterstoneAxe", "type").setValue("axe");
				config.getNode("pixelmon:WaterstoneShovel", "type").setValue("shovel");
				config.getNode("pixelmon:WaterstoneHoe", "type").setValue("hoe");
				
				config.getNode("pixelmon:LeafstonePickaxe", "type").setValue("pickaxe");
				config.getNode("pixelmon:LeafstoneAxe", "type").setValue("axe");
				config.getNode("pixelmon:LeafstoneShovel", "type").setValue("shovel");
				config.getNode("pixelmon:LeafstoneHoe", "type").setValue("hoe");
				
				config.getNode("pixelmon:ThunderstonePickaxe", "type").setValue("pickaxe");
				config.getNode("pixelmon:ThunderstoneAxe", "type").setValue("axe");
				config.getNode("pixelmon:ThunderstoneShovel", "type").setValue("shovel");
				config.getNode("pixelmon:ThunderstoneHoe", "type").setValue("hoe");
				
				config.getNode("pixelmon:SunstonePickaxe", "type").setValue("pickaxe");
				config.getNode("pixelmon:SunstoneAxe", "type").setValue("axe");
				config.getNode("pixelmon:SunstoneShovel", "type").setValue("shovel");
				config.getNode("pixelmon:SunstoneHoe", "type").setValue("hoe");
				
				config.getNode("pixelmon:MoonstonePickaxe", "type").setValue("pickaxe");
				config.getNode("pixelmon:MoonstoneAxe", "type").setValue("axe");
				config.getNode("pixelmon:MoonstoneShovel", "type").setValue("shovel");
				config.getNode("pixelmon:MoonstoneHoe", "type").setValue("hoe");
				
				config.getNode("pixelmon:DawnstonePickaxe", "type").setValue("pickaxe");
				config.getNode("pixelmon:DawnstoneAxe", "type").setValue("axe");
				config.getNode("pixelmon:DawnstoneShovel", "type").setValue("shovel");
				config.getNode("pixelmon:DawnstoneHoe", "type").setValue("hoe");
				
				config.getNode("pixelmon:DuskstonePickaxe", "type").setValue("pickaxe");
				config.getNode("pixelmon:DuskstoneAxe", "type").setValue("axe");
				config.getNode("pixelmon:DuskstoneShovel", "type").setValue("shovel");
				config.getNode("pixelmon:DuskstoneHoe", "type").setValue("hoe");
				
				manager.save(config);
			}
	        config = manager.load();
	        
	        for (CommentedConfigurationNode idNode : config.getChildrenMap().values()) {
	        	ToolType type = ToolType.matchName(idNode.getNode("type").getString());
	        	if (type == null) { Main.getInstance().getGame().getServer().getConsole().sendMessage(Text.of(TextColors.RED, "[SpongeMMO]: Error finding type for item ", idNode.getKey().toString())); continue; }
	        	
	        	tools.add(new ModdedTool(idNode.getKey().toString(), type));
	        }
		}
		catch (IOException e) { Main.getInstance().getGame().getServer().getConsole().sendMessage(Text.of(TextColors.RED, "[SpongeMMO]: Error loading Modded Tools")); e.printStackTrace(); return; }
		Main.getInstance().getGame().getServer().getConsole().sendMessage(Text.of(TextColors.GREEN, "[SpongeMMO]: Loaded ", tools.size(), " Modded Tools"));
	}
	public static ModdedTool getToolType(String id) {
		for (ModdedTool mTool : tools) {
			if (mTool.id.equalsIgnoreCase(id)) {
				return mTool;
			}
		}
		return null;
	}
}