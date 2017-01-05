package me.mrdaniel.mmo.utils;

public class Formula {

	private final double start;
	private final double increment;
	private final double max;

	public Formula(final double start, final double increment, final double max) {
		this.start = start;
		this.increment = increment;
		this.max = max;
	}

	public double getValue(final double level) {
		return Math.min(this.max, this.start + (this.increment * level));
	}
}