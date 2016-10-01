package me.mrdaniel.mmo.skills;

import me.mrdaniel.mmo.enums.SkillType;

public class SkillAction {
	private SkillType type;
	private int exp;
	
	public SkillAction(SkillType type, int exp) {
		this.type = type;
		this.exp = exp;
	}
	public SkillAction(SkillType type, double exp) {
		this.type = type;
		this.exp = (int)exp;
	}
	
	public SkillType getType() { return type; }
	public int getExp() { return exp; }
}