package com.example.foodorderapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.foodorderapplication.Add.GlideApp;
import com.example.foodorderapplication.R;
import com.example.foodorderapplication.models.DealMenuModel;
import com.example.foodorderapplication.models.ShowFromCatModel;
import com.example.foodorderapplication.models.VerticalMenuModel2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView imageView;
    TextView name, description, price, quantity;
    RatingBar rating;
    Button addToCart, buyNow;
    ImageView addItems, removeItems;

    int totalQuantity = 1;
    int totalPrice = 0;
    int ProductPrice = 0;

    VerticalMenuModel2 verticalMenuModel2 = null;
    ShowFromCatModel showCat = null;
    DealMenuModel showDeal = null;

    Toolbar tb;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseFirestore db;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        imageView = findViewById(R.id.detailed_image);
        name = findViewById(R.id.detailed_name);
        description = findViewById(R.id.detailed_des);
        price = findViewById(R.id.detailed_price);
        rating = findViewById(R.id.detailed_rating);
        addToCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.process_to_payment);
        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);
        quantity = findViewById(R.id.quantity);
        tb = findViewById(R.id.detailed_toolbar);

        tb.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24); // your drawable
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");

        if (obj instanceof VerticalMenuModel2) {
            verticalMenuModel2 = (VerticalMenuModel2) obj;
        } else if (obj instanceof ShowFromCatModel) {
            showCat = (ShowFromCatModel) obj;
        } else if (obj instanceof DealMenuModel) {
            showDeal = (DealMenuModel) obj;
        }

        if (verticalMenuModel2 != null) {
            GlideApp.with(getApplicationContext()).load(verticalMenuModel2.getImage()).into(imageView);
            name.setText(verticalMenuModel2.getName());
            description.setText(verticalMenuModel2.getDescription());
            price.setText(String.valueOf(verticalMenuModel2.getPrice()));
            rating.setRating(verticalMenuModel2.getRating());

            ProductPrice = verticalMenuModel2.getPrice();
            totalPrice = verticalMenuModel2.getPrice() * totalQuantity;
        }

        if (showCat != null) {
            GlideApp.with(getApplicationContext()).load(showCat.getImage()).into(imageView);
            name.setText(showCat.getName());
            description.setText(showCat.getDescription());
            price.setText(String.valueOf(showCat.getPrice()));
            rating.setRating(showCat.getRating());

            ProductPrice = showCat.getPrice();
            totalPrice = showCat.getPrice() * totalQuantity;
        }

        if (showDeal != null) {
            GlideApp.with(getApplicationContext()).load(showDeal.getImage()).into(imageView);
            name.setText(showDeal.getName());
            description.setText(showDeal.getDescription());
            price.setText(String.valueOf(showDeal.getPrice()));
            rating.setRating(showDeal.getRating());

            ProductPrice = showDeal.getPrice();
            totalPrice = showDeal.getPrice() * totalQuantity;
        }

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCart();
            }
        });

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity < 100) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));

                    if (verticalMenuModel2 != null) {
                        totalPrice = verticalMenuModel2.getPrice() * totalQuantity;
                    }
                    if (showCat != null) {
                        totalPrice = showCat.getPrice() * totalQuantity;
                    }
                    if (showDeal != null) {
                        totalPrice = showDeal.getPrice() * totalQuantity;
                    }
                }
            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity > 1) {
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });
    }


    private void addCart() {
        String saveCurrentTime, saveCurrentDate;

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("productDate", saveCurrentDate);
        cartMap.put("productName", name.getText().toString());
        cartMap.put("productPrice", ProductPrice);
        cartMap.put("totalQuantity", quantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);

        db.collection("UserOrder").document(fAuth.getCurrentUser().getUid())
                .collection("Add To Cart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    finish();
                                }
                            }
                        });
                        thread.start();
                        Toast.makeText(DetailedActivity.this, "Add To Cart", Toast.LENGTH_SHORT).show();
                        //finish();
                    }
                });

    }

    public void onBackPressed() {
        Intent intent = new Intent(DetailedActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}