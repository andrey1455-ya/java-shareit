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
        for (BookingState bs : values()) {
            if (bs.name().equalsIgnoreCase(state)) {
                return Optional.of(bs);
            }
        }
        return Optional.empty();
    }
}