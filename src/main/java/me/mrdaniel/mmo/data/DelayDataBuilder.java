package me.mrdaniel.mmo.data;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import com.google.common.collect.Maps;

public class DelayDataBuilder extends AbstractDataBuilder<DelayData> implements DataManipulatorBuilder<DelayData, ImmutableDelayData> {

    public DelayDataBuilder() {
        super(DelayData.class, 1);
    }

    @Nonnull 
    @Override
    public DelayData create() {
        return new DelayData(Maps.newHashMap());
    }

    @Nonnull 
    @Override
    public Optional<DelayData> createFrom(@Nonnull DataHolder dataHolder) {
        return create().fill(dataHolder);
    }

    @Nonnull 
    @Override
    protected Optional<DelayData> buildContent(@Nonnull DataView view) throws InvalidDataException {
        return create().from(view);
    }
}