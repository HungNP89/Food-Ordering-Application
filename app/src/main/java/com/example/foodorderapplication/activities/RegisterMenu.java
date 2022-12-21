package com.example.foodorderapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodorderapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterMenu extends AppCompatActivity {
    TextView textViewLogin;

    TextInputLayout Username, Email, Password, ConfirmPassword, Address;
    Button signUp;
    FirebaseAuth fAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String username, email, password, confirm, address;
    String role = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_menu);

        textViewLogin = findViewById(R.id.tvLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent movedSignIn = new Intent(RegisterMenu.this,
                        EntryMenu.class);
                startActivity(movedSignIn);
                finish();
            }
        });

        Username = findViewById(R.id.Username);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        ConfirmPassword = findViewById(R.id.ConfirmPassword);
        Address = findViewById(R.id.Address);
        signUp = findViewById(R.id.btnSignUp);

        databaseReference = firebaseDatabase.getInstance().getReference("Users");
        fAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = Username.getEditText().getText().toString().trim();
                email = Email.getEditText().getText().toString().trim();
                password = Password.getEditText().getText().toString().trim();
                confirm = ConfirmPassword.getEditText().getText().toString().trim();
                address = Address.getEditText().getText().toString().trim();

                if (isValid()) {
                    final ProgressDialog mDialog = new ProgressDialog(RegisterMenu.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Sign Up is now processing , please wait ...");
                    mDialog.show();
                    Log.w("", "OK Con de 1");
                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("All").child(userId);
                                final HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("Role", role);
                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        HashMap<String, String> hashMap1 = new HashMap<>();
                                        hashMap1.put("Username", username);
                                        hashMap1.put("Email", email);
                                        hashMap1.put("Password", password);
                                        hashMap1.put("ConfirmPassword", confirm);
                                        hashMap1.put("Address", address);
                                        Log.w("", "Ok con de 2");
                                        firebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        mDialog.dismiss();
                                                        final FirebaseUser user = fAuth.getCurrentUser();
                                                        assert user != null;
                                                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterMenu.this);
                                                                    builder.setMessage("Registered! Please verify your email");
                                                                    builder.setCancelable(false);
                                                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                                            mDialog.dismiss();
                                                                        }
                                                                    });
                                                                    AlertDialog Alert = builder.create();
                                                                    Alert.show();
                                                                } else {
                                                                    mDialog.dismiss();
                                                                    Log.w("", "Error");
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid() {
        Username.setErrorEnabled(false);
        Username.setError("");
        Email.setErrorEnabled(false);
        Email.setError("");
        Password.setErrorEnabled(false);
        Password.setError("");
        ConfirmPassword.setErrorEnabled(false);
        ConfirmPassword.setError("");
        Address.setErrorEnabled(false);
        Address.setError("");

        boolean isValid = false, isValidemail = false, isValidusername = false, isValidpassword = false, isValidconfirmpassword = false, isValidaddress = false;
        if (TextUtils.isEmpty(username)) {
            Username.setErrorEnabled(true);
            Username.setError("Please enter your username");
        } else {
            isValidusername = true;
        }
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
        if (TextUtils.isEmpty(password)) {
            Password.setErrorEnabled(true);
            Password.setError("Please enter your password");
        } else {
            if (password.length() < 8) {
                Password.setErrorEnabled(true);
                Password.setError("Password requires 8 characters or more ");
            } else {
                isValidpassword = true;
            }
        }
        if (TextUtils.isEmpty(confirm)) {
            ConfirmPassword.setErrorEnabled(true);
            ConfirmPassword.setError("Enter password again");
        } else {
            if (!password.equals(confirm)) {
                ConfirmPassword.setErrorEnabled(true);
                ConfirmPassword.setError("Password doesn't match");
            } else {
                isValidconfirmpassword = true;
            }
        }
        if (TextUtils.isEmpty(address)) {
            Address.setErrorEnabled(true);
            Address.setError("Please enter your address");
        } else {
            isValidaddress = true;
        }

        isValid = (isValidusername && isValidemail && isValidpassword && isValidconfirmpassword && isValidaddress) ? true : false;
        return isValid;
    }

    ;
}