package me.mrdaniel.mmo.io.players;

import java.util.concurrent.ConcurrentHashMap;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.Setting;

public class Settings {
	
	private ConcurrentHashMap<Setting, Boolean> settings;

	public Settings(boolean... values) {
		settings = new ConcurrentHashMap<Setting, Boolean>();
		loadDefaults();
		for (int i = 0; i < values.length && i < Setting.values().length; i++) {
			settings.put(Setting.get(i), values[i]);
		}
	}
	
	private void loadDefaults() {
		settings.put(Setting.EFFECTS, true);
		settings.put(Setting.SCOREBOARD, true);
		settings.put(Setting.SCOREBOARDPERMANENT, false);
		
		for (Setting s : Setting.values()) {
			if (Main.getInstance().getConfig().FORCEDSETTINGS.containsKey(s)) settings.put(s, Main.getInstance().getConfig().FORCEDSETTINGS.get(s));
		}
	}
	
	/**
	 * Set a setting for a player
	 * 
	 * @param Setting setting
	 * Which setting to set
	 * 
	 * @param boolean value
	 * Value of what to set the Setting
	 */
	public synchronized void setSetting(Setting setting, boolean value) {
		settings.put(setting, value);
	}
	
	/**
	 * Get a setting from a player
	 * 
	 * @param Setting setting
	 * Which setting to set
	 * 
	 * @return boolean value
	 * Value of the Setting
	 */
	public synchronized boolean getSetting(Setting setting) {
		return settings.get(setting);
	}
	
	/**
	 * Serialize the Settings into an boolean[].
	 * 
	 * @return boolean[] serialized Settings. 
	 * Returns the serialized Settings.
	 */
	public synchronized boolean[] serialize() {
		return new boolean[]{settings.get(Setting.EFFECTS), settings.get(Setting.SCOREBOARD), settings.get(Setting.SCOREBOARDPERMANENT)};
	}
}