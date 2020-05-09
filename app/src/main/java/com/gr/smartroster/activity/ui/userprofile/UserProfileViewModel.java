package com.gr.smartroster.activity.ui.userprofile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;
import com.gr.smartroster.callback.IUserProfileCallbackLister;
import com.gr.smartroster.model.User;
import com.gr.smartroster.util.ConstantUtil;
import com.gr.smartroster.util.SpUtil;

public class UserProfileViewModel extends AndroidViewModel implements IUserProfileCallbackLister {
    private MutableLiveData<String> mText;      //phonenumber
    private IUserProfileCallbackLister mIUserProfileCallbackLister;
    private String mEmail;
    private CollectionReference dbUsers;

    public UserProfileViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mIUserProfileCallbackLister = this;
        dbUsers = FirebaseFirestore.getInstance().collection("users");
        mEmail = (String) SpUtil.get(application.getApplicationContext(), ConstantUtil.EMAIL_SP,"");
    }

    public LiveData<String> getText() {
        loadUserPhoneNumber();
        return mText;
    }

    private void loadUserPhoneNumber() {
        //User documnet in Firestore will use email as id.
        dbUsers.document(mEmail).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    User user = documentSnapshot.toObject(User.class);
                    onPhoneNumberLoadSuccessul(user.getPhoneNumber());
                }
            }
        });
    }

    public void setUserPhoneNumber(String phoneNumber) {
        //set user phone number to db
        dbUsers.document(mEmail).update(ConstantUtil.PHONE_NUMBER_SP, phoneNumber).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mText.setValue("phoneNum");
            }
        });
    }

    public void setPassword(String newPassword) {
        dbUsers.document(mEmail).update(ConstantUtil.PASSWORD_SP, newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mText.setValue("password");
            }
        });
    }


    @Override
    public void onPhoneNumberLoadSuccessul(String phoneNumber) {
        mText.setValue(phoneNumber);
    }
}