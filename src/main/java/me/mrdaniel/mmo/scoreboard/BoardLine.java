package me.mrdaniel.mmo.scoreboard;

import javax.annotation.Nonnull;

import org.spongepowered.api.text.Text;

public class BoardLine {

	@Nonnull public final Text line;
	public final int loc;

	public BoardLine(@Nonnull final Text line, final int loc) {
		this.line = line;
		this.loc = loc;
	}
}