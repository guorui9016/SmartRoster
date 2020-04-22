package com.gr.smartroster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.gr.smartroster.R;
import com.gr.smartroster.util.ConstantUtil;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddTimeActivity extends AppCompatActivity {
    TextView tvDateValue, tvStartTimeValue, tvEndTimeValue;
    ImageView ivSave, ivCancel;
    int year, month, day, hour, minutes;
    Calendar mCalendar;
    Timestamp date_TS, startTime_TS, endTime_TS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);

        //initial
        tvDateValue = findViewById(R.id.tvDateValue);
        tvStartTimeValue = findViewById(R.id.tvStartTimeValue);
        tvEndTimeValue = findViewById(R.id.tvEndTimeValue);
        ivSave = findViewById(R.id.ivSave);
        ivCancel = findViewById(R.id.ivCancel);
        mCalendar = new GregorianCalendar();

        //Get current time
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minutes = cal.get(Calendar.MINUTE);

        tvDateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener dateSetListener  = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //Converts date to Timestamp format
                        mCalendar.set(year, month, dayOfMonth);
                        date_TS = new Timestamp(mCalendar.getTime());
                        //show time on textview
                        month = month +1;  //month start from 0 to 11.
                        String date = dayOfMonth + " / " + month + " / " + year;
                        tvDateValue.setText(date);
                    }
                };

                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(AddTimeActivity.this,android.R.style.Theme_Material_Light_Dialog_MinWidth ,dateSetListener, year,month, day);
                datePickerDialog.show();
            }
        });

        tvStartTimeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //Converts to timestamp
                        mCalendar.add(Calendar.HOUR_OF_DAY,hour);
                        mCalendar.add(Calendar.MINUTE, minute);
                        startTime_TS = new Timestamp(mCalendar.getTime());
                        //display
                        String startTime = hourOfDay + " : " + minute;
                        if (minute == 0) {
                            startTime = startTime + "0";
                        }
                        tvStartTimeValue.setText(startTime);
                    }
                };

                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(AddTimeActivity.this, timeSetListener, hour, minutes, true);
                timePickerDialog.show();
            }
        });

        tvEndTimeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //Convert to timestamp
                        mCalendar.add(Calendar.HOUR_OF_DAY,hour);
                        mCalendar.add(Calendar.MINUTE, minute);
                        endTime_TS = new Timestamp(mCalendar.getTime());

                        //display
                        String endTime = hourOfDay + " : " + minute;
                        if (minute == 0) {
                            endTime = endTime + "0";
                        }
                        tvEndTimeValue.setText(endTime);
                    }
                };

                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(AddTimeActivity.this, timeSetListener, hour, minutes, true);
                timePickerDialog.show();
            }
        });

        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date_TS == null || startTime_TS == null || endTime_TS == null) {
                    Toast.makeText(AddTimeActivity.this, "Please select date and time", Toast.LENGTH_SHORT).show();
                } else {
                    Intent data = new Intent();
                    data.putExtra(ConstantUtil.TIMESTAMP_DATE, date_TS);
                    data.putExtra(ConstantUtil.TIMESTAMP_START_TIME, startTime_TS);
                    data.putExtra(ConstantUtil.TIMESTAMP_END_TIME, endTime_TS);
                    setResult(ConstantUtil.SAVETIME_RESULT_CODE, data);
                    finish();
                }
            }
        });

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
