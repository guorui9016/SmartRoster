package com.gr.smartroster.activity.fragment.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.gr.smartroster.R;
import com.gr.smartroster.util.ConstantUtil;
import com.gr.smartroster.util.SpUtil;

public class SettingFragment extends Fragment implements View.OnClickListener {
    private SettingViewModel mSettingViewModel;
    private Switch mSwNotification,mSwAddToCalendar;
    private TextView mTvExitGroup, mTvDeleteAccount, mTvLogout;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSettingViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(SettingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        //initial items
        mSwNotification = root.findViewById(R.id.swNotification);
        mSwAddToCalendar = root.findViewById(R.id.swAddToCalendar);
        mTvExitGroup = root.findViewById(R.id.tvExitGroup);
        mTvDeleteAccount = root.findViewById(R.id.tvDeleteAccount);
        mTvLogout = root.findViewById(R.id.tvLogout);

        mSettingViewModel.getLiveData().observe(getViewLifecycleOwner(), s -> {
            switch (s) {
                case "deleted":
                    //display dialog and jump to the main page

                    break;
                case "exited":
                    //jump to group list page.

                    break;
                default:
                    break;
            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDeleteAccount:
                //delete account
                mSettingViewModel.deleteAccount((String) SpUtil.get(getContext(), ConstantUtil.EMAIL_SP, ""));
                break;
            case R.id.tvExitGroup:
                //exit the currect group
                break;
            case R.id.tvLogout:
                //logout
                break;
            default:
                break;
        }

    }
}
