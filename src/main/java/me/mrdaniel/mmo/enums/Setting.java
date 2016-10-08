package me.mrdaniel.mmo.enums;

public enum Setting {
	
	SOUNDS("Sounds"),
	EFFECTS("Effects"),
	SCOREBOARD("Scoreboard"),
	SCOREBOARDPERMANENT("ScoreboardPermanent");
	
	public String name;
	
	Setting(String name) {
		this.name = name;
	}
	
	public static Setting match(String name) {
		for (Setting setting : Setting.values()) {
			if (setting.name.equalsIgnoreCase(name)) { return setting; }
		}
		return null;
	}
}