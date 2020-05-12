package com.gr.smartroster.activity.fragment.userprofile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.gr.smartroster.R;

public class UserProfileFragment extends Fragment implements View.OnClickListener {

    private UserProfileViewModel mUserProfileViewModel;
    private EditText mEtPhoneNumber, mEtOldPassword, mEtNewPassword, mEtComfPassword;
    private ImageView mIvSavePhoneNum, mIvSaveNewPassword, mIvErrorIcon, mIvSavePhoneNum_Disable, mIvSaveNewPassword_Disable;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mUserProfileViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(UserProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_userprofile, container, false);
        initUI(root);  //init all of elements

        mUserProfileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s) {
                    case "phoneNum":
                        Toast.makeText(getContext(), "Phone number update successful", Toast.LENGTH_SHORT).show();
                        mIvSavePhoneNum_Disable.setVisibility(View.VISIBLE);
                        mIvSavePhoneNum.setVisibility(View.INVISIBLE);
                        break;
                    case "password":
                        Toast.makeText(getContext(), "New password update successful", Toast.LENGTH_SHORT).show();
                        mIvSaveNewPassword_Disable.setVisibility(View.VISIBLE);
                        mIvSaveNewPassword.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        mEtPhoneNumber.setText(s);
                        break;
                }
            }
        });

        //Watch the text context, once the context has been changed, the save icon will be enable
        mEtPhoneNumber.addTextChangedListener(new CustomerTextWatcher(mEtPhoneNumber));
        mEtOldPassword.addTextChangedListener(new CustomerTextWatcher(mEtOldPassword));
        mEtNewPassword.addTextChangedListener(new CustomerTextWatcher(mEtNewPassword));
        mEtComfPassword.addTextChangedListener(new CustomerTextWatcher(mEtComfPassword));

        //save new phone number or new password
        mIvSavePhoneNum.setOnClickListener(this);
        mIvSaveNewPassword.setOnClickListener(this);

        return root;
    }

    private void initUI(View root) {
        mEtPhoneNumber = root.findViewById(R.id.etPhoneNumber);
        mEtOldPassword = root.findViewById(R.id.etOldPassword_Profile);
        mEtNewPassword = root.findViewById(R.id.etNewPassword_Profile);
        mEtComfPassword = root.findViewById(R.id.etComfPassword_Profile);
        mIvSavePhoneNum = root.findViewById(R.id.ivSavePhoneNum_Profile);
        mIvSavePhoneNum_Disable = root.findViewById(R.id.ivSavePhoneNum_disable_Profile);
        mIvSavePhoneNum = root.findViewById(R.id.ivSavePhoneNum_Profile);
        mIvSaveNewPassword_Disable = root.findViewById(R.id.ivSaveNewPassword_Disable_Profile);
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
                    mIvErrorIcon.setVisibility(View.INVISIBLE);
                    mIvSaveNewPassword_Disable.setVisibility(View.INVISIBLE);
                    mIvSaveNewPassword.setVisibility(View.VISIBLE);
                }
            } else {
                mIvErrorIcon.setVisibility(View.VISIBLE);
                mIvSaveNewPassword_Disable.setVisibility(View.VISIBLE);
                mIvSaveNewPassword.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivSavePhoneNum_Profile:
                String phoneNum = mEtPhoneNumber.getText().toString().trim();
                mUserProfileViewModel.setUserPhoneNumber(phoneNum);
                break;
            case R.id.ivSaveNewPassword_Profile:
                String newPwd = mEtNewPassword.getText().toString().trim();
                mUserProfileViewModel.setPassword(newPwd);
                break;
            default:
                break;
        }
    }

    private class CustomerTextWatcher implements TextWatcher {
        EditText mEditText;

        public CustomerTextWatcher(EditText mEditText) {
            this.mEditText = mEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (mEditText.getId()) {
                case R.id.etPhoneNumber:
                    mIvSavePhoneNum_Disable.setVisibility(View.INVISIBLE);
                    mIvSavePhoneNum.setVisibility(View.VISIBLE);
                    break;
                case R.id.etNewPassword_Profile:
                    checkPassword();
                    break;
                case R.id.etComfPassword_Profile:
                    checkPassword();
                    break;
                case R.id.etOldPassword_Profile:
                    checkPassword();
                    break;
                default:
                    break;
            }
        }
    }
}
