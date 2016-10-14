package me.mrdaniel.mmo.commands;

import java.util.ArrayList;
import java.util.TreeMap;

import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.mmo.enums.Ability;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.scoreboard.BoardLine;
import me.mrdaniel.mmo.scoreboard.ScoreboardManager;
import me.mrdaniel.mmo.skills.Skill;
import me.mrdaniel.mmo.utils.TopInfo;

public class BoardMenus {
	
	public static void sendSkillInfo(Player p, MMOPlayer mmop, SkillType type, Skill skill, ArrayList<Ability> abilities, String cmd) {
		int level = skill.level;
		
		int c = 2 + (abilities.size()*4);
		ArrayList<BoardLine> lines = new ArrayList<BoardLine>();
		lines.add(new BoardLine("&aLevel: " + String.valueOf(level), c)); c--;
		lines.add(new BoardLine("&aEXP: " + String.valueOf(skill.exp) + "/" + String.valueOf(mmop.getSkills().expTillNextLevel(skill.level)), c)); c--;
		
		for (int i = 0; i < abilities.size(); i++) {
			Ability ability = abilities.get(i);
			lines.add(new BoardLine("", c)); c--;
			lines.add(new BoardLine("&c}-= &e" + ability.name + " &c=-{", c)); c--;
			lines.add(new BoardLine("&2" + ability.desc, c)); c--;
			lines.add(new BoardLine("&2" + ability.showState.create(ability.getValue(level)), c)); c--;
		}
		ScoreboardManager.getInstance().setScoreboard(p, mmop, lines, "&c}--=== &b" + type.name + " &c==---{", cmd);
	}
	public static void sendTop(Player p, MMOPlayer mmop, TreeMap<Integer, TopInfo> top, String title, String cmd) {
		
		ArrayList<BoardLine> lines = new ArrayList<BoardLine>();
		for (int i : top.keySet()) {
			TopInfo topInfo = top.get(i);
			lines.add(new BoardLine("&b" + topInfo.name + " &7- ", topInfo.level));
		}
		ScoreboardManager.getInstance().setScoreboard(p, mmop, lines, "&c}--=== &b" + title + " &c==---{", cmd);
	}
}