package com.link.cart_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.link.cart_service.entity.Cart;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
	//
}
