package me.mrdaniel.mmo.scoreboard;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Score;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;

import me.mrdaniel.mmo.io.players.MMOPlayer;

@Singleton
public class ScoreboardManager {

	private int scoreSpaces;

	public ScoreboardManager() {
		this.scoreSpaces = 0;
	}

	public void setScoreboard(@Nonnull final Player p, @Nonnull final MMOPlayer mmop, @Nonnull final List<BoardLine> lines, @Nonnull final Text title) {
		Scoreboard board = Scoreboard.builder().build();
		Objective obj = Objective.builder().name("MMO").criterion(Criteria.DUMMY).displayName(title).build();

		for (BoardLine line : lines) {
			if (line.line.toPlain().equals("")) {
				Score score = nextEmptyScore(obj);
				score.setScore(line.loc);
			}
			else {
				Score score = obj.getOrCreateScore(line.line);
				score.setScore(line.loc);
			}
		}
		board.addObjective(obj);
		board.updateDisplaySlot(obj, DisplaySlots.SIDEBAR);
		p.setScoreboard(board);
	}

	private Score nextEmptyScore(Objective obj) {
		String txt = "";
		for (int i = 0; i < scoreSpaces; i++) { txt += " "; }
		scoreSpaces++;
		if (scoreSpaces >= 6) { scoreSpaces = 0; }
		return obj.getOrCreateScore(Text.of(txt));
	}
}