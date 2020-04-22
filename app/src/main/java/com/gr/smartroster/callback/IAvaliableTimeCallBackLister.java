package com.gr.smartroster.callback;

import com.gr.smartroster.model.AvaliableTime;
import java.util.List;

public interface IAvaliableTimeCallBackLister {
    void OnAvaliableTimeLoadSuccessful(List<AvaliableTime> avaliableTimeList);

    void OnAvaliableTimeLoadFailed(String message);
}