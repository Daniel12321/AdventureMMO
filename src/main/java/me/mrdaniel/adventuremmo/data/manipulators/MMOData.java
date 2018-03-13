package me.mrdaniel.adventuremmo.data.manipulators;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import me.mrdaniel.adventuremmo.data.MMOKeys;

public class MMOData extends AbstractData<MMOData, ImmutableMMOData> {

	private Map<String, Long> delays;
	private Map<String, Long> abilities;

	private boolean action_bar;
	private boolean scoreboard;
	private boolean scoreboard_permanent;

	public MMOData() {
		this(Maps.newHashMap(), Maps.newHashMap(), true, true, false);
	}

	public MMOData(@Nonnull final Map<String, Long> delays, @Nonnull final Map<String, Long> abilities,
			final boolean action_bar, final boolean scoreboard, final boolean scoreboard_permanent) {
		this.delays = delays;
		this.abilities = abilities;

		this.action_bar = action_bar;
		this.scoreboard = scoreboard;
		this.scoreboard_permanent = scoreboard_permanent;

		registerGettersAndSetters();
	}

	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(MMOKeys.DELAYS, this::getDelays);
		registerFieldSetter(MMOKeys.DELAYS, this::setDelays);
		registerKeyValue(MMOKeys.DELAYS, this::getDelaysValue);

		registerFieldGetter(MMOKeys.ABILITIES, this::getAbilities);
		registerFieldSetter(MMOKeys.ABILITIES, this::setAbilities);
		registerKeyValue(MMOKeys.ABILITIES, this::getAbilitiesValue);

		registerFieldGetter(MMOKeys.ACTION_BAR, this::getActionBar);
		registerFieldSetter(MMOKeys.ACTION_BAR, this::setActionBar);
		registerKeyValue(MMOKeys.ACTION_BAR, this::getActionBarValue);

		registerFieldGetter(MMOKeys.SCOREBOARD, this::getScoreboard);
		registerFieldSetter(MMOKeys.SCOREBOARD, this::setScoreboard);
		registerKeyValue(MMOKeys.SCOREBOARD, this::getScoreboardValue);

		registerFieldGetter(MMOKeys.SCOREBOARD_PERMANENT, this::getScoreboardPermanent);
		registerFieldSetter(MMOKeys.SCOREBOARD_PERMANENT, this::setScoreboardPermanent);
		registerKeyValue(MMOKeys.SCOREBOARD_PERMANENT, this::getScoreboardPermanentValue);
	}

	public Value<Map<String, Long>> getDelaysValue() {
		return MMOKeys.FACTORY.createMapValue(MMOKeys.DELAYS, this.delays);
	}

	public Value<Map<String, Long>> getAbilitiesValue() {
		return MMOKeys.FACTORY.createMapValue(MMOKeys.ABILITIES, this.abilities);
	}

	public Value<Boolean> getActionBarValue() {
		return MMOKeys.FACTORY.createValue(MMOKeys.ACTION_BAR, this.action_bar);
	}

	public Value<Boolean> getScoreboardValue() {
		return MMOKeys.FACTORY.createValue(MMOKeys.SCOREBOARD, this.scoreboard);
	}

	public Value<Boolean> getScoreboardPermanentValue() {
		return MMOKeys.FACTORY.createValue(MMOKeys.SCOREBOARD_PERMANENT, this.scoreboard_permanent);
	}

	public Map<String, Long> getDelays() {
		return this.delays;
	}

	public long getDelay(@Nonnull final String id) {
		return Optional.ofNullable(this.delays.get(id)).orElse(0L);
	}

	public void setDelays(Map<String, Long> delays) {
		this.delays = delays;
	}

	public void setDelay(@Nonnull final String id, final long endtime) {
		this.delays.put(id, endtime);
	}

	public boolean isDelayActive(@Nonnull final String id) {
		return this.getDelay(id) > System.currentTimeMillis();
	}

	public Map<String, Long> getAbilities() {
		return this.abilities;
	}

	public long getAbility(@Nonnull final String id) {
		return Optional.ofNullable(this.abilities.get(id)).orElse(0L);
	}

	public void setAbilities(@Nonnull final Map<String, Long> abilities) {
		this.abilities = abilities;
	}

	public void setAbility(@Nonnull final String id, final long endtime) {
		this.abilities.put(id, endtime);
	}

	public boolean isAbilityActive(@Nonnull final String id) {
		return this.getAbility(id) > System.currentTimeMillis();
	}

	public boolean getActionBar() {
		return this.action_bar;
	}

	public void setActionBar(final boolean action_bar) {
		this.action_bar = action_bar;
	}

	public boolean getScoreboard() {
		return this.scoreboard;
	}

	public void setScoreboard(final boolean scoreboard) {
		this.scoreboard = scoreboard;
	}

	public boolean getScoreboardPermanent() {
		return this.scoreboard_permanent;
	}

	public void setScoreboardPermanent(final boolean scoreboard_permanent) {
		this.scoreboard_permanent = scoreboard_permanent;
	}

	@SuppressWarnings("unchecked")
	public Optional<MMOData> from(@Nonnull final DataView view) {
		return Optional.of(new MMOData(
				view.getMap(MMOKeys.DELAYS.getQuery()).map(map -> Maps.newHashMap((Map<String, Long>) map))
						.orElse(Maps.newHashMap()),
				view.getMap(MMOKeys.ABILITIES.getQuery()).map(map -> Maps.newHashMap((Map<String, Long>) map))
						.orElse(Maps.newHashMap()),
				view.getBoolean(MMOKeys.ACTION_BAR.getQuery()).orElse(true),
				view.getBoolean(MMOKeys.SCOREBOARD.getQuery()).orElse(true),
				view.getBoolean(MMOKeys.SCOREBOARD_PERMANENT.getQuery()).orElse(false)));
	}

	@Override
	public DataContainer toContainer() {
		return super.toContainer().set(MMOKeys.DELAYS.getQuery(), this.delays)
				.set(MMOKeys.ABILITIES.getQuery(), this.abilities).set(MMOKeys.ACTION_BAR.getQuery(), this.action_bar)
				.set(MMOKeys.SCOREBOARD.getQuery(), this.scoreboard)
				.set(MMOKeys.SCOREBOARD_PERMANENT.getQuery(), this.scoreboard_permanent);
	}

	@Override
	public Optional<MMOData> fill(DataHolder holder, MergeFunction overlap) {
		return Optional.of(Preconditions.checkNotNull(overlap).merge(copy(), from(holder.toContainer()).orElse(null)));
	}

	@Override
	public Optional<MMOData> from(DataContainer container) {
		return from((DataView) container);
	}

	@Override
	public MMOData copy() {
		return new MMOData(this.delays, this.abilities, this.action_bar, this.scoreboard, this.scoreboard_permanent);
	}

	@Override
	public ImmutableMMOData asImmutable() {
		return new ImmutableMMOData(this.delays, this.abilities, this.action_bar, this.scoreboard,
				this.scoreboard_permanent);
	}

	@Override
	public int getContentVersion() {
		return 1;
	}
}