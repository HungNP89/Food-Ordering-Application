package com.example.foodorderapplication.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodorderapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class Payment extends AppCompatActivity {

    Button stripePayment, paypalPayment;

    String SECRET_KEY = "sk_test_51MHoNsENJjpyig3UlZta5JBOqeSTFq0bUY19vqNZM0NppSRsjiqY8ZogYhbUFIdJcoHbySdYoQligIGHZbrx8odU0040FSwHGL";
    String PUBLIC_KEY = "pk_test_51MHoNsENJjpyig3U13dJ82cxwNBShJ8DcmA7K4TIIg9eP8lrW8vp5C1zL8yYIWdTTMArG8NYxngscZzPbp7u3Usi00JAUHocWE";

    String CustomerID, EphericalKey, ClientSecret;
    String accessToken;
    FirebaseAuth FAuth;

    private static final String TAG = "CheckoutActivity";
    private static final String BACKEND_URL = "http://10.0.2.2:4242";

    private String paymentIntentClientSecret;
    private PaymentSheet paymentSheet;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Hook up the pay button
        stripePayment = findViewById(R.id.payment_stripe);
        stripePayment.setOnClickListener(view -> {
            onPayClicked();
        });
        stripePayment.setEnabled(false);
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);
        fetchPaymentIntent();
    }

    private void showAlert(String title, @Nullable String message) {
        runOnUiThread(() -> {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("Ok", null)
                    .create();
            dialog.show();
        });
    }

    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_LONG).show());
    }

    private void fetchPaymentIntent() {
        final String shoppingCartContent = "{\"items\": [ {\"id\":\"xl-tshirt\"}]}";

        final RequestBody requestBody = RequestBody.create(
                MediaType.get("application/json; charset=utf-8"),
                shoppingCartContent
        );

        Request request = new Request.Builder()
                .url(BACKEND_URL + "/create-payment-intent")
                .post(requestBody)
                .build();

        new OkHttpClient()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        showAlert("Failed to load data", "Error: " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            showAlert(
                                    "Failed to load page",
                                    "Error: " + response.toString()
                            );
                        } else {
                            final JSONObject responseJson = parseResponse(response.body());
                            paymentIntentClientSecret = responseJson.optString("client_secret");
                            Toast.makeText(Payment.this, paymentIntentClientSecret, Toast.LENGTH_SHORT).show();
                            runOnUiThread(() -> stripePayment.setEnabled(true));
                            Log.i(TAG, "Retrieved PaymentIntent");
                        }
                    }
                });
    }

    private JSONObject parseResponse(ResponseBody responseBody) {
        if (responseBody != null) {
            try {
                return new JSONObject(responseBody.string());
            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error parsing response", e);
            }
        }

        return new JSONObject();
    }

    private void onPayClicked() {
        paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, new PaymentSheet.Configuration("Entropy9029"));
    }


    private void onPaymentSheetResult(
            final PaymentSheetResult paymentSheetResult
    ) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            showToast("Payment complete!");
        } else if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Log.i(TAG, "Payment canceled!");
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Throwable error = ((PaymentSheetResult.Failed) paymentSheetResult).getError();
            showAlert("Payment failed", error.getLocalizedMessage());
        }
    }

}