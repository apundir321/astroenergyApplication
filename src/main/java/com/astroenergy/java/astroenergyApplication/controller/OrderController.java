package com.astroenergy.java.astroenergyApplication.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.astroenergy.java.astroenergyApplication.config.RazorPayClientConfig;
import com.astroenergy.java.astroenergyApplication.model.ApiResponse;
import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.OrderRequest;
import com.astroenergy.java.astroenergyApplication.model.OrderResponse;
import com.astroenergy.java.astroenergyApplication.model.PaymentResponse;
import com.astroenergy.java.astroenergyApplication.service.AppointmentService;
import com.astroenergy.java.astroenergyApplication.service.OrderService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@RestController
public class OrderController {

	private RazorpayClient client;

	private RazorPayClientConfig razorPayClientConfig;
	
	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private OrderService orderService;

	@Autowired
	public OrderController(RazorPayClientConfig razorpayClientConfig) throws RazorpayException {
		this.razorPayClientConfig = razorpayClientConfig;
		this.client = new RazorpayClient(razorpayClientConfig.getKey(), razorpayClientConfig.getSecret());
	}

	@PostMapping("/order")
	public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest, @RequestParam String userId) {
		OrderResponse razorPay = null;
		try {
			// The transaction amount is expressed in the currency subunit, such
			// as paise (in case of INR)
			String amountInPaise = convertRupeeToPaise(orderRequest.getAmount());
			// Create an order in RazorPay and get the order id
			com.razorpay.Order order = createRazorPayOrder(amountInPaise);
			razorPay = getOrderResponse((String) order.get("id"), amountInPaise);
			// Save order in the database
			orderService.saveOrder(razorPay.getRazorpayOrderId(), Long.parseLong(userId));
		} catch (RazorpayException e) {
			return new ResponseEntity<>(new ApiResponse(false, "Error while create payment order: " + e.getMessage()),
					HttpStatus.EXPECTATION_FAILED);
		}
		return ResponseEntity.ok(razorPay);
	}

	@PutMapping("/order")
	public ResponseEntity<?> updateOrder(@RequestBody PaymentResponse paymentResponse,
			@RequestParam String appointmentId) {
		try {
			String errorMsg = orderService.validateAndUpdateOrder(paymentResponse.getRazorpayOrderId(),
					paymentResponse.getRazorpayPaymentId(), paymentResponse.getRazorpaySignature(),
					razorPayClientConfig.getSecret());
			if (errorMsg != null) {
				return new ResponseEntity<>(new ApiResponse(false, errorMsg), HttpStatus.BAD_REQUEST);
			}

			Appointment savedAppointment =  appointmentService.getAppointMentDetail(Long.parseLong(appointmentId));
			savedAppointment.setStatus("PAYMENT_PAID");
			savedAppointment.setOrderId(paymentResponse.getRazorpayOrderId());
			appointmentService.updateAppointment(savedAppointment);
			return ResponseEntity.ok(new ApiResponse(true, paymentResponse.getRazorpayPaymentId()));
		} catch (Exception e) {
			return null;
		}
	}

	private OrderResponse getOrderResponse(String orderId, String amountInPaise) {
		OrderResponse razorPay = new OrderResponse();
		razorPay.setApplicationFee(amountInPaise);
		razorPay.setRazorpayOrderId(orderId);
		razorPay.setSecretKey(razorPayClientConfig.getKey());
		return razorPay;
	}

	private com.razorpay.Order createRazorPayOrder(String amount) throws RazorpayException {
		JSONObject options = new JSONObject();
		options.put("amount", amount);
		options.put("currency", "INR");
		options.put("receipt", "txn_123456");
		return client.Orders.create(options);
	}

	private String convertRupeeToPaise(String paise) {
		BigDecimal b = new BigDecimal(paise);
		BigDecimal value = b.multiply(new BigDecimal("100"));
		return value.setScale(0, RoundingMode.UP).toString();
	}

}
