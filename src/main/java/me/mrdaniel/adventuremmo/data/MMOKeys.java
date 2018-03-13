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

public class MMOKeys {

	public static final ValueFactory FACTORY = Sponge.getRegistry().getValueFactory();

	private static final TypeToken<MapValue<String, Long>> DELAYS_VALUE_TOKEN = new TypeToken<MapValue<String, Long>>() {
		private static final long serialVersionUID = -1;
	};

	private static final TypeToken<MapValue<String, Long>> ABILITIES_VALUE_TOKEN = new TypeToken<MapValue<String, Long>>() {
		private static final long serialVersionUID = -1;
	};

	private static final TypeToken<Value<Boolean>> ACTION_BAR_VALUE_TOKEN = new TypeToken<Value<Boolean>>() {
		private static final long serialVersionUID = -1;
	};

	private static final TypeToken<Value<Boolean>> SCOREBOARD_VALUE_TOKEN = new TypeToken<Value<Boolean>>() {
		private static final long serialVersionUID = -1;
	};
	private static final TypeToken<Value<Boolean>> SCOREBOARD_PERMANENT_VALUE_TOKEN = new TypeToken<Value<Boolean>>() {
		private static final long serialVersionUID = -1;
	};
	private static final TypeToken<ListValue<Enchantment>> ENCHANTS_VALUE_TOKEN = new TypeToken<ListValue<Enchantment>>() {
		private static final long serialVersionUID = -1;
	};
	private static final TypeToken<Value<String>> NAME_VALUE_TOKEN = new TypeToken<Value<String>>() {
		private static final long serialVersionUID = -1;
	};
	private static final TypeToken<Value<Integer>> DURABILITY_VALUE_TOKEN = new TypeToken<Value<Integer>>() {
		private static final long serialVersionUID = -1;
	};

	// MMOData
	public static final Key<MapValue<String, Long>> DELAYS = Key.builder().type(DELAYS_VALUE_TOKEN)
			.query(DataQuery.of("delays")).id("delays").name("Delays").build();
	public static final Key<MapValue<String, Long>> ABILITIES = Key.builder().type(ABILITIES_VALUE_TOKEN)
			.query(DataQuery.of("abilities")).id("abilities").name("Abilities").build();

	public static final Key<Value<Boolean>> ACTION_BAR = Key.builder().type(ACTION_BAR_VALUE_TOKEN)
			.query(DataQuery.of("action_bar")).id("action_bar").name("Action Bar").build();
	public static final Key<Value<Boolean>> SCOREBOARD = Key.builder().type(SCOREBOARD_VALUE_TOKEN)
			.query(DataQuery.of("scoreboard")).id("scoreboard").name("Scoreboard").build();;
	public static final Key<Value<Boolean>> SCOREBOARD_PERMANENT = Key.builder().type(SCOREBOARD_PERMANENT_VALUE_TOKEN)
			.query(DataQuery.of("scoreboard_permanent")).id("scoreboard_permanent").name("Scoreboard Permanent")
			.build();;

	// SuperToolData
	public static final Key<ListValue<Enchantment>> ENCHANTS = Key.builder().type(ENCHANTS_VALUE_TOKEN)
			.query(DataQuery.of("enchants")).id("enchants").name("Enchants").build();
	public static final Key<Value<String>> NAME = Key.builder().type(NAME_VALUE_TOKEN).query(DataQuery.of("name"))
			.name("MMO Name").id("mmo:name").build();
	public static final Key<Value<Integer>> DURABILITY = Key.builder().type(DURABILITY_VALUE_TOKEN)
			.query(DataQuery.of("durability")).id("durability").name("Durability").build();
}