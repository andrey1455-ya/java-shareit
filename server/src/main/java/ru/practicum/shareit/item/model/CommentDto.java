package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;

@Builder
@Data
public class CommentDto {
	private final long id;
	@NotBlank(message = "Комментарий не может быть пустым")
	private final String text;
	private final ItemDto item;
	private final String authorName;
	private final LocalDateTime created;
}
