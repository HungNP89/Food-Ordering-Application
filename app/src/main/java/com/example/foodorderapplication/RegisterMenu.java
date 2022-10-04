package com.example.foodorderapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RegisterMenu extends AppCompatActivity {
    TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_menu);

        textView4 = findViewById(R.id.tvLogin);
        textView4.setOnClickListener(view -> RegisterMenu.this.finish());
    }
}