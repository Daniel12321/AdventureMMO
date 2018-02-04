package me.mrdaniel.adventuremmo.data;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.ValueFactory;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.item.enchantment.Enchantment;

import com.google.common.reflect.TypeToken;

@SuppressWarnings("serial")
public class MMOKeys {

	public static final ValueFactory FACTORY = Sponge.getRegistry().getValueFactory();

	// MMOData
	public static final Key<MapValue<String, Long>> DELAYS = Key.builder()
			.type(new TypeToken<MapValue<String, Long>>() {
			}).query(DataQuery.of("delays")).id("mmo:delays").name("MMO Delays").build();
	public static final Key<MapValue<String, Long>> ABILITIES = Key.builder()
			.type(new TypeToken<MapValue<String, Long>>() {
			}).query(DataQuery.of("abilities")).id("mmo:abilities").name("MMO Abilities").build();

	public static final Key<Value<Boolean>> ACTION_BAR = Key.builder().type(new TypeToken<Value<Boolean>>() {
	}).query(DataQuery.of("action_bar")).id("mmo:action_bar").name("MMO Action Bar").build();
	public static final Key<Value<Boolean>> SCOREBOARD = Key.builder().type(new TypeToken<Value<Boolean>>() {
	}).query(DataQuery.of("scoreboard")).id("mmo:scoreboard").name("MMO Scoreboard").build();;
	public static final Key<Value<Boolean>> SCOREBOARD_PERMANENT = Key.builder().type(new TypeToken<Value<Boolean>>() {
	}).query(DataQuery.of("scoreboard_permanent")).id("mmo:scoreboard_permanent").name("MMO Scoreboard Permanent")
			.build();;

	// SuperToolData
	public static final Key<ListValue<Enchantment>> ENCHANTS = Key.builder()
			.type(new TypeToken<ListValue<Enchantment>>() {
			}).query(DataQuery.of("enchants")).id("mmo:enchants").name("MMO Enchants").build();
	public static final Key<Value<String>> NAME = Key.builder().type(new TypeToken<Value<String>>() {
	}).query(DataQuery.of("name")).name("MMO Name").id("mmo:name").build();
	public static final Key<Value<Integer>> DURABILITY = Key.builder().type(new TypeToken<Value<Integer>>() {
	}).query(DataQuery.of("durability")).id("mmo:durability").name("MMO Durability").build();
}