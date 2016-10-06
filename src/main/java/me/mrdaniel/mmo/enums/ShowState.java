package me.mrdaniel.mmo.enums;

public enum ShowState {
	DURATION("Duration: ", "s"),
	CHANCE("Change: ", "%"),
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
		return pre + value + post;
	}
}