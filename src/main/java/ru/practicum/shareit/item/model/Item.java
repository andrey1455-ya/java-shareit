package ru.practicum.shareit.item.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "items")
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Column(length = 512)
	private String description;
	private Boolean available;
	@JoinColumn(name = "user_id")
	@ManyToOne
	private User owner;
	@CollectionTable(name = "comments", joinColumns = @JoinColumn(name = "item_id"))
	@Column(name = "text")
	@ElementCollection(fetch = FetchType.LAZY)
	@ToString.Exclude
	private List<String> comments;
}
