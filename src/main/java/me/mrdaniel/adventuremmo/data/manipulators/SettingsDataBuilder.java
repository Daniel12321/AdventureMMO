package me.mrdaniel.adventuremmo.data.manipulators;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

public class SettingsDataBuilder extends AbstractDataBuilder<SettingsData> implements DataManipulatorBuilder<SettingsData, ImmutableSettingsData> {

	public SettingsDataBuilder() {
		super(SettingsData.class, 1);
	}

	@Override public SettingsData create() {  return new SettingsData(true, false); }
	@Override public Optional<SettingsData> createFrom(@Nonnull DataHolder holder) { return create().fill(holder); }
	@Override protected Optional<SettingsData> buildContent(@Nonnull DataView view) throws InvalidDataException { return create().from(view); }
}