package me.mrdaniel.adventuremmo.data.manipulators;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import com.google.common.collect.Maps;

import me.mrdaniel.adventuremmo.data.MMOKeys;
import me.mrdaniel.adventuremmo.enums.DelayType;

public class DelayData extends AbstractData<DelayData, ImmutableDelayData> {

	public DelayData(@Nonnull final Map<String, Long> delays) {
		this.delays = delays;

		registerGettersAndSetters();
	}
	private Map<String, Long> delays;

	@Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(MMOKeys.DELAYS, this::getDelays);
        registerFieldSetter(MMOKeys.DELAYS, this::setDelays);
        registerKeyValue(MMOKeys.DELAYS, this::getDelaysValue);
    }

	public Map<String, Long> getDelays() { return this.delays; }
	public void setDelays(Map<String, Long> delays) { this.delays = delays; }
	public Value<Map<String, Long>> getDelaysValue() { return MMOKeys.FACTORY.createMapValue(MMOKeys.DELAYS, this.delays); }

	public int getRemainingSeconds(@Nonnull final DelayType type) {
		return this.delays.containsKey(type.getName()) ? (int) (this.delays.get(type.getName()) - System.currentTimeMillis()) / 1000 : 0;
	}

	public boolean isActive(@Nonnull final DelayType type) {
		if (this.delays.containsKey(type.getName())) {
			if (this.delays.get(type.getName()) > System.currentTimeMillis()) { return true; }
			else { this.delays.remove(type.getName()); }
		}
		return false;
	}
	public void add(@Nonnull final DelayType type, final long endTime) { this.delays.put(type.getName(), endTime); }

    @SuppressWarnings("unchecked")
	public Optional<DelayData> from(@Nonnull final DataView view) {
    	return Optional.of(new DelayData((Map<String, Long>) view.get(MMOKeys.DELAYS.getQuery()).orElse(Maps.newHashMap())));
    }

    @Override public Optional<DelayData> fill(DataHolder holder, MergeFunction overlap) { return Optional.of(checkNotNull(overlap).merge(copy(), from(holder.toContainer()).orElse(null))); }
    @Override public Optional<DelayData> from(DataContainer container) { return from((DataView)container); }
    @Override public DelayData copy() { return new DelayData(this.delays); }
    @Override public ImmutableDelayData asImmutable() { return new ImmutableDelayData(this.delays); }
    @Override public int getContentVersion() { return 1; }
    @Override public DataContainer toContainer() { return super.toContainer().set(MMOKeys.DELAYS.getQuery(), this.delays); }
}