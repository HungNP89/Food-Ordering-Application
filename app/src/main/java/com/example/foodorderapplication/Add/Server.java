package com.example.foodorderapplication.Add;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodListParams;

import java.nio.file.Paths;

import java.nio.file.Paths;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.port;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentMethodListParams;
import com.stripe.exception.StripeException;

public class Server {
    private static Gson gson = new Gson();

    static class CreatePayment {
        @SerializedName("items")
        Object[] items;

        public Object[] getItems() {
            return items;
        }
    }

    static class CreatePaymentResponse {
        private String clientSecret;

        public CreatePaymentResponse(String clientSecret) {
            this.clientSecret = clientSecret;
        }
    }

    static int calculateOrderAmount(Object[] items) {
        // Replace this constant with a calculation of the order's amount
        // Calculate the order total on the server to prevent
        // people from directly manipulating the amount on the client
        return 1400;
    }

    // Call this function with the ID of the Customer you want to charge
    static void chargeCustomer(String customerId) throws StripeException {
        // Lookup the payment methods available for the customer
        PaymentMethodListParams listParams = new PaymentMethodListParams.Builder().setCustomer(customerId)
                .setType(PaymentMethodListParams.Type.CARD).build();
        PaymentMethodCollection paymentMethods = PaymentMethod.list(listParams);
        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder().setCurrency("eur")
                .setAmount(new Long(1099))
                .setPaymentMethod(paymentMethods.getData().get(0).getId())
                .setCustomer(customerId)
                .setConfirm(true)
                .setOffSession(true)
                .build();
        try {
            // Charge the customer and payment method immediately
            PaymentIntent paymentIntent = PaymentIntent.create(createParams);
        } catch (CardException e) {
            // Error code will be authentication_required if authentication is needed
            System.out.println("Error code is : " + e.getCode());
            String paymentIntentId = e.getStripeError().getPaymentIntent().getId();
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            System.out.println(paymentIntent.getId());
        }
    }

    public static void main(String[] args) {
        port(4242);
        staticFiles.externalLocation(Paths.get("public").toAbsolutePath().toString());

        // This is your test secret API key.
        Stripe.apiKey = "sk_test_51MHoNsENJjpyig3UlZta5JBOqeSTFq0bUY19vqNZM0NppSRsjiqY8ZogYhbUFIdJcoHbySdYoQligIGHZbrx8odU0040FSwHGL";

        post("/create-payment-intent", (request, response) -> {
            response.type("application/json");
            // Alternatively, set up a webhook to listen for the payment_intent.succeeded event
            // and attach the PaymentMethod to a new Customer
            CustomerCreateParams customerParams = new CustomerCreateParams.Builder().build();
            Customer customer = Customer.create(customerParams);

            CreatePayment postBody = gson.fromJson(request.body(), CreatePayment.class);
            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setCustomer(customer.getId())
                            .setSetupFutureUsage(PaymentIntentCreateParams.SetupFutureUsage.OFF_SESSION)
                            .setAmount(new Long(calculateOrderAmount(postBody.getItems())))
                            .setCurrency("eur")
                            .setAutomaticPaymentMethods(
                                    PaymentIntentCreateParams.AutomaticPaymentMethods
                                            .builder()
                                            .setEnabled(true)
                                            .build()
                            )
                            .build();

            // Create a PaymentIntent with the order amount and currency
            PaymentIntent paymentIntent = PaymentIntent.create(params);

            CreatePaymentResponse paymentResponse = new CreatePaymentResponse(paymentIntent.getClientSecret());
            return gson.toJson(paymentResponse);
        });
    }
}
