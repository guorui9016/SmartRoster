package com.gr.smartroster.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gr.smartroster.R;
import com.gr.smartroster.adapter.MemberInfoRecyclerViewAdapter;
import com.gr.smartroster.model.Staff;
import com.gr.smartroster.util.ConstantUtil;
import com.gr.smartroster.util.SpUtil;
import com.gr.smartroster.viewmodel.GroupInfoViewModel;
import java.util.List;

public class GroupInfoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private MemberInfoRecyclerViewAdapter memberInfoRecyclerViewAdapter;
    private GroupInfoViewModel groupInfoViewModel;
    private RecyclerView rvMemberInfo;
    private TextView tvNumber, tvGroupName_GroupInfo,tvGroupManager_GroupInfo,tvCompany_GroupInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        InitUI();
        //init toolbar
        initToolBar();
        //init RecyclerView
        memberInfoRecyclerViewAdapter = new MemberInfoRecyclerViewAdapter();
        rvMemberInfo.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        rvMemberInfo.setHasFixedSize(true);
        //init view model
        groupInfoViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(GroupInfoViewModel.class);
        groupInfoViewModel.getmMutableLiveDataList().observe(this, new Observer<List<Staff>>() {
            @Override
            public void onChanged(List<Staff> staffList) {
                tvNumber.setText("Total staffs: " + staffList.size() + " staffs" );
                memberInfoRecyclerViewAdapter.setStaffList(staffList);
                rvMemberInfo.setAdapter(memberInfoRecyclerViewAdapter);
            }
        });
        //init general information
        initGeneralInfo();
    }

    private void initToolBar() {
        toolbar.setTitle("Group Information");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void InitUI() {
        rvMemberInfo = findViewById(R.id.rvMemberInfo);
        tvNumber = findViewById(R.id.tvNumber);
        toolbar = findViewById(R.id.toolbar);
        rvMemberInfo = findViewById(R.id.rvMemberInfo);
        tvGroupName_GroupInfo = findViewById(R.id.tvGroupName_GroupInfo);
        tvGroupManager_GroupInfo =findViewById(R.id.tvGroupManager_GroupInfo);
        tvCompany_GroupInfo = findViewById(R.id.tvCompany_GroupInfo);
    }

    private void initGeneralInfo() {
        String groupName = (String) SpUtil.get(getApplicationContext(), ConstantUtil.GROUP_NAME,"");
        String company = (String) SpUtil.get(getApplicationContext(), ConstantUtil.COMPANY,"");

        tvGroupName_GroupInfo.setText(groupName);
        tvCompany_GroupInfo.setText(company);
        groupInfoViewModel.getmMutableLiveDateString().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvGroupManager_GroupInfo.setText(s);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
