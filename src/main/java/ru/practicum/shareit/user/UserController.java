package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final String usersIdPath = "/{id}";
	private final UserService userService;

	@GetMapping
	public List<UserDto> findAllUsers() {
		return userService.findAllUsers();
	}

	@GetMapping(usersIdPath)
	public UserDto findUserById(@PathVariable Long id) {
		return userService.findUserById(id);
	}

	@PostMapping
	public UserDto createUser(@Valid @RequestBody UserDto userDto) {
		return userService.createUser(UserMapper.mapToUser(userDto));
	}

	@PatchMapping(usersIdPath)
	public UserDto updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long id) {
		userDto.setId(id);
		return userService.updateUser(UserMapper.mapToUser(userDto));
	}

	@DeleteMapping(usersIdPath)
	public UserDto deleteUser(@PathVariable Long id) {
		return userService.deleteUser(id);
	}
}