package me.mrdaniel.mmo.io.top;

import java.io.File;

import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.skills.SkillSet;

public class SkillTop {
	
	private static SkillTop instance = null;
	public static SkillTop getInstance() { if (instance == null) { instance = new SkillTop(); } return instance; }
	private SkillTop() {}
	
	private Top total;
	private Top mining;
	private Top woodcutting;
	private Top excavation;
	private Top fishing;
	private Top farming;
	private Top acrobatics;
	private Top taming;
	private Top salvage;
	private Top repair;
	
	public void setup() {
		File folder = new File("config/mmo/top");
		if (!folder.exists()) folder.mkdir();
		
		total = new Top(new File("config/mmo/top/totaltop.conf"));
		mining = new Top(new File("config/mmo/top/miningtop.conf"));
		woodcutting = new Top(new File("config/mmo/top/woodcuttingtop.conf"));
		excavation = new Top(new File("config/mmo/top/excavationtop.conf"));
		fishing = new Top(new File("config/mmo/top/fishingtop.conf"));
		farming = new Top(new File("config/mmo/top/farmingtop.conf"));
		acrobatics = new Top(new File("config/mmo/top/acrobaticstop.conf"));
		taming = new Top(new File("config/mmo/top/tamingtop.conf"));
		salvage = new Top(new File("config/mmo/top/salvagetop.conf"));
		repair = new Top(new File("config/mmo/top/repairtop.conf"));
		
		total.setup();
		mining.setup();
		woodcutting.setup();
		excavation.setup();
		fishing.setup();
		farming.setup();
		acrobatics.setup();
		taming.setup();
		salvage.setup();
		repair.setup();
	}
	public void update(String name, MMOPlayer mmop) {
		SkillSet skills = mmop.getSkills();
		
		total.update(name, mmop.totalLevels());
		mining.update(name, skills.getSkill(SkillType.MINING).level);
		woodcutting.update(name, skills.getSkill(SkillType.WOODCUTTING).level);
		excavation.update(name, skills.getSkill(SkillType.EXCAVATION).level);
		fishing.update(name, skills.getSkill(SkillType.FISHING).level);
		farming.update(name, skills.getSkill(SkillType.FARMING).level);
		acrobatics.update(name, skills.getSkill(SkillType.ACROBATICS).level);
		taming.update(name, skills.getSkill(SkillType.TAMING).level);
		salvage.update(name, skills.getSkill(SkillType.SALVAGE).level);
		repair.update(name, skills.getSkill(SkillType.REPAIR).level);
	}
	public Top getTop(SkillType type) {
		if (type == null) { return total; }
		else if (type == SkillType.MINING) { return mining; }
		else if (type == SkillType.WOODCUTTING) { return woodcutting; }
		else if (type == SkillType.EXCAVATION) { return excavation; }
		else if (type == SkillType.FISHING) { return fishing; }
		else if (type == SkillType.FARMING) { return farming; }
		else if (type == SkillType.ACROBATICS) { return acrobatics; }
		else if (type == SkillType.TAMING) { return taming; }
		else if (type == SkillType.SALVAGE) { return salvage; }
		else if (type == SkillType.REPAIR) { return repair; }
		return total;
	}
}