package com.buyerservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.buyerservice.entity.Complaint;
import com.buyerservice.entity.Review;
import com.buyerservice.entity.ShoppingCart;

@Repository

public interface buyerdaoservice extends JpaRepository<Review,Long> {

	 @Query("from com.buyerservice.entity.Review sc WHERE sc.user.userId = :userId")
	   List<Review> findbyuserid(@Param("userId") Long userId);
	 @Modifying
	 @Query("Delete from com.buyerservice.entity.Review sc WHERE sc.product.productId = :prid")

	void deletebyproid(@Param("prid")Long prid);

	

}
