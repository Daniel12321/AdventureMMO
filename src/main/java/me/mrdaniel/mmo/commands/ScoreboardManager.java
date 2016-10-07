package me.mrdaniel.mmo.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Score;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.io.Config;
import me.mrdaniel.mmo.utils.TextUtils;

public class ScoreboardManager {
	
	private static ScoreboardManager instance = null;
	public static ScoreboardManager getInstance() {
		if (instance == null) { instance = new ScoreboardManager(); }
		return instance;
	}
	private HashMap<String, Long> delays;
	
	private ScoreboardManager() { 
		this.delays = new HashMap<String, Long>();
		
		Main.getInstance().getGame().getScheduler().createTaskBuilder()
		.name("MMO Scoreboard Task")
		.delay(3, TimeUnit.SECONDS)
		.interval(3, TimeUnit.SECONDS)
		.execute(()-> { 
			for (String name : delays.keySet()) {
				if (delays.get(name) < System.currentTimeMillis()) {
					Optional<Player> p = Main.getInstance().getGame().getServer().getPlayer(name);
					if (p.isPresent()) { p.get().setScoreboard(null); }
					delays.remove(name);
				} 
			}
		})
		.submit(Main.getInstance());
	}
	public void setScoreboard(Player p, ArrayList<BoardLine> lines, String title) {
		Scoreboard board = Scoreboard.builder().build();
		
		List<Score> scoreLines = new ArrayList<>();
		Objective obj;
		
		obj = Objective.builder().name("MMO").criterion(Criteria.DUMMY).displayName(TextUtils.color(title)).build();
		
		for (BoardLine line : lines) {
			Text text = TextUtils.color(line.line);
			Score score = obj.getOrCreateScore(text);
			score.setScore(line.location);
			scoreLines.add(score);
		}
		board.addObjective(obj);
		board.updateDisplaySlot(obj, DisplaySlots.SIDEBAR);
		p.setScoreboard(board);
		
		delays.put(p.getName(), System.currentTimeMillis() + 1000*Config.getInstance().SCOREBOARD_ACTIVE_SECONDS);
	}
}