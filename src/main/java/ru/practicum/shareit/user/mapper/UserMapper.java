package ru.practicum.shareit.user.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
	public static UserDto mapToUserDto(User user) {
		return UserDto.builder()
				.id(user.getId())
				.name(user.getName())
				.email(user.getEmail())
				.build();
	}

	public static User mapToUser(UserDto userDto) {
		return User.builder()
				.id(userDto.getId())
				.name(userDto.getName())
				.email(userDto.getEmail())
				.build();
	}

	public static User mapUserUpdateDtoToUser(UserUpdateDto userDto) {
		return User.builder()
				.name(userDto.getName())
				.email(userDto.getEmail())
				.build();
	}
}
