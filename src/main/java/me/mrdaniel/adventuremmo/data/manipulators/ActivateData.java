package me.mrdaniel.adventuremmo.data.manipulators;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;

public class ActivateData extends AbstractData<ActivateData, ImmutableActivateData> {

	public ActivateData() {
		
	}

	@Override
	protected void registerGettersAndSetters() {
		
	}

	public Optional<ActivateData> from(@Nonnull final DataView view) {
		return Optional.of(new ActivateData());
	}

	@Override public Optional<ActivateData> fill(DataHolder holder, MergeFunction overlap) { return Optional.ofNullable(checkNotNull(overlap).merge(copy(), from(holder.toContainer()).orElse(null))); }
	@Override public Optional<ActivateData> from(DataContainer container) { return this.from((DataView)container); }
	@Override public ActivateData copy() { return new ActivateData(); }
	@Override public ImmutableActivateData asImmutable() { return new ImmutableActivateData(); }
	@Override public int getContentVersion() { return 1; }
}