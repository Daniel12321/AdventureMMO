package me.mrdaniel.mmo.skills;

import me.mrdaniel.mmo.enums.SkillType;

public class SkillSet {
	
	private Skill mining;
	private Skill woodcutting;
	private Skill excavation;
	private Skill fishing;
	private Skill farming;
	private Skill acrobatics;
	private Skill taming;
	private Skill salvage;
	private Skill repair;
	
	public SkillSet(Skill mining, Skill woodcutting, Skill excavation, Skill fishing, Skill farming, Skill acrobatics, Skill taming, Skill salvage, Skill repair) {
		this.mining = mining; this.woodcutting = woodcutting;
		this.excavation = excavation; this.fishing = fishing;
		this.farming = farming; this.acrobatics = acrobatics;
		this.taming = taming; this.salvage = salvage;
		this.repair = repair;
	}
	
	/**
	 * Get a new empty SkillSet.
	 * 
	 * @return SkillSet 
	 * Empty SkillSet
	 */
	public SkillSet() {
		this.mining = new Skill(0,0); this.woodcutting = new Skill(0,0);
		this.excavation = new Skill(0,0); this.fishing = new Skill(0,0);
		this.farming = new Skill(0,0); this.acrobatics = new Skill(0,0);
		this.taming = new Skill(0,0); this.salvage = new Skill(0,0);
		this.repair = new Skill(0,0);
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
	public Skill getSkill(SkillType type) {
		if (type == SkillType.MINING) { return mining; }
		else if (type == SkillType.WOODCUTTING) { return woodcutting; }
		else if (type == SkillType.EXCAVATION) { return excavation; }
		else if (type == SkillType.FISHING) { return fishing; }
		else if (type == SkillType.FARMING) { return farming; }
		else if (type == SkillType.ACROBATICS) { return acrobatics; }
		else if (type == SkillType.TAMING) { return taming; }
		else if (type == SkillType.SALVAGE) { return salvage; }
		else if (type == SkillType.REPAIR) { return repair; }
		else return null;
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
	public void setSkill(SkillType type, Skill skill) {
		if (type == SkillType.MINING) { mining = skill; }
		else if (type == SkillType.WOODCUTTING) { woodcutting = skill; }
		else if (type == SkillType.EXCAVATION) { excavation = skill; }
		else if (type == SkillType.FISHING) { fishing = skill; }
		else if (type == SkillType.FARMING) { farming = skill; }
		else if (type == SkillType.ACROBATICS) { acrobatics = skill; }
		else if (type == SkillType.TAMING) { taming = skill; }
		else if (type == SkillType.SALVAGE) { salvage = skill; }
		else if (type == SkillType.REPAIR) { repair = skill; }
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
	public boolean addExp(SkillType type, int exp) {
		Skill skill = getSkill(type);
		
		int newExp = exp + skill.exp;
		int nextLvl = expTillNextLevel(skill.level);
		
		if (newExp >= nextLvl) { skill.level = skill.level+1; skill.exp = newExp - nextLvl; return true; }
		else { skill.exp = newExp; return false; }
	}
	
	/**
	 * Add exp to a Skill
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
	public int[][] serialize() {
		int[][] sRaw = new int[SkillType.values().length][2];
		for (SkillType type : SkillType.values()) {
			Skill s = getSkill(type);
			int id = type.id;
			sRaw[id][0] = s.level; sRaw[id][1] = s.exp;
		}
		return sRaw;
	}
	
	/**
	 * Deserialize the int[][] into an SkillSet.
	 * Automatically updates SkillSet's from old versions.
	 * 
	 * @return SkillSet deserialized int[][].
	 * Returns the deserialized int[][].
	 */
	public static SkillSet deserialize(int[][] sRaw) {
		if (sRaw.length != SkillType.values().length) { return update(sRaw); }
		
		return new SkillSet(
				new Skill(sRaw[SkillType.MINING.id][0], sRaw[SkillType.MINING.id][1]),
				new Skill(sRaw[SkillType.WOODCUTTING.id][0], sRaw[SkillType.WOODCUTTING.id][1]),
				new Skill(sRaw[SkillType.EXCAVATION.id][0], sRaw[SkillType.EXCAVATION.id][1]),
				new Skill(sRaw[SkillType.FISHING.id][0], sRaw[SkillType.FISHING.id][1]),
				new Skill(sRaw[SkillType.FARMING.id][0], sRaw[SkillType.FARMING.id][1]),
				new Skill(sRaw[SkillType.ACROBATICS.id][0], sRaw[SkillType.ACROBATICS.id][1]),
				new Skill(sRaw[SkillType.TAMING.id][0], sRaw[SkillType.TAMING.id][1]),
				new Skill(sRaw[SkillType.SALVAGE.id][0], sRaw[SkillType.SALVAGE.id][1]),
				new Skill(sRaw[SkillType.REPAIR.id][0], sRaw[SkillType.REPAIR.id][1]));
	}
	private static SkillSet update(int[][] sRawOld) {
		int[][] sRaw = new int[SkillType.values().length][2];
		
		int lowest = (sRaw.length > sRawOld.length) ? sRawOld.length : sRaw.length;
		
		for (int i = 0; i < lowest; i++) {
			sRaw[i][0] = sRawOld[i][0]; sRaw[i][1] = sRawOld[i][1];
		}
		return deserialize(sRaw);
	}
}