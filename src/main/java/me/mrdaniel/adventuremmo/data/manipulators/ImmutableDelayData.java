package me.mrdaniel.adventuremmo.data.manipulators;

import java.util.Map;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;

import me.mrdaniel.adventuremmo.data.MMOKeys;

public class ImmutableDelayData extends AbstractImmutableData<ImmutableDelayData, DelayData> {

	public ImmutableDelayData(@Nonnull final Map<String, Long> delays) {
		this.delays = delays;

		registerGetters();
	}
	private Map<String, Long> delays;

    @Override
    protected void registerGetters() {
        registerFieldGetter(MMOKeys.DELAYS, () -> this.delays);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(MMOKeys.DELAYS.getQuery(), this.delays);
    }

	@Override
	public DelayData asMutable() {
		return new DelayData(this.delays);
	}
}