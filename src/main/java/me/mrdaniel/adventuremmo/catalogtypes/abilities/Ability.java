package me.mrdaniel.adventuremmo.catalogtypes.abilities;

import javax.annotation.Nonnull;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(Abilities.class)
public abstract class Ability implements CatalogType {

	private final String name;
	private final String id;
	private final double initial;
	private final double increment;

	public Ability(@Nonnull final String name, @Nonnull final String id, final double initial, final double increment) {
		this.name = name;
		this.id = id;
		this.initial = initial;
		this.increment = increment;
	}

	@Nonnull
	public String getName() {
		return this.name;
	}

	@Nonnull
	public String getId() {
		return this.id;
	}

	public double getIncrement() {
		return this.increment;
	}

	public double getInitial() { 
		return this.initial;
	}
}