package me.mrdaniel.mmo.data;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;

public class ImmutableMMOData extends AbstractImmutableData<ImmutableMMOData, MMOData> {

	public ImmutableMMOData(boolean enabled) {
		this.enabled = enabled;

		registerGetters();
	}
	private boolean enabled;

    @Override
    protected void registerGetters() {
        registerFieldGetter(MMOKeys.MMOTOOL, () -> this.enabled);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(MMOKeys.MMOTOOL.getQuery(), this.enabled);
    }

	@Override
	public MMOData asMutable() {
		return new MMOData(this.enabled);
	}
}