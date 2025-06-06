package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingState;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {
	private static final String SHARER_USER_ID_HEADER = "X-Sharer-User-Id";

	private final BookingClient bookingClient;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Object> createBooking(
			@Valid @RequestBody BookingDto requestDto,
			@RequestHeader(SHARER_USER_ID_HEADER) Long userId) {
		return bookingClient.createBooking(userId, requestDto);
	}

	@PatchMapping("/{bookingId}")
	public ResponseEntity<Object> updateBooking(
			@RequestParam Boolean approved,
			@PathVariable Long bookingId,
			@RequestHeader(SHARER_USER_ID_HEADER) Long userId) {
		return bookingClient.updateBooking(userId, bookingId, approved);
	}

	@GetMapping("/{bookingId}")
	public ResponseEntity<Object> getBookingById(
			@PathVariable Long bookingId,
			@RequestHeader(SHARER_USER_ID_HEADER) Long userId) {
		return bookingClient.getBookingById(bookingId, userId);
	}

	@GetMapping
	public ResponseEntity<Object> getBookingsByUser(
			@RequestParam(defaultValue = "ALL") BookingState state,
			@RequestHeader(SHARER_USER_ID_HEADER) Long userId) {
		return bookingClient.getBookingsByUser(userId, state);
	}

	@GetMapping("/owner")
	public ResponseEntity<Object> getBookingsByOwner(
			@RequestParam(defaultValue = "ALL") BookingState state,
			@RequestHeader(SHARER_USER_ID_HEADER) Long userId) {
		return bookingClient.getBookingsByOwner(userId, state);
	}
}
