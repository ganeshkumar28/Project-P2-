package com.buyerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buyerservice.entity.Products;

public interface productdaointerface extends JpaRepository<Products,Long> {
	

}
