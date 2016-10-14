package me.mrdaniel.mmo.io.top;

import java.io.File;
import java.nio.file.Path;

import me.mrdaniel.mmo.Main;
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
	private Top swords;
	private Top axes;
	private Top unarmed;
	private Top archery;
	
	public void setup() {
		
		File folder = Main.getInstance().getPath().resolve("top").toFile();
		if (!folder.exists()) folder.mkdir();
		Path path = folder.toPath();
		
		total = new Top(path.resolve("totaltop.conf").toFile());
		mining = new Top(path.resolve("miningtop.conf").toFile());
		woodcutting = new Top(path.resolve("woodcuttingtop.conf").toFile());
		excavation = new Top(path.resolve("excavationtop.conf").toFile());
		fishing = new Top(path.resolve("fishingtop.conf").toFile());
		farming = new Top(path.resolve("farmingtop.conf").toFile());
		acrobatics = new Top(path.resolve("acrobaticstop.conf").toFile());
		taming = new Top(path.resolve("tamingtop.conf").toFile());
		salvage = new Top(path.resolve("salvagetop.conf").toFile());
		repair = new Top(path.resolve("repairtop.conf").toFile());
		swords = new Top(path.resolve("swordstop.conf").toFile());
		axes = new Top(path.resolve("axestop.conf").toFile());
		unarmed = new Top(path.resolve("unarmedtop.conf").toFile());
		archery = new Top(path.resolve("archerystop.conf").toFile());
		
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
		swords.setup();
		axes.setup();
		unarmed.setup();
		archery.setup();
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
		swords.update(name, skills.getSkill(SkillType.SWORDS).level);
		axes.update(name, skills.getSkill(SkillType.AXES).level);
		unarmed.update(name, skills.getSkill(SkillType.UNARMED).level);
		archery.update(name, skills.getSkill(SkillType.ARCHERY).level);
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
		else if (type == SkillType.SWORDS) { return swords; }
		else if (type == SkillType.AXES) { return axes; }
		else if (type == SkillType.UNARMED) { return unarmed; }
		else if (type == SkillType.ARCHERY) { return archery; }
		return total;
	}
}