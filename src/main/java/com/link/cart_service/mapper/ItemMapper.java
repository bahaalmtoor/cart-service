package com.link.cart_service.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.link.cart_service.dto.ItemDto;
import com.link.cart_service.entity.Item;

@Mapper
public interface ItemMapper {

	ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

	Item convert(ItemDto itemDto);

	ItemDto convert(Item item);
	
	Set<Item> convert(Set<ItemDto> itemDtos);
}
