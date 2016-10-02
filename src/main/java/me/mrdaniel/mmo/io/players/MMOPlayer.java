package me.mrdaniel.mmo.io.players;

import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.top.SkillTop;
import me.mrdaniel.mmo.skills.SkillAction;
import me.mrdaniel.mmo.skills.SkillManager;
import me.mrdaniel.mmo.skills.SkillSet;

public class MMOPlayer {
	
	private String uuid;
	private SkillSet skills;
	
	public MMOPlayer(String uuid, SkillSet skills) {
		this.uuid = uuid;
		this.skills = skills;
	}
	public String getUUID() { return uuid; }
	public SkillSet getSkills() { return skills; }
	
	public void process(SkillAction action) {
		if (skills.addExp(action.getType(), action.getExp())) { SkillManager.level(this, action.getType()); }
	}
	public int totalLevels() { 
		int total = 0;
		for (SkillType type : SkillType.values()) { total += skills.getSkill(type).level; }
		return total;
	}
	public void save() { MMOPlayerDatabase.getInstance().save(this); }
	public void updateTop(String name) { SkillTop.getInstance().update(name, this); }
	public void updateTop(String name, SkillType type) { SkillTop.getInstance().getTop(type).update(name, skills.getSkill(type).level); }
}