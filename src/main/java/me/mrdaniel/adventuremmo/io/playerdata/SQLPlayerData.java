package me.mrdaniel.adventuremmo.io.playerdata;

import javax.annotation.Nonnull;

import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;

public class SQLPlayerData implements PlayerData {

	private long last_use;

	public SQLPlayerData() {
		this.last_use = System.currentTimeMillis();
	}

	@Override public int getExp(@Nonnull final SkillType skill) { this.setLastuse(); return 0; }
	@Override public void setExp(@Nonnull final SkillType skill, final int exp) { this.setLastuse(); }
	@Override public int getLevel(@Nonnull final SkillType skill) { this.setLastuse(); return 0; }
	@Override public void setLevel(@Nonnull final SkillType skill, final int level) { this.setLastuse(); }

	@Override public long getLastUse() { return this.last_use; }
	private void setLastuse() { this.last_use = System.currentTimeMillis(); }

	@Override public void save() { ; }
}