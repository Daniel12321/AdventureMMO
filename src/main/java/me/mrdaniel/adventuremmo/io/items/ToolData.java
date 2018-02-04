package me.mrdaniel.adventuremmo.io.items;

import java.util.Optional;

import javax.annotation.Nonnull;

import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolTypes;

public class ToolData {

	private final ToolType type;

	public ToolData(@Nonnull final ToolType type) {
		this.type = type;
	}

	@Nonnull
	public ToolType getType() {
		return this.type;
	}

	@Nonnull
	public String serialize() {
		return this.type.getId();
	}

	@Nonnull
	public static Optional<ToolData> deserialize(@Nonnull final String str) {
		try {
			return Optional.of(new ToolData(ToolTypes.of(str).get()));
		} catch (final Exception exc) {
			return Optional.empty();
		}
	}
}