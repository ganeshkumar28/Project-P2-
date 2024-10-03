package com.revbookstoreclient.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.buyerservice.dto.UserProjection;
import com.revbookstoreclient.dto.Products;
import com.revbookstoreclient.dto.User;

@Controller
@RequestMapping("/user")
public class LoginSignupController {
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@RequestMapping("/register")
	public ModelAndView adminRegister(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("name") String name,@RequestParam("phone_number") String phoneNumber,
			@RequestParam("address") String address,@RequestParam("pincode") String pincode,
			@RequestParam("userType") String userType)
	{
		ModelAndView mv = new ModelAndView();
		
		String status = "active";
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		user.setPhoneNumber(phoneNumber);
		user.setPincode(pincode);
		user.setEmail(email);
		user.setAddress(address);
		user.setUserType(userType);
		
		user.setStatus(status);
		
		
		System.out.println(user);
		
		String registered=null;
		
		System.out.println(user.getUserType());

		
		
			if(userType.equals("admin"))
			{
				List<ServiceInstance> instances = discoveryClient.getInstances("ADMINSERVICE");
				ServiceInstance serviceInstance = instances.get(0);

				String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
				baseUrl = baseUrl + "/admin/register";
				
				RestTemplate restTemplate = new RestTemplate();
				registered = restTemplate.postForObject(baseUrl, user,String.class);
			}
			else if(userType.equals("buyer"))
			{
				List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
				ServiceInstance serviceInstance = instances.get(0);

				String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
				baseUrl = baseUrl + "/buyer/register";
				
				RestTemplate restTemplate = new RestTemplate();
				registered = restTemplate.postForObject(baseUrl, user,String.class);
			}
			else
			{
				List<ServiceInstance> instances = discoveryClient.getInstances("SELLERSERVICE");
				ServiceInstance serviceInstance = instances.get(0);

				String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
				baseUrl = baseUrl + "/product/register";
				
				RestTemplate restTemplate = new RestTemplate();
				registered = restTemplate.postForObject(baseUrl, user,String.class);
			}
			
			
			if(!"Registered".equals(registered))
			{
				mv.addObject("errorMessage", "Registration failed.");
				mv.setViewName("/signup.jsp");
			}
			else
			{
				mv.addObject("successMessage", "Registration successful.");
				mv.setViewName("/signup.jsp");
			}
			
		
		return mv;
		}

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response,
	                          @RequestParam("email") String email, @RequestParam("password") String password,
	                          HttpSession session) {
	    System.out.println("Email: " + email);
	    System.out.println("Password: " + password);
	    ModelAndView mv = new ModelAndView();
	    HttpSession hs = request.getSession();

	    try {
	        // Step 1: Authenticate user
	        List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
	        if (instances.isEmpty()) {
	            System.err.println("No instances of BUYERSERVICE available.");
	            mv.setViewName("/index.jsp");
	            return mv;
	        }
	        
	        ServiceInstance serviceInstance = instances.get(0);
	        String baseUrl = serviceInstance.getUri().toString() + "/buyer/login/" + email + "/" + password;
	        System.out.println("Buyer Service URL: " + baseUrl);

	        RestTemplate restTemplate = new RestTemplate();
	        User us = restTemplate.getForObject(baseUrl, User.class);

	        if (us == null) {
	            System.err.println("User not found or invalid credentials.");
	            mv.setViewName("/index.jsp");
	            return mv;
	        }

	        long userId = us.getUserId();
	        System.out.println("User Type: " + us.getUserType());
	        System.out.println("User Status: " + us.getStatus());

	        // Step 2: Determine user type and fetch respective data
	        switch (us.getUserType()) {
	            case "buyer":
	                if (us.getStatus().equals("active")) {
	                    session.setAttribute("userId", us.getUserId());
	                    session.setAttribute("user-role", us.getUserType());
	                    session.setAttribute("name", us.getName());

	                    // Fetch products for the buyer
	                    List<ServiceInstance> instances1 = discoveryClient.getInstances("SELLERSERVICE");
	                    if (instances1.isEmpty()) {
	                        System.err.println("No instances of SELLERSERVICE available.");
	                        mv.setViewName("/index.jsp");
	                        return mv;
	                    }
	                    ServiceInstance serviceInstance1 = instances1.get(0);
	                    String baseUrl1 = serviceInstance1.getUri().toString() + "/product/getProduct";
	                    System.out.println("Seller Service URL: " + baseUrl1);

	                    List<Products> products = restTemplate.getForObject(baseUrl1, List.class);
	                    mv.addObject("product_list", products);
	                    mv.setViewName("/products.jsp");
	                }
	                break;

	            case "seller":
	                if (us.getStatus().equals("active")) {
	                    session.setAttribute("userId", us.getUserId());
	                    session.setAttribute("user-role", us.getUserType());
	                    session.setAttribute("name", us.getName());

	                    // Fetch products for the seller
	                    List<ServiceInstance> instances2 = discoveryClient.getInstances("SELLERSERVICE");
	                    if (instances2.isEmpty()) {
	                        System.err.println("No instances of SELLERSERVICE available.");
	                        mv.setViewName("/index.jsp");
	                        return mv;
	                    }
	                    ServiceInstance serviceInstance2 = instances2.get(0);
	                    String baseUrl2 = serviceInstance2.getUri().toString() + "/product/viewProduct/" + userId;

	                    List<Products> products = restTemplate.getForObject(baseUrl2, List.class);
	                    mv.addObject("product_list", products);
	                    mv.setViewName("/seller-views/inventory.jsp");
	                }
	                break;

	            case "admin":
	            	
	                session.setAttribute("userId", us.getUserId());
	                System.out.println("usertype"+us.getUserType());
	                session.setAttribute("user-role", us.getUserType());
	                session.setAttribute("name", us.getName());

	                // Step 3: Fetch data for the admin dashboard
	                List<ServiceInstance> instances3 = discoveryClient.getInstances("ADMINSERVICE");
	                if (instances3.isEmpty()) {
	                    System.err.println("No instances of ADMINSERVICE available.");
	                    mv.setViewName("/index.jsp");
	                    return mv;
	                }
	                System.out.println("user name" );
	                ServiceInstance serviceInstance3 = instances3.get(0);
	                String baseUrl3 = serviceInstance3.getUri().toString() + "/admin/login";

	                List<Object> counts;
	                try {
	                    counts = restTemplate.getForObject(baseUrl3, List.class);
	                    // Extract counts from the response
	                    System.out.println(counts.size());
	                    if (counts != null && counts.size() >= 4) { // Fixed size check
	                        int noofcomplaint = (Integer) counts.get(0);
	                        System.out.println(noofcomplaint);
	                        int nooforder = (Integer) counts.get(1);
	                        int noofbuyer = (Integer) counts.get(2);
	                        int noofseller = (Integer) counts.get(3); // Corrected index for seller count

	                        // Step 4: Set attributes for the dashboard
	                        mv.addObject("noofcomplaint", noofcomplaint);
	                        mv.addObject("nooforder", nooforder);
	                        mv.addObject("noofcustomer", noofbuyer);
	                        System.out.println("client"+noofbuyer);
	                        mv.addObject("noofproduct", noofseller);
	                        mv.setViewName("/admin/adminDashboard.jsp");
	                    } else {
	                        System.err.println("Invalid counts response: " + counts);
	                        mv.setViewName("/index.jsp");
	                    }
	                } catch (RestClientException e) {
	                    System.err.println("Error fetching admin data: " + e.getMessage());
	                    mv.setViewName("/index.jsp");
	                }
	                break;

	            default:
	                mv.setViewName("/dumb.jsp");
	                break;
	        }
	    } catch (HttpClientErrorException e) {
	        System.err.println("HTTP error: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
	        mv.setViewName("/index.jsp");
	    } catch (Exception e) {
	        System.err.println("An error occurred: " + e.getMessage());
	        mv.setViewName("/index.jsp");
	    }

	    return mv;
	}




}
