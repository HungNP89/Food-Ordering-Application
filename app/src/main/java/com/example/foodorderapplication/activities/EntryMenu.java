package com.example.foodorderapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorderapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class EntryMenu extends AppCompatActivity {
    TextView textViewSignUp, forgotP;
    TextInputLayout Email, Password;
    Button signIn;
    String email, pwd;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_menu);

        try {
            Email = findViewById(R.id.inputEmail);
            Password = findViewById(R.id.inputPass);
            signIn = findViewById(R.id.btnLogin);
            forgotP = findViewById(R.id.forgotP);
            textViewSignUp = findViewById(R.id.tvSignUp);

            fAuth = FirebaseAuth.getInstance();
            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    email = Email.getEditText().getText().toString().trim();
                    pwd = Password.getEditText().getText().toString().trim();

                    if (isValid()) {
                        final ProgressDialog mDialog = new ProgressDialog(EntryMenu.this);
                        mDialog.setCancelable(false);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setMessage("SignIn is now processing , please wait ...");
                        mDialog.show();
                        Log.w("", "OK Con de 3");

                        fAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mDialog.dismiss();


                                    if (fAuth.getCurrentUser().isEmailVerified()) {
                                        mDialog.dismiss();
                                        Toast.makeText(EntryMenu.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                                        Intent moveToMain = new Intent(EntryMenu.this, Home.class);
                                        startActivity(moveToMain);
                                        finish();
                                        Log.w("", "login checked!");
                                    } else {
                                        Toast.makeText(EntryMenu.this, "Please verify your email first", Toast.LENGTH_SHORT).show();
                                        Log.w("", "Login failed");
                                    }
                                } else {
                                    mDialog.dismiss();
                                    Toast.makeText(EntryMenu.this, "Wrong account", Toast.LENGTH_SHORT).show();
                                    Log.w("", "Error");
                                }
                            }
                        });
                    }
                }
            });

            textViewSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent movedSignUp = new Intent(EntryMenu.this, RegisterMenu.class);
                    startActivity(movedSignUp);
                    finish();
                }
            });
            forgotP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent movedForgotP = new Intent(EntryMenu.this, ForgotPassword.class);
                    startActivity(movedForgotP);
                    finish();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid() {
        Email.setErrorEnabled(false);
        Email.setError("");
        Password.setErrorEnabled(false);
        Password.setError("");

        boolean isValid = false, isValidemail = false, isValidpassword = false;
        if (TextUtils.isEmpty(email)) {
            Email.setErrorEnabled(true);
            Email.setError("Please enter your email");
        } else {
            if (email.matches(pattern)) {
                isValidemail = true;
            } else {
                Email.setErrorEnabled(true);
                Email.setError("Please enter valid email");
            }
        }
        if (TextUtils.isEmpty(pwd)) {
            Password.setErrorEnabled(true);
            Password.setError("Please enter your password");
        } else {
            isValidpassword = true;
        }

        isValid = (isValidemail && isValidpassword) ? true : false;
        return isValid;
    }

    ;
}