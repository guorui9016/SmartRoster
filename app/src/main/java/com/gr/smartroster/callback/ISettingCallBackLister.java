package com.gr.smartroster.callback;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public interface ISettingCallBackLister {
    void addReference(DocumentReference reference);
}
