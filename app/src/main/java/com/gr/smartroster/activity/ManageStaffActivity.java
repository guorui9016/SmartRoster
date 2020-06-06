package com.gr.smartroster.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.gr.smartroster.R;
import com.gr.smartroster.adapter.ManageStaffRecyclerViewAdapter;
import com.gr.smartroster.callback.IRecyclerViewItemClickLister;
import com.gr.smartroster.model.Staff;
import com.gr.smartroster.viewmodel.ManageStaffViewModel;
import java.util.List;

public class ManageStaffActivity extends AppCompatActivity implements IRecyclerViewItemClickLister {
    private Toolbar toolbar;
    private RecyclerView mRvStaffs;
    private ManageStaffRecyclerViewAdapter mViewAdapter;
    private ManageStaffViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_staff);
        mViewAdapter = new ManageStaffRecyclerViewAdapter(this);
        //init toolbar
        toolbar = findViewById(R.id.toolbar);
        initToolBar();
        //initial RecyclerView
        mRvStaffs = findViewById(R.id.rvStaffs);
        mRvStaffs.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRvStaffs.setHasFixedSize(true);
        //init viewModel
        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ManageStaffViewModel.class);
        mViewModel.getMutableLiveData().observe(this, new Observer<List<Staff>>() {
            @Override
            public void onChanged(List<Staff> staffList) {
                mViewAdapter.setStaffList(staffList);
                mRvStaffs.setAdapter(mViewAdapter);
            }
        });
    }

    private void initToolBar() {
        toolbar.setTitle("Manage Staffs");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        Log.d("Ray - ", "onItemClick: Staff item has been clicked. The position is: " + position);
        Staff staff = mViewModel.getStaff(position);
        Intent intent = new Intent(this, EditStaffActivity.class);
        intent.putExtra("staff", new Gson().toJson(staff));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {

    }
}
