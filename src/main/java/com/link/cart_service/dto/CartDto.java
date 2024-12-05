package com.link.cart_service.dto;

import java.util.Collections;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.Valid;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartDto {

	private String id;

	@Valid
	private Set<ItemDto> items;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<ItemDto> getItems() {
		if (items == null) {
			return Collections.emptySet();
		}
		
		return items;
	}

	public void setItems(Set<ItemDto> items) {
		this.items = items;
	}
}
