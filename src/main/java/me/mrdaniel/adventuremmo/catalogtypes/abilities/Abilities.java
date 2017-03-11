package me.mrdaniel.adventuremmo.catalogtypes.abilities;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import me.mrdaniel.adventuremmo.catalogtypes.abilities.abilities.Bloodshed;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.abilities.FishCraze;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.abilities.GigaDrill;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.abilities.GreenThumbs;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.abilities.MadMiner;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.abilities.SaitamaPunch;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.abilities.Slaughter;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.abilities.TreeVeller;

public final class Abilities {

	private Abilities(){}

	// Active
	public static final ActiveAbility MAD_MINER = new MadMiner();
	public static final ActiveAbility GIGA_DRILL = new GigaDrill();
	public static final ActiveAbility TREE_VELLER = new TreeVeller();
	public static final ActiveAbility FISH_CRAZE = new FishCraze();
	public static final ActiveAbility GREEN_THUMBS = new GreenThumbs();
	public static final ActiveAbility BLOODSHED = new Bloodshed();
	public static final ActiveAbility SLAUGHTER = new Slaughter();
	public static final ActiveAbility SAITAMA_PUNCH = new SaitamaPunch();

	// Passive
	public static final PassiveAbility ROLL = new PassiveAbility("Roll", "roll", 0, 0.2);
	public static final PassiveAbility DODGE = new PassiveAbility("Dodge", "dodge", 0, 0.1);
	public static final PassiveAbility DISARM = new PassiveAbility("Disarm", "disarm", 5, 0.08);
	public static final PassiveAbility DECAPITATE = new PassiveAbility("Decapitate", "decapitate", 0, 0.1);
	public static final PassiveAbility ARROW_RAIN = new PassiveAbility("Arrow Rain", "arrowrain", 0, 0.1);
	public static final PassiveAbility TREASURE_HUNT = new PassiveAbility("Treasure Hunt", "treasurehunt", 1, 0.08);
	public static final PassiveAbility WATER_TREASURE = new PassiveAbility("Water Treasure", "watertreasure", 8, 0.2);
	public static final PassiveAbility DOUBLE_DROP = new PassiveAbility("Double Drop", "doubledrop", 0, 0.15);

	@Nonnull
	public static List<ActiveAbility> getActive() {
		return Lists.newArrayList(MAD_MINER, GIGA_DRILL, TREE_VELLER, FISH_CRAZE, GREEN_THUMBS, BLOODSHED, SLAUGHTER, SAITAMA_PUNCH);
	}

	@Nonnull
	public static List<PassiveAbility> getPassive() {
		return Lists.newArrayList(ROLL, DODGE, DISARM, DECAPITATE, ARROW_RAIN, TREASURE_HUNT, WATER_TREASURE, DOUBLE_DROP);
	}

	@Nonnull
	public static List<Ability> getAll() {
		return Lists.newArrayList(MAD_MINER, GIGA_DRILL, TREE_VELLER, FISH_CRAZE, GREEN_THUMBS, BLOODSHED, SLAUGHTER, SAITAMA_PUNCH, ROLL, DODGE, DISARM, DECAPITATE, ARROW_RAIN, TREASURE_HUNT, WATER_TREASURE, DOUBLE_DROP);
	}

	@Nonnull
	public static Optional<Ability> of(@Nonnull final String id) {
		for (Ability type : getAll()) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}