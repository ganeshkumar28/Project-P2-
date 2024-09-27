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
	public ResponseEntity<Object> submitreview(@RequestBody Review review) {
		
			
			int i=bservice.submitreview(review);
			String message="success";
			if(i>0) {
				message="success";
			}
			ResponseEntity<Object> rentity=new ResponseEntity<Object>(message ,HttpStatus.OK);
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
		    public ResponseEntity<List<ShoppingCart>> getCartItems(@PathVariable("userId") Long userId) {
			 List<ShoppingCart> cartItems = bservice.getCartItemsByUserId(userId);
		        return ResponseEntity.ok(cartItems);
		    }
		 @PostMapping("/submitorder")
			public ResponseEntity<Object> submitorder(@RequestBody Orders order) {
				
				
				int i=bservice.submitorder(order);
				String message="success";
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
	


