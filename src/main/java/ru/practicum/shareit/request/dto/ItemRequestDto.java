package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Data
@Builder
public class ItemRequestDto {
	private Long id;
	private String description;
	@NotNull
	private User requester;
	private LocalDateTime created;
}
