package ru.practicum.shareit.booking;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "requestor" })
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking {
	Long id;
	LocalDateTime start;
	LocalDateTime end;
	Item item;
	User booker;
	ItemRequest request;
	Status status;
}
