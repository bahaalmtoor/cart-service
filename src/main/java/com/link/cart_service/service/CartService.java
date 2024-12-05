package com.link.cart_service.service;

import java.util.Set;

import com.link.cart_service.dto.CartDto;
import com.link.cart_service.dto.ItemDto;

public interface CartService {

	CartDto createCart(CartDto cartDto);

	CartDto addItemsToCart(String cartId, Set<ItemDto> itemsDto);

	CartDto removeItemsFromCart(String cartId, Set<String> itemIds);

	CartDto updateCart(String cartId, CartDto cartDto);
	
	CartDto getCart(String cartId);
}
