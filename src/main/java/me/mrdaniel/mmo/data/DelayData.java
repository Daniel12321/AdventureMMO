package me.mrdaniel.mmo.data;

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

import me.mrdaniel.mmo.enums.DelayType;

public class DelayData extends AbstractData<DelayData, ImmutableDelayData> {

	public DelayData(@Nonnull final Map<String, Long> delays) {
		this.delays = delays;

		registerGettersAndSetters();
	}
	private Map<String, Long> delays;

	public Value<Map<String, Long>> getDelaysValue() { return MMOKeys.FACTORY.createMapValue(MMOKeys.DELAYS, this.delays); }
	public void setDelays(Map<String, Long> delays) { this.delays = delays; }
	public Map<String, Long> getDelays() { return this.delays; }

	public int getRemainingSeconds(@Nonnull final DelayType type) {
		long endTime = (this.delays.containsKey(type.getName())) ? this.delays.get(type.getName()) : System.currentTimeMillis();
		return (int) ((endTime - System.currentTimeMillis()) / 1000);
	}

	public boolean isActive(@Nonnull final DelayType type) {
		if (this.delays.containsKey(type.getName())) {
			if (System.currentTimeMillis() < this.delays.get(type.getName())) { return true; }
			else { this.delays.remove(type.getName()); }
		}
		return false;
	}
	public void add(@Nonnull final DelayType type, final long endTime) { this.delays.put(type.getName(), endTime); }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(MMOKeys.DELAYS, () -> this.delays);
        registerFieldSetter(MMOKeys.DELAYS, this::setDelays);
        registerKeyValue(MMOKeys.DELAYS, this::getDelaysValue);
    }

    @Override
    public Optional<DelayData> fill(DataHolder dataHolder, MergeFunction overlap) {
    	DelayData data = checkNotNull(overlap).merge(copy(), from(dataHolder.toContainer()).orElse(null));
        return Optional.of(data);
    }

    @Override
    public Optional<DelayData> from(DataContainer container) {
        return from((DataView)container);
    }

    @SuppressWarnings("unchecked")
	public Optional<DelayData> from(DataView view) {
        if (!view.contains(MMOKeys.DELAYS)) { return Optional.empty(); }
    	return Optional.of(new DelayData((Map<String, Long>) view.get(MMOKeys.DELAYS.getQuery()).get()));
    }

    @Override
	public DelayData copy() {
    	return new DelayData(this.delays);		
	}

    @Override
    public ImmutableDelayData asImmutable() {
    	return new ImmutableDelayData(this.delays);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(MMOKeys.DELAYS.getQuery(), this.delays);
    }
}