package me.mrdaniel.mmo.skills;

public class Skill {
	public int level;
	public int exp;
	
	public Skill(int level, int exp) {
		this.level = level;
		this.exp = exp;
	}
	public String serialize() { String str = String.valueOf(level) + ":" + String.valueOf(exp); return str; }
}