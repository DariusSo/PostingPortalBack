package Dariaus.PostingPortal.controllers;

import Dariaus.PostingPortal.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/stripe")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @CrossOrigin
    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map<String, String>> createPaymentIntent(@RequestBody Map<String, Object> data) {
        try {
            Long amount = Long.parseLong(data.get("amount").toString());
            String currency = data.get("currency").toString();
            PaymentIntent paymentIntent = stripeService.createPaymentIntent(amount, currency);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("clientSecret", paymentIntent.getClientSecret());

            return ResponseEntity.ok(responseData);
        } catch (StripeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    @CrossOrigin
    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody Map<String, Object> data) throws SQLException {
        try {
            Long amount = Long.parseLong(data.get("amount").toString());
            String currency = data.get("currency").toString();
            String title = data.get("title").toString();
            String number = data.get("number").toString();
            String content = data.get("content").toString();


            Session session = stripeService.createCheckoutSession(title, number, content, amount, currency);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("id", session.getId());

            return ResponseEntity.ok(responseData);
        } catch (StripeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

}
