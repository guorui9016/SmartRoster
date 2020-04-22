package com.gr.smartroster.activity.ui.home;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.gr.smartroster.callback.IRosterCallbackLister;
import com.gr.smartroster.model.Roster;
import com.gr.smartroster.util.ConstantUtil;
import com.gr.smartroster.util.SpUtil;

import java.util.List;

public class HomeViewModel extends AndroidViewModel implements IRosterCallbackLister {
    private MutableLiveData<List<Roster>> rosterLiveDataList;
    private MutableLiveData<String> errorMessage;
    private IRosterCallbackLister rosterCallbackLister;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        rosterCallbackLister = this;
    }

    public MutableLiveData<List<Roster>> getRosterLiveDataList() {
        if (rosterLiveDataList == null) {
            rosterLiveDataList = new MutableLiveData<>();
            errorMessage = new MutableLiveData<>();
            getRoster();
        }
        return rosterLiveDataList;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    private void getRoster() {
        Log.i("Ray - ", "getRoster: Start connect db to get data");

        String email = (String) SpUtil.get(getApplication().getApplicationContext(), ConstantUtil.EMAIL_SP,"");
        String groupName = (String) SpUtil.get(getApplication().getApplicationContext(),ConstantUtil.GROUPNAME_SP,"");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("roster")
                .whereEqualTo(ConstantUtil.EMAIL_SP, email)
                .whereEqualTo(ConstantUtil.GROUPNAME_SP, groupName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Roster> tempList = task.getResult().toObjects(Roster.class);
                            if (!tempList.isEmpty()) {
                                int i = Log.i("Ray - ", "onComplete: get the roster list from db");
                                rosterCallbackLister.onRosterLoadSuccessful(tempList);
                            } else {
                                rosterCallbackLister.onRosterLoadFailed("You have no roster!");
                            }
                        } else {
                            rosterCallbackLister.onRosterLoadFailed("Load data error");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                rosterCallbackLister.onRosterLoadFailed(e.toString());
            }
        });
    }

    @Override
    public void onRosterLoadSuccessful(List<Roster> rosterList) {
        rosterLiveDataList.setValue(rosterList);
    }

    @Override
    public void onRosterLoadFailed(String message) {
        errorMessage.setValue(message);
    }
}