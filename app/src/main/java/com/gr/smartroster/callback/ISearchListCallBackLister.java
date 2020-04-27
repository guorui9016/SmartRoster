package com.gr.smartroster.callback;

import com.gr.smartroster.model.Group;

import java.util.List;

public interface ISearchListCallBackLister {
    void onSearchSuccessfulLister(List<Group> groupList);
    void onFailedLister(String message);
}
