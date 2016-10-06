package me.mrdaniel.mmo.io.players;

import java.io.Serializable;

public class MMOPlayerData implements Serializable {
	
	private static final long serialVersionUID = -4027611425461562030L;
	public int[][] skills;
	public boolean[] settings;
	
	public MMOPlayerData(int[][] skills, boolean[] settings) {
		this.skills = skills;
		this.settings = settings;
	}
}