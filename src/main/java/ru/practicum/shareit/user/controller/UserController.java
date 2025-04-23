package ru.practicum.shareit.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.user.interfaces.UserService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	@GetMapping
	public Collection<UserDto> getAllUsers() {
		return userService.getAllUsers();
	}

	@PostMapping
	public UserDto createUser(@Valid @RequestBody UserDto dto) {
		return userService.createUser(dto);
	}

	@PatchMapping("/{id}")
	public UserDto updateUser(@PathVariable Long id, @RequestBody UserUpdateDto dto) {
		return userService.updateUser(id, dto);
	}

	@GetMapping("/{id}")
	public UserDto findUserById(@PathVariable Long id) {
		return userService.findUserById(id);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
	}
}
