package com.buyerservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buyerservice.dao.buyerdaoservice;
import com.buyerservice.dao.cartdaodervice;
import com.buyerservice.dao.complaintdaoservice;
import com.buyerservice.dao.orderdaointerface;
import com.buyerservice.dao.productdaointerface;
import com.buyerservice.dao.userdaointerface;
import com.buyerservice.dto.ShoppingCartProjection;
import com.buyerservice.entity.Complaint;
import com.buyerservice.entity.Orders;
import com.buyerservice.entity.Products;
import com.buyerservice.entity.Review;
import com.buyerservice.entity.ShoppingCart;
import com.buyerservice.entity.User;

@Service
@Transactional
public class buyerservic implements buyerserviceinterface {
	@Autowired
	private buyerdaoservice bdao;
	@Autowired
	private complaintdaoservice ddao;
	@Autowired
	private cartdaodervice cdao;
	@Autowired
	private orderdaointerface odao;
	@Autowired
	private productdaointerface pdao;
	@Autowired
	private userdaointerface udao;
	

	@Override
	public int submitreview(Review review) {
		// TODO Auto-generated method stub
		int i=0;
		bdao.save(review);
		i=1;
		return i;
	}

	@Override
	public int submitcomplaint(Complaint complaint) {
		// TODO Auto-generated method stub
		int i=0;
		ddao.save(complaint);
		i=1;
		return i;
	}

	

	@Override
	public int submitorder(Orders order) {
		// TODO Auto-generated method stub
		int i=0;
		 odao.save(order);
		 i=1;
		 return i;
	}

	@Override
	public Products getproductByUserId(Long pid) {
		Optional<Products> pp=pdao.findById(pid);
		return pp.get();
	}

	@Override
	public User getByUserId(Long uid) {
		Optional<User> pp=udao.findById(uid);
		return pp.get();
	}

	@Override
	public List<Review> getAllreviews(Long userId) {
		// TODO Auto-generated method stub
		
		return bdao.findbyuserid(userId);
	}

	@Override
	public void deleteReviewById(Long prid) {
		// TODO Auto-generated method stub
		bdao.deletebyproid(prid);
		
	}

	@Override
	public  List<ShoppingCartProjection>viewCartProducts(Long userId) {
		List<ShoppingCartProjection>cartItems = cdao.findCartByUserId(userId);
		return cartItems;

	}

}
