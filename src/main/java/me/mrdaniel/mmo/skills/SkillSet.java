package me.mrdaniel.mmo.skills;

import java.util.concurrent.ConcurrentHashMap;

import me.mrdaniel.mmo.enums.SkillType;

public class SkillSet {
	
	private ConcurrentHashMap<SkillType, Skill> skills;
	
	public SkillSet(Skill... values) {
		skills = new ConcurrentHashMap<SkillType, Skill>();
		loadDefaults();
		for (int i = 0; i < values.length; i++) {
			setSkill(SkillType.get(i), values[i]);
		}
	}
	private void loadDefaults() {
		for (SkillType t : SkillType.values()) {
			setSkill(t, new Skill(0,0));
		}
	}
	
	/**
	 * Get a Skill from the player.
	 * 
	 * @param SkillType type
	 * The SkillType of the skill you want to get.
	 * 
	 * @return Skill
	 * The skill info of the SkillType you gave.
	 */
	public synchronized Skill getSkill(SkillType type) {
		return skills.get(type);
	}
	
	/**
	 * Set a Skill from the player.
	 * 
	 * @param SkillType type
	 * The SkillType of the skill you want to set.
	 * 
	 * @param Skill skill
	 * The Skill that you want the player to have.
	 */
	public synchronized void setSkill(SkillType type, Skill skill) {
		skills.put(type, skill);
	}
	
	/**
	 * Add exp to a Skill
	 * 
 	 * @param SkillType type
	 * The SkillType you want to add exp to.
	 * 
  	 * @param int exp
	 * The amount of exp you want to add.
	 * 
	 * @return boolean levelup 
	 * Returns true when the aditional exp caused a levelup.
	 */
	public synchronized boolean addExp(SkillType type, int exp) {
		Skill skill = getSkill(type);
		
		int newExp = exp + skill.exp;
		int nextLvl = expTillNextLevel(skill.level);
		
		if (newExp >= nextLvl) { skill.level = skill.level+1; skill.exp = newExp - nextLvl; return true; }
		else { skill.exp = newExp; return false; }
	}
	
	/**
	 * Get the exp needed to level up.
	 * 
 	 * @param int level
	 * The level the player has.
	 * 
	 * @return int exp 
	 * Returns the amount of exp needed to levelup.
	 */
	public int expTillNextLevel(int level) { return 83 * level + 500; }
	
	/**
	 * Serialize the SkillSet into an int[][].
	 * 
	 * @return int[][] serialized SkillSet 
	 * Returns the serialized SkillSet.
	 */	
	public synchronized int[][] serialize() {
		int[][] sRaw = new int[SkillType.values().length][2];
		for (SkillType t : SkillType.values()) sRaw[t.id] = getSkill(t).serialize();
		return sRaw;
	}
	
	/**
	 * Deserialize the int[][] into a SkillSet.
	 * Automatically updates SkillSet's from old versions.
	 * 
	 * @return SkillSet deserialized int[][].
	 * Returns the deserialized int[][].
	 */
	public static SkillSet deserialize(int[][] sRaw) {		
		Skill[] skills = new Skill[sRaw.length];
		for (int i = 0; i < sRaw.length; i++) { skills[i] = new Skill(sRaw[i][0],sRaw[i][1]); }
		return new SkillSet(skills);
	}
}