package me.mrdaniel.adventuremmo.utils;

public class MathUtils {

	public static int expTillNextLevel(final int current_level) {
		return (int) (Math.pow(1.005, current_level + 600) * 1000 - 19250);
	}

	public static int between(final int value, final int min, final int max) {
		return value < min ? min : value > max ? max : value;
	}

	public static int secondsTillTime(final long time) {
		return (int) ((time - System.currentTimeMillis()) / 1000);
	}
}