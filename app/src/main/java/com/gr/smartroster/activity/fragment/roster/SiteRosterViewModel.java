package com.gr.smartroster.activity.fragment.roster;

import android.app.Application;
import android.text.format.Time;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.gr.smartroster.callback.IRosterCallbackLister;
import com.gr.smartroster.model.Roster;
import com.gr.smartroster.util.ConstantUtil;
import com.gr.smartroster.util.SpUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SiteRosterViewModel extends AndroidViewModel implements IRosterCallbackLister {
    private MediatorLiveData mLiveData;
    private IRosterCallbackLister mRosterCallbackLister;
    private FirebaseFirestore mDb;
    private Timestamp mDate;
    private final String mGroupName;

    public void setDate(Timestamp date) {
        mDate = date;
        getRoster();
    }

    public SiteRosterViewModel(@NonNull Application application) {
        super(application);
        mRosterCallbackLister = this;
        mDb = FirebaseFirestore.getInstance();
        //get groupname from SharedPreferences
        mGroupName = (String) SpUtil.get(getApplication(), ConstantUtil.GROUP_NAME, "");
    }

    public MediatorLiveData<List<Roster>> getLiveData() {
        if (mLiveData==null) {
            mLiveData = new MediatorLiveData();
            getRoster();
        }
        return mLiveData;
    }

    private void getRoster() {
        //check the date, use the current date if date is null.
        if (mDate == null) {
            Log.d("Ray-SiteRosterViewModel", "getRoster: set current date." );
            Calendar currentCal = Calendar.getInstance();
            currentCal.setTimeInMillis(System.currentTimeMillis());
            Calendar currentDate = new GregorianCalendar();
            currentDate.set(currentCal.get(Calendar.YEAR),
                    currentCal.get(Calendar.MONTH-1),
                    currentCal.get(Calendar.DAY_OF_MONTH-1),0,0,0);
            Log.d("Ray-SiteRosterViewModel", "getRoster: The date is:" + currentCal.get(Calendar.YEAR) + (currentCal.get(Calendar.MONTH)+1)+ currentCal.get(Calendar.DAY_OF_MONTH));
            mDate = new Timestamp(currentDate.getTime());
        }

        //query roster by date and group name.
        mDb.collection("roster")
                .whereGreaterThan(ConstantUtil.DATE, mDate)
                .whereEqualTo(ConstantUtil.GROUP_NAME, mGroupName)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Roster> rosters = queryDocumentSnapshots.toObjects(Roster.class);
                Log.d("Ray-SiteRosterViewModel" , "onSuccess: the roster list size is: " + rosters.size()  );
                mRosterCallbackLister.onRosterLoadSuccessful(rosters);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Ray", "onFailure: " + e);
            }
        });
    }

    @Override
    public void onRosterLoadSuccessful(List<Roster> rosterList) {
        mLiveData.setValue(rosterList);
    }

    @Override
    public void onRosterLoadFailed(String message) {
    }
}
