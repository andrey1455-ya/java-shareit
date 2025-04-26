package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exceptions.InternalServerException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.CommentDto;
import ru.practicum.shareit.item.model.CreateItemDto;
import ru.practicum.shareit.item.model.ExtendedItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.model.ItemDtoRequest;
import ru.practicum.shareit.item.model.UpdateItemDto;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;


@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
	private final ItemRepository itemRepository;
	private final UserRepository userRepository;
	private final BookingRepository bookingRepository;
	private final CommentRepository commentRepository;
	private final ItemRequestRepository itemRequestRepository;

	@Override
	@Transactional
	public ItemDtoRequest createItem(Long id, CreateItemDto dto) {
		User itemOwner = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = %s не найден", id)));
		Item item = ItemMapper.mapToItem(dto);
		item.setOwner(itemOwner);
		Long itemRequestId = null;
		if (dto.getRequestId() != null) {
			checkItemRequest(dto.getRequestId());
			ItemRequest itemRequest = itemRequestRepository.findById(dto.getRequestId())
					.orElseThrow(() -> new NotFoundException("Запрос с id = " + dto.getRequestId() + " не найден"));
			item.setItemRequest(itemRequest);
			itemRequestId = itemRequest.getId();
		}
		return ItemMapper.toItemDtoRequest(itemRepository.save(item), itemRequestId);
	}

	@Override
	public ItemDto updateItem(Long itemId, UpdateItemDto dto, Long userId) {
		Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException(String.format("Вещь с id = %d не найдена", itemId)));
		if (!item.getOwner().getId().equals(userId)) {
			throw new InternalServerException(String.format("Пользователь с id = %s не владелец вещи с id = %s", userId, itemId));
		}
		if (dto.getName() != null && !dto.getName().isBlank()) {
			item.setName(dto.getName());
		}
		if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
			item.setDescription(dto.getDescription());
		}
		if (dto.getAvailable() != null) {
			item.setAvailable(dto.getAvailable());
		}
		return ItemMapper.mapToItemDto(itemRepository.save(item));
	}

	@Override
	public ExtendedItemDto getItemById(Long itemId) {
		Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException(String.format("Вещь с id = %d не найдена", itemId)));

		Collection<Booking> bookings = bookingRepository.findByItemId(itemId);

		return ItemMapper.mapToExtendedItemDto(item, bookings);
	}

	@Override
	public Collection<ItemDto> getAllItemsByOwner(Long id) {
		return itemRepository.findByOwnerId(id).stream()
				.map(ItemMapper::mapToItemDto)
				.toList();
	}

	@Override
	public Collection<ItemDto> searchItems(String text) {
		if (text == null || text.isBlank()) {
			return Collections.emptyList();
		}

		return itemRepository.findByNameOrDescriptionContainingIgnoreCase(text).stream()
				.map(ItemMapper::mapToItemDto)
				.toList();
	}

	@Override
	public CommentDto addCommentToItem(long authorId, long itemId, CommentDto commentDto) {
		Comment comment = CommentMapper.mapToComment(authorId, itemId, commentDto);
		Collection<Booking> authorBookings = bookingRepository.findAllByBookerIdAndItemIdAndEndBefore(authorId, itemId, LocalDateTime.now());

		if (authorBookings.isEmpty()) {
			throw new InternalServerException(String.format("Пользователь с id = %d не может оставить комментарии к вещи с id = %d", comment.getAuthor().getId(), comment.getItem().getId()));
		}

		comment.setItem(itemRepository.findById(comment.getItem().getId()).orElseThrow(() -> new NotFoundException(String.format("Вещь с id = %d не найдена", comment.getItem().getId()))));
		comment.setAuthor(userRepository.findById(comment.getAuthor().getId()).orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = %d не найден", comment.getAuthor().getId()))));

		return CommentMapper.mapToCommentDto(commentRepository.save(comment));
	}

	private void checkItemRequest(Long id) {
		if (!itemRequestRepository.existsById(id)) {
			throw new NotFoundException(String.format("Запроса с Id = %s не существует", id));
		}
	}
}
