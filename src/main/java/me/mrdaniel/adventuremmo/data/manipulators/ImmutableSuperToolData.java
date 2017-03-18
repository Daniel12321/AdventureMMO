package me.mrdaniel.adventuremmo.data.manipulators;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;

import me.mrdaniel.adventuremmo.data.MMOKeys;

public class ImmutableSuperToolData extends AbstractImmutableData<ImmutableSuperToolData, SuperToolData> {

	private final boolean enabled;

	public ImmutableSuperToolData(final boolean enabled) {
		this.enabled = enabled;

		registerGetters();
	}

	@Override
	protected void registerGetters() {
		registerFieldGetter(MMOKeys.ENABLED, () -> this.enabled);
	}

	@Override public DataContainer toContainer() { return this.asMutable().toContainer(); }
	@Override public SuperToolData asMutable() { return new SuperToolData(this.enabled); }
	@Override public int getContentVersion() { return 1; }
}