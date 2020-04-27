package com.gr.smartroster.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.gr.smartroster.callback.ISearchListCallBackLister;
import com.gr.smartroster.model.Group;
import java.util.ArrayList;
import java.util.List;

public class JoinGroupViewModel extends AndroidViewModel implements ISearchListCallBackLister {
    private List<Group> mGrouplist = new ArrayList<>();
    private MutableLiveData<List<Group>> mutableLiveData;
    private ISearchListCallBackLister mSearchCallBackLister;
    private CollectionReference groupRef;

    public JoinGroupViewModel(@NonNull Application application) {
        super(application);
        mSearchCallBackLister = this;
    }

    public MutableLiveData<List<Group>> getMutableLiveData() {
        mutableLiveData = new MutableLiveData<>();
        return mutableLiveData;
    }

    public void searchGroup(String sKeyWords) {
        groupRef = FirebaseFirestore.getInstance().collection("groups");
        if (mGrouplist.isEmpty()) {
            searchGroupName(sKeyWords);
            searchCompanyName(sKeyWords);
        } else {
            mGrouplist.clear();
            searchGroupName(sKeyWords);
            searchCompanyName(sKeyWords);
        }
    }

    private void searchCompanyName(String searchKeyWords) {
        groupRef.whereEqualTo("company_Lower", searchKeyWords).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Group> groupList = task.getResult().toObjects(Group.class);
                    mSearchCallBackLister.onSearchSuccessfulLister(groupList);
                }
            }
        });
    }

    private void searchGroupName(String searchKeyWords) {
        groupRef.whereEqualTo("groupName_Lower", searchKeyWords).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Group> groupList = task.getResult().toObjects(Group.class);
                    mSearchCallBackLister.onSearchSuccessfulLister(groupList);
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
