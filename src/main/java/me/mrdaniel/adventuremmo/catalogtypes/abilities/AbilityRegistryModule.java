package me.mrdaniel.adventuremmo.catalogtypes.abilities;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.registry.CatalogRegistryModule;

public class AbilityRegistryModule implements CatalogRegistryModule<Ability> {

	@Override
	public Optional<Ability> getById(@Nonnull final String id) {
		return Abilities.of(id);
	}

	@Override
	public Collection<Ability> getAll() {
		return Abilities.VALUES;
	}
}