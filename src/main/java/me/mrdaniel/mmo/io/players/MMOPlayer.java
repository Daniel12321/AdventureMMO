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
	
	/**
	 * Get the players UUID in String format.
	 * 
	 * @return UUID String
	 */
	public String getUUID() { 
		return uuid; 
	}
	
	/**
	 * Get the player's skills.
	 * 
	 * @return SkillSet
	 */
	public SkillSet getSkills() { 
		return skills; 
	}
	
	/**
	 * Process a SkillAction to add exp to a certain skill.
	 * 
	 * @param SkillAction
	 * To create a SkillAction, create a new instance of SkillAction with the exp and SkillType as parameters.
	 */
	public void process(SkillAction action) {
		if (skills.addExp(action.getType(), action.getExp())) {
			SkillManager.level(this, action.getType());
		}
	}
	
	/**
	 * Get the player total level.
	 * 
	 * @return int
	 * Total levels.
	 */
	public int totalLevels() { 
		int total = 0;
		for (SkillType type : SkillType.values()) { total += skills.getSkill(type).level; }
		return total;
	}
	
	/**
	 * Save the player.
	 */
	public void save() { 
		MMOPlayerDatabase.getInstance().save(this);
	}
	
	/**
	 * Update the SkillTop for this player.
	 * 
	 * @param String name
	 * The name of the player.
	 */
	public void updateTop(String name) {
		SkillTop.getInstance().update(name, this);
	}
	
	/**
	 * Update the SkillTop for this player.
	 * 
	 * @param String name
	 * The name of the player.
	 */
	public void updateTop(String name, SkillType type) {
		SkillTop.getInstance().getTop(type).update(name, skills.getSkill(type).level);
	}
}