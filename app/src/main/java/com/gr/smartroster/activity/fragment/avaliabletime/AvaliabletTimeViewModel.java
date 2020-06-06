package com.gr.smartroster.activity.fragment.avaliabletime;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;
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
import com.gr.smartroster.callback.IFireStoreCallBackLister;
import com.gr.smartroster.model.AvaliableTime;
import com.gr.smartroster.util.ConstantUtil;
import com.gr.smartroster.util.SpUtil;

import java.util.Collections;
import java.util.List;

public class AvaliabletTimeViewModel extends AndroidViewModel implements IFireStoreCallBackLister {

    private MutableLiveData<List<AvaliableTime>> avaliableTimeLiveDataList;
    private MutableLiveData<String> errorMessage;
    private IFireStoreCallBackLister mIFireStoreCallBackLister;
    private List<AvaliableTime> mAvaliableTimeList = null;
    String email, groupName, company;
    CollectionReference mCollectionRef = FirebaseFirestore.getInstance().collection("avaliableTime");


    public AvaliabletTimeViewModel(@NonNull Application application) {
        super(application);
        mIFireStoreCallBackLister = this;
        email = (String) SpUtil.get(getApplication().getApplicationContext(), ConstantUtil.EMAIL, "");
        groupName = (String) SpUtil.get(getApplication().getApplicationContext(), ConstantUtil.GROUP_NAME,"");
        company = (String) SpUtil.get(getApplication().getApplicationContext(), ConstantUtil.COMPANY,"");
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

    public AvaliableTime getAvaliableTime(int position) {
        return mAvaliableTimeList.get(position);
    }

    public void insertAvaliableTime(AvaliableTime avaliableTime)  {
        Log.i("Ray", "insertAvaliableTime: Start intsert a new time");
        //insert data to db
        avaliableTime.setEmail(email);
        avaliableTime.setCompany(company);
        avaliableTime.setGroupName(groupName);
        DocumentReference documentReference = mCollectionRef.document();
        avaliableTime.setDocID(documentReference.getId());
        documentReference.set(avaliableTime).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mAvaliableTimeList.add(avaliableTime);
                Collections.sort(mAvaliableTimeList);
                mIFireStoreCallBackLister.OnSuccessfulLister(mAvaliableTimeList);
                Log.i("Ray", "insertAvaliableTime: new time has been add");
            }
        });
    }

    public void updateAvaliableTime(AvaliableTime avaliableTime) {

        mCollectionRef.document(avaliableTime.getDocID()).set(avaliableTime).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                for (int i = 0; i < mAvaliableTimeList.size() ; i++) {
                    if (avaliableTime.getDocID().equals(mAvaliableTimeList.get(i).getDocID())) {
                        mAvaliableTimeList.set(i, avaliableTime);
                        Log.i("Ray - ", "onSuccess: The time has been update");
                        break;
                    }
                }
                Collections.sort(mAvaliableTimeList);
                mIFireStoreCallBackLister.OnSuccessfulLister(mAvaliableTimeList);
            }
        });
    }

    public void deleteAvaliableTime(int position) {
        Log.i("Ray - ", "deleteAvaliableTime: Start delete a time");
        AvaliableTime avaliableTime = mAvaliableTimeList.get(position);
        mCollectionRef.document(avaliableTime.getDocID()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mAvaliableTimeList.remove(position);
                mIFireStoreCallBackLister.OnSuccessfulLister(mAvaliableTimeList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplication(), "Error occured when delete the time.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAvaliableTime() {
        Log.i("Ray", "getAvaliableTime: Start load avaliable time data from db");
        //get all data from db
                mCollectionRef
                .whereEqualTo(ConstantUtil.EMAIL, email)
                .whereEqualTo(ConstantUtil.GROUP_NAME, groupName)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<AvaliableTime> tempList = task.getResult().toObjects(AvaliableTime.class);
                    Log.i("Ray - ", "onComplete: the size of avalibaletime list is: " + tempList.size());
                    if (tempList.size()>0) {
                        Collections.sort(tempList);
                        mIFireStoreCallBackLister.OnSuccessfulLister(mAvaliableTimeList = tempList);
                    }
                } else {
                    mIFireStoreCallBackLister.OnFailedLister("Load data error");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mIFireStoreCallBackLister.OnFailedLister(e.toString());
            }
        });
    }

    @Override
    public void OnSuccessfulLister(List itemList) {
        avaliableTimeLiveDataList.setValue(itemList);
    }

    @Override
    public void OnFailedLister(String errorMessage) {
        mIFireStoreCallBackLister.OnFailedLister(errorMessage);
    }
}