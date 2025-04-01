package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.DuplicatedDataException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
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
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Пользователь с id=%d не найден", id)));
		return UserMapper.mapToUserDto(user);
	}

	public UserDto createUser(User user) {
		validateUserForCreate(user);
		if (userRepository.findAllUsers().stream()
				.anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
			throw new DuplicatedDataException("Этот email уже используется");
		}
		User savedUser = userRepository.saveUser(user);
		return UserMapper.mapToUserDto(savedUser);
	}

	public UserDto updateUser(User newUser) {
		User existingUser = userRepository.findUserById(newUser.getId())
				.orElseThrow(() -> new NotFoundException(String.format("Пользователь с id=%d не найден", newUser.getId())));

		if (newUser.getEmail() != null) {
			if (!newUser.getEmail().equals(existingUser.getEmail()) &&
					userRepository.findAllUsers().stream().anyMatch(u -> u.getEmail().equals(newUser.getEmail()))) {
				throw new DuplicatedDataException("Этот email уже используется");
			}
			existingUser.setEmail(newUser.getEmail());
		}
		if (newUser.getName() != null) {
			existingUser.setName(newUser.getName());
		}

		User updatedUser = userRepository.updateUser(existingUser);
		return UserMapper.mapToUserDto(updatedUser);
	}

	public UserDto deleteUser(Long id) {
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Пользователь с id=%d не найден", id)));
		userRepository.deleteUser(id);
		return UserMapper.mapToUserDto(user);
	}

	private void validateUserForCreate(User user) {
		if (user.getName() == null || user.getName().isBlank()) {
			throw new ValidationException("Имя должно быть указано");
		}
		if (user.getEmail() == null || user.getEmail().isBlank()) {
			throw new ValidationException("Email должен быть указан");
		}
	}
}
