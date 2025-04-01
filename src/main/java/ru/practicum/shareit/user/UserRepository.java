package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class UserRepository {
	private final Map<Long, User> users = new HashMap<>();
	private long counter = 1L;

	public Collection<User> findAllUsers() {
		log.info("Получение списка всех пользователей");
		return users.values();
	}

	public Optional<User> findUserById(Long id) {
		log.info("Поиск пользователя с id: {}", id);
		return Optional.ofNullable(users.get(id));
	}

	public User saveUser(User user) {
		if (user.getId() == null) {
			user.setId(getNextId());
		}
		users.put(user.getId(), user);
		log.info("Пользователь с id = {} сохранён", user.getId());
		return user;
	}

	public User updateUser(User user) {
		users.put(user.getId(), user);
		log.info("Пользователь с id = {} обновлён", user.getId());
		return user;
	}

	public Optional<User> deleteUser(Long id) {
		log.info("Удаление пользователя с id: {}", id);
		return Optional.ofNullable(users.remove(id));
	}

	private long getNextId() {
		return counter++;
	}
}
