package com.example.learningspring.layeredArchitecture.controller;

import com.example.learningspring.layeredArchitecture.dto.PaymentRequest;
import com.example.learningspring.layeredArchitecture.dto.PaymentResponse;
import com.example.learningspring.layeredArchitecture.entity.User;
import com.example.learningspring.layeredArchitecture.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.beans.PropertyEditorSupport;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @InitBinder
    public void initBinder(DataBinder binder) {
        binder.registerCustomEditor(String.class, "accountName", new AccountNameEditor());
    }

    public class AccountNameEditor extends PropertyEditorSupport {

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            setValue(text.trim().toLowerCase());
        }
    }

    /*
    curl --location --request GET 'http://localhost:8080/payments/1' --header 'Content-Type: application/json' \
    --data-raw '{"user_name" : "john"}'
     */

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable(value = "id") Long id,
                                                          @RequestParam(value = "accountName", required = false) String accountName,
                                                          @RequestBody User user) {

        //Convert to payment request DTO and send to payment service
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setId(id);

        //Pass request DTO to further layer for processing
        PaymentResponse paymentResponse = paymentService.getPaymentById(paymentRequest);
        //return repsonse DTO
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
    }
}
