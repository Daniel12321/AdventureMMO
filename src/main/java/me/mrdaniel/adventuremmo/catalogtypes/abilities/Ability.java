package me.mrdaniel.adventuremmo.catalogtypes.abilities;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(Abilities.class)
public abstract class Ability implements CatalogType {

	public abstract double getIncrement();
	public abstract double getInitial();
}