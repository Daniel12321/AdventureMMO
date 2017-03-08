package me.mrdaniel.adventuremmo.data.manipulators;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;

import me.mrdaniel.adventuremmo.data.MMOKeys;

public class ImmutableSettingsData extends AbstractImmutableData<ImmutableSettingsData, SettingsData> {

	public ImmutableSettingsData(final boolean scoreboard, final boolean scoreboard_permanent) {
		this.scoreboard = scoreboard;
		this.scoreboard_permanent = scoreboard_permanent;

		registerGetters();
	}
	private final boolean scoreboard;
	private final boolean scoreboard_permanent;

	@Override
	protected void registerGetters() {
		registerFieldGetter(MMOKeys.SCOREBOARD, () -> this.scoreboard);
		registerFieldGetter(MMOKeys.SCOREBOARD_PERMANENT, () -> this.scoreboard_permanent);
	}

	@Override public int getContentVersion() { return 1; }
	@Override public DataContainer toContainer() { return this.asMutable().toContainer(); }
	@Override public SettingsData asMutable() { return new SettingsData(this.scoreboard, this.scoreboard_permanent); }
}