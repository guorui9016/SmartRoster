package com.gr.smartroster.activity.ui.userprofile;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.gr.smartroster.R;

public class UserProfileFragment extends Fragment {

    private UserProfileViewModel mUserProfileViewModel;
    private EditText mEtPhoneNumber, mEtOldPassword,mEtNewPassword,mEtComfPassword;
    private ImageView mIvSavePhoneNum, mIvSaveNewPassword, mIvErrorIcon;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mUserProfileViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(UserProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_userprofile, container, false);
        initUI(root);  //init all of elements
        mIvSavePhoneNum.setColorFilter(Color.GREEN);
        mIvSavePhoneNum.setClickable(true);

        initPhoneNumber();   //get phone from database

        mEtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mIvSavePhoneNum.setColorFilter(Color.GREEN);
                mIvSavePhoneNum.setClickable(true);
            }
        });

        mEtNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkPassword();
            }
        });

        mEtComfPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkPassword();
            }
        });

        return root;
    }

    private void initPhoneNumber() {
        mUserProfileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });
    }

    private void initUI(View root) {
        mEtPhoneNumber = root.findViewById(R.id.etPhoneNumber);
        mEtOldPassword = root.findViewById(R.id.etOldPassword_Profile);
        mEtNewPassword = root.findViewById(R.id.etNewPassword_Profile);
        mEtComfPassword = root.findViewById(R.id.etComfPassword_Profile);
        mIvSavePhoneNum = root.findViewById(R.id.ivSavePhoneNum_Profile);
        mIvSaveNewPassword = root.findViewById(R.id.ivSaveNewPassword_Profile);
        mIvSavePhoneNum = root.findViewById(R.id.ivSavePhoneNum_Profile);
        mIvSaveNewPassword = root.findViewById(R.id.ivSaveNewPassword_Profile);
        mIvErrorIcon = root.findViewById(R.id.ivErrorIcon);
    }

    private void checkPassword() {
        String oldPwd = mEtOldPassword.getText().toString().trim();
        String newPwd = mEtNewPassword.getText().toString().trim();
        String comfPwd = mEtComfPassword.getText().toString().trim();
        if (!newPwd.isEmpty() && !comfPwd.isEmpty()) {
            if (newPwd.equals(comfPwd)) {
                //save icon is clickable when new password and comfirm password are same
                if (!oldPwd.isEmpty()) {
                    mIvSaveNewPassword.setColorFilter(Color.GREEN);
                    mIvSaveNewPassword.setClickable(true);
                    mIvErrorIcon.setVisibility(View.INVISIBLE);
                }
            } else {
                mIvErrorIcon.setVisibility(View.VISIBLE);
            }
        }
    }
}
