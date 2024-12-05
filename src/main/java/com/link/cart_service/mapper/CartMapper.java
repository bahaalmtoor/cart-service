package com.link.cart_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.link.cart_service.dto.CartDto;
import com.link.cart_service.entity.Cart;

@Mapper
public interface CartMapper {

	CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

	Cart convert(CartDto cartDto);

	CartDto convert(Cart cart);
}
