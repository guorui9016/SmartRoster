package com.gr.smartroster.activity.fragment.managegroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.gr.smartroster.R;
import com.gr.smartroster.activity.GroupInfoActivity;
import com.gr.smartroster.activity.GroupListActivity;
import com.gr.smartroster.activity.ManageStaffActivity;
import com.gr.smartroster.util.ConstantUtil;
import com.gr.smartroster.util.SpUtil;

public class ManageGroupFragment extends Fragment implements View.OnClickListener {
    TextView mTvGroupInfo, mTvSwitchGroup, mTvStaffs, mTvRoles, mTvCreatGroup, mTvExitGroup, mTvDeleteGroup;
    private boolean mAdmin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_manage_group, container, false);
        //load admin info from SP
        mAdmin = (boolean) SpUtil.get(getContext(), ConstantUtil.ADMIN, false);
        //initUI
        initUI(root);

        //setup OnClickLister
        mTvGroupInfo.setOnClickListener(this);
        mTvSwitchGroup.setOnClickListener(this);
        mTvStaffs.setOnClickListener(this);
        mTvCreatGroup.setOnClickListener(this);
        mTvExitGroup.setOnClickListener(this);
        mTvDeleteGroup.setOnClickListener(this);
        return root;
    }

    private void initUI(View view) {
        //find item
        mTvGroupInfo = view.findViewById(R.id.tvGroupInfo);
        mTvSwitchGroup = view.findViewById(R.id.tvSwitchGroup);
        mTvStaffs = view.findViewById(R.id.tvStaffs);
        mTvCreatGroup = view.findViewById(R.id.tvCreatGroup);
        mTvExitGroup = view.findViewById(R.id.tvExitGroup);
        mTvDeleteGroup = view.findViewById(R.id.tvDeleteGroup);
        if (mAdmin) {
            //manage group item will be invisible.
            //todo

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvGroupInfo:
                getGroupInfo();
                break;
            case R.id.tvSwitchGroup:
                Intent intentSwitchGroup = new Intent(getContext(), GroupListActivity.class);
                startActivity(intentSwitchGroup);
                break;
            case R.id.tvStaffs:
                Intent intentStaff = new Intent(getContext(), ManageStaffActivity.class);
                startActivity(intentStaff);
                break;
            case R.id.tvCreatGroup:
                break;
            case R.id.tvExitGroup:
                break;
            case R.id.tvDeleteGroup:
                break;
            default:
                break;
        }
    }

    private void getGroupInfo() {
        Intent intent = new Intent(getContext(), GroupInfoActivity.class);
        startActivity(intent);
    }
}
