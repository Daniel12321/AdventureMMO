package me.mrdaniel.adventuremmo.catalogtypes.settings;

import javax.annotation.Nonnull;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(Settings.class)
public class Setting implements CatalogType {

	private final String name;
	private final String id;

	Setting(@Nonnull final String name, @Nonnull final String id) {
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