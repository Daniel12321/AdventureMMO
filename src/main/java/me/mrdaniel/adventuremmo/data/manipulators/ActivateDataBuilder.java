package me.mrdaniel.adventuremmo.data.manipulators;

import java.util.Optional;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

public class ActivateDataBuilder extends AbstractDataBuilder<ActivateData> implements DataManipulatorBuilder<ActivateData, ImmutableActivateData> {

	public ActivateDataBuilder() {
		super(ActivateData.class, 1);
	}

	@Override
	public ActivateData create() {
		return new ActivateData();
	}

	@Override
	public Optional<ActivateData> createFrom(DataHolder holder) {
		return this.create().fill(holder);
	}

	@Override
	protected Optional<ActivateData> buildContent(DataView view) throws InvalidDataException {
		return this.create().from(view);
	}
}