package com.gr.smartroster.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.gr.smartroster.callback.ISearchListCallBackLister;
import com.gr.smartroster.model.Group;
import com.gr.smartroster.model.Staff;
import com.gr.smartroster.util.ConstantUtil;
import com.gr.smartroster.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

public class JoinGroupViewModel extends AndroidViewModel implements ISearchListCallBackLister {
    private List<Group> mGrouplist = new ArrayList<>();
    private MutableLiveData<List<Group>> mutableLiveData;
    private ISearchListCallBackLister mSearchCallBackLister;
    private FirebaseFirestore mFirestore;
    private CollectionReference mGroupRef;

    public JoinGroupViewModel(@NonNull Application application) {
        super(application);
        mSearchCallBackLister = this;
    }

    public MutableLiveData<List<Group>> getMutableLiveData() {
        mutableLiveData = new MutableLiveData<>();
        return mutableLiveData;
    }

    public void searchGroup(String sKeyWords) {
        mFirestore = FirebaseFirestore.getInstance();

        if (mGrouplist.isEmpty()) {
            searchGroupName(sKeyWords);
            Log.i("Ray - ", "searchGroup: if list is empty");
//            searchCompanyName(sKeyWords);
        } else {
            mGrouplist.clear();
            searchGroupName(sKeyWords);
            Log.i("Ray - ", "searchGroup: if list is not empty");
//            searchCompanyName(sKeyWords);
        }
    }

    private void searchCompanyName(String searchKeyWords) {
        mFirestore.collection("groups")
                .whereEqualTo("company_Lower", searchKeyWords)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Group> groupList = task.getResult().toObjects(Group.class);
                    Log.i("Ray - ", "onComplete: The number of company has been found: " + groupList.size());
                    mSearchCallBackLister.onSearchSuccessfulLister(groupList);
                }
            }
        });
    }

    private void searchGroupName(String searchKeyWords) {
        mFirestore.collection("groups")
                .whereEqualTo("groupName_Lower", searchKeyWords)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Group> groupList = task.getResult().toObjects(Group.class);
                    Log.i("Ray - ", "onComplete: The number of group has been found: " + groupList.size());
                    mSearchCallBackLister.onSearchSuccessfulLister(groupList);
                }
            }
        });
    }

    public void addUser(int position) {

        Group group = mGrouplist.get(position);
        //save user to staff list
        String email = (String) SpUtil.get(getApplication(), ConstantUtil.EMAIL_SP, "");
        //check the database first. Only the user not exist in the group, it can add in.
        mFirestore.collection("staffs")
                .whereEqualTo("email", email)
                .whereEqualTo("groupName", group).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().isEmpty()) {
                        Staff staff = new Staff(email, group.getGroupName(), null, group.getCompany(), "0");
                        mFirestore.collection("staffs").add(staff).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                //save information to SP.
                                Log.i("Ray - ", "saveDataToSp: Save user information");
                                SpUtil.set(getApplication(), ConstantUtil.GROUPNAME_SP, staff.getGroupName());
                                SpUtil.set(getApplication(), ConstantUtil.COMPANY_SP, staff.getCompany());
                                SpUtil.set(getApplication(), ConstantUtil.ADMIN_SP, staff.getAdmin());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Ray", "onFailure: Can not add staff to firestore");
                                ;
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onSearchSuccessfulLister(List<Group> groupList) {
        mGrouplist.addAll(groupList);
        mutableLiveData.setValue(mGrouplist);
    }

    @Override
    public void onFailedLister(String message) {
    }
}
