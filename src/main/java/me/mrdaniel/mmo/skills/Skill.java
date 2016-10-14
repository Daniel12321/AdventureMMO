package me.mrdaniel.mmo.skills;

public class Skill {
	public int level;
	public int exp;
	
	public Skill(int level, int exp) {
		this.level = level;
		this.exp = exp;
	}
	public int[] serialize() {
		return new int[]{level,exp};
	}
}