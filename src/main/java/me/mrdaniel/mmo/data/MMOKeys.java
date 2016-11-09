package me.mrdaniel.mmo.data;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.Value;

import com.google.common.reflect.TypeToken;

public class MMOKeys {
	
	public static final Key<Value<Boolean>> MMOTOOL = KeyFactory.makeSingleKey(TypeToken.of(Boolean.class),
			new TypeToken<Value<Boolean>>() {}, DataQuery.of("mmotool"), "mmorpg:mmotool", "RPG MMOTool");
}