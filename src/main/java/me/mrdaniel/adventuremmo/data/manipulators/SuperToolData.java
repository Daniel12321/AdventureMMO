package me.mrdaniel.adventuremmo.data.manipulators;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import com.google.common.base.Preconditions;

import me.mrdaniel.adventuremmo.data.MMOKeys;

public class SuperToolData extends AbstractData<SuperToolData, ImmutableSuperToolData> {

	private boolean enabled;

	public SuperToolData(final boolean enabled) {
		this.enabled = enabled;

		registerGettersAndSetters();
	}

	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(MMOKeys.SCOREBOARD, this::isEnabled);
		registerFieldSetter(MMOKeys.SCOREBOARD, this::setEnabledd);
		registerKeyValue(MMOKeys.SCOREBOARD, this::getEnabledValue);
	}

	public boolean isEnabled() { return this.enabled; }
	public void setEnabledd(final boolean enabled) { this.enabled = enabled; }

	public Value<Boolean> getEnabledValue() { return MMOKeys.FACTORY.createValue(MMOKeys.ENABLED, this.enabled); }

	public Optional<SuperToolData> from(@Nonnull final DataView view) {
		return Optional.of(new SuperToolData(view.getBoolean(MMOKeys.ENABLED.getQuery()).orElse(false)));
	}

	@Override
	public DataContainer toContainer() {
		return super.toContainer().set(MMOKeys.ENABLED.getQuery(), this.enabled);
	}

	@Override public Optional<SuperToolData> fill(DataHolder holder, MergeFunction overlap) { return Optional.of(Preconditions.checkNotNull(overlap).merge(copy(), from(holder.toContainer()).orElse(null))); }
	@Override public Optional<SuperToolData> from(DataContainer container) { return from((DataView)container); }
	@Override public SuperToolData copy() { return new SuperToolData(this.enabled); }
	@Override public ImmutableSuperToolData asImmutable() { return new ImmutableSuperToolData(this.enabled); }
	@Override public int getContentVersion() { return 1; }
}