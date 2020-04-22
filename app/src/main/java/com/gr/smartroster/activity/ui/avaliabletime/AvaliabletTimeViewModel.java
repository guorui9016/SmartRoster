package com.gr.smartroster.activity.ui.avaliabletime;

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
import com.gr.smartroster.callback.IAvaliableTimeCallBackLister;
import com.gr.smartroster.model.AvaliableTime;
import com.gr.smartroster.util.ConstantUtil;
import com.gr.smartroster.util.SpUtil;
import java.util.List;

public class AvaliabletTimeViewModel extends AndroidViewModel implements IAvaliableTimeCallBackLister {

    private MutableLiveData<List<AvaliableTime>> avaliableTimeLiveDataList;
    private MutableLiveData<String> errorMessage;
    private IAvaliableTimeCallBackLister avaliableTimeCallBackLister;

    public AvaliabletTimeViewModel(@NonNull Application application) {
        super(application);
        avaliableTimeCallBackLister = this;
    }

    public MutableLiveData<List<AvaliableTime>> getAvaliableTimeLiveDataList() {
        //check data first
        if (avaliableTimeLiveDataList == null) {
            avaliableTimeLiveDataList = new MutableLiveData<>();
            errorMessage = new MutableLiveData<>();
            getAvaliableTime();
        }
        return avaliableTimeLiveDataList;
    }

    private void getAvaliableTime() {
        Log.i("Ray", "getAvaliableTime: Start load avaliable time data from db");
        String email = (String) SpUtil.get(getApplication().getApplicationContext(), ConstantUtil.EMAIL_SP, "");
        String groupName = (String) SpUtil.get(getApplication().getApplicationContext(), ConstantUtil.GROUPNAME_SP,"");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("avaliableTime")
                .whereEqualTo(ConstantUtil.EMAIL_SP, email)
                .whereEqualTo(ConstantUtil.GROUPNAME_SP, groupName)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<AvaliableTime> tempList = task.getResult().toObjects(AvaliableTime.class);
                    if (!tempList.isEmpty()) {
                        avaliableTimeCallBackLister.OnAvaliableTimeLoadSuccessful(tempList);
                    } else {
                        avaliableTimeCallBackLister.OnAvaliableTimeLoadFailed("No Avaliable Time");
                    }
                } else {
                    avaliableTimeCallBackLister.OnAvaliableTimeLoadFailed("Load data error");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                avaliableTimeCallBackLister.OnAvaliableTimeLoadFailed(e.toString());
            }
        });
    }

    @Override
    public void OnAvaliableTimeLoadSuccessful(List<AvaliableTime> avaliableTimeList) {
        avaliableTimeLiveDataList.setValue(avaliableTimeList);
    }

    @Override
    public void OnAvaliableTimeLoadFailed(String message) {
        avaliableTimeCallBackLister.OnAvaliableTimeLoadFailed(message);
    }
}