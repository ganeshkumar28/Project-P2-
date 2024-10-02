package com.amazonclientapp.controller;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
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

import com.amazonclientapp.buyerdto.ShoppingCartDTO;
//import com.amazonclientapp.buyerdto.ShoppingCartProjection;
import com.amazonclientapp.dto.Complaint;
import com.amazonclientapp.dto.Orders;
import com.amazonclientapp.dto.Products;
import com.amazonclientapp.dto.Review;
import com.amazonclientapp.dto.ShoppingCart;
import com.amazonclientapp.dto.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/buyer")

public class buyercontroller {
	@Autowired
	private DiscoveryClient discoveryclient;
	@RequestMapping("/submitreview")
	public ModelAndView submitreview(HttpServletRequest request,HttpServletResponse response,@RequestParam("productId") String productId,@RequestParam("reviewText") String reviewtext,@RequestParam("rating") String rating,HttpSession session) {
		long pid=Long.parseLong(productId);
		System.out.println("prodid "+pid);
		int rate=Integer.parseInt(rating);
//		long userId = (long) session.getAttribute("userId");
		long userId = 1L;

		
		User user = new User();
		user.setUserId(userId);
		
//		System.out.println(user.getUserId());
		Products product = new Products();
		product.setProductId(pid);
		Review review=new Review();
		review.setProduct(product);
		review.setRating(rate);
		review.setReviewText(reviewtext);
		
		review.setUser(user);
		//System.out.println("in client "+review.getUser().getUserId());
		
		List<ServiceInstance> instance2=discoveryclient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance2=instance2.get(0);
	
		String baseUrl2=serviceInstance2.getUri().toString(); //return http://localhost:8080
		
	
		baseUrl2=baseUrl2+"/buyer/submitreview";
		System.out.println(baseUrl2);
		RestTemplate rs = new RestTemplate();

		String addToFav = rs.postForObject(baseUrl2, review, String.class);
		System.out.println(addToFav);

	ModelAndView mv= new ModelAndView();
	String message="success";
	mv.addObject("message",addToFav);

	return mv;

}
	@RequestMapping("/submitcomplaint")
	public ModelAndView submitcomplaint(HttpServletRequest request,HttpServletResponse response,@RequestParam("username") String username,@RequestParam("complaintText") String comtext) {
		//List<ServiceInstance> instances1=discoveryclient.getInstances("BUYERSERVICE");
		//ServiceInstance serviceInstance1=instances1.get(0);
		
		//String baseUrl1=serviceInstance1.getUri().toString(); //return http://localhost:8080
		
		//baseUrl1=baseUrl1+"/viewuser/"+ 1L;
		RestTemplate restTemplate=new RestTemplate();
   User user = new User();
   user.setUserId(1L);
		
	 // User user =restTemplate.getForObject(baseUrl1,User.class);
		Complaint complaint=new Complaint();
		complaint.setUserName(username);
		complaint.setUser(user);
		complaint.setComplaintText(comtext);
		
	List<ServiceInstance> instances=discoveryclient.getInstances("BUYERSERVICE");
	ServiceInstance serviceInstance=instances.get(0);
	
	String baseUrl=serviceInstance.getUri().toString(); //return http://localhost:8080
	
	baseUrl=baseUrl+"/buyer/submitcomplaint";
	
	

	

	RestTemplate rs = new RestTemplate();

	String addToFav = rs.postForObject(baseUrl, complaint, String.class);
	System.out.println(addToFav);

			//.getBody();
	int i=0;
	ModelAndView mv= new ModelAndView();
	String message="success";
	mv.addObject("message",i);

	return mv;
}


    @RequestMapping("/viewcartitems")
	public ModelAndView viewCartProducts(HttpServletRequest request,HttpServletResponse response,@RequestParam("address") String address,@RequestParam("pincode") String pincode,@RequestParam("phoneNumber") String pno,@RequestParam("paymentMethod") String pm)
	{
		ModelAndView mv = new ModelAndView();
		HttpSession hs = request.getSession();
//		long userId = (long) hs.getAttribute("userId");
		long userId =1l;
		
		List<ServiceInstance> instances = discoveryclient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance = instances.get(0);

		String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080

		baseUrl = baseUrl + "/buyer/viewcartitems/" + userId;
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<ShoppingCartDTO>> response1 = restTemplate.exchange(
			    baseUrl, 
			    HttpMethod.GET, 
			    null, 
			    new ParameterizedTypeReference<List<ShoppingCartDTO>>() {}
			);

			List<ShoppingCartDTO> cartitems = response1.getBody();
			System.out.println(cartitems);

		User user=new User();
		user.setUserId(userId);
	List<ServiceInstance> instance2=discoveryclient.getInstances("BUYERSERVICE");
	ServiceInstance serviceInstance2=instance2.get(0);
	
	String baseUrl2=serviceInstance2.getUri().toString(); //return http://localhost:8080
	
	baseUrl2=baseUrl2+"/buyer/submitorder/";
	Orders order=new Orders();
	
	for(ShoppingCartDTO sc: cartitems) {
		Products product=new Products();
		product.setProductName(sc.getProductName());
		order.setProduct(product);
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
	
	
	
	
	

	
		if(cartitems != null)
		{
			mv.addObject("products",cartitems);
			mv.setViewName("/cart.jsp");
		}
		else
		{
			mv.addObject("error", "Product not found in cart.");
			mv.setViewName("/cart.jsp");
		}
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


	


