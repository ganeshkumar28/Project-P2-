package com.buyerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buyerservice.entity.Complaint;
import com.buyerservice.entity.Review;

@Repository
public interface complaintdaoservice extends JpaRepository<Complaint,Long>{

}
