package me.mrdaniel.mmo.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Tuple;

import com.google.common.collect.Lists;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.Ability;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.scoreboard.BoardLine;
import me.mrdaniel.mmo.skills.Skill;

public class BoardMenus {

	public static void sendSkill(Player p, MMOPlayer mmop, SkillType type) {
		Skill skill = mmop.getSkills().getSkill(type);
		List<Ability> abilities = Ability.getAll(type);

		int c = 2 + (abilities.size()*4);
		List<BoardLine> lines = Lists.newArrayList();
		lines.add(new BoardLine(Text.of(TextColors.GREEN, "Level: ", skill.level), c)); c--;
		lines.add(new BoardLine(Text.of(TextColors.GREEN, "EXP: ", skill.exp, "/", mmop.getSkills().expTillNextLevel(skill.level)), c)); c--;

		for (Ability ability : abilities) {
			lines.add(new BoardLine(Text.of(""), c)); c--;
			lines.add(new BoardLine(Text.of(TextColors.RED, "}-= ", TextColors.YELLOW, ability.getName(), TextColors.RED, " =-{"), c)); c--;
			lines.add(new BoardLine(Text.of(TextColors.AQUA, ability.getDesc()), c)); c--;
			lines.add(new BoardLine(ability.getShowState().create(TextColors.DARK_GREEN, Main.getInstance().getValueStore().getAbility(ability).getSecond().getValue(skill.level)), c)); c--;
		}
		Main.getInstance().getScoreboardManager().setScoreboard(p, mmop, lines, Text.of(TextColors.RED, "}--=== ", TextColors.AQUA, type.getName(), TextColors.RED, " ==---{"));
	}

	public static void sendTop(Player p, MMOPlayer mmop, SkillType type) {
		TreeMap<Integer, Tuple<String, Integer>> top = Main.getInstance().getSkillTop().getTop(type).getTop();

		ArrayList<BoardLine> lines = new ArrayList<BoardLine>();
		for (int i : top.keySet()) {
			lines.add(new BoardLine(Text.of(TextColors.AQUA, top.get(i).getFirst(), TextColors.GRAY, " - "), top.get(i).getSecond()));
		}
		String title = (type == null) ? "Total" : type.getName();
		Main.getInstance().getScoreboardManager().setScoreboard(p, mmop, lines, Text.of(TextColors.RED, "}--=== ", TextColors.AQUA, title, " SkillTop", TextColors.RED, " ==---{"));
	}
}