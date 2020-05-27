package com.gr.smartroster.activity.fragment.roster;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.gr.smartroster.R;
import com.gr.smartroster.adapter.SiteRosterRecyclerViewAdapter;
import com.gr.smartroster.model.Roster;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SiteRosterFragment extends Fragment implements View.OnClickListener {
    private SiteRosterViewModel mSiteRosterViewModel;
    private RecyclerView mRvSiteRoster;
    private SiteRosterRecyclerViewAdapter mViewAdapter;
    private Timestamp mTimestamp;
    private TextView mTvDate;
    private ImageView mIvDatePicker;
    private SimpleDateFormat mDateFormat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_site_roster, container, false);
        mSiteRosterViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(SiteRosterViewModel.class);

        //initial items
        mTvDate = root.findViewById(R.id.tvDate_site_roster);
        mIvDatePicker = root.findViewById(R.id.ivDatePicker);

        //initial recyclerview
        mRvSiteRoster = root.findViewById(R.id.rvSiteRoster);
        mRvSiteRoster.setHasFixedSize(true);
        mRvSiteRoster.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        //default data
        mDateFormat = new SimpleDateFormat("E, dd MMM yyyy");
        if (mTimestamp ==null) {
            mTimestamp = new Timestamp(new Date(System.currentTimeMillis()));
            Date date = new Date();
            String s = mDateFormat.format(date);
            mTvDate.setText(s);
        }
        mViewAdapter = new SiteRosterRecyclerViewAdapter();
        mSiteRosterViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<List<Roster>>() {
            @Override
            public void onChanged(List<Roster> rosters) {
                mViewAdapter.setRosterList(rosters);
                mRvSiteRoster.setAdapter(mViewAdapter);
            }
        });

        mIvDatePicker.setOnClickListener(this);

        return root;
    }


    @Override
    public void onClick(View v) {
        Calendar currentCal = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar selectCal = Calendar.getInstance();
                selectCal.set(year, month, dayOfMonth, 0, 0,0);
                Date selectDate = selectCal.getTime();
                //pass the date to view model
                mSiteRosterViewModel.setDate(new Timestamp(selectDate));
                //display the date on the title
                String seletDateString = mDateFormat.format(selectDate);
                mTvDate.setText(seletDateString);
            }
        };

        DatePickerDialog datePickerDialog =
                new DatePickerDialog(getContext(),
                        android.R.style.Theme_Material_Light_Dialog_MinWidth,
                        dateSetListener,
                        currentCal.get(Calendar.YEAR),
                        currentCal.get(Calendar.MONTH),
                        currentCal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
