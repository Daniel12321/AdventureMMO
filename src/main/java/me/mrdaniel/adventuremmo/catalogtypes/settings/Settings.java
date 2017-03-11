package me.mrdaniel.adventuremmo.catalogtypes.settings;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

public final class Settings {

	private Settings(){}

	public static final Setting SCOREBOARD = new Setting("Scoreboard", "scoreboard");
	public static final Setting SCOREBOARD_PERMANENT = new Setting("Scoreboard Permanent", "scoreboard_permanent");

	@Nonnull
	public static List<Setting> getAll() {
		return Lists.newArrayList(SCOREBOARD, SCOREBOARD_PERMANENT);
	}

	@Nonnull
	public static Optional<Setting> of(@Nonnull final String id) {
		for (Setting s : getAll()) { if (s.getId().equalsIgnoreCase(id)) { return Optional.of(s); } }
		return Optional.empty();
	}
}