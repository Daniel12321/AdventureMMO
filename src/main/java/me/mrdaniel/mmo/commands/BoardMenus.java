package me.mrdaniel.mmo.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Scoreboard;

import me.mrdaniel.mmo.enums.Ability;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.skills.Skill;
import me.mrdaniel.mmo.utils.TopInfo;

public class BoardMenus {
	
	public static void sendMainInfo(Player p, String name, MMOPlayer mmop) {
		
		HashMap<Integer, String> lines = new HashMap<Integer, String>();
		lines.put(mmop.getSkills().getSkill(SkillType.MINING).level, "&bMining &7- ");
		lines.put(mmop.getSkills().getSkill(SkillType.WOODCUTTING).level, "&bWoodcutting &7- ");
		lines.put(mmop.getSkills().getSkill(SkillType.EXCAVATION).level, "&bExcavation &7- ");
		lines.put(mmop.getSkills().getSkill(SkillType.FISHING).level, "&bFishing &7- ");
		lines.put(mmop.getSkills().getSkill(SkillType.FARMING).level, "&bFarming &7- ");
		lines.put(mmop.getSkills().getSkill(SkillType.ACROBATICS).level, "&bAcrobatics &7- ");
		lines.put(mmop.getSkills().getSkill(SkillType.TAMING).level, "&bTaming &7- ");
		lines.put(mmop.getSkills().getSkill(SkillType.SALVAGE).level, "&bSalvage &7- ");
		lines.put(mmop.getSkills().getSkill(SkillType.REPAIR).level, "&bRepair &7- ");
		lines.put(mmop.totalLevels(), "&bTotal &7- ");
		
		Scoreboard board = ScoreboardManager.getScoreboard(lines, "&c}--=== &b" + name + " &c==---{");
		p.setScoreboard(board);
	}
	public static void sendSkillInfo(Player p, MMOPlayer mmop, SkillType type, Skill skill, ArrayList<Ability> abilities) {
		int level = skill.level;
		
		int c = 2 + (abilities.size()*4);
		HashMap<Integer, String> lines = new HashMap<Integer, String>();
		lines.put(c, "&aLevel: " + String.valueOf(level)); c--;
		lines.put(c, "&aEXP: " + String.valueOf(skill.exp) + "/" + String.valueOf(mmop.getSkills().expTillNextLevel(skill.level))); c--;
		
		for (int i = 0; i < abilities.size(); i++) {
			Ability ability = abilities.get(i);
			lines.put(c, ""); c--;
			lines.put(c, "&c-= &e" + ability.name + " &c=-"); c--;
			lines.put(c, "&2" + ability.desc); c--;
			lines.put(c, "&2" + ability.showState.create(ability.getValue(level))); c--;
		}
		Scoreboard board = ScoreboardManager.getScoreboard(lines, "&c}--=== &b" + type.name + " &c==---{");
		p.setScoreboard(board);
	}
	public static void sendTop(Player p, TreeMap<Integer, TopInfo> top, String title) {
		
		HashMap<Integer, String> lines = new HashMap<Integer, String>();
		for (int i : top.keySet()) {
			TopInfo topInfo = top.get(i);
			lines.put(topInfo.level, "&b" + topInfo.name + " &7- ");
		}
		Scoreboard board = ScoreboardManager.getScoreboard(lines, "&c}--=== &b" + title + " &c==---{");
		p.setScoreboard(board);
	}
}