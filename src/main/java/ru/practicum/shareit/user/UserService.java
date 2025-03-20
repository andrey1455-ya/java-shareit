package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@Slf4j
@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<UserDto> findAllUsers() {
		return userRepository.findAllUsers()
				.stream()
				.map(UserMapper::mapToUserDto)
				.toList();
	}

	public UserDto findUserById(Long id) {
		return userRepository.findUserById(id)
				.map(UserMapper::mapToUserDto)
				.orElseThrow(() -> new NotFoundException(String.format("Пользователь с id=%d не найден", id)));
	}

	public UserDto createUser(User user) {
		return UserMapper.mapToUserDto(userRepository.createUser(user));
	}

	public UserDto updateUser(User newUser) {
		return UserMapper.mapToUserDto(userRepository.updateUser(newUser));
	}

	public UserDto deleteUser(Long id) {
		return userRepository.deleteUser(id)
				.map(UserMapper::mapToUserDto)
				.orElseThrow(() -> new NotFoundException(String.format("Пользователь с id=%d не найден", id)));
	}
}

