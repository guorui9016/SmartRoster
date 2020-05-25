package com.gr.smartroster.activity.fragment.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gr.smartroster.R;
import com.gr.smartroster.activity.GroupListActivity;
import com.gr.smartroster.activity.MainActivity;
import com.gr.smartroster.util.ConstantUtil;
import com.gr.smartroster.util.SpUtil;

public class SettingFragment extends Fragment implements View.OnClickListener {
    private SettingViewModel mSettingViewModel;
    private Switch mSwNotification, mSwAddToCalendar;
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
        mTvExitGroup.setOnClickListener(this);
        mTvDeleteAccount.setOnClickListener(this);
        mTvLogout.setOnClickListener(this);

        mSettingViewModel.getLiveData().observe(getViewLifecycleOwner(), s -> {
            switch (s) {
                case "deleted":
                    //display dialog and jump to the main page
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Your account has been deleted! Please click 'OK' back to home page")
                            .setTitle("Delete Account")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            });
                    builder.show();
                    break;
                case "exited":
                    //jump to group list page.
                    Toast.makeText(getContext(), "Exit this group", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), GroupListActivity.class);
                    startActivity(intent);
                    getActivity().finish();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Waring")
                        .setMessage("Are you sure delete this account?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mSettingViewModel.deleteAccount((String) SpUtil.get(getContext(), ConstantUtil.EMAIL, ""));
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.tvExitGroup:
                //exit the currect group
                mSettingViewModel.exitGroup((String) SpUtil.get(getContext(), ConstantUtil.GROUP_NAME, ""),
                        (String) SpUtil.get(getContext(), ConstantUtil.EMAIL, ""));
                break;
            case R.id.tvLogout:
                Log.d("Ray - ", "onClick: Logout");
                //logout
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            default:
                break;
        }
    }
}
