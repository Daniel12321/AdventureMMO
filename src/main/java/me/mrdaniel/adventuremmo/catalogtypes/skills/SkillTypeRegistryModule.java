package me.mrdaniel.adventuremmo.catalogtypes.skills;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.registry.CatalogRegistryModule;

public class SkillTypeRegistryModule implements CatalogRegistryModule<SkillType> {

	@Override
	public Optional<SkillType> getById(@Nonnull final String id) {
		return SkillTypes.of(id);
	}

	@Override
	public Collection<SkillType> getAll() {
		return SkillTypes.VALUES;
	}
}