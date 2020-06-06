package com.gr.smartroster.callback;

import java.util.List;

public interface IFireStoreCallBackLister {
    void OnSuccessfulLister(List itemList);
    void OnFailedLister(String errorMessage);
}
