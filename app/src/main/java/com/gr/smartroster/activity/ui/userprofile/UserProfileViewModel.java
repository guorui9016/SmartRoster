package com.gr.smartroster.activity.ui.userprofile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;
import com.gr.smartroster.callback.IUserProfileCallbackLister;
import com.gr.smartroster.util.ConstantUtil;
import com.gr.smartroster.util.SpUtil;

public class UserProfileViewModel extends AndroidViewModel implements IUserProfileCallbackLister {
    private MutableLiveData<String> mText;      //phonenumber
    private IUserProfileCallbackLister mIUserProfileCallbackLister;
    private String mEmail;

    public UserProfileViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mIUserProfileCallbackLister = this;
        mEmail = (String) SpUtil.get(application.getApplicationContext(), ConstantUtil.EMAIL_SP,"");
    }


    public LiveData<String> getText() {
        loadUserPhoneNumber();
        return mText;
    }

    private void loadUserPhoneNumber() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //todo
        //User documnet in Firestore will use email as id.
    }

    @Override
    public void onPhoneNumberLoadSuccessul(String phoneNumber) {
        mText.setValue(phoneNumber);
    }
}