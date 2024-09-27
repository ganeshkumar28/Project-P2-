package com.amazonclientapp.controller;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.amazonclientapp.dto.Complaint;
import com.amazonclientapp.dto.Orders;
import com.amazonclientapp.dto.Products;
import com.amazonclientapp.dto.Review;
import com.amazonclientapp.dto.ShoppingCart;
import com.amazonclientapp.dto.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Controller
@RequestMapping("/buyer")

public class buyercontroller {
	@Autowired
	private DiscoveryClient discoveryclient;
	@RequestMapping("/submitreview")
	public ModelAndView submitreview(HttpServletRequest request,HttpServletResponse response,@RequestParam("productId") String productId,@RequestParam("ReviewText") String reviewtext,@RequestParam("rating") String rating) {
		long pid=Long.parseLong(productId);
		//long uid = Long.parseLong(userId);
		int rate=Integer.parseInt(rating);
		List<ServiceInstance> instances=discoveryclient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance=instances.get(0);
		
		String baseUrl=serviceInstance.getUri().toString(); //return http://localhost:8080
		
		baseUrl=baseUrl+"/viewproduct/" + pid;
		RestTemplate restTemplate=new RestTemplate();
		Products product=restTemplate.getForObject(baseUrl,Products.class);
		List<ServiceInstance> instances1=discoveryclient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance1=instances1.get(0);
		
		String baseUrl1=serviceInstance1.getUri().toString(); //return http://localhost:8080
		
		baseUrl1=baseUrl1+"/viewuser/"+ 1L;
		
	  User user =restTemplate.getForObject(baseUrl1,User.class);
		
		
		Review review=new Review();
		review.setProduct(product);
		review.setRating(rate);
		review.setReviewText(reviewtext);
		review.setUser(user);
	List<ServiceInstance> instance2=discoveryclient.getInstances("BUYERSERVICE");
	ServiceInstance serviceInstance2=instance2.get(0);
	
	String baseUrl2=serviceInstance2.getUri().toString(); //return http://localhost:8080
	
	baseUrl2=baseUrl2+"/submitreview";
	
	

	

	HttpHeaders headers = new HttpHeaders();
	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	HttpEntity<String> entity = new HttpEntity<String>(headers);

	ResponseEntity<Object> str = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, Object.class);
			//.getBody();
	int i=0;
	ModelAndView mv= new ModelAndView();
	String message="success";
	mv.addObject("message",i);

	return mv;

}
	@RequestMapping("/submitcomplaint")
	public ModelAndView submitcomplaint(HttpServletRequest request,HttpServletResponse response,@RequestParam("username") String username,@RequestParam("complaintText") String comtext) {
		List<ServiceInstance> instances1=discoveryclient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance1=instances1.get(0);
		
		String baseUrl1=serviceInstance1.getUri().toString(); //return http://localhost:8080
		
		baseUrl1=baseUrl1+"/viewuser/"+ 1L;
		RestTemplate restTemplate=new RestTemplate();

		
	  User user =restTemplate.getForObject(baseUrl1,User.class);
		Complaint complaint=new Complaint();
		complaint.setUserName(username);
		complaint.setUser(user);
		complaint.setComplaintText(comtext);
		
	List<ServiceInstance> instances=discoveryclient.getInstances("BUYERSERVICE");
	ServiceInstance serviceInstance=instances.get(0);
	
	String baseUrl=serviceInstance.getUri().toString(); //return http://localhost:8080
	
	baseUrl=baseUrl+"/submitcomplaint";
	
	

	

	HttpHeaders headers = new HttpHeaders();
	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	HttpEntity<String> entity = new HttpEntity<String>(headers);

	ResponseEntity<Object> str = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, Object.class);
			//.getBody();
	int i=0;
	ModelAndView mv= new ModelAndView();
	String message="success";
	mv.addObject("message",i);

	return mv;
}



	@RequestMapping("/viewcartitems")
	public ModelAndView viewcartitems(HttpServletRequest request,HttpServletResponse response,@RequestParam("address") String address,@RequestParam("pincode") String pincode,@RequestParam("phoneNumber") String pno,@RequestParam("paymentMethod") String pm) {
		
		
	List<ServiceInstance> instances=discoveryclient.getInstances("BUYERSERVICE");
	ServiceInstance serviceInstance=instances.get(0);
	
	String baseUrl=serviceInstance.getUri().toString(); //return http://localhost:8080
	
	baseUrl=baseUrl+"/viewcartitems"+1L;
	
	

	RestTemplate restTemplate=new RestTemplate();
	List<ServiceInstance> instances1=discoveryclient.getInstances("BUYERSERVICE");
	ServiceInstance serviceInstance1=instances1.get(0);
	
	String baseUrl1=serviceInstance1.getUri().toString(); //return http://localhost:8080
	
	baseUrl1=baseUrl1+"/viewuser/"+ 1L;
	
  User user =restTemplate.getForObject(baseUrl1,User.class);

	

	List<ShoppingCart> cartitems = restTemplate.getForObject(baseUrl, List.class, 1L);
			//.getBody();
	List<ServiceInstance> instance2=discoveryclient.getInstances("BUYERSERVICE");
	ServiceInstance serviceInstance2=instance2.get(0);
	
	String baseUrl2=serviceInstance2.getUri().toString(); //return http://localhost:8080
	
	baseUrl2=baseUrl2+"/submitorder";
	Orders order=new Orders();
	for(ShoppingCart sc: cartitems) {
		order.setProduct(sc.getProduct());
		order.setPaymentMode(pm);
		order.setPhoneNumber(pno);
		order.setPipncode(pincode);
		order.setOrderDate(new Timestamp(System.currentTimeMillis()));
		order.setShoppingAddress(address);
		order.setStatus("pending");
		order.setTotalPrice(sc.getTotalPrice());
		order.setUser(user);
		
		
		
		
	}
	int k=restTemplate.postForObject(baseUrl2,order, Integer.class);
	 String message="not";
	if(k>0) {
		 message="order success";
	}
	
	
	ModelAndView mv= new ModelAndView();
	
	mv.addObject("message",k);
	mv.setViewName("success.jsp");

	return mv;
}
	@RequestMapping("/myreviews")
	public ModelAndView myreviews(HttpServletRequest request,HttpServletResponse response) {
		
		
		List<ServiceInstance> instances=discoveryclient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance=instances.get(0);
		
		String baseUrl=serviceInstance.getUri().toString(); //return http://localhost:8080
		
		baseUrl=baseUrl+"/myreviews"+1L;
		
		

		RestTemplate restTemplate=new RestTemplate();

		

		List<Review> userreviews  = restTemplate.getForObject(baseUrl, List.class);
				//.getBody();
      ModelAndView mv=new ModelAndView();
      mv.addObject("userreviews",userreviews);
      mv.setViewName("reviews.jsp");
      return mv;
}
	@RequestMapping("/reviews")
	public ModelAndView reviews(HttpServletRequest request,HttpServletResponse response,@RequestParam("productId") String pid) {
		
		long prid=Integer.parseInt(pid);
		List<ServiceInstance> instances=discoveryclient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance=instances.get(0);
		
		String baseUrl=serviceInstance.getUri().toString(); //return http://localhost:8080
		
		baseUrl=baseUrl+"/deletereviews"+prid;
		
		

		RestTemplate restTemplate=new RestTemplate();

		

		restTemplate.delete(baseUrl);
				//.getBody();
      ModelAndView mv=new ModelAndView();
     mv.setViewName("reviews.jsp");
      return mv;
}
	
}


	


