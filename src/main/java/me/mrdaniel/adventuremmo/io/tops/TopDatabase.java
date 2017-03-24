package me.mrdaniel.adventuremmo.io.tops;

import java.util.Map;

import org.spongepowered.api.util.Tuple;

import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;

public interface TopDatabase {

	void update(String player, SkillType skill, int level);
	Map<Integer, Tuple<String, Integer>> getTop(SkillType skill);
}