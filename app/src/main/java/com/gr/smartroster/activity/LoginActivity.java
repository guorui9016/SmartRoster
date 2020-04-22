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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.gr.smartroster.R;
import com.gr.smartroster.model.User;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnLogin;
    TextView textView;

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
                        .whereEqualTo("email", email)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (!task.getResult().isEmpty()) {
                                        //get data
                                        List<User> users = task.getResult().toObjects(User.class);
                                        User user = users.get(0);
                                        if (user.getPassword().equals(password)) {
                                            Toast.makeText(LoginActivity.this, R.string.login_successful_message, Toast.LENGTH_SHORT).show();
                                            Log.i("Ray - ", "onDataChange: Login successful.");
                                            Intent intent = new Intent(LoginActivity.this, GroupListActivity.class);
                                            intent.putExtra("email", user.getEmail());
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            textView.setText(R.string.login_invalid_message);
                                            Toast.makeText(LoginActivity.this, R.string.login_invalid_message, Toast.LENGTH_SHORT).show();
                                            Log.i("Ray - ", "onDataChange: Password error.");
                                        }
                                    } else {
                                        Toast.makeText(LoginActivity.this, R.string.login_invalid_message, Toast.LENGTH_SHORT).show();
                                        Log.i("Ray - ", "onDataChange: User is not eixt!");
                                    }
                                } else {
                                    //failed
                                    Log.e("Ray - ", "onCancelled: Failed to get data from database" );
                                    textView.setText(R.string.firebase_database_error_message);
                                    Toast.makeText(LoginActivity.this, R.string.firebase_database_error_message, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });



        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tableUser = database.getReference("User");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = etUserName.getText().toString().trim();
                final String passwrod = etPassword.getText().toString().trim();

                tableUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //check the user name if the user exist.
                        if (dataSnapshot.child(userName).exists()) {
                            User user = dataSnapshot.child(userName).getValue(User.class);
                            Log.d("Ray - ", "onDataChange: " + "Input password is: " + passwrod + " The password in database is: " + user.getPassword());
                            if (user.getPassword().equals(passwrod)) {
                                Toast.makeText(LoginActivity.this, R.string.login_successful_message, Toast.LENGTH_SHORT).show();
                                Log.i("Ray - ", "onDataChange: Login successful.");
                            } else {
                                textView.setText(R.string.login_invalid_message);
                                Toast.makeText(LoginActivity.this, R.string.login_invalid_message, Toast.LENGTH_SHORT).show();
                                Log.i("Ray - ", "onDataChange: Password error.");
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.login_invalid_message, Toast.LENGTH_SHORT).show();
                            Log.i("Ray - ", "onDataChange: User is not eixt!");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Ray - ", "onCancelled: Failed to get data from database" );
                        textView.setText(R.string.firebase_database_error_message);
                        Toast.makeText(LoginActivity.this, R.string.firebase_database_error_message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/
    }

    private void initActivity() {
        etEmail = findViewById(R.id.etEmail_Login);
        etPassword = findViewById(R.id.etPassword_Login);
        btnLogin = findViewById(R.id.btnLogin_Login);
        textView = findViewById(R.id.tvInfo_Login);
    }
}
