package me.mrdaniel.adventuremmo.data.manipulators;

import java.util.Map;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;

import me.mrdaniel.adventuremmo.data.MMOKeys;

public class ImmutableMMOData extends AbstractImmutableData<ImmutableMMOData, MMOData> {

	private final Map<String, Long> delays;
	private final Map<String, Long> abilities;

	public ImmutableMMOData(@Nonnull final Map<String, Long> delays, @Nonnull final Map<String, Long> abilities) {
		this.delays = delays;
		this.abilities = abilities;

		registerGetters();
	}

	@Override
	protected void registerGetters() {
		registerFieldGetter(MMOKeys.DELAYS, () -> this.delays);
		registerFieldGetter(MMOKeys.ABILITIES, () -> this.abilities);
	}

	@Override public DataContainer toContainer() { return this.asMutable().toContainer(); }
	@Override public MMOData asMutable() { return new MMOData(this.delays, this.abilities); }
	@Override public int getContentVersion() { return 1; }
}