package me.mrdaniel.mmo.enums;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;

public enum ShowState {

	DURATION("Duration: ", "s"),
	CHANCE("Chance: ", "%"),
	DELAY("Delay: ", "s"),
	RETRIEVE("Retrieve: ", "%"),
	REPAIR("Repair: ", "%");

	String pre;
	String post;

	ShowState(String pre, String post) {
		this.pre = pre;
		this.post = post;
	}

	public Text create(TextColor color, double value) {
		String str = String.valueOf(value);
		if (str.length() > 5) { str = str.substring(0, 6); }
		return Text.of(color, pre, str, post);
	}
}