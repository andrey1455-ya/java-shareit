package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.interfaces.UserService;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.model.UserUpdateDto;

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
	@ResponseStatus(HttpStatus.CREATED)
	public UserDto createUser(@Valid @RequestBody UserDto dto) {
		return userService.createUser(dto);
	}

	@PatchMapping("/{id}")
	public UserDto updateUser(@PathVariable Long id, @RequestBody UserUpdateDto dto) {
		return userService.updateUser(id, dto);
	}

	@GetMapping("/{id}")
	public UserDto getUserById(@PathVariable Long id) {
		return userService.findUserById(id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
	}
}
