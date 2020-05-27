package com.gr.smartroster.activity.fragment.groupmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gr.smartroster.R;

public class GroupManagerFragment  extends Fragment {
    TextView tvGroupInfo, tvSwitchGroup, tvStaffs, tvRoles,tvCreatGroup,tvExitGroup,tvDeleteGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_group_manager, container, false);
        //initUI
        initUI();


        return root;
    }

    private void initUI() {
    }
}
