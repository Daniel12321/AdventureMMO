package me.mrdaniel.mmo.data;

import java.util.List;
import java.util.Map;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.ValueFactory;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.Value;

import com.google.common.reflect.TypeToken;

@SuppressWarnings("serial")
public class MMOKeys {

	public static final ValueFactory FACTORY = Sponge.getRegistry().getValueFactory();

	public static final Key<Value<Boolean>> MMOTOOL = KeyFactory.makeSingleKey(TypeToken.of(Boolean.class),
			new TypeToken<Value<Boolean>>() {}, DataQuery.of("tool"), "mmo:tool", "MMO Tool");

	public static final Key<ListValue<String>> MMOSTORE = KeyFactory.makeListKey(new TypeToken<List<String>>(){},
			new TypeToken<ListValue<String>>(){}, DataQuery.of("store"), "mmo:store", "MMO Store");

	public static final Key<MapValue<String, Long>> DELAYS = KeyFactory.makeMapKey(new TypeToken<Map<String, Long>>(){},
			new TypeToken<MapValue<String, Long>>(){}, DataQuery.of("delays"), "mmo:delays", "MMO Delays");
}