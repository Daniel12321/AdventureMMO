package me.mrdaniel.adventuremmo.data;

import java.util.List;
import java.util.Map;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.data.value.ValueFactory;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.Value;

import com.google.common.reflect.TypeToken;

@SuppressWarnings("serial")
public class MMOKeys {

	public static final ValueFactory FACTORY = Sponge.getRegistry().getValueFactory();

	// MMOData
	public static final Key<MapValue<String, Long>> DELAYS = KeyFactory.makeMapKey(new TypeToken<Map<String, Long>>(){}, new TypeToken<MapValue<String, Long>>(){}, DataQuery.of("delays"), "mmo:delays", "MMO Delays");
	public static final Key<MapValue<String, Long>> ABILITIES = KeyFactory.makeMapKey(new TypeToken<Map<String, Long>>(){}, new TypeToken<MapValue<String, Long>>(){}, DataQuery.of("abilities"), "mmo:abilities", "MMO Abilities");

	public static final Key<Value<Boolean>> ACTION_BAR = KeyFactory.makeSingleKey(TypeToken.of(Boolean.class), new TypeToken<Value<Boolean>>() {}, DataQuery.of("action_bar"), "mmo:action_bar", "MMO Action Bar");
	public static final Key<Value<Boolean>> SCOREBOARD = KeyFactory.makeSingleKey(TypeToken.of(Boolean.class), new TypeToken<Value<Boolean>>() {}, DataQuery.of("scoreboard"), "mmo:scoreboard", "MMO Scoreboard");
	public static final Key<Value<Boolean>> SCOREBOARD_PERMANENT = KeyFactory.makeSingleKey(TypeToken.of(Boolean.class), new TypeToken<Value<Boolean>>() {}, DataQuery.of("scoreboard_permanent"), "mmo:scoreboard_permanent", "MMO Scoreboard Permanent");

	// SuperToolData
	public static final Key<ListValue<ItemEnchantment>> ENCHANTS = KeyFactory.makeListKey(new TypeToken<List<ItemEnchantment>>(){}, new TypeToken<ListValue<ItemEnchantment>>(){}, DataQuery.of("enchants"), "mmo:enchants", "MMO Enchants");
	public static final Key<Value<String>> NAME = KeyFactory.makeSingleKey(TypeToken.of(String.class), new TypeToken<Value<String>>(){}, DataQuery.of("name"), "mmo:name", "MMO Name");
	public static final Key<Value<Integer>> DURABILITY = KeyFactory.makeSingleKey(TypeToken.of(Integer.class), new TypeToken<Value<Integer>>(){}, DataQuery.of("durability"), "mmo:durability", "MMO Durability");
}