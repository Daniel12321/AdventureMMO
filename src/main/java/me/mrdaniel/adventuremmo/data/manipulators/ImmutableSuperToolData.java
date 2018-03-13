package me.mrdaniel.adventuremmo.data.manipulators;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.item.enchantment.Enchantment;

import me.mrdaniel.adventuremmo.data.MMOKeys;

public class ImmutableSuperToolData extends AbstractImmutableData<ImmutableSuperToolData, SuperToolData> {

	private final List<Enchantment> enchants;
	private final String name;
	private final int durability;

	public ImmutableSuperToolData(@Nonnull final List<Enchantment> enchants, @Nonnull final String name,
			final int durability) {
		this.enchants = enchants;
		this.name = name;
		this.durability = durability;

		registerGetters();
	}

	@Override
	protected void registerGetters() {
		registerFieldGetter(MMOKeys.ENCHANTS, () -> this.enchants);
		registerFieldGetter(MMOKeys.NAME, () -> this.name);
		registerFieldGetter(MMOKeys.DURABILITY, () -> this.durability);
	}

	@Override
	public DataContainer toContainer() {
		return this.asMutable().toContainer();
	}

	@Override
	public SuperToolData asMutable() {
		return new SuperToolData(this.enchants, this.name, this.durability);
	}

	@Override
	public int getContentVersion() {
		return 1;
	}
}