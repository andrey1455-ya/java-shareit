package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

@Builder
@Data
public final class ExtendedItemDto {
	private final long id;

	private final String name;

	private final String description;

	private final Boolean available;

	private Collection<String> comments;

	private final LocalDateTime lastBooking;

	private final LocalDateTime nextBooking;
}
