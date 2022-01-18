package com.astroenergy.java.astroenergyApplication.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astroenergy.java.astroenergyApplication.dao.AstroOrderRepository;
import com.astroenergy.java.astroenergyApplication.model.AstroOrder;
import com.astroenergy.java.astroenergyApplication.model.Signature;

@Service
public class OrderService {
	
	@Autowired
    private AstroOrderRepository orderRepository;
 
    @Transactional
    public AstroOrder saveOrder(final String razorpayOrderId, final Long userId) {
    	AstroOrder order = new AstroOrder();
        order.setRazorpayOrderId(razorpayOrderId);
        order.setUserId(String.valueOf(userId));
        return orderRepository.save(order);
    }
 
    @Transactional
    public String validateAndUpdateOrder(final String razorpayOrderId, final String razorpayPaymentId, final String razorpaySignature, final String secret) {
        String errorMsg = null;
        try {
        	AstroOrder order = orderRepository.findByRazorpayOrderId(razorpayOrderId);
            // Verify if the razorpay signature matches the generated one to
            // confirm the authenticity of the details returned
            String generatedSignature = Signature.calculateRFC2104HMAC(order.getRazorpayOrderId() + "|" + razorpayPaymentId, secret);
            if (generatedSignature.equals(razorpaySignature)) {
                order.setRazorpayOrderId(razorpayOrderId);
                order.setRazorpayPaymentId(razorpayPaymentId);
                order.setRazorpaySignature(razorpaySignature);
                orderRepository.save(order);
            } else {
                errorMsg = "Payment validation failed: Signature doesn't match";
            }
        } catch (Exception e) {
            
            errorMsg = "Payment validation failed";
        }
        return errorMsg;
    }

}
