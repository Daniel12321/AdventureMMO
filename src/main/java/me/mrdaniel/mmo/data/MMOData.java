package me.mrdaniel.mmo.data;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.ValueFactory;
import org.spongepowered.api.data.value.mutable.Value;

public class MMOData extends AbstractData<MMOData, ImmutableMMOData> {

    public static final ValueFactory VALUEFACTORY = Sponge.getRegistry().getValueFactory();

	public MMOData(boolean enabled) {
		this.enabled = enabled;
		registerGettersAndSetters();
	}

	private boolean enabled;

	public Value<Boolean> getEnabledValue() { return VALUEFACTORY.createValue(MMOKeys.MMOTOOL, this.enabled); }
	public void setEnabled(boolean enabled) { this.enabled = enabled; }
	public boolean getEnabled() { return this.enabled; }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(MMOKeys.MMOTOOL, () -> this.enabled);
        registerFieldSetter(MMOKeys.MMOTOOL, this::setEnabled);
        registerKeyValue(MMOKeys.MMOTOOL, this::getEnabledValue);
    }

    @Override
    public Optional<MMOData> fill(DataHolder dataHolder, MergeFunction overlap) {
    	MMOData data = checkNotNull(overlap).merge(copy(), from(dataHolder.toContainer()).orElse(null));
        return Optional.of(data);
    }

    @Override
    public Optional<MMOData> from(DataContainer container) {
        return from((DataView)container);
    }

    public Optional<MMOData> from(DataView view) {
        if (!view.contains(MMOKeys.MMOTOOL)) { return Optional.empty(); }
    	return Optional.of(new MMOData((boolean) view.get(MMOKeys.MMOTOOL.getQuery()).get()));
    }

    @Override
	public MMOData copy() {
    	return new MMOData(this.enabled);		
	}

    @Override
    public ImmutableMMOData asImmutable() {
    	return new ImmutableMMOData(this.enabled);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(MMOKeys.MMOTOOL.getQuery(), this.enabled);
    }
}