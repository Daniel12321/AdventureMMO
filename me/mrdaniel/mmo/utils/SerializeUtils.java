package me.mrdaniel.mmo.utils;

import me.mrdaniel.mmo.skills.Skill;

public class SerializeUtils {
	public static Skill deserializeSkill(String str) {
		String[] split = str.split(":");
		Skill skill = new Skill(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
		return skill;
	}
}