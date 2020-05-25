package com.gr.smartroster.activity.fragment.avaliabletime;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.gr.smartroster.R;
import com.gr.smartroster.adapter.AvaliableTimeRecycerViewAdapter;
import com.gr.smartroster.callback.IRecyclerViewItemClickLister;
import com.gr.smartroster.model.AvaliableTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class AvaliableTimeFragment extends Fragment implements IRecyclerViewItemClickLister {

    private AvaliabletTimeViewModel avaliabletTimeViewModel;
    private RecyclerView recyclerView;
    private TextView tvInfo_Avali;
    private LayoutAnimationController animationController;
    private FloatingActionButton fabAddAvaliableTime;
    private int mYear, mMonth, mDay;
    private Calendar selectCal, tempCal;
    private AvaliableTimeRecycerViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        avaliabletTimeViewModel = ViewModelProviders.of(this).get(AvaliabletTimeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_avaliable, container, false);
        recyclerView = root.findViewById(R.id.rv_avaliable_time_list);
        fabAddAvaliableTime = root.findViewById(R.id.fab_add_avaliable_time);
        tvInfo_Avali = root.findViewById(R.id.tvInfo_Avaliable);
        selectCal = new GregorianCalendar(TimeZone.getDefault());
        initRecycleView();

        adapter = new AvaliableTimeRecycerViewAdapter(this);
        avaliabletTimeViewModel.getAvaliableTimeLiveDataList().observe(getViewLifecycleOwner(), new Observer<List<AvaliableTime>>() {
            @Override
            public void onChanged(List<AvaliableTime> avaliableTimes) {
                adapter.setAvaliableTimesList(avaliableTimes);
                recyclerView.setAdapter(adapter);
                //Set information if no avaliable time.
                if (avaliableTimes.isEmpty()) {
                    tvInfo_Avali.setText("Please add new avaliable time!");
                    tvInfo_Avali.setVisibility(View.VISIBLE);
                } else {
                    tvInfo_Avali.setVisibility(View.INVISIBLE);
                }
            }
        });

        fabAddAvaliableTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAvaliableTime();
            }
        });

        ItemTouchHelper itemTouchHelper =
                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        avaliabletTimeViewModel.deleteAvaliableTime(viewHolder.getAdapterPosition());
//                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                        Toast.makeText(getContext(), "Time deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                                .addBackgroundColor(Color.rgb(224, 101, 115))
                                .addActionIcon(R.drawable.ic_delete_light_gray)
                                .create()
                                .decorate();

                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return root;
    }

    private void addAvaliableTime() {
        callDatePicker(new AvaliableTime());
/*        Calendar tempCal = Calendar.getInstance();
        callDatePicker(tempCal.get(Calendar.YEAR),tempCal.get(Calendar.MONTH),tempCal.get(Calendar.DAY_OF_MONTH));
        callStartTimePicker(tempCal.get(Calendar.HOUR_OF_DAY), tempCal.get(Calendar.MINUTE));
        callEndTimePicker(tempCal.get(Calendar.HOUR_OF_DAY), tempCal.get(Calendar.MINUTE));
        avaliabletTimeViewModel.insertAvaliableTime(timestamps);*/
    }

    private void callDatePicker(AvaliableTime avaliableTime) {
        tempCal = Calendar.getInstance();
        if (avaliableTime.getDocID() != null) {
            tempCal.setTime(avaliableTime.getDate().toDate());
        }
        //pick a date
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Converts date to Timestamp format
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                selectCal.set(year, month, dayOfMonth,0,0,0);
                avaliableTime.setDate(new Timestamp(selectCal.getTime()));
                Log.i("Ray - ", "onTimeSet: Start date: " + year + month + dayOfMonth);
                callStartTimePicker(avaliableTime);
            }
        };

        DatePickerDialog datePickerDialog =
                new DatePickerDialog(getContext(),
                        android.R.style.Theme_Material_Light_Dialog_MinWidth,
                        dateSetListener,
                        tempCal.get(Calendar.YEAR),
                        tempCal.get(Calendar.MONTH),
                        tempCal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setTitle("Add New Avaliable Time");
        datePickerDialog.show();
    }

    private void callStartTimePicker(AvaliableTime avaliableTime) {
        tempCal = Calendar.getInstance();
        if (avaliableTime.getDocID() != null) {
            tempCal.setTime(avaliableTime.getStartTime().toDate());
        }
        //pick start time
        TimePickerDialog.OnTimeSetListener startTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Converts to timestamp
                selectCal.set(mYear, mMonth, mDay, hourOfDay, minute);
                avaliableTime.setStartTime(new Timestamp(selectCal.getTime()));
                Log.i("Ray - ", "onTimeSet: Start Time: " + hourOfDay + " : " + minute);
                callEndTimePicker(avaliableTime);
            }
        };

        TimePickerDialog startTimePickerDialog =
                new TimePickerDialog(getContext(),
                        startTimeSetListener,
                        tempCal.get(Calendar.HOUR_OF_DAY),
                        tempCal.get(Calendar.MINUTE),
                        true);
        startTimePickerDialog.setTitle("Start Time");
        startTimePickerDialog.show();
    }

    private void callEndTimePicker(AvaliableTime avaliableTime) {
        tempCal = Calendar.getInstance();
        if (avaliableTime.getDocID() != null) {
            tempCal.setTime(avaliableTime.getEndTime().toDate());
        }
        //Pick end Time
        TimePickerDialog.OnTimeSetListener endtimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Converts to timestamp
                selectCal.set(mYear, mMonth, mDay, hourOfDay, minute);
                avaliableTime.setEndTime(new Timestamp(selectCal.getTime()));
                Log.i("Ray - ", "onTimeSet: Start Time: " + hourOfDay + " : " + minute);
                saveTime(avaliableTime);
            }
        };
        TimePickerDialog endTimePickerDialog =
                new TimePickerDialog(getContext(), endtimeSetListener, tempCal.get(Calendar.HOUR_OF_DAY), tempCal.get(Calendar.MINUTE),true);
        endTimePickerDialog.setTitle("End Time");
        endTimePickerDialog.show();
    }

    private void initRecycleView() {
        animationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_item_from_left);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int positon) {
        AvaliableTime avaliableTime = avaliabletTimeViewModel.getAvaliableTime(positon);
        callDatePicker(avaliableTime);
    }

    private void saveTime(AvaliableTime avaliableTime) {
        if (avaliableTime.getDocID() != null) {
            Log.i("Ray - ", "saveTime: update time");
            avaliabletTimeViewModel.updateAvaliableTime(avaliableTime);
        } else {
            Log.i("Ray - ", "saveTime: insert time");
            avaliabletTimeViewModel.insertAvaliableTime(avaliableTime);
        }
    }
}