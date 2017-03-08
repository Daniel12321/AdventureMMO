package me.mrdaniel.adventuremmo.data.manipulators;

import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;

public class ImmutableActivateData extends AbstractImmutableData<ImmutableActivateData, ActivateData> {

	public ImmutableActivateData() {
		
	}

	@Override
	protected void registerGetters() {
		
	}

	@Override public ActivateData asMutable() { return new ActivateData(); }
	@Override public int getContentVersion() { return 1; }
}