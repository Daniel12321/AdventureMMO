package me.mrdaniel.adventuremmo.catalogtypes.skills;

import javax.annotation.Nonnull;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(SkillTypes.class)
public class SkillType implements CatalogType {

	private final String name;
	private final String id;

	SkillType(@Nonnull final String name, @Nonnull final String id) {
		this.name = name;
		this.id = id;
	}

	@Override
	@Nonnull
	public String getName() {
		return this.name;
	}

	@Override
	@Nonnull
	public String getId() {
		return this.id;
	}
}