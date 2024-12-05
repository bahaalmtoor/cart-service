package com.link.cart_service.service.impl;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.link.cart_service.dto.CartDto;
import com.link.cart_service.dto.ItemDto;
import com.link.cart_service.entity.Cart;
import com.link.cart_service.entity.Item;
import com.link.cart_service.error.ConflictException;
import com.link.cart_service.error.NotFoundException;
import com.link.cart_service.mapper.CartMapper;
import com.link.cart_service.mapper.ItemMapper;
import com.link.cart_service.repository.CartRepository;
import com.link.cart_service.rest.RestCall;
import com.link.cart_service.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

	@Value("${item.service.url}")
	private String itemServiceUrl;

	@Autowired
	private RestCall restCall;

	@Autowired
	private CartRepository cartRepository;

	private Cart convert(CartDto cartDto) {
		return CartMapper.INSTANCE.convert(cartDto);
	}

	private CartDto convert(Cart cart) {
		return CartMapper.INSTANCE.convert(cart);
	}

	private Cart getOrElseThrow(String id) {
		return cartRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Cart with Id [%s] not found!", id)));
	}

	@Override
	public CartDto createCart(CartDto cartDto) {

		LOGGER.info("Create cart: {}", cartDto);

		String id = UUID.randomUUID().toString();

		if (cartRepository.findById(id).orElse(null) != null) {
			throw new ConflictException(String.format("Cart with Id [%s] already exists!", id));
		}

		Cart cart = convert(cartDto);
		cart.setId(id);

		return convert(cartRepository.save(cart));
	}

	@Override
	public CartDto addItemsToCart(String id, Set<ItemDto> itemsDto) {

		LOGGER.info("Add items to cart with id: {}", id);

		Cart cart = getOrElseThrow(id);

		Set<Item> newItems = ItemMapper.INSTANCE.convert(itemsDto);
		if (CollectionUtils.isEmpty(cart.getItems())) {
			cart.setItems(newItems);
		} else {
			Set<Item> itemsAlreadyExists = cart.getItems().stream().filter(item -> newItems.contains(item))
					.collect(Collectors.toSet());
			if (!CollectionUtils.isEmpty(itemsAlreadyExists)) {
				throw new ConflictException(String.format("Cart with Id [%s] already contains items! [%s]", id,
						itemsAlreadyExists.stream().map(item -> item.getId()).collect(Collectors.joining(","))));
			}

			cart.getItems().addAll(newItems);
		}

		return convert(cartRepository.save(cart));
	}

	@Override
	public CartDto removeItemsFromCart(String id, Set<String> itemIds) {

		LOGGER.info("Remove items from cart with id: {}", id);

		Cart cart = getOrElseThrow(id);

		if (CollectionUtils.isEmpty(cart.getItems())) {
			throw new NotFoundException(String.format("Cart with Id [%s] not contains items!", id));
		}

		Set<Item> items = cart.getItems().stream().filter(item -> {
			if (itemIds.contains(item.getId())) {
				itemIds.remove(item.getId());
				return false;
			}
			return true;
		}).collect(Collectors.toSet());

		if (!CollectionUtils.isEmpty(itemIds)) {
			throw new NotFoundException(String.format("Cart with Id [%s] not contains items! [%s]", id,
					itemIds.stream().collect(Collectors.joining(","))));
		}

		cart.setItems(items);

		return convert(cartRepository.save(cart));
	}

	@Override
	public CartDto updateCart(String id, CartDto cartDto) {

		LOGGER.info("Update cart: {} with id: {}", cartDto, id);

		Cart cart = getOrElseThrow(id);

		Cart cartRequest = convert(cartDto);
		if (CollectionUtils.isEmpty(cart.getItems()) || CollectionUtils.isEmpty(cartRequest.getItems())) {
			throw new NotFoundException(String.format("Cart with Id [%s] not contains items!", id));
		}

		Set<Item> items = cart.getItems().stream().map(item -> {
			Item returnItem = null;
			if (cartRequest.getItems().contains(item)) {
				returnItem = cartRequest.getItems().stream().filter(itemR -> itemR.equals(item)).findFirst().get();
				cartRequest.getItems().remove(item);
				return returnItem;
			}
			return item;
		}).collect(Collectors.toSet());

		if (!CollectionUtils.isEmpty(cartRequest.getItems())) {
			throw new NotFoundException(String.format("Cart with Id [%s] not contains items! [%s]", id,
					cartRequest.getItems().stream().map(item -> item.getId()).collect(Collectors.joining(","))));
		}

		cart.setItems(items);

		return convert(cartRepository.save(cart));
	}

	@Override
	public CartDto getCart(String id) {

		LOGGER.info("Fetching cart with id: {}", id);

		Cart cart = getOrElseThrow(id);

		CartDto cartDto = convert(cart);
		Set<ItemDto> items = cartDto.getItems().stream().map(e -> {
			ItemDto itemDto = restCall.getForEntity(itemServiceUrl + e.getId(), ItemDto.class);
			itemDto.setQuantity(e.getQuantity());
			return itemDto;
		}).collect(Collectors.toSet());
		cartDto.setItems(items);

		return cartDto;
	}
}