package com.buyerservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.buyerservice.entity.ShoppingCart;

public interface cartdaodervice extends JpaRepository<ShoppingCart,Long> {
	 @Query("from com.buyerservice.entity.ShoppingCart sc WHERE sc.user.userId = :userId")
	   

	List<ShoppingCart> findByuserId(@Param("userId") Long userId);

}
