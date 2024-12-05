package com.link.cart_service.entity;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "carts")
public class Cart {

	@Id
	private String id;

	private Set<Item> items;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<Item> getItems() {
		if (items == null) {
			return Collections.emptySet();
		}
		
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Cart)) {
			return false;
		}

		return this.getId().equals(((Cart) obj).getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.getId());
	}
}
