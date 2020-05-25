package com.gr.smartroster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.gr.smartroster.R;
import com.gr.smartroster.model.User;
import com.gr.smartroster.util.ConstantUtil;
import com.gr.smartroster.util.SpUtil;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnLogin, btnReg;
    TextView tvInfo_Login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initActivity();  //init all the items.
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();     //inti Firebase
                //get email and password
                String email = etEmail.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();
                //Login
                db.collection("users")
                        .document(email)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    User user = documentSnapshot.toObject(User.class);
                                    if (user.getPassword().equals(password)) {
                                        Toast.makeText(LoginActivity.this, R.string.login_successful_message, Toast.LENGTH_SHORT).show();
                                        Log.i("Ray - ", "onDataChange: Login successful.");
                                        Intent intent = new Intent(LoginActivity.this, GroupListActivity.class);
                                        SpUtil.set(getApplicationContext(), ConstantUtil.EMAIL, email);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        tvInfo_Login.setText(R.string.login_invalid_message);
                                        Toast.makeText(LoginActivity.this, R.string.login_invalid_message, Toast.LENGTH_SHORT).show();
                                        Log.i("Ray - ", "onDataChange: Password error.");
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, R.string.login_invalid_message, Toast.LENGTH_SHORT).show();
                                    Log.i("Ray - ", "onDataChange: User is not exist!");
                                    tvInfo_Login.setVisibility(View.VISIBLE);
                                    tvInfo_Login.setText("User is not exist! \n Please register a new account");
                                    btnReg.setVisibility(View.VISIBLE);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Ray - ", "onCancelled: Failed to get data from database");
                                tvInfo_Login.setText(R.string.firebase_database_error_message);
                                Toast.makeText(LoginActivity.this, R.string.firebase_database_error_message, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initActivity() {
        etEmail = findViewById(R.id.etEmail_Login);
        etPassword = findViewById(R.id.etPassword_Login);
        btnLogin = findViewById(R.id.btnLogin_Login);
        tvInfo_Login = findViewById(R.id.tvInfo_Login);
        btnReg = findViewById(R.id.btnRegister_Login);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
