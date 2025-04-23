package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.interfaces.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.comment.mapper.CommentMapper;
import ru.practicum.shareit.item.comment.model.Comment;
import ru.practicum.shareit.item.comment.storage.CommentRepository;
import ru.practicum.shareit.exceptions.InternalServerException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ExtendedItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.interfaces.ItemRepository;
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.interfaces.UserRepository;

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

	@Override
	public ItemDto createItem(Long userId, ItemDto dto) {
		userRepository.findById(userId);
		Item item = ItemMapper.mapToItem(dto);
		item.setOwner(userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = %d не найден", userId))));
		return ItemMapper.mapToItemDto(itemRepository.save(item));
	}

	@Override
	public ItemDto updateItem(Long itemId, UpdateItemDto dto, Long userId) {
		Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException(String.format("Вещь с id = %d не найдена", itemId)));
		if (!item.getOwner().getId().equals(userId)) {
			throw new NotFoundException(String.format("Пользователь с id = %s не владелец вещи с id = %s", userId, itemId));
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
		Collection<Booking> authorBookings = bookingRepository.findByBookerIdAndItemId(comment.getAuthor().getId(), comment.getItem().getId());

		if (authorBookings.isEmpty() || authorBookings.stream()
				.filter(b -> b.getEnd().isBefore(LocalDateTime.now()))
				.filter(booking -> booking.getItem().getId() == itemId)
				.toList().isEmpty()) {
			throw new InternalServerException(String.format("Пользователь с id = %d не может оставить комментарии к вещи с id = %d", comment.getAuthor().getId(), comment.getItem().getId()));
		}

		comment.setItem(itemRepository.findById(comment.getItem().getId()).orElseThrow(() -> new NotFoundException(String.format("Вещь с id = %d не найдена", comment.getItem().getId()))));
		comment.setAuthor(userRepository.findById(comment.getAuthor().getId()).orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = %d не найден", comment.getAuthor().getId()))));

		return CommentMapper.mapToCommentDto(commentRepository.save(comment));
	}
}
