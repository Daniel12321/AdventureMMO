package me.mrdaniel.mmo.utils;

import me.mrdaniel.mmo.enums.Ability;

public class DelayWrapper {
	public long expires;
	public Ability ability;
	
	public DelayWrapper(long expires, Ability ability) {
		this.expires = expires;
		this.ability = ability;
	}
}