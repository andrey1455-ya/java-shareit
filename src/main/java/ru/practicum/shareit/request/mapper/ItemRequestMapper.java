package ru.practicum.shareit.request.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemRequestMapper {
	public static ItemRequest mapToItemRequest(ItemRequestDto itemRequestDto) {
		return ItemRequest.builder()
				.id(itemRequestDto.getId())
				.description(itemRequestDto.getDescription())
				.requester(itemRequestDto.getRequester())
				.created(itemRequestDto.getCreated())
				.build();
	}

	public static ItemRequestDto mapToItemRequestDto(ItemRequest itemRequest) {
		return ItemRequestDto.builder()
				.id(itemRequest.getId())
				.description(itemRequest.getDescription())
				.requester(itemRequest.getRequester())
				.created(itemRequest.getCreated())
				.build();
	}
}
