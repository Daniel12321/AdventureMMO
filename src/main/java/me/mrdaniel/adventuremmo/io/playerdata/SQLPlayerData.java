package me.mrdaniel.adventuremmo.io.playerdata;

import javax.annotation.Nonnull;

import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;

public class SQLPlayerData implements PlayerData {

	public SQLPlayerData() {
		;
	}

	public int getExp(@Nonnull final SkillType skill) {
		return 0;
	}

	public void setExp(@Nonnull final SkillType skill, final int exp) {
		;
	}

	public int getLevel(@Nonnull final SkillType skill) {
		return 0;
	}

	public void setLevel(@Nonnull final SkillType skill, final int level) {
		;
	}
}