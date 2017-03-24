package me.mrdaniel.adventuremmo.io.playerdata;

import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;

public interface PlayerData {

	int getExp(SkillType skill);
	void setExp(SkillType skill, final int exp);
	int getLevel(SkillType skill);
	void setLevel(SkillType skill, final int level);

	default int getLevels() { return SkillTypes.VALUES.stream().mapToInt(this::getLevel).sum(); }
}