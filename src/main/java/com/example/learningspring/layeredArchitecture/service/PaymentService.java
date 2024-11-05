package com.example.learningspring.layeredArchitecture.service;

import com.example.learningspring.layeredArchitecture.dto.PaymentRequest;
import com.example.learningspring.layeredArchitecture.dto.PaymentResponse;
import com.example.learningspring.layeredArchitecture.entity.PaymentEntity;
import com.example.learningspring.layeredArchitecture.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    public PaymentResponse getPaymentById(PaymentRequest paymentRequest) {
        PaymentEntity paymentModel = paymentRepository.getPaymentById(paymentRequest);

        //Convert PaymentEntity to PaymentResponse
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentStatus(paymentModel.getPaymentStatus());
        return paymentResponse;
    }
}
