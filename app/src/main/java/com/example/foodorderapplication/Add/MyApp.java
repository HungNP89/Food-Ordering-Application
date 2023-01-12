package com.example.foodorderapplication.Add;

import android.app.Application;

import com.stripe.android.PaymentConfiguration;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PaymentConfiguration.init(
                getApplicationContext(),
                "pk_test_51MHoNsENJjpyig3U13dJ82cxwNBShJ8DcmA7K4TIIg9eP8lrW8vp5C1zL8yYIWdTTMArG8NYxngscZzPbp7u3Usi00JAUHocWE"
        );
    }
}
