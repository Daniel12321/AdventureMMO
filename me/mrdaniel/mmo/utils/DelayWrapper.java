package me.mrdaniel.mmo.utils;

import me.mrdaniel.mmo.enums.AbilityEnum;

public class DelayWrapper {
	public long expires;
	public AbilityEnum ae;
	
	public DelayWrapper(long expires, AbilityEnum ae) {
		this.expires = expires;
		this.ae = ae;
	}
}