package com.gr.smartroster.activity.fragment.setting;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.gr.smartroster.util.ConstantUtil;

public class SettingViewModel extends AndroidViewModel {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MutableLiveData<String> mLiveData;

    public SettingViewModel(@NonNull Application application) {
        super(application);
        mLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<String> getLiveData() {
        return mLiveData;
    }

    public void deleteAccount(String email) {
        WriteBatch batch = db.batch();
        //delete from users
        DocumentReference user = db.collection("users").document(email);
        batch.delete(user);
        //delete from staffs
        db.collection("staffs").whereEqualTo(ConstantUtil.EMAIL_SP, email).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                    batch.delete(queryDocumentSnapshot.getReference());
                }
            }
        });

        //delete from roster
        db.collection("roster").whereEqualTo(ConstantUtil.EMAIL_SP,email).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                    batch.delete(queryDocumentSnapshot.getReference());
                }
            }
        });

        //delete from avaliableTime
        db.collection("avaliableTime").whereEqualTo(ConstantUtil.EMAIL_SP,email).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                    batch.delete(queryDocumentSnapshot.getReference());
                }
            }
        });

        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mLiveData.setValue("deleted");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mLiveData.setValue("error");
            }
        });
    }
}
