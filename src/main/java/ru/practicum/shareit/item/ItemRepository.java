package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class ItemRepository {
	private final Map<Long, Item> items = new HashMap<>();
	private long counter = 1L;

	public Collection<Item> findAllItems() {
		log.info("Получение всех вещей");
		return items.values();
	}

	public Optional<Item> findItemById(Long id) {
		log.info("Получение вещи с id: {}", id);
		return Optional.ofNullable(items.get(id));
	}

	public Collection<Item> findItemByText(String text) {
		log.info("Поиск вещей по тексту: {}", text);
		return items.values().stream()
				.filter(item -> item.getAvailable() != null && item.getAvailable())
				.filter(item -> item.getName().toLowerCase().contains(text.toLowerCase()) ||
						item.getDescription().toLowerCase().contains(text.toLowerCase()))
				.toList();
	}

	public Item saveItem(Item item) {
		if (item.getId() == null) {
			item.setId(getNextId());
		}
		items.put(item.getId(), item);
		log.info("Вещь с id {} сохранена", item.getId());
		return item;
	}

	private long getNextId() {
		return counter++;
	}
}
