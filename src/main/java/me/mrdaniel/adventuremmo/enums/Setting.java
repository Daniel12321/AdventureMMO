package me.mrdaniel.adventuremmo.enums;

import java.util.Optional;

import javax.annotation.Nonnull;

public enum Setting {

	SCOREBOARD("Scoreboard", "scoreboard"),
	SCOREBOARD_PERMANENT("Scoreboard Permanent", "scoreboard_permanent");

	private final String name;
	private final String id;

	Setting(String name, String id) {
		this.name = name;
		this.id = id;
	}

	@Nonnull
	public String getName() {
		return this.name;
	}

	@Nonnull
	public String getID() {
		return this.id;
	}

	@Nonnull
	public Optional<Setting> of(@Nonnull final String id) {
		for (Setting s : values()) { if (s.id.equalsIgnoreCase(id)) { return Optional.of(s); } }
		return Optional.empty();
	}
}