package me.mrdaniel.adventuremmo.data.manipulators;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.meta.ItemEnchantment;

import me.mrdaniel.adventuremmo.data.MMOKeys;

public class ImmutableSuperToolData extends AbstractImmutableData<ImmutableSuperToolData, SuperToolData> {

	private final List<ItemEnchantment> enchants;
	private final String name;

	public ImmutableSuperToolData(@Nonnull final List<ItemEnchantment> enchants, @Nonnull final String name) {
		this.enchants = enchants;
		this.name = name;

		registerGetters();
	}

	@Override
	protected void registerGetters() {
		registerFieldGetter(MMOKeys.ENCHANTS, () -> this.enchants);
		registerFieldGetter(MMOKeys.NAME, () -> this.name);
	}

	@Override public DataContainer toContainer() { return this.asMutable().toContainer(); }
	@Override public SuperToolData asMutable() { return new SuperToolData(this.enchants, this.name); }
	@Override public int getContentVersion() { return 1; }
}