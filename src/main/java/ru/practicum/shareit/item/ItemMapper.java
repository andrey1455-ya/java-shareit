package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import ru.practicum.shareit.item.dto.ItemDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {
	public static ItemDto mapToItemDto(Item item) {
		ItemDto dto = new ItemDto();
		dto.setId(item.getId());
		dto.setName(item.getName());
		dto.setDescription(item.getDescription());
		dto.setAvailable(item.getAvailable());
		dto.setOwner(item.getOwner());
		dto.setRequest(item.getRequest());
		return dto;
	}

	public static Item mapToItem(ItemDto dto) {
		Item item = new Item();
		item.setId(dto.getId());
		item.setName(dto.getName());
		item.setDescription(dto.getDescription());
		item.setAvailable(dto.getAvailable());
		item.setOwner(dto.getOwner());
		item.setRequest(dto.getRequest());
		return item;
	}
}
