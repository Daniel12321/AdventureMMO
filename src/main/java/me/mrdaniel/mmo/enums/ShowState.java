package me.mrdaniel.mmo.enums;

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
	public String create(double value) {
		String str = String.valueOf(value);
		if (str.length() > 5) { str = str.substring(0, 6); }
		return pre + str + post;
	}
}