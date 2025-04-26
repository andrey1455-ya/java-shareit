package ru.practicum.shareit.booking.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class CreateBookingDto {
	@NotNull(message = "Ид вещи не может быть пустым")
	private final Long itemId;
	@NotNull(message = "Дата начала бронирования не может быть пустой")
	private final LocalDateTime start;
	@Future(message = "Дата окончания бронирования должна быть в будущем")
	@NotNull(message = "Дата окончания бронирования не может быть пустой")
	private final LocalDateTime end;
}
