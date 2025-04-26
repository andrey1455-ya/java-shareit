package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.item.model.CommentDto;
import ru.practicum.shareit.item.model.CreateItemDto;
import ru.practicum.shareit.item.model.ExtendedItemDto;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.model.ItemDtoRequest;
import ru.practicum.shareit.item.model.UpdateItemDto;

import java.util.Collection;


@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
	private static final String HEADER_SHARER_USER_ID = "X-Sharer-User-Id";
	private final ItemService itemService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ItemDtoRequest createItem(@Valid @RequestBody CreateItemDto dto, @RequestHeader(HEADER_SHARER_USER_ID) Long userId) {
		return itemService.createItem(userId, dto);
	}

	@PatchMapping("/{itemId}")
	public ItemDto updateItem(@PathVariable Long itemId, @RequestBody UpdateItemDto dto,
							  @RequestHeader(HEADER_SHARER_USER_ID) Long userId) {
		return itemService.updateItem(itemId, dto, userId);
	}

	@GetMapping("/{itemId}")
	public ExtendedItemDto getItemById(@PathVariable Long itemId, @RequestHeader(HEADER_SHARER_USER_ID) Long userId) {
		return itemService.getItemById(itemId);
	}

	@GetMapping
	public Collection<ItemDto> getAllItemsByOwner(@RequestHeader(HEADER_SHARER_USER_ID) Long userId) {
		return itemService.getAllItemsByOwner(userId);
	}

	@GetMapping("/search")
	public Collection<ItemDto> searchItems(@RequestParam String text, @RequestHeader(HEADER_SHARER_USER_ID) Long userId) {
		return itemService.searchItems(text);
	}

	@PostMapping("/{itemId}/comment")
	@ResponseStatus(HttpStatus.CREATED)
	public CommentDto addCommentToItem(@PathVariable long itemId, @RequestHeader(HEADER_SHARER_USER_ID) long authorId, @RequestBody @Valid CommentDto dto) {
		return itemService.addCommentToItem(authorId, itemId, dto);
	}
}
