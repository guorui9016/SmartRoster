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
import com.gr.smartroster.R;
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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").whereEqualTo("email", "john@gmail.com")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                            }
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                User user = doc.toObject(User.class);
                                Toast.makeText(MainActivity.this, "This is the test message. load " + user.getEmail() + "from database", Toast.LENGTH_SHORT).show();
                                Log.d("Ray - ", "onComplete: " + user.getFullName());
                                Log.d("Ray - ", "onComplete: " + user.getEmail());
                                Log.d("Ray - ", "onComplete: " + user.getPhoneNumber());
                                Log.d("Ray - ", "onComplete: " + user.getPassword());
                            }
                        } else {
                            Log.w("Ray - ", "onComplete: Error geting document" + task.getException());
                        }
                    }
                });
    }
}
