package com.gr.smartroster.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.gr.smartroster.callback.IFireStoreCallBackLister;
import com.gr.smartroster.model.Staff;
import com.gr.smartroster.util.ConstantUtil;
import com.gr.smartroster.util.SpUtil;

import java.util.List;

public class ManageStaffViewModel extends AndroidViewModel implements IFireStoreCallBackLister {
    MutableLiveData<List<Staff>> mMutableLiveData;
    IFireStoreCallBackLister mFireStoreCallBackLister;

    public ManageStaffViewModel(@NonNull Application application) {
        super(application);
        mMutableLiveData = new MutableLiveData();
        mFireStoreCallBackLister = this;
    }

    public MutableLiveData<List<Staff>> getMutableLiveData() {
        getStaffs();
        return mMutableLiveData;
    }

    private void getStaffs() {
        String groupName = (String) SpUtil.get(getApplication().getApplicationContext(), ConstantUtil.GROUP_NAME, "");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("staffs").whereEqualTo(ConstantUtil.GROUP_NAME, groupName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Staff> staffList = queryDocumentSnapshots.toObjects(Staff.class);
                mFireStoreCallBackLister.OnSuccessfulLister(staffList);
            }
        });
    }

    public Staff getStaff(int position) {
        return mMutableLiveData.getValue().get(position);
    }

    @Override
    public void OnSuccessfulLister(List itemList) {
        mMutableLiveData.setValue(itemList);
    }

    @Override
    public void OnFailedLister(String errorMessage) {

    }
}
