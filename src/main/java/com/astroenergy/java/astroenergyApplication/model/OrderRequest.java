package com.astroenergy.java.astroenergyApplication.model;

public class OrderRequest {
	 private String customerName;
	    private String email;
	    private String phoneNumber;
	    private String amount;
	    private String orderId;
		public String getCustomerName() {
			return customerName;
		}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
}
