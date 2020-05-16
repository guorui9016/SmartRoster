package com.gr.smartroster.activity.fragment.setting;

import android.app.Application;
import android.util.Log;

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

import java.util.List;

public class SettingViewModel extends AndroidViewModel {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MutableLiveData<String> mLiveData;

    List<DocumentReference> mDocumentReferences;

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
                //delete from roster
                db.collection("roster").whereEqualTo(ConstantUtil.EMAIL_SP, email).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            batch.delete(queryDocumentSnapshot.getReference());
                        }
                        //delete from avaliableTime
                        db.collection("avaliableTime").whereEqualTo(ConstantUtil.EMAIL_SP, email).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                    batch.delete(queryDocumentSnapshot.getReference());
                                }
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
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mLiveData.setValue("error");
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mLiveData.setValue("error");
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mLiveData.setValue("error");
            }
        });
    }

    public void exitGroup(String groupName, String email) {
        WriteBatch batch = db.batch();
        Log.d("Ray - ", "exitGroup: Start query");
        //query from staffs collection and add document to batch
        db.collection("staffs")
                .whereEqualTo(ConstantUtil.GROUPNAME_SP, groupName)
                .whereEqualTo(ConstantUtil.EMAIL_SP, email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            batch.delete(queryDocumentSnapshot.getReference());
                        }
                        Log.d("Ray - ", "onSuccess: add document from staffs collection");
                        //query from roster
                        db.collection("roster")
                                .whereEqualTo(ConstantUtil.GROUPNAME_SP, groupName)
                                .whereEqualTo(ConstantUtil.EMAIL_SP, email)
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                            batch.delete(queryDocumentSnapshot.getReference());
                                        }
                                        Log.d("Ray - ", "onSuccess: add doc from roster collection");
                                        //query from avaliable time collection
                                        db.collection("avaliableTime")
                                                .whereEqualTo(ConstantUtil.GROUPNAME_SP, groupName)
                                                .whereEqualTo(ConstantUtil.EMAIL_SP, email)
                                                .get()
                                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                                            batch.delete(queryDocumentSnapshot.getReference());
                                                        }
                                                        //delete all document
                                                        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                mLiveData.setValue("exited");
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                mLiveData.setValue("error");
                                                            }
                                                        });
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                mLiveData.setValue("error");
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mLiveData.setValue("error");
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mLiveData.setValue("error");
            }
        });
    }
}