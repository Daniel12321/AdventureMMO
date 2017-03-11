package me.mrdaniel.adventuremmo.catalogtypes.tools;

import javax.annotation.Nonnull;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(ToolTypes.class)
public class ToolType implements CatalogType {

	private final String name;
	private final String id;

	ToolType(@Nonnull final String name, @Nonnull final String id) {
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