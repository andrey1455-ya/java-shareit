package ru.practicum.shareit.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	Long id;
	String name;
	@Email(message = "Email должен быть валидным")
	String email;
}
