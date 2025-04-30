package ru.practicum.shareit.booking.dto;

import java.util.Optional;

public enum BookingState {
	ALL,
	CURRENT,
	FUTURE,
	PAST,
	REJECTED,
	WAITING;

	public static Optional<BookingState> from(String state) {
		if (state == null) {
			return Optional.empty();
		}
		try {
			return Optional.of(BookingState.valueOf(state.toUpperCase()));
		} catch (IllegalArgumentException e) {
			return Optional.empty();
		}
	}
}