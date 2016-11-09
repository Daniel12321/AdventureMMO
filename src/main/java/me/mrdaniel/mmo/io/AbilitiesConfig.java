package me.mrdaniel.mmo.io;

import java.io.File;
import java.io.IOException;

import com.google.common.collect.ImmutableMap;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.Ability;
import me.mrdaniel.mmo.enums.SkillType;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class AbilitiesConfig {
	
	private static AbilitiesConfig instance = null;
	public static AbilitiesConfig getInstance() {
		if (instance == null) { instance = new AbilitiesConfig(); }
		return instance;
	}
	
	public File file;
	public ConfigurationLoader<CommentedConfigurationNode> manager;
	public CommentedConfigurationNode config;

	public ImmutableMap<SkillType, double[]> skillExps;
	public ImmutableMap<Ability, double[]> abilities;

	private AbilitiesConfig() {
		file = Main.getInstance().getPath().resolve("abilities.conf").toFile();
		manager = HoconConfigurationLoader.builder().setFile(file).build();
		config = manager.createEmptyNode(ConfigurationOptions.defaults());
	}

	public void setup() {
		Main.getInstance().getLogger().info("Loading Abilities Config File");
		try {
			if (!file.exists()) {
				file.createNewFile();

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

				config.getNode("superbreaker", "blocked").setValue(false);
				config.getNode("treeveller", "blocked").setValue(false);
				config.getNode("gigadrillbreaker", "blocked").setValue(false);
				config.getNode("greenterra", "blocked").setValue(false);
				config.getNode("miningdoubledrop", "blocked").setValue(false);
				config.getNode("farmingdoubledrop", "blocked").setValue(false);
				config.getNode("excavationdoubledrop", "blocked").setValue(false);
				config.getNode("woodcuttingdoubledrop", "blocked").setValue(false);
				config.getNode("roll", "blocked").setValue(false);
				config.getNode("dodge", "blocked").setValue(false);
				config.getNode("salvage", "blocked").setValue(false);
				config.getNode("repair", "blocked").setValue(false);
				config.getNode("treasurehunt", "blocked").setValue(false);
				config.getNode("watertreasure", "blocked").setValue(false);
				config.getNode("summonwolf", "blocked").setValue(false);
				config.getNode("summonocelot", "blocked").setValue(false);
				config.getNode("summonhorse", "blocked").setValue(false);

				config.getNode("slaughter", "blocked").setValue(false);
				config.getNode("bloodshed", "blocked").setValue(false);
				config.getNode("saitama_punch", "blocked").setValue(false);
				config.getNode("decapitation", "blocked").setValue(false);
				config.getNode("arrowrain", "blocked").setValue(false);
				config.getNode("disarm", "blocked").setValue(false);
				
				config.getNode("superbreaker", "beginvalue").setValue(5.0);
				config.getNode("superbreaker", "increment").setValue(0.08);
				config.getNode("superbreaker", "maximum").setValue(45.0);
				config.getNode("treeveller", "beginvalue").setValue(5.0);
				config.getNode("treeveller", "increment").setValue(0.08);
				config.getNode("treeveller", "maximum").setValue(45.0);
				config.getNode("gigadrillbreaker", "beginvalue").setValue(5.0);
				config.getNode("gigadrillbreaker", "increment").setValue(0.08);
				config.getNode("gigadrillbreaker", "maximum").setValue(45.0);
				config.getNode("greenterra", "beginvalue").setValue(5.0);
				config.getNode("greenterra", "increment").setValue(0.08);
				config.getNode("greenterra", "maximum").setValue(45.0);
				config.getNode("slaughter", "beginvalue").setValue(5.0);
				config.getNode("slaughter", "increment").setValue(0.08);
				config.getNode("slaughter", "maximum").setValue(45.0);
				config.getNode("bloodshed", "beginvalue").setValue(5.0);
				config.getNode("bloodshed", "increment").setValue(0.08);
				config.getNode("bloodshed", "maximum").setValue(45.0);
				config.getNode("saitama_punch", "beginvalue").setValue(5.0);
				config.getNode("saitama_punch", "increment").setValue(0.08);
				config.getNode("saitama_punch", "maximum").setValue(45.0);
				
				config.getNode("miningdoubledrop", "beginvalue").setValue(0.0);
				config.getNode("miningdoubledrop", "increment").setValue(0.2);
				config.getNode("miningdoubledrop", "maximum").setValue(85.0);
				config.getNode("farmingdoubledrop", "beginvalue").setValue(0.0);
				config.getNode("farmingdoubledrop", "increment").setValue(0.2);
				config.getNode("farmingdoubledrop", "maximum").setValue(85.0);
				config.getNode("excavationdoubledrop", "beginvalue").setValue(0);
				config.getNode("excavationdoubledrop", "increment").setValue(0.2);
				config.getNode("excavationdoubledrop", "maximum").setValue(85.0);
				config.getNode("woodcuttingdoubledrop", "beginvalue").setValue(0.0);
				config.getNode("woodcuttingdoubledrop", "increment").setValue(0.2);
				config.getNode("woodcuttingdoubledrop", "maximum").setValue(85.0);
				config.getNode("roll", "beginvalue").setValue(0.0);
				config.getNode("roll", "increment").setValue(0.16);
				config.getNode("roll", "maximum").setValue(50.0);
				config.getNode("dodge", "beginvalue").setValue(0.0);
				config.getNode("dodge", "increment").setValue(0.08);
				config.getNode("dodge", "maximum").setValue(30.0);
				config.getNode("decapitation", "beginvalue").setValue(0.0);
				config.getNode("decapitation", "increment").setValue(0.03);
				config.getNode("decapitation", "maximum").setValue(30.0);
				config.getNode("arrowrain", "beginvalue").setValue(0.0);
				config.getNode("arrowrain", "increment").setValue(0.05);
				config.getNode("arrowrain", "maximum").setValue(50.0);
				config.getNode("disarm", "beginvalue").setValue(0.0);
				config.getNode("disarm", "increment").setValue(0.03);
				config.getNode("disarm", "maximum").setValue(15.0);
				
				config.getNode("salvage", "beginvalue").setValue(20.0);
				config.getNode("salvage", "increment").setValue(0.2);
				config.getNode("salvage", "maximum").setValue(100.0);
				config.getNode("repair", "beginvalue").setValue(10.0);
				config.getNode("repair", "increment").setValue(0.12);
				config.getNode("repair", "maximum").setValue(100.0);
				config.getNode("treasurehunt", "beginvalue").setValue(2.0);
				config.getNode("treasurehunt", "increment").setValue(0.04);
				config.getNode("treasurehunt", "maximum").setValue(50.0);
				config.getNode("watertreasure", "beginvalue").setValue(8.0);
				config.getNode("watertreasure", "increment").setValue(0.2);
				config.getNode("watertreasure", "maximum").setValue(80.0);
				config.getNode("summonwolf", "beginvalue").setValue(1000.0);
				config.getNode("summonwolf", "increment").setComment("summonwolf increment must have a negative value");
				config.getNode("summonwolf", "increment").setValue(-2.0);
				config.getNode("summonwolf", "maximum").setValue(10000.0);
				config.getNode("summonocelot", "beginvalue").setValue(1500.0);
				config.getNode("summonocelot", "increment").setComment("summonocelot increment must have a negative value");
				config.getNode("summonocelot", "increment").setValue(-2.0);
				config.getNode("summonocelot", "maximum").setValue(10000.0);
				config.getNode("summonhorse", "beginvalue").setValue(2000.0);
				config.getNode("summonhorse", "increment").setComment("summonhorse increment must have a negative value");
				config.getNode("summonhorse", "increment").setValue(-2.0);
				config.getNode("summonhorse", "maximum").setValue(10000.0);
				
		        manager.save(config);
			}
	        config = manager.load();

	        ImmutableMap.Builder<SkillType, double[]> b2 = ImmutableMap.builder();
	        this.skillExps = b2.put(SkillType.ACROBATICS, new double[]{config.getNode("acrobatics").getDouble()})
	        .put(SkillType.FISHING, new double[]{config.getNode("fishing").getDouble()})
	        .put(SkillType.TAMING, new double[]{config.getNode("taming").getDouble()})
	        
	        .put(SkillType.SWORDS, new double[]{config.getNode("swords", "kill").getDouble(), config.getNode("swords", "hurt").getDouble()})
	        .put(SkillType.AXES, new double[]{config.getNode("axes", "kill").getDouble(), config.getNode("axes", "hurt").getDouble()})
	        .put(SkillType.ARCHERY, new double[]{config.getNode("archery", "kill").getDouble(), config.getNode("archery", "hurt").getDouble()})
	        .put(SkillType.UNARMED, new double[]{config.getNode("unarmed", "kill").getDouble(), config.getNode("unarmed", "hurt").getDouble()})
	        .build();
	        
	        ImmutableMap.Builder<Ability, double[]> b3 = ImmutableMap.builder();
	        this.abilities = b3
	        		
	        .put(Ability.SUPER_BREAKER, new double[]{config.getNode("superbreaker", "beginvalue").getDouble(), config.getNode("superbreaker", "increment").getDouble(), config.getNode("superbreaker", "maximum").getDouble()})
	        .put(Ability.TREE_VELLER, new double[]{config.getNode("treeveller", "beginvalue").getDouble(), config.getNode("treeveller", "increment").getDouble(), config.getNode("treeveller", "maximum").getDouble()})
	        .put(Ability.GIGA_DRILL_BREAKER, new double[]{config.getNode("gigadrillbreaker", "beginvalue").getDouble(), config.getNode("gigadrillbreaker", "increment").getDouble(), config.getNode("gigadrillbreaker", "maximum").getDouble()})
	        .put(Ability.GREEN_TERRA, new double[]{config.getNode("greenterra", "beginvalue").getDouble(), config.getNode("greenterra", "increment").getDouble(), config.getNode("greenterra", "maximum").getDouble()})
	        .put(Ability.MINING_DOUBLEDROP, new double[]{config.getNode("miningdoubledrop", "beginvalue").getDouble(), config.getNode("miningdoubledrop", "increment").getDouble(), config.getNode("miningdoubledrop", "maximum").getDouble()})
	        .put(Ability.FARMING_DOUBLEDROP, new double[]{config.getNode("farmingdoubledrop", "beginvalue").getDouble(), config.getNode("farmingdoubledrop", "increment").getDouble(), config.getNode("farmingdoubledrop", "maximum").getDouble()})
	        .put(Ability.EXCAVATION_DOUBLEDROP, new double[]{config.getNode("excavationdoubledrop", "beginvalue").getDouble(), config.getNode("excavationdoubledrop", "increment").getDouble(), config.getNode("excavationdoubledrop", "maximum").getDouble()})
	        .put(Ability.WOODCUTTING_DOUBLEDROP, new double[]{config.getNode("woodcuttingdoubledrop", "beginvalue").getDouble(), config.getNode("woodcuttingdoubledrop", "increment").getDouble(), config.getNode("woodcuttingdoubledrop", "maximum").getDouble()})
	        .put(Ability.ROLL, new double[]{config.getNode("roll", "beginvalue").getDouble(), config.getNode("roll", "increment").getDouble(), config.getNode("roll", "maximum").getDouble()})
	        .put(Ability.DODGE, new double[]{config.getNode("dodge", "beginvalue").getDouble(), config.getNode("dodge", "increment").getDouble(), config.getNode("dodge", "maximum").getDouble()})
	        .put(Ability.SALVAGE, new double[]{config.getNode("salvage", "beginvalue").getDouble(), config.getNode("salvage", "increment").getDouble(), config.getNode("salvage", "maximum").getDouble()})
	        .put(Ability.REPAIR, new double[]{config.getNode("repair", "beginvalue").getDouble(), config.getNode("repair", "increment").getDouble(), config.getNode("repair", "maximum").getDouble()})
	        .put(Ability.TREASURE_HUNT, new double[]{config.getNode("treasurehunt", "beginvalue").getDouble(), config.getNode("treasurehunt", "increment").getDouble(), config.getNode("treasurehunt", "maximum").getDouble()})
	        .put(Ability.WATER_TREASURE, new double[]{config.getNode("watertreasure", "beginvalue").getDouble(), config.getNode("watertreasure", "increment").getDouble(), config.getNode("watertreasure", "maximum").getDouble()})
	        .put(Ability.SUMMON_WOLF, new double[]{config.getNode("summonwolf", "beginvalue").getDouble(), config.getNode("summonwolf", "increment").getDouble(), config.getNode("summonwolf", "maximum").getDouble()})
	        .put(Ability.SUMMON_OCELOT, new double[]{config.getNode("summonocelot", "beginvalue").getDouble(), config.getNode("summonocelot", "increment").getDouble(), config.getNode("summonocelot", "maximum").getDouble()})
	        .put(Ability.SUMMON_HORSE, new double[]{config.getNode("summonhorse", "beginvalue").getDouble(), config.getNode("summonhorse", "increment").getDouble(), config.getNode("summonhorse", "maximum").getDouble()})
	        		
	        .put(Ability.SLAUGHTER, new double[]{config.getNode("slaughter", "beginvalue").getDouble(), config.getNode("slaughter", "increment").getDouble(), config.getNode("slaughter", "maximum").getDouble()})
	        .put(Ability.BLOODSHED, new double[]{config.getNode("bloodshed", "beginvalue").getDouble(), config.getNode("bloodshed", "increment").getDouble(), config.getNode("bloodshed", "maximum").getDouble()})
	        .put(Ability.SAITAMA_PUNCH, new double[]{config.getNode("saitama_punch", "beginvalue").getDouble(), config.getNode("saitama_punch", "increment").getDouble(), config.getNode("saitama_punch", "maximum").getDouble()})
	        .put(Ability.DECAPITATION, new double[]{config.getNode("decapitation", "beginvalue").getDouble(), config.getNode("decapitation", "increment").getDouble(), config.getNode("decapitation", "maximum").getDouble()})
	        .put(Ability.ARROW_RAIN, new double[]{config.getNode("arrowrain", "beginvalue").getDouble(), config.getNode("arrowrain", "increment").getDouble(), config.getNode("arrowrain", "maximum").getDouble()})
	        .put(Ability.DISARM, new double[]{config.getNode("disarm", "beginvalue").getDouble(), config.getNode("disarm", "increment").getDouble(), config.getNode("disarm", "maximum").getDouble()})
	        .build();
		}
		catch (IOException e) {
			Main.getInstance().getLogger().error("Error while loading Abilities Config file");
			e.printStackTrace();
			return;
		}
		Main.getInstance().getLogger().info("Done Loading Abilities Config File");
	}
}