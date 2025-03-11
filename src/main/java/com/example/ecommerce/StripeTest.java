package com.example.ecommerce;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

public class StripeTest {
    public static void main(String[] args) {
        String stripeSecretKey = System.getenv("STRIPE_SECRET_KEY");
        Stripe.apiKey = stripeSecretKey;
        System.out.println("Using Stripe API key: " + stripeSecretKey);

        try {
            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount(1000L) // $10.00
                            .setCurrency("usd")
                            .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);
            System.out.println("PaymentIntent created: " + paymentIntent.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
