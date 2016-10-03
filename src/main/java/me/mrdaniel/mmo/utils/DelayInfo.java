package me.mrdaniel.mmo.utils;

import me.mrdaniel.mmo.enums.Ability;

public class DelayInfo {
	public long expires;
	public Ability ability;
	
	public DelayInfo(long expires, Ability ability) {
		this.expires = expires;
		this.ability = ability;
	}
}