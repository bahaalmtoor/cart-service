package com.link.cart_service.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.link.cart_service.dto.CartDto;
import com.link.cart_service.dto.ItemDto;
import com.link.cart_service.service.CartService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@RestController
@Validated
@RequestMapping("/carts")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping
	public ResponseEntity<CartDto> createCart(@Valid @RequestBody CartDto cartDto) {
		return new ResponseEntity<>(cartService.createCart(cartDto), HttpStatus.CREATED);
	}

	@PostMapping("/{cartId}")
	public ResponseEntity<CartDto> addItemsToCart(@PathVariable String cartId,
			@RequestBody @NotEmpty Set<@Valid ItemDto> itemsDto) {
		return ResponseEntity.ok(cartService.addItemsToCart(cartId, itemsDto));
	}

	@DeleteMapping("/{cartId}")
	public ResponseEntity<CartDto> removeItemsFromCart(@PathVariable String cartId,
			@RequestBody @NotEmpty Set<String> itemIds) {
		return ResponseEntity.ok(cartService.removeItemsFromCart(cartId, itemIds));
	}

	@PutMapping("/{cartId}")
	public ResponseEntity<CartDto> updateCart(@PathVariable String cartId, @RequestBody @Valid CartDto cartDto) {
		return ResponseEntity.ok(cartService.updateCart(cartId, cartDto));
	}

	@GetMapping("/{cartId}")
	public ResponseEntity<CartDto> getCart(@PathVariable String cartId) {
		return ResponseEntity.ok(cartService.getCart(cartId));
	}
}
