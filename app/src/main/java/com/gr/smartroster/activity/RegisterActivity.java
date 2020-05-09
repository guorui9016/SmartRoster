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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.gr.smartroster.R;
import com.gr.smartroster.model.User;

import java.util.List;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText etEmail, etFullName, etPassword, etComfPasswrod;
    Button btnRegiest, btnLog;
    TextView tvInfo;
    FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        initActivity();
        Log.i("Ray - ", "onCreate: Initial RegisterActivity");

        //initial firebase
        btnRegiest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvInfo.setText("");
                db = FirebaseFirestore.getInstance();
                btnRegiest.setEnabled(false);
                String email = etEmail.getText().toString().trim();
                String fullName = etFullName.getText().toString().trim();
                String psd = etPassword.getText().toString().trim();
                String comfPsd = etComfPasswrod.getText().toString().trim();

                Log.i("Ray - ", "onClick: Click register button");
                //check email and password
                if (checkUserInfo(email, psd, comfPsd)) {
                    Log.i("Ray - ", "onClick: The user info are correct");
                    final User user = new User(email, psd, fullName);
                    db.collection("users")
                            .document(email)
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            //if the email is not exists, then save user info to db.
                            if (!documentSnapshot.exists()) {
                                Log.i("Ray - ", "onComplete: Can creat a new user with the email address ");
                                //save data to db
                                db.collection("users").document(email)
                                        .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.i("Ray - ", "onSuccess: " + "documnet ID is: " + email);
                                        Toast.makeText(RegisterActivity.this, "Register successful!", Toast.LENGTH_SHORT).show();
                                        tvInfo.setText("Register successful! \n Login now?");
                                        btnRegiest.setVisibility(View.INVISIBLE);
                                        btnLog.setVisibility(View.VISIBLE);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("Ray - ", "onFailure: " + "User add failed! " + e);
                                        tvInfo.setText("Register failed. Please try again later");
                                    }
                                });
                            } else {
                                //Email address was exist in database
                                tvInfo.setText(R.string.register_user_exist_message);
                                btnRegiest.setEnabled(true);
                                btnLog.setEnabled(true);
                                Log.i("Ray - ", "onDataChange: user name is existed");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Ray - ", "onCancelled: database connect error");
                            tvInfo.setText(R.string.firebase_database_error_message);
                        }
                    });
                } else {
                    btnRegiest.setEnabled(true);
                }
            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private boolean checkUserInfo(String email, String psd, String comfPsd) {
        //user name inculde 6-16 letter, number and "_". First one must be a letter.
        Log.i("Ray - ", "checkUserInfo: Check all information");
        Pattern pattern_email = Pattern.compile("^[A-Za-z0-9]+([_\\.][A-Za-z0-9]+)*@([A-Za-z0-9\\-]+\\.)+[A-Za-z]{2,6}$");
        //password inculde 6-20 letter, number and symbol. At least 1 Cap letter, 1 little letter and 1 symbol.
        Pattern pattern_psd = Pattern.compile("[a-zA-Z0-9]{1,16}");
        final boolean[] flag = new boolean[1];
        if (pattern_email.matcher(email).matches()) {     //check is a email or not
            if (psd.equals(comfPsd)) {      //check the two passowrd is match or not
                if (pattern_psd.matcher(psd).matches()) {       //check if password is right format
                    //check the email is exist or not
                    return true;
                } else {        //invalid password format
                    tvInfo.setText(R.string.register_password_info_message);
                    return false;
                }
            } else {        //two password are different
                tvInfo.setText(R.string.register_password_nomatch_message);
                return false;
            }
        } else {      //invalid email address format
            tvInfo.setText(R.string.register_username_info_message);
            return false;
        }
    }

    private void initActivity() {
        etEmail = findViewById(R.id.etEmail_Reg);
        etFullName = findViewById(R.id.etFullName_Reg);
        etPassword = findViewById(R.id.etPassword_Reg);
        etComfPasswrod = findViewById(R.id.etComfPassword_Reg);
        btnRegiest = findViewById(R.id.btnRegister_Reg);
        tvInfo = findViewById(R.id.tvInfo_Reg);
        btnLog = findViewById(R.id.btnLogin_Reg);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
