package me.mrdaniel.adventuremmo.catalogtypes.skills;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import me.mrdaniel.adventuremmo.catalogtypes.abilities.Abilities;
import me.mrdaniel.adventuremmo.listeners.skills.AcrobaticsListener;
import me.mrdaniel.adventuremmo.listeners.skills.ArcheryListener;
import me.mrdaniel.adventuremmo.listeners.skills.AxesListener;
import me.mrdaniel.adventuremmo.listeners.skills.ExcavationListener;
import me.mrdaniel.adventuremmo.listeners.skills.FarmingListener;
import me.mrdaniel.adventuremmo.listeners.skills.FishingListener;
import me.mrdaniel.adventuremmo.listeners.skills.MiningListener;
import me.mrdaniel.adventuremmo.listeners.skills.SwordsListener;
import me.mrdaniel.adventuremmo.listeners.skills.UnarmedListener;
import me.mrdaniel.adventuremmo.listeners.skills.WoodcuttingListener;

public final class SkillTypes {

	private SkillTypes(){}

	public static final SkillType MINING = new SkillType("Mining", "mining", (mmo, config) -> new MiningListener(mmo), Abilities.MAD_MINER, Abilities.DOUBLE_DROP);
	public static final SkillType WOODCUTTING = new SkillType("Woodcutting", "woodcutting", (mmo, config) -> new WoodcuttingListener(mmo), Abilities.TREE_VELLER, Abilities.DOUBLE_DROP);
	public static final SkillType EXCAVATION = new SkillType("Excavation", "excavation", (mmo, config) -> new ExcavationListener(mmo, config), Abilities.GIGA_DRILL, Abilities.TREASURE_HUNT, Abilities.DOUBLE_DROP);
	public static final SkillType FISHING = new SkillType("Fishing", "fishing", (mmo, config) -> new FishingListener(mmo, config), Abilities.FISH_FRENZY, Abilities.WATER_TREASURE, Abilities.DOUBLE_DROP);
	public static final SkillType FARMING = new SkillType("Farming", "farming", (mmo, config) -> new FarmingListener(mmo), Abilities.GREEN_THUMBS, Abilities.DOUBLE_DROP);
	public static final SkillType ACROBATICS = new SkillType("Acrobatics", "acrobatics", (mmo, config) -> new AcrobaticsListener(mmo, config.getNode("skills", "acrobatics", "exp_multiplier").getDouble(5.0)), Abilities.ROLL, Abilities.DODGE);
	public static final SkillType SWORDS = new SkillType("Swords", "swords", (mmo, config) -> new SwordsListener(mmo, config.getNode("skills", "swords", "damage_exp").getInt(20), config.getNode("skills", "swords", "kill_exp").getInt(200)), Abilities.BLOODSHED, Abilities.DECAPITATE);
	public static final SkillType AXES = new SkillType("Axes", "axes", (mmo, config) -> new AxesListener(mmo, config.getNode("skills", "axes", "damage_exp").getInt(20), config.getNode("skills", "axes", "kill_exp").getInt(200)), Abilities.SLAUGHTER, Abilities.DECAPITATE);
	public static final SkillType UNARMED = new SkillType("Unarmed", "unarmed", (mmo, config) -> new UnarmedListener(mmo, config.getNode("skills", "unarmed", "damage_exp").getInt(10), config.getNode("skills", "unarmed", "kill_exp").getInt(200)), Abilities.SAITAMA_PUNCH, Abilities.DISARM);
	public static final SkillType ARCHERY = new SkillType("Archery", "archery", (mmo, config) -> new ArcheryListener(mmo, config.getNode("skills", "archery", "damage_exp").getInt(35), config.getNode("skills", "archery", "kill_exp").getInt(200)), Abilities.ARROW_RAIN);
//	public static final SkillType TAMING = new SkillType("Taming", "taming");
//	public static final SkillType SALVAGE = new SkillType("Salvage", "salvage");
//	public static final SkillType REPAIR = new SkillType("Repair", "repair");

	public static final List<SkillType> VALUES = Lists.newArrayList(MINING, WOODCUTTING, EXCAVATION, FISHING, FARMING, ACROBATICS, SWORDS, AXES, UNARMED, ARCHERY);

	@Nonnull
	public static Optional<SkillType> of(@Nonnull final String id) {
		for (SkillType type : VALUES) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}