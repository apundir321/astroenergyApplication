package com.astroenergy.java.astroenergyApplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.astroenergy.java.astroenergyApplication.model.AstroOrder;


@Repository
public interface AstroOrderRepository extends JpaRepository<AstroOrder, Long> {

	
	AstroOrder findByRazorpayOrderId(String orderId);
}
