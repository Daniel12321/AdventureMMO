package me.mrdaniel.adventuremmo.data.manipulators;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import me.mrdaniel.adventuremmo.data.MMOKeys;

public class SettingsData extends AbstractData<SettingsData, ImmutableSettingsData> {

	public SettingsData(final boolean scoreboard, final boolean scoreboard_permanent) {
		this.scoreboard = scoreboard;
		this.scoreboard_permanent = scoreboard_permanent;

		registerGettersAndSetters();
	}
	private boolean scoreboard;
	private boolean scoreboard_permanent;

	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(MMOKeys.SCOREBOARD, this::getScoreboard);
		registerFieldSetter(MMOKeys.SCOREBOARD, this::setScoreboard);
		registerKeyValue(MMOKeys.SCOREBOARD, this::getScoreboardValue);

		registerFieldGetter(MMOKeys.SCOREBOARD_PERMANENT, this::getScoreboardPermanent);
		registerFieldSetter(MMOKeys.SCOREBOARD_PERMANENT, this::setScoreboardPermanent);
		registerKeyValue(MMOKeys.SCOREBOARD_PERMANENT, this::getScoreboardPermanentValue);
	}

	public boolean getScoreboard() { return this.scoreboard; }
	public void setScoreboard(final boolean scoreboard) { this.scoreboard = scoreboard; }

	public boolean getScoreboardPermanent() { return this.scoreboard_permanent; }
	public void setScoreboardPermanent(final boolean scoreboard_permanent) { this.scoreboard_permanent = scoreboard_permanent; }

	public Value<Boolean> getScoreboardValue() { return MMOKeys.FACTORY.createValue(MMOKeys.SCOREBOARD, this.scoreboard); }
	public Value<Boolean> getScoreboardPermanentValue() { return MMOKeys.FACTORY.createValue(MMOKeys.SCOREBOARD_PERMANENT, this.scoreboard_permanent); }

	public Optional<SettingsData> from(@Nonnull final DataView view) {
		return Optional.of(new SettingsData(
				view.getBoolean(MMOKeys.SCOREBOARD.getQuery()).orElse(true),
				view.getBoolean(MMOKeys.SCOREBOARD_PERMANENT.getQuery()).orElse(false)));
	}

	@Override public Optional<SettingsData> fill(DataHolder holder, MergeFunction overlap) { return Optional.of(checkNotNull(overlap).merge(copy(), from(holder.toContainer()).orElse(null))); }
	@Override public Optional<SettingsData> from(DataContainer container) { return from((DataView)container); }
	@Override public SettingsData copy() { return new SettingsData(this.scoreboard, this.scoreboard_permanent); }
	@Override public ImmutableSettingsData asImmutable() { return new ImmutableSettingsData(this.scoreboard, this.scoreboard_permanent); }
	@Override public int getContentVersion() { return 1; }
	@Override public DataContainer toContainer() { return super.toContainer().set(MMOKeys.SCOREBOARD.getQuery(), this.scoreboard).set(MMOKeys.SCOREBOARD_PERMANENT.getQuery(), this.scoreboard_permanent); }
}