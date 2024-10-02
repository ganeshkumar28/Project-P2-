package com.buyerservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buyerservice.dto.ShoppingCartProjection;
import com.buyerservice.entity.Complaint;
import com.buyerservice.entity.Orders;
import com.buyerservice.entity.Products;
import com.buyerservice.entity.Review;
import com.buyerservice.entity.ShoppingCart;
import com.buyerservice.entity.User;
import com.buyerservice.service.buyerserviceinterface;

@RestController
@RequestMapping("/buyer")
public class buyercontroller {
	
	@Autowired
	private buyerserviceinterface bservice;
	
	@PostMapping("/submitreview")
	public ResponseEntity<String> submitreview(@RequestBody Review review) {
		System.out.println("dfghj");
//		System.out.println("Received review: " + userId);
//		User us = new User();
//		us.setUserId(userId);
//		System.out.println(us.getUserId());
//		Review rv = new Review();
//		rv.setUser(us);
//		System.out.println(rv.getUser().getUserId());
		
			int i=bservice.submitreview(review);
			String message="success";
			if(i>0) {
				message="success";
			}
			ResponseEntity<String> rentity=new ResponseEntity<String>(message ,HttpStatus.OK);
			return rentity;
			
		

	}
	@PostMapping("/submitcomplaint")
	public ResponseEntity<Object> submitcomplaint(@RequestBody Complaint complaint) {
		
		
		int i=bservice.submitcomplaint(complaint);
		String message="success";
		if(i>0) {
			message="success";
		}
		ResponseEntity<Object> rentity=new ResponseEntity<Object>(message ,HttpStatus.OK);
		return rentity;
		
	

}
	
	

	@GetMapping("/viewcartitems/{userId}")
	public ResponseEntity<List<ShoppingCartProjection>> viewCartProducts(@PathVariable("userId") Long userId) {
		System.out.println(userId);
		List<ShoppingCartProjection> cartItems = bservice.viewCartProducts(userId);
		System.out.println(cartItems);
		

		if (cartItems != null && !cartItems.isEmpty()) {
			return new ResponseEntity<>(cartItems, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(cartItems,HttpStatus.NO_CONTENT);
		}
	}
		 @PostMapping("/submitorder/")
			public ResponseEntity<Object> submitorder(@RequestBody Orders order) {
				
				
				int i=bservice.submitorder(order);
				String message="isuccess";
				if(i>0) {
					message="success";
				}
				ResponseEntity<Object> rentity=new ResponseEntity<Object>(message ,HttpStatus.OK);
				return rentity;
		 }
		 @GetMapping("/viewproduct/{pid}")
		    public Products viewproduct(@PathVariable("pid") Long pid) {
			 bservice.getproductByUserId(pid);
		        return bservice.getproductByUserId(pid);
	}
		 @GetMapping("/viewuser/{uid}")
		 public User viewuser(@PathVariable("uid") Long uid) {
			 bservice.getByUserId(uid);
		        return bservice.getByUserId(uid);
}
		 @GetMapping("/myreviews/{userId}")
			public List<Review> viewreviews(@PathVariable("userId") Long userId){
				return bservice.getAllreviews(userId);
			}
		 @DeleteMapping("/reviews/{prid}")
		 public void deleteReview(@PathVariable("prid") Long prid) {
		      
		            bservice.deleteReviewById(prid);
		           
}
}
	


