package me.mrdaniel.adventuremmo.utils;

import javax.annotation.Nonnull;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

public class TextUtils {

	@Nonnull
	public static Text toText(@Nonnull final String text) {
		return TextSerializers.formattingCode('&').deserialize(text);
	}

	@Nonnull
	public static String toString(@Nonnull final Text text) {
		return TextSerializers.formattingCode('&').serialize(text);
	}

	@Nonnull
	public static Text getCommandText(@Nonnull final Text msg, @Nonnull final Text hover, @Nonnull final String cmd) {
		return Text.builder().append(msg).onHover(TextActions.showText(hover)).onClick(TextActions.runCommand(cmd))
				.build();
	}

	@Nonnull
	public static TextColor getColor(final boolean value) {
		return value ? TextColors.GREEN : TextColors.RED;
	}

	@Nonnull
	public static Text getValueText(final boolean value) {
		return value ? Text.of(TextColors.GREEN, "Enabled") : Text.of(TextColors.RED, "Disabled");
	}

	@Nonnull
	public static Text getToggleText(final boolean value) {
		return value ? Text.of(TextColors.RED, "Disable") : Text.of(TextColors.GREEN, "Enable");
	}

	// @Nonnull
	// public static String getTimeFormat(final long millis) {
	// int seconds = (int) ((millis) / 1000);
	// int minutes = 0;
	// int hours = 0;
	// int days = 0;
	// while (seconds >= 86400) { days++; seconds -= 86400; }
	// while (seconds >= 3600) { hours++; seconds -= 3600; }
	// while (seconds >= 60) { minutes++; seconds -= 60; }
	//
	// String str = "";
	// if (days > 0) { str += days + "d"; }
	// if (hours > 0) { str += (str.equals("") ? "" : " ") + hours + "h"; }
	// if (minutes > 0) { str += (str.equals("") ? "" : " ") + minutes + "m"; }
	// if (seconds > 0) { str += (str.equals("") ? "" : " ") + seconds + "s"; }
	// return str;
	// }
}