package me.mrdaniel.mmo.utils;

import javax.annotation.Nonnull;

public class Tuple<F, S, T> {

	@Nonnull private final F first;
	@Nonnull private final S second;
	@Nonnull private final T third;

	public Tuple(@Nonnull  final F first, @Nonnull  final S second, @Nonnull final T third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}

	@Nonnull public F getFirst() { return this.first; }
	@Nonnull public S getSecond() { return this.second; }
	@Nonnull public T getThird() { return this.third; }
}