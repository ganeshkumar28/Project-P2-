package com.buyerservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.buyerservice.dto.ShoppingCartProjection;
import com.buyerservice.entity.ShoppingCart;

public interface cartdaodervice extends JpaRepository<ShoppingCart,Long> {
	@Query(nativeQuery = true, value ="select * from shopping_cart sc where sc.user_user_id = :userId")
	List<ShoppingCartProjection>findCartByUserId(@Param("userId") long userId);

}
