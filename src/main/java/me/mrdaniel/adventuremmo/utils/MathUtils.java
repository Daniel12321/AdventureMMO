package me.mrdaniel.adventuremmo.utils;

public class MathUtils {

	public static int expTillNextLevel(final int current_level) {
		return 83 * current_level + 500;
	}

	public static double between(final double value, final double min, final double max) {
		return value < min ? min : value > max ? max : value;
	}

	public static int secondsTillTime(final long time) {
		return (int) ((time - System.currentTimeMillis()) / 1000);
	}
}