package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;

	@GetMapping
	public List<ItemDto> findAllItemsFromUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
		return itemService.findAllItemsForUser(userId);
	}

	@GetMapping("/{id}")
	public ItemDto findItemById(@PathVariable Long id) {
		return itemService.findItemById(id);
	}

	@GetMapping("/search")
	public List<ItemDto> findItemByText(@RequestParam String text) {
		return itemService.findItemByText(text);
	}

	@PostMapping
	public ItemDto createItem(@Valid @RequestBody ItemDto itemDto,
							  @RequestHeader("X-Sharer-User-Id") Long userId) {
		return itemService.createItem(itemDto, userId);
	}

	@PatchMapping("/{id}")
	public ItemDto updateItem(@Valid @RequestBody ItemDto itemDto,
							  @RequestHeader("X-Sharer-User-Id") Long userId,
							  @PathVariable Long id) {
		return itemService.updateItem(itemDto, userId, id);
	}
}


