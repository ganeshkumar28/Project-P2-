package com.buyerservice.service;

import java.util.List;

import com.buyerservice.dto.ShoppingCartProjection;
import com.buyerservice.entity.Complaint;
import com.buyerservice.entity.Orders;
import com.buyerservice.entity.Products;
import com.buyerservice.entity.Review;
import com.buyerservice.entity.ShoppingCart;
import com.buyerservice.entity.User;

public interface buyerserviceinterface {

	int submitreview(Review review);

	int submitcomplaint(Complaint complaint);

	

	int submitorder(Orders order);

	Products getproductByUserId(Long pid);

	User getByUserId(Long uid);

	List<Review> getAllreviews(Long userId);

	void deleteReviewById(Long prid);

	List<ShoppingCartProjection> viewCartProducts(Long userId);

}
