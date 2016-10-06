package me.mrdaniel.mmo.io.players;

import me.mrdaniel.mmo.enums.Setting;

public class Settings {
	
	private boolean sounds;
	private boolean effects;
	private boolean scoreboard;
	
	public Settings(boolean sounds, boolean effects, boolean scoreboard) {
		this.sounds = sounds;
		this.effects = effects;
		this.scoreboard = scoreboard;
	}
	
	/**
	 * Get a new default Settings instance.
	 * 
	 * @return Settings playerSettings 
	 * Default Settings
	 */
	public Settings() {
		this.sounds = true;
		this.effects = true;
		this.scoreboard = true;
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
	public void setSetting(Setting setting, boolean value) {
		if (setting == Setting.EFFECTS) { effects = value; }
		else if (setting == Setting.SOUNDS) { sounds = value; }
		else if (setting == Setting.SCOREBOARD) { scoreboard = value; }
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
	public boolean getSetting(Setting setting) {
		if (setting == Setting.EFFECTS) { return effects; }
		else if (setting == Setting.SOUNDS) { return sounds; }
		else if (setting == Setting.SCOREBOARD) { return scoreboard; }
		return true;
	}
	
	/**
	 * Serialize the Settings into an boolean[].
	 * 
	 * @return boolean[] serialized Settings. 
	 * Returns the serialized Settings.
	 */
	public boolean[] serialize() {
		boolean[] sRaw = {sounds, effects, scoreboard};
		return sRaw;
	}
	
	/**
	 * Deserialize the boolean[] into a Settings.
	 * 
	 * @return Settings deserialized boolean[].
	 * Returns the deserialized boolean[].
	 */
	public static Settings deserialize(boolean[] sRaw) {
		if (sRaw.length != 3) { return update(sRaw); }
		return new Settings(sRaw[0], sRaw[1], sRaw[2]);
	}
	
	private static Settings update(boolean[] sRawOld) {
		boolean[] sRaw = {true,true,true};
		int lowest = (sRaw.length > sRawOld.length) ? sRawOld.length : sRaw.length;
		
		for (int i = 0; i < lowest; i++) {
			sRaw[i] = sRawOld[i];
		}
		return deserialize(sRaw);
	}
}