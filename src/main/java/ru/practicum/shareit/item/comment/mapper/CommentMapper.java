package ru.practicum.shareit.item.comment.mapper;

import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.comment.model.Comment;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

public class CommentMapper {
	public static Comment mapToComment(long authorId, long itemId, CommentDto dto) {
		return Comment.builder()
				.text(dto.getText())
				.item(Item.builder().id(itemId).build())
				.author(User.builder().id(authorId).build())
				.createDate(LocalDateTime.now())
				.build();

	}

	public static CommentDto mapToCommentDto(Comment comment) {
		return CommentDto.builder()
				.id(comment.getId())
				.text(comment.getText())
				.item(ItemMapper.mapToItemDto(comment.getItem()))
				.authorName(comment.getAuthor().getName())
				.created(comment.getCreateDate())
				.build();
	}
}
