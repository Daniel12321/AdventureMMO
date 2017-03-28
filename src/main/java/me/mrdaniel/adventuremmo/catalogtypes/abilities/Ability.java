package me.mrdaniel.adventuremmo.catalogtypes.abilities;

import javax.annotation.Nonnull;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.annotation.CatalogedBy;

import ninja.leaping.configurate.ConfigurationNode;

@CatalogedBy(Abilities.class)
public abstract class Ability implements CatalogType {

	private final String name;
	private final String id;
	private double initial;
	private double increment;
	private double cap;

	private boolean enabled;

	public Ability(@Nonnull final String name, @Nonnull final String id) {
		this.name = name;
		this.id = id;
		this.initial = 0;
		this.increment = 0;
		this.cap = 0;

		this.enabled = false;
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

	public double getIncrement() {
		return this.increment;
	}

	public double getInitial() { 
		return this.initial;
	}

	public double getCap() {
		return this.cap;
	}

	public double getValue(final int level) {
		return Math.min(this.getInitial() + (this.getIncrement()*level), this.getCap());
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public abstract Text getValueLine(final int level);

	public void setValues(@Nonnull final ConfigurationNode node) {
		this.initial = node.getNode("initial_value").getDouble(0.0);
		this.increment = node.getNode("level_increment").getDouble(0.1);
		this.cap = node.getNode("value_cap").getDouble(80.0);
		this.enabled = true;
	}
}