package com.buyerservice.dao;
import com.buyerservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userdaointerface extends JpaRepository<User,Long>{
	

}
