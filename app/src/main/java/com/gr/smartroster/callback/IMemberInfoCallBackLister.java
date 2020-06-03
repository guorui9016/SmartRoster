package com.gr.smartroster.callback;

import com.gr.smartroster.model.Staff;
import java.util.List;

public interface IMemberInfoCallBackLister {
    void OnAvaliableTimeSuccessful(List<Staff> staffList);
    void OnAvaliableTimeSuccessful(String string);
    void OnAvaliableTimeLoadFailed(String message);
}
