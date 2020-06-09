package com.gr.smartroster.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gr.smartroster.R;
import com.gr.smartroster.model.Staff;

public class EditStaffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_staff);
        String jsonData = getIntent().getStringExtra("staff");
        Staff staff = new Gson().fromJson(jsonData, Staff.class);
    }
}
