package me.mrdaniel.adventuremmo.exception;

import javax.annotation.Nonnull;

public class ServiceException extends Exception {

	private static final long serialVersionUID = -1383370684649893591L;

	public ServiceException(@Nonnull final String message) {
		super(message);
	}
}