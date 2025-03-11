package com.example.ecommerce.controller;

import com.example.ecommerce.service.StripeService;
import com.stripe.model.PaymentIntent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/create-payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestParam Long amount) {
        try {
            PaymentIntent paymentIntent = stripeService.createPaymentIntent(amount);
            return ResponseEntity.ok(paymentIntent.getClientSecret());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création du paiement : " + e.getMessage());
        }
    }
}

