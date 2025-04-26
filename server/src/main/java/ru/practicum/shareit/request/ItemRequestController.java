package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.request.interfaces.ItemRequestService;
import ru.practicum.shareit.request.model.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequestDtoCreate;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/requests")
public class ItemRequestController {
	private final ItemRequestService itemRequestService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ItemRequestDto createItemRequest(@RequestBody ItemRequestDtoCreate dto,
											@RequestHeader("X-Sharer-User-Id") Long userId) {
		return itemRequestService.createItemRequest(userId, dto);
	}

	@GetMapping
	public Collection<ItemRequestDto> getAllItemRequestsByrequestor(@RequestHeader("X-Sharer-User-Id") Long userId) {
		return itemRequestService.getAllItemRequestsByRequester(userId);
	}

	@GetMapping("/all")
	public Collection<ItemRequestDto> getAllItemRequests() {
		return itemRequestService.getAllItemRequests();
	}

	@GetMapping("/{requestId}")
	public ItemRequestDto getItemRequestById(@PathVariable Long requestId) {
		return itemRequestService.getItemRequestById(requestId);
	}
}
