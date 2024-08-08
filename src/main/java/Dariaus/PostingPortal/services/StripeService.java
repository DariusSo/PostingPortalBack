package Dariaus.PostingPortal.services;

import Dariaus.PostingPortal.repositories.PostRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.UUID;

@Service
public class StripeService {
    @Value("${stripe.api.key}")
    private String stripeApiKey;


    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    public PaymentIntent createPaymentIntent(Long amount, String currency) throws StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(amount)
                        .setCurrency(currency)
                        .build();

        return PaymentIntent.create(params);
    }
    public Session createCheckoutSession(String title, String number, String content, Long amount, String currency) throws StripeException, SQLException {
        UUID uniqueKey = UUID.randomUUID();
        PostRepository.pendingPost(title, number, content, uniqueKey);
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/redirect?uniqueKey=" + uniqueKey)
                .setCancelUrl("http://127.0.0.1:5500/index.html")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency(currency)
                                .setUnitAmount(amount)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Post fee")
                                        .build())
                                .build())
                        .build())
                .build();

        return Session.create(params);
    }

}
