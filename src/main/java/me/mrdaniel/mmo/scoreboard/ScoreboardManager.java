package me.mrdaniel.mmo.scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
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
import me.mrdaniel.mmo.enums.Setting;
import me.mrdaniel.mmo.io.Config;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.utils.TextUtils;

public class ScoreboardManager {
	
	private static ScoreboardManager instance = null;
	public static ScoreboardManager getInstance() {
		if (instance == null) { instance = new ScoreboardManager(); }
		return instance;
	}
	private HashMap<String, Long> delays;
	public HashMap<String, String> updates;
	private int scoreSpaces;
	
	private ScoreboardManager() { 
		this.delays = new HashMap<String, Long>();
		this.updates = new HashMap<String, String>();
		
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
			for (String name : updates.keySet()) {
				Optional<Player> p = Main.getInstance().getGame().getServer().getPlayer(name);
				if (p.isPresent()) { Main.getInstance().getGame().getCommandManager().process(p.get(), updates.get(name)); }
				else { updates.remove(name); }
			}
		})
		.submit(Main.getInstance());
	}
	public void setScoreboard(Player p, MMOPlayer mmop, ArrayList<BoardLine> lines, String title, String cmd) {
		Scoreboard board = Scoreboard.builder().build();
		Objective obj;
		
		obj = Objective.builder().name("MMO").criterion(Criteria.DUMMY).displayName(TextUtils.color(title)).build();
		
		for (BoardLine line : lines) {
			if (line.line.equals("")) {
				Score score = nextEmptyScore(obj);
				score.setScore(line.location);
			}
			else {
				Text text = TextUtils.color(line.line);
				Score score = obj.getOrCreateScore(text);
				score.setScore(line.location);
			}
		}
		board.addObjective(obj);
		board.updateDisplaySlot(obj, DisplaySlots.SIDEBAR);
		p.setScoreboard(board);
		
		if (mmop.getSettings().getSetting(Setting.SCOREBOARDPERMANENT)) { updates.put(p.getName(), cmd); }
		else { delays.put(p.getName(), System.currentTimeMillis() + 1000*Config.getInstance().SCOREBOARD_ACTIVE_SECONDS); }
	}
	private Score nextEmptyScore(Objective obj) {
		String txt = "";
		for (int i = 0; i < scoreSpaces; i++) { txt += " "; }
		scoreSpaces++;
		if (scoreSpaces >= 6) { scoreSpaces = 0; }
		return obj.getOrCreateScore(Text.of(txt));
	}
}