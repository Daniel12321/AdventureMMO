package me.mrdaniel.adventuremmo.data.manipulators;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

public class SuperToolDataBuilder extends AbstractDataBuilder<SuperToolData>
		implements DataManipulatorBuilder<SuperToolData, ImmutableSuperToolData> {

	public SuperToolDataBuilder() {
		super(SuperToolData.class, 1);
	}

	@Override
	public SuperToolData create() {
		return new SuperToolData();
	}

	@Override
	public Optional<SuperToolData> createFrom(@Nonnull DataHolder dataHolder) {
		return create().fill(dataHolder);
	}

	@Override
	protected Optional<SuperToolData> buildContent(@Nonnull DataView view) throws InvalidDataException {
		return create().from(view);
	}
}