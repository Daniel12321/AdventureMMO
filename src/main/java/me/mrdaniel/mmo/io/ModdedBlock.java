package me.mrdaniel.mmo.io;

import me.mrdaniel.mmo.enums.SkillType;

public class ModdedBlock {
	
	public String id;
	public SkillType type;
	public int exp;
	
	public ModdedBlock(String id, SkillType type, int exp) {
		this.id = id;
		this.type = type;
		this.exp = exp;
	}
}