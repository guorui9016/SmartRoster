package com.gr.smartroster.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.gr.smartroster.R;
import com.gr.smartroster.model.Staff;
import com.gr.smartroster.model.User;

public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin_Main);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void test(View view) {
        Staff staff = new Staff();
        staff.setAdmin(false);
        staff.setCompany("Home");
        staff.setContractType("Parttime");
        staff.setEmail("Guorui9016@gmail.com");
        staff.setGroupName("Great");

        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra("staff", new Gson().toJson(staff));
        Log.i("Ray - ", "test: " + staff.toString());
        startActivity(intent);
    }
}
