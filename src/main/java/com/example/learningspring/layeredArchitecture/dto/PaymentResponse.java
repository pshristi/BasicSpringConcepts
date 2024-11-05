package com.example.learningspring.layeredArchitecture.dto;

public class PaymentResponse {
    String paymentStatus;
    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String payment) {
        this.paymentStatus = payment;
    }
}
