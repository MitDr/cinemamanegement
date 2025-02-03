package com.project.cinemamanagement.Config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    @Value("${stripe.api.key}")
    String STRIPE_KEY;

    @Bean
    public StripeConfig initializeStripe() {
        Stripe.apiKey = STRIPE_KEY;
        return this;
    }

}
