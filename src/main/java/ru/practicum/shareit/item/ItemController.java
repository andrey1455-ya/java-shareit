package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
	private final String itemsIdPath = "/{id}";
	private final String searchPath = "/search";
	private final String userIdHeader = "X-Sharer-User-Id";
	private final ItemService itemService;
	private final UserService userService;

	@GetMapping()
	public List<ItemDto> findAllItemsFromUser(@RequestHeader(value = userIdHeader, required = false) Long userId) {
		if (userId == null) {
			throw new ValidationException("Id владельца должен быть указан");
		}
		userService.findUserById(userId);
		return itemService.findAllItemsForUser(userId);
	}

	@GetMapping(itemsIdPath)
	public ItemDto findItemById(@PathVariable Long id) {
		return itemService.findItemById(id);
	}

	@GetMapping(searchPath)
	public List<ItemDto> findItemByText(@RequestParam(required = false) String text) {
		if (text == null || text.isBlank()) {
			return List.of();
		}
		return itemService.findItemByText(text);
	}

	@PostMapping()
	public ItemDto createItem(@Valid @RequestBody Item item,
							  @RequestHeader(value = userIdHeader, required = false) Long userId) {
		if (userId == null) {
			throw new ValidationException("Id пользователя владельца должен быть указан");
		}
		userService.findUserById(userId);
		item.setOwner(userId);
		return itemService.createItem(item);
	}

	@PatchMapping(itemsIdPath)
	public ItemDto updateItem(@Valid @RequestBody Item item,
							  @RequestHeader(value = userIdHeader, required = false) Long userId,
							  @PathVariable Long id) {
		if (userId == null) {
			throw new ValidationException("Id пользователя владельца должен быть указан");
		}
		userService.findUserById(userId);
		item.setOwner(userId);
		item.setId(id);
		return itemService.updateItem(item);
	}
}

