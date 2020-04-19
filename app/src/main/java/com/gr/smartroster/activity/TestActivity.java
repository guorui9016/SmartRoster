package com.gr.smartroster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.gr.smartroster.R;
import com.gr.smartroster.model.Staff;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String staffJosn = getIntent().getStringExtra("staff");
        Staff staff = new Gson().fromJson(staffJosn, Staff.class);

        Log.i("Ray - ", "onCreate: " + staff.toString());
    }
}
