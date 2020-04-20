package com.gr.smartroster.activity.ui.avaliabletime;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AvaliabletTimeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AvaliabletTimeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}