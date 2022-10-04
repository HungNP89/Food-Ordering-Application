package com.example.foodorderapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EntryMenu extends AppCompatActivity {
    TextView textView2;
    TextView textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_menu);

        textView2 = findViewById(R.id.tvSignUp);
        textView3 = findViewById(R.id.tvSignUp2);

        textView2.setOnClickListener(ClickSignUp());
        textView3.setOnClickListener(ClickSignUp());
    }

    private View.OnClickListener ClickSignUp() {
        return view -> {
            Intent intent = new Intent(EntryMenu.this,RegisterMenu.class);
            EntryMenu.this.startActivity(intent);
        };
    }
}