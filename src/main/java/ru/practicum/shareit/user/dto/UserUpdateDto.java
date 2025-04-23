package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserUpdateDto {
	private String name;
	@Email(message = "Email должен быть корректный")
	private String email;
}
