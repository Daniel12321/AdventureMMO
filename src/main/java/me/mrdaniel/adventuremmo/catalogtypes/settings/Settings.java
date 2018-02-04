package me.mrdaniel.adventuremmo.catalogtypes.settings;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

public final class Settings {

	private Settings() {
	}

	public static final Setting ACTION_BAR = new Setting("Action Bar", "action_bar");
	public static final Setting SCOREBOARD = new Setting("Scoreboard", "scoreboard");
	public static final Setting SCOREBOARD_PERMANENT = new Setting("Scoreboard Permanent", "scoreboard_permanent");

	public static final List<Setting> VALUES = Lists.newArrayList(ACTION_BAR, SCOREBOARD, SCOREBOARD_PERMANENT);

	@Nonnull
	public static Optional<Setting> of(@Nonnull final String id) {
		for (Setting s : VALUES) {
			if (s.getId().equalsIgnoreCase(id)) {
				return Optional.of(s);
			}
		}
		return Optional.empty();
	}
}