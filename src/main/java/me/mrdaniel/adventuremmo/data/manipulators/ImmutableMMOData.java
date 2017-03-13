package me.mrdaniel.adventuremmo.data.manipulators;

import java.util.Map;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;

import me.mrdaniel.adventuremmo.data.MMOKeys;

public class ImmutableMMOData extends AbstractImmutableData<ImmutableMMOData, MMOData> {

	private final Map<String, Long> delays;
	private final Map<String, Long> abilities;

	private final boolean scoreboard;
	private final boolean scoreboard_permanent;

	public ImmutableMMOData(@Nonnull final Map<String, Long> delays, @Nonnull final Map<String, Long> abilities, final boolean scoreboard, final boolean scoreboard_permanent) {
		this.delays = delays;
		this.abilities = abilities;

		this.scoreboard = scoreboard;
		this.scoreboard_permanent = scoreboard_permanent;

		registerGetters();
	}

	@Override
	protected void registerGetters() {
		registerFieldGetter(MMOKeys.DELAYS, () -> this.delays);
		registerFieldGetter(MMOKeys.ABILITIES, () -> this.abilities);

		registerFieldGetter(MMOKeys.SCOREBOARD, () -> this.scoreboard);
		registerFieldGetter(MMOKeys.SCOREBOARD_PERMANENT, () -> this.scoreboard_permanent);
	}

	@Override public DataContainer toContainer() { return this.asMutable().toContainer(); }
	@Override public MMOData asMutable() { return new MMOData(this.delays, this.abilities, this.scoreboard, this.scoreboard_permanent); }
	@Override public int getContentVersion() { return 1; }
}