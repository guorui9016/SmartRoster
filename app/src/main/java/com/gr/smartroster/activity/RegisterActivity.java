package com.gr.smartroster.activity;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gr.smartroster.R;
import com.gr.smartroster.model.User;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText etUserId, etFullName, etPassword, etComfPasswrod;
    Button btnRegiest;
    TextView tvInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        initActivity();
        Log.i("Ray - ", "onCreate: Initial RegisterActivity");
        //initial firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tableUser = database.getReference("User");
        btnRegiest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uerId = etUserId.getText().toString().trim();
                final String fullName = etFullName.getText().toString().trim();
                final String psd = etPassword.getText().toString().trim();
                String comfPsd = etComfPasswrod.getText().toString().trim();

                //check user name and passpword
                if (checkUserInfo(uerId, psd, comfPsd)) {
                    Log.i("Ray - ", "onClick: The user info are correct");
                    btnRegiest.setEnabled(false);
                    tableUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.child(uerId).exists()) {  //User name is exist or not.
                                User user = new User(psd, fullName);
                                tableUser.child(uerId).setValue(user);
                                Toast.makeText(RegisterActivity.this, R.string.register_adduser_succ, Toast.LENGTH_SHORT).show();
                                Log.i("Ray - ", "onDataChange: Write data to database");
                                finish();
                            } else {
                                tvInfo.setText(R.string.register_user_exist_message);
                                btnRegiest.setEnabled(true);
                                Log.i("Ray - ", "onDataChange: user name is existed");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("Ray - ", "onCancelled: Failed to get data from database");
                            tvInfo.setText(R.string.firebase_database_error_message);
                            Toast.makeText(RegisterActivity.this, R.string.firebase_database_error_message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private boolean checkUserInfo(String userId, String psd, String comfPsd) {
        //user name inculde 6-16 letter, number and "_". First one must be a letter.
        Pattern pattern_name = Pattern.compile("[a-zA-Z]{1}[a-zA-Z0-9_]{5,15}");
        //password inculde 6-20 letter, number and symbol. At least 1 Cap letter, 1 little letter and 1 symbol.
        Pattern pattern_psd = Pattern.compile("[a-zA-Z0-9]{1,16}");
        if (pattern_name.matcher(userId).matches()) {     //check user name
            if (psd.equals(comfPsd)) {      //check the two passowrd is match or not
                if (pattern_psd.matcher(psd).matches()) {       //check the password
                    return true;
                } else {        //invalid password
                    tvInfo.setText(R.string.register_password_info_message);
                    return false;
                }
            } else {        //two password are different
                tvInfo.setText(R.string.register_password_nomatch_message);
                return false;
            }
        } else {      //invalid user name
            tvInfo.setText(R.string.register_username_info_message);
            return false;
        }
    }

    private void initActivity() {
        etUserId = findViewById(R.id.etUserID_Reg);
        etFullName = findViewById(R.id.etFullName_Reg);
        etPassword = findViewById(R.id.etPassword_Reg);
        etComfPasswrod = findViewById(R.id.etComfPassword_Reg);
        btnRegiest = findViewById(R.id.btnRegister_Reg);
        tvInfo = findViewById(R.id.tvInfo_Reg);
    }
}
