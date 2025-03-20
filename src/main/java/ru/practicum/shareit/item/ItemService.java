package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@Service
public class ItemService {
	private final ItemRepository itemRepository;

	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	public List<ItemDto> findAllItemsForUser(Long userId) {
		return itemRepository.findAllItemsForUser()
				.stream()
				.filter(item -> item.getOwner() != null && item.getOwner().equals(userId))
				.map(ItemMapper::mapToItemDto)
				.toList();
	}

	public ItemDto findItemById(Long id) {
		return itemRepository.findItemById(id)
				.map(ItemMapper::mapToItemDto)
				.orElseThrow(() -> new NotFoundException(String.format("Вещь с id=%d не найдена", id)));
	}

	public List<ItemDto> findItemByText(String text) {
		return itemRepository.findItemByText(text)
				.stream()
				.map(ItemMapper::mapToItemDto)
				.toList();
	}

	public ItemDto createItem(Item item) {
		return ItemMapper.mapToItemDto(itemRepository.createItem(item));
	}

	public ItemDto updateItem(Item newItem) {
		return ItemMapper.mapToItemDto(itemRepository.updateItem(newItem));
	}
}
