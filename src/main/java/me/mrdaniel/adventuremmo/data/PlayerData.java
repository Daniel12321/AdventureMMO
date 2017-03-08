package me.mrdaniel.adventuremmo.data;

import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.adventuremmo.enums.SkillType;

public interface PlayerData {

	public int getLevels();
	public int getLevel(SkillType skill);
	public void setLevel(SkillType skill, int level);
	public void addLevel(SkillType skill, int level);

	public int getExp(SkillType skill);
	public void setExp(SkillType skill, int exp);
	public void addExp(Player p, SkillType skill, int exp);
}