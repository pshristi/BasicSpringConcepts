package com.example.learningspring.layeredArchitecture.repository;

import com.example.learningspring.layeredArchitecture.dto.PaymentRequest;
import com.example.learningspring.layeredArchitecture.entity.PaymentEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepository {
    public PaymentEntity getPaymentById(PaymentRequest paymentRequest) {
        //Call to DB and return payment entity
        PaymentEntity paymentModel = new PaymentEntity();
        paymentModel.setId(paymentRequest.getId());
        paymentModel.setPaymentStatus("Done");
        return paymentModel;
    }
}
