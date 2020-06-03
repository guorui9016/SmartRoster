package com.gr.smartroster.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.gr.smartroster.callback.IMemberInfoCallBackLister;
import com.gr.smartroster.model.Staff;
import com.gr.smartroster.model.User;
import com.gr.smartroster.util.ConstantUtil;
import com.gr.smartroster.util.SpUtil;

import java.util.List;

public class GroupInfoViewModel extends AndroidViewModel implements IMemberInfoCallBackLister {
    private MutableLiveData<List<Staff>> mMutableLiveDataList;
    private MutableLiveData<String> mMutableLiveDateString;
    private IMemberInfoCallBackLister mGroupInfoCallBackLister;
    private FirebaseFirestore db;
    private String groupName;

    public GroupInfoViewModel(@NonNull Application application) {
        super(application);
        mMutableLiveDataList = new MutableLiveData<>();
        mMutableLiveDateString = new MutableLiveData<>();
        db = FirebaseFirestore.getInstance();
        mGroupInfoCallBackLister = this;
        groupName = (String) SpUtil.get(getApplication(), ConstantUtil.GROUP_NAME, "");
    }

    public MutableLiveData<List<Staff>> getmMutableLiveDataList() {
        getGroupMember();
        return mMutableLiveDataList;
    }

    public MutableLiveData<String> getmMutableLiveDateString() {
        getManagerName();
        return mMutableLiveDateString;
    }

    private void getManagerName() {
        db.collection("staffs").whereEqualTo("groupName", groupName).whereEqualTo("admin", true).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Staff> staffList = queryDocumentSnapshots.toObjects(Staff.class);
                if (staffList.size() == 1) {
                    String managerEmail = staffList.get(0).getEmail();
                    db.collection("users").document(managerEmail).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            User user = documentSnapshot.toObject(User.class);
                            mGroupInfoCallBackLister.OnAvaliableTimeSuccessful(user.getFullName());
                        }
                    });
                } else if (staffList.size() > 1) {
                    //display error message
                    mGroupInfoCallBackLister.OnAvaliableTimeSuccessful("Error: More than one manager. Should only one manager");
                } else {
                    mGroupInfoCallBackLister.OnAvaliableTimeSuccessful("Can not find manager.");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void getGroupMember() {
        db.collection("staffs").whereEqualTo("groupName", groupName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.d("Ray-GroupInfoViewModel", "onSuccess: Load data from firebase succseeful");
                List<Staff> staffList = queryDocumentSnapshots.toObjects(Staff.class);
                Log.d("Ray-GroupInfoViewModel", "onSuccess: List size is: " + staffList.size());
                mGroupInfoCallBackLister.OnAvaliableTimeSuccessful(staffList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Ray-GroupInfoViewModel", "onFailure: Error. Load data failed. -- " + e);
            }
        });
    }

    @Override
    public void OnAvaliableTimeSuccessful(List<Staff> staffList) {
        mMutableLiveDataList.setValue(staffList);
    }

    @Override
    public void OnAvaliableTimeSuccessful(String string) {
        mMutableLiveDateString.setValue(string);
    }

    @Override
    public void OnAvaliableTimeLoadFailed(String message) {

    }
}
