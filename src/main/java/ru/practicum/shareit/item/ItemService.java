package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserService;

import java.util.List;

@Service
public class ItemService {
	private final ItemRepository itemRepository;
	private final UserService userService;

	public ItemService(ItemRepository itemRepository, UserService userService) {
		this.itemRepository = itemRepository;
		this.userService = userService;
	}

	public List<ItemDto> findAllItemsForUser(Long userId) {
		if (userId == null) {
			throw new ValidationException("Id владельца должен быть указан");
		}
		userService.findUserById(userId);
		return itemRepository.findAllItems()
				.stream()
				.filter(item -> userId.equals(item.getOwner()))
				.map(ItemMapper::mapToItemDto)
				.toList();
	}

	public ItemDto findItemById(Long id) {
		Item item = itemRepository.findItemById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Вещь с id=%d не найдена", id)));
		return ItemMapper.mapToItemDto(item);
	}

	public List<ItemDto> findItemByText(String text) {
		if (text == null || text.isBlank()) {
			return List.of();
		}
		return itemRepository.findItemByText(text)
				.stream()
				.map(ItemMapper::mapToItemDto)
				.toList();
	}

	public ItemDto createItem(ItemDto itemDto, Long userId) {
		if (userId == null) {
			throw new ValidationException("Id пользователя владельца должен быть указан");
		}
		userService.findUserById(userId);
		itemDto.setOwner(userId);
		Item item = ItemMapper.mapToItem(itemDto);
		validateItemForCreate(item);
		Item savedItem = itemRepository.saveItem(item);
		return ItemMapper.mapToItemDto(savedItem);
	}

	public ItemDto updateItem(ItemDto itemDto, Long userId, Long id) {
		if (userId == null) {
			throw new ValidationException("Id пользователя владельца должен быть указан");
		}
		userService.findUserById(userId);
		itemDto.setId(id);
		itemDto.setOwner(userId);
		Item newItem = ItemMapper.mapToItem(itemDto);
		Item existingItem = itemRepository.findItemById(newItem.getId())
				.orElseThrow(() -> new NotFoundException(String.format("Вещь с id=%d не найдена", newItem.getId())));

		if (newItem.getName() != null) {
			if (newItem.getName().isBlank()) {
				throw new ValidationException("Название вещи не может быть пустым");
			}
			existingItem.setName(newItem.getName());
		}
		if (newItem.getDescription() != null) {
			if (newItem.getDescription().isBlank()) {
				throw new ValidationException("Описание вещи не может быть пустым");
			}
			existingItem.setDescription(newItem.getDescription());
		}
		if (newItem.getAvailable() != null) {
			existingItem.setAvailable(newItem.getAvailable());
		}
		if (newItem.getOwner() != null) {
			existingItem.setOwner(newItem.getOwner());
		}
		if (newItem.getRequest() != null) {
			existingItem.setRequest(newItem.getRequest());
		}

		Item updatedItem = itemRepository.saveItem(existingItem);
		return ItemMapper.mapToItemDto(updatedItem);
	}

	private void validateItemForCreate(Item item) {
		if (item.getName() == null || item.getName().isBlank()) {
			throw new ValidationException("Необходимо указать название вещи");
		}
		if (item.getDescription() == null || item.getDescription().isBlank()) {
			throw new ValidationException("Необходимо указать описание вещи");
		}
		if (item.getAvailable() == null) {
			throw new ValidationException("Необходимо указать доступность вещи");
		}
	}
}
