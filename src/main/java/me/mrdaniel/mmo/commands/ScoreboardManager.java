package me.mrdaniel.mmo.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.spongepowered.api.scoreboard.Score;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;

import me.mrdaniel.mmo.utils.TextUtils;

public class ScoreboardManager {
	
	public static Scoreboard getScoreboard(String[] lines, String title) {
		Scoreboard scoreboard = Scoreboard.builder().build();
		
		List<Score> scoreLines = new ArrayList<>();
		Objective obj;
		
		obj = Objective.builder().name("MMO").criterion(Criteria.DUMMY).displayName(TextUtils.color(title)).build();
		
		for (int i = lines.length; i >= 1; i--) {
			Text text = TextUtils.color(lines[i-1]);
			Score score = obj.getOrCreateScore(text);
			score.setScore(i);
			scoreLines.add(score);
		}
		scoreboard.addObjective(obj);
		scoreboard.updateDisplaySlot(obj, DisplaySlots.SIDEBAR);
		return scoreboard;
	}
	public static Scoreboard getScoreboard(HashMap<Integer, String> lines, String title) {
		Scoreboard scoreboard = Scoreboard.builder().build();
		
		List<Score> scoreLines = new ArrayList<>();
		Objective obj;
		
		obj = Objective.builder().name("MMO").criterion(Criteria.DUMMY).displayName(TextUtils.color(title)).build();
		
		for (int i : lines.keySet()) {
			Text text = TextUtils.color(lines.get(i));
			Score score = obj.getOrCreateScore(text);
			score.setScore(i);
			scoreLines.add(score);
		}
		scoreboard.addObjective(obj);
		scoreboard.updateDisplaySlot(obj, DisplaySlots.SIDEBAR);
		return scoreboard;
	}
}