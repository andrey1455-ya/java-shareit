package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;
import ru.practicum.shareit.user.interfaces.UserRepository;
import ru.practicum.shareit.user.interfaces.UserService;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Override
	public Collection<UserDto> getAllUsers() {
		return userRepository.findAll().stream().map(UserMapper::mapToUserDto).toList();
	}

	@Override
	public UserDto createUser(UserDto dto) {
		User user = UserMapper.mapToUser(dto);
		return UserMapper.mapToUserDto(userRepository.save(user));
	}

	@Override
	public UserDto updateUser(Long userId, UserUpdateDto dto) {
		checkId(userId);
		User updateUser = UserMapper.mapUserUpdateDtoToUser(dto);
		User oldUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = %d не найден", userId)));
		if (updateUser.getName() != null && !updateUser.getName().isBlank()) {
			oldUser.setName(updateUser.getName());
		}
		String email = updateUser.getEmail();
		if (email != null && !email.isBlank()) {
			oldUser.setEmail(email);
		}

		return UserMapper.mapToUserDto(userRepository.save(oldUser));
	}

	@Override
	public UserDto findUserById(Long id) {

		return UserMapper.mapToUserDto(userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = %d не найден", id))));
	}

	@Override
	public void deleteUser(Long id) {
		checkId(id);
		userRepository.deleteById(id);
	}

	private void checkId(Long id) {
		if (id == null) {
			throw new NotFoundException(String.format("Пользователь с id = %d не найден", id));
		}
	}
}
