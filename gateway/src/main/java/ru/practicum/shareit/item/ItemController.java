package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
	private static final String USER_ID_HEADER = "X-Sharer-User-Id";
	private final ItemClient itemClient;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Object> createItem(@Valid @RequestBody ItemDto dto,
											 @RequestHeader(USER_ID_HEADER) Long userId) {
		return itemClient.createItem(userId, dto);
	}

	@PatchMapping("/{itemId}")
	public ResponseEntity<Object> updateItem(@PathVariable Long itemId,
											 @RequestBody ItemDto dto,
											 @RequestHeader(USER_ID_HEADER) Long userId) {
		return itemClient.updateItem(itemId, dto, userId);
	}

	@GetMapping("/{itemId}")
	public ResponseEntity<Object> getItemById(@PathVariable Long itemId,
											  @RequestHeader(USER_ID_HEADER) Long userId) {
		return itemClient.getItemById(itemId, userId);
	}

	@GetMapping
	public ResponseEntity<Object> getAllItemsByOwner(@RequestHeader(USER_ID_HEADER) Long userId) {
		return itemClient.getAllItemsByOwner(userId);
	}

	@GetMapping("/search")
	public ResponseEntity<Object> searchItems(@RequestParam String text,
											  @RequestHeader(USER_ID_HEADER) Long userId) {
		return itemClient.searchItems(text, userId);
	}

	@PostMapping("/{itemId}/comment")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDto dto,
												@RequestHeader(USER_ID_HEADER) Long userId,
												@PathVariable Long itemId) {
		return itemClient.createComment(itemId, userId, dto);
	}
}
