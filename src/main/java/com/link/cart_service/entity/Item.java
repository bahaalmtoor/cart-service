package com.link.cart_service.entity;

import java.util.Objects;

public class Item {

	private String id;

	private Long quantity;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Item)) {
			return false;
		}

		return this.getId().equals(((Item) obj).getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.getId().length());
	}
}