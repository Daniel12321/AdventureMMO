package me.mrdaniel.mmo.utils;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.serializer.TextSerializers;

public class TextUtils {

	public static Text toText(String message) {
    	return TextSerializers.formattingCode('&').deserialize(message);
	}

	public static String toString(Text message) {
		return TextSerializers.formattingCode('&').serialize(message);
	}

	public static Text setCommandClick(Text message, String command, Text hover) { 
		return Text.builder().append(message).onHover(TextActions.showText(hover)).onClick(TextActions.runCommand(command)).build();
	}
}